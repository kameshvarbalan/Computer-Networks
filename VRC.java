import java.util.Scanner;
class VRC{
    public static int calcVRC(char data){
        int bits = Character.digit(data, 16);
        int vrc = 0;
        while(bits > 0){
            vrc += (bits&1);
            bits >>= 1;
        }
        return (vrc%2==0)? 1:0;
    }
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter regno: ");
        String input = sc.next();
        sc.close();
        int[] vrc = new int[input.length()];
        for(int i=0; i<input.length(); i++){
            System.out.print(input.charAt(i) + " - ");
            System.out.printf("%s - ", String.format("%4s", Integer.toBinaryString(
                Character.digit(input.charAt(i), 16))).replace(' ', '0'));
            vrc[i] = calcVRC(input.charAt(i));
            System.out.println("VRC=" + vrc[i]);
        }
        System.out.print("Data transmitted:");
        for(int i=0; i<input.length(); i++){
            System.out.printf(" %s", String.format("%4s", Integer.toBinaryString(
                Character.digit(input.charAt(i), 16))).replace(' ', '0'));
            System.out.print(vrc[i]);
        } System.out.println("\n");
    }
}
