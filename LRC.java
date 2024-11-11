import java.util.*;
class LRC{
    public static int calcLRC(String data){
        int lrc = 0;
        for (int i=0; i<data.length(); i++){
            lrc ^= Character.digit(data.charAt(i), 16);
        }
        return lrc;
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter regno: ");
        String input = sc.next();
        sc.close();
        int lrc = calcLRC(input);
        for(int i=0; i<input.length(); i++){
            System.out.print(input.charAt(i) + " - ");
            System.out.printf("%s \n", String.format("%4s", Integer.toBinaryString
            (Character.digit(input.charAt(i), 16))).replace(' ', '0'));
        }
        System.out.printf("The calculated LRC for " + input + ": %s\n", String.format("%4s", 
        Integer.toBinaryString(lrc)).replace(' ', '0'));
        System.out.print("Data transmitted: ");
        for(int i=0; i<input.length(); i++){
            System.out.printf("%s ", String.format("%4s", Integer.toBinaryString(
                Character.digit(input.charAt(i), 16))).replace(' ', '0'));
        }
        System.out.printf("%s\n\n", String.format("%4s", Integer.toBinaryString(lrc)
        ).replace(' ', '0'));
    }
}
