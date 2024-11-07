import java.util.*;
class CheckSum{
    public static int findCheckSum(String data){
        int checksum = 0;
        for (int i=0; i<data.length(); i++){
            int val = Character.digit(data.charAt(i), 16);
            checksum += val;
        }
        while (checksum > 0xF){
            checksum = (checksum & 0x0F) + (checksum >> 4);
        }
        checksum = ~checksum & 0xF;
        return checksum;
    }
    public static boolean check(String data, int checksum){
        int sum = 0;
        for (int i=0; i<data.length(); i++){
            int val = Character.digit(data.charAt(i), 16);
            sum += val;
        }
        checksum += sum;
        while (checksum > 0xF){
            checksum = (checksum & 0x0F) + (checksum >> 4);
        }
        checksum = ~checksum & 0xF;
        System.out.printf("Checksum at receiver: 0x%X / ", checksum);
        System.out.printf("%s \n", String.format("%4s", Integer.toBinaryString(checksum)
        ).replace(' ', '0'));
        return (checksum==0);
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter regno: ");
        String input = sc.next();
        int checksum = findCheckSum(input);
        for(int i=0; i<input.length(); i++){
            System.out.print(input.charAt(i) + " - ");
            System.out.printf("%s \n", String.format("%4s", Integer.toBinaryString
            (Character.digit(input.charAt(i), 16))).replace(' ', '0'));
        }
        System.out.println(input + " given to transmitter to calculate checksum and transmit");
        System.out.printf("Checksum from transmitter: 0x%X / ", checksum);
        System.out.printf("%s \n", String.format("%4s", Integer.toBinaryString(
            checksum)).replace(' ', '0'));
        System.out.print("\nEnter checksum to the receiver to check transmitted data (binary value): ");
        String binary = sc.next();
        int cs = Integer.parseInt(binary, 2);
        sc.close();
        if (check(input, cs)){
            System.out.println("\nData transferred without error\n");
        }
        else{
            System.out.println("\nError is present in the data\n");
        }
    }
}
