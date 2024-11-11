import java.util.*;

public class stopwait{
    public static void main(String[] args) throws InterruptedException{
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of frames: ");
        int frames = sc.nextInt();
        sc.close();
        Sender sender = new Sender(frames);
        while(sender.getCurrentFrame()<frames){
            sender.sendFrame();
            Thread.sleep(4000);
        }     
        System.out.println("\nAll frames successfully sent and acknowledged\n");
    }

    static final int TIMEOUT = 3000;
    static Random random = new Random();
    static boolean simulateLoss(double probability){
        return random.nextDouble() < probability;
    }

    static class Sender{
        Timer timer;
        int frame = 0, totalFrames;
        boolean ackReceived = false;

        Sender(int totalFrames){
            this.totalFrames = totalFrames;
        }

        void sendFrame(){
            if(frame>=totalFrames){
                return;
            }
            System.out.println("\nSender: Sending frame " + frame);
            if(simulateLoss(0.4)){
                System.out.println("Sender: Frame " + frame + " lost");
                return;
            }
            Receiver receiver = new Receiver();
            receiver.receiveFrame(frame, this);
            startTimer();
        }

        void receiveAck(int frameNo){
            if(frameNo==frame){
                ackReceived = true;
                System.out.println("Sender: Acknowledgement received for frame " + frame);
                stopTimer();
                frame++;
            }
        }

        void startTimer(){
            ackReceived = false;
            timer = new Timer();
            timer.schedule(new TimerTask(){
                @Override
                public void run(){
                    if(!ackReceived && frame<totalFrames){
                    System.out.println("Sender: Timeout - No Acknowledgement received for Frame " + frame);
                        sendFrame();
                    }
                }
            }, TIMEOUT);
        }
        void stopTimer(){
            if (timer != null){
                timer.cancel();
            }
        }
        int getCurrentFrame(){
            return frame;
        }
    }

    static class Receiver{
        void receiveFrame(int frameNo, Sender sender){
            System.out.println("Receiver: Frame " + frameNo + " received");
            if(simulateLoss(0.4)){
                System.out.println("Receiver: Acknowledgement lost");
                return;
            }
            sendAck(frameNo, sender);
        }

        void sendAck(int frameNo, Sender sender){
            System.out.println("Receiver: Sending Acknowledgement for frame " + frameNo);
            sender.receiveAck(frameNo);
        }
    } }