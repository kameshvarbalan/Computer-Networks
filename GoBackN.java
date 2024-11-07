import java.util.*;

public class GoBackN{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of frames to be transmitted: ");
        int frames = sc.nextInt();
        sc.close();
        transmission(frames);
    }

    static Random random = new Random();
    static boolean simulate(double probability){
        return random.nextDouble() < probability;
    }

    public static void transmission(int frames){
        int i = 0;
        int totalTransmissions = 0;
        int window = 7;

        while(i<frames){
            System.out.println();
            int success = 0;
            for(int k=i; k<i+window && k<frames; k++){
                System.out.println("Sending Frame " + k);
                totalTransmissions++;
            }
            for(int k=i; k<i+window && k<frames; k++){
                boolean received = simulate(0.7);
                if(received){
                    System.out.println("Acknowledgment for Frame " + k);
                    success++;
                } 
                else{
                    System.out.println("Timeout - No Acknowledgement for Frame " + k);
                    System.out.println("Retransmitting Window");
                    break;
                }
            }
            i += success;
        }
        System.out.println("\nAll frames successfully transmitted");
        System.out.println("Total number of frames which were sent and resent: " + totalTransmissions +"\n");
    }
}
