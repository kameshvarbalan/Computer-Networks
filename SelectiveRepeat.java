import java.util.*;

public class SelectiveRepeat{
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
        boolean[] acknowledged = new boolean[frames];
        int i = 0;
        int window = 7;
        int totalTransmissions = 0;

        while(i<frames){
            System.out.println();
            for(int k=i; k<i+window && k<frames; k++){
                if(!acknowledged[k]){
                    System.out.println("Sending Frame " + k);
                    totalTransmissions++;
                }
            }
            for(int k=i; k<i+window && k<frames; k++){
                if(!acknowledged[k]){
                    boolean received = simulate(0.7);
                    if(received){
                        System.out.println("Acknowledgment for Frame " + k);
                        acknowledged[k] = true;
                    } 
                    else{
                        System.out.println("Timeout - No Acknowledgment for Frame " + k);
                        System.out.println("Retransmitting Frame " + k);
                        totalTransmissions++;
                    }
                }
            }
            while(i<frames && acknowledged[i]){
                i++;
            }
        }
        System.out.println("\nAll frames successfully transmitted");
        System.out.println("Total number of frames which were sent and resent: " + totalTransmissions);
    }
}