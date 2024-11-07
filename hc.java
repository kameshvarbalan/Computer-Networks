import java.util.*;
class hc {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter last 4 digits of regno: ");
        String input = sc.next();
        int[] data = new int[input.length()*4];
        int k = input.length()*4-1;
        System.out.print("Data: ");
        for(int i=0; i<input.length(); i++){
            int digit = input.charAt(i)-'0';
            String binary = String.format("%4s", Integer.toBinaryString(digit)).replace
            (' ', '0');
            for(int j=0; j<4; j++){
                System.out.print(binary.charAt(j));
                data[k] = binary.charAt(j)-'0';
                k--;
            }
        }
        int parity = parityBits(data.length);
        int[] ans = new int[parity+data.length];
        System.out.println("\n");
        ans = HammingCode(data, parity);
        System.out.print("\nHamming Code: ");
        for(int bit=ans.length-1; bit>-1; bit--){
            System.out.print(ans[bit]);
        }
        System.out.print("\n\nEnter the code at the receiver: ");
        int[] received = new int[parity+data.length];
        String receive = sc.next();
        sc.close();
        if(receive.length() != received.length){
            System.out.println("\nError: Code size mismatch");
            return;
        }
        for(int i=0; i<received.length; i++){
            received[i] = receive.charAt(receive.length()-i-1)-'0';
            if(received[i]!=0 && received[i]!=1){
                System.out.println("\nError: Data should contain only 0 or 1");
                return;
            }
        }
        int errorPos = receiver(received);
        if(errorPos==0){
            System.out.println("\nData Transmitted and Received without any error\n");
        }
        else{
            System.out.println("\nError found at position " + errorPos + " at the Receiver\n");
        }
    }

    private static int parityBits(int data){
        int bits = 0;
        while(Math.pow(2, bits) < (data+bits+1)){
            bits++;
        }
        return bits;
    }

    private static int[] HammingCode(int[] data, int parity){
        int total = data.length + parity;
        int[] code = new int[total];
        int j = 0, k = 0;
        for(int i=0; i<total; i++){
            if((i+1)==(1<<j)){
                code[i] = -1;
                j++;
            }
            else{
                code[i] = data[k++];
            }
        }
        for(int i=0; i<parity; i++){
            int pos = (1<<i) - 1;
            int p = 0;
            for(int x=pos; x<total; x+=(1<<(i+1))){
                for(int y=x; y<x+(1<<i) && y<total; y++){
                    if(code[y]!=-1 && y!=pos){
                        p ^= code[y];
                    }
                }
            }
            System.out.println("Parity bit at " + (pos+1) + " = " + p);
            code[pos] = p;
        }
        return code;
    }

    private static int receiver(int[] code) {
        int n = code.length;
        int errorPos = 0;
        for(int i=0; i<n; i++){
            if((i+1&i)==0){
                int parity = 0;
                for(int j=i; j<n; j+=(i+1)*2){
                    for(int k=j; k<j+i+1 && k<n; k++){
                        parity ^= code[k];
                    }
                }
                if(parity != 0){
                    errorPos += (i+1);
                }
            }
        }
        return errorPos;
    }
}