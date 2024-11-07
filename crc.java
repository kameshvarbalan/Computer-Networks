import java.util.*;
public class crc {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter last 4 digits of regno: ");
        String input = sc.next();
        String code = "";
        for(int i=0; i<input.length(); i++){
            code += String.format("%4s", Integer.toBinaryString(input.charAt(i)-'0')).
            replace(' ', '0');
        }
        System.out.println("\nCode to be transmitted: " + code);
        String poly = "101";
        System.out.println("\nGenerating Polynomial = x^2+1\nDivisor = " + poly);
        String crc = divide((code+"0".repeat(poly.length() - 1)), poly);
        System.out.println("\nCRC/Remainder = " + crc);
        System.out.println("Transmitted Data: " + (code+crc));
        System.out.print("\nEnter the code at the receiver: ");
        String received = sc.next();
        sc.close();
        if(received.length() != code.length()+crc.length()){
            System.out.println("\nError: Code size mismatch");
            return;
        }
        for(int i=0; i<received.length(); i++){
            if(received.charAt(i)!='0' && received.charAt(i)!='1'){
                System.out.println("\nError: Data should contain only 0 or 1");
                return;
            }
        }
        String rem = divide(received, poly);
        System.out.println("\nRemainder/CRC at receiver = " + rem);
        if(Integer.valueOf(rem)==0){
            System.out.println("Data Transmitted and Received without any error\n");
        }
        else{
            System.out.println("Error found in Transmitted Data at the Receiver\n");
        }
    }

    private static String divide(String dividend, String divisor){
        int len = divisor.length();
        String current = dividend.substring(0, len);
        while(len<dividend.length()){
            if(current.charAt(0)=='1'){
                StringBuilder result = new StringBuilder();
                for(int i=1; i<current.length(); i++){
                    result.append(current.charAt(i)==divisor.charAt(i)? '0':'1');
                }
                current = result.toString() + dividend.charAt(len);
            }
            else{
                current += dividend.charAt(len);
                current = current.substring(1);
            }
            len++;
        }
        if(current.charAt(0)=='1'){
            StringBuilder result = new StringBuilder();
            for(int i=0; i<current.length(); i++){
                result.append(current.charAt(i)==divisor.charAt(i)? '0':'1');
            }
            current = result.toString();
        }
        return current.substring(1);
    }
}
