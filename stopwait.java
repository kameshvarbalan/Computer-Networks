import java.util.*;
import java.util.concurrent.TimeUnit;

public class stopwait{
    static final int timeout = 3;

    static class Sender implements Runnable{
        private Receiver receiver;
        private int frames;
        Sender(Receiver receiver, int frames){
            this.receiver = receiver;
            this.frames = frames;
        }
        @Override
        public void run(){
            for(int i=0; i<frames; i++){
                try{
                    System.out.println("\nSender: Sending frame " + i);
                    boolean ack = receiver.recieveFrame(i);
                    if(!ack){
                        System.out.println("Sender: No ack for frame " + i);
                        i--;
                    }
                    else{
                        System.out.println("Sender: ACK Received for frame " + i);
                    }
                    TimeUnit.SECONDS.sleep(timeout);
                }
                catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static class Receiver{
        private Random random = new Random();
        private boolean recieveFrame(int frame){
            boolean rec = random.nextDouble()<0.8;
            if(!rec){
                System.out.println("Receiver: Timeout - Didnt receive frame " + frame);
                return false;
            }
            else{
                System.out.println("Reciever: Received frame " + frame);
                System.out.println("Reciver: Sending ACK for frame " + frame);
            }
            boolean ack = random.nextDouble()<0.8;
            return ack;
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of frames to be transmitted: ");
        int frames = sc.nextInt();
        sc.close();
        Receiver receiver = new Receiver();
        Sender sender = new Sender(receiver, frames);
        Thread arq = new Thread(sender);
        arq.start();
        try{
            arq.join();
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}