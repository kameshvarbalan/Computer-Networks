import java.util.Scanner;

class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter registration number: ");
        String regno = sc.next();
        sc.close();
        String message = "I am " + name + ", Reg.no is " + regno;
        OSI obj = new OSI();
        System.out.println("\nThe message to be transmitted and received: " + message);
        obj.Transmitter(message);
        obj.Receiver();
    }
}

class OSI{
    private String application;
    private String presentation;
    private String session;
    private String transport;
    private String network;
    private String datalink;
    private String physical;

    public void Transmitter(String message){
        System.out.println("\nTRANSMITTER");
        application = "AH " + message;
        System.out.println("APPLICATION LAYER: " + application);
        presentation = "PH" + application;
        System.out.println("PRESENTATION LAYER: " + presentation);
        session = "SH" + presentation;
        System.out.println("SESSION LAYER: " + session);
        transport = "TH" + session;
        System.out.println("TRANSPORT LAYER: " + transport);
        network = "NH" + transport;
        System.out.println("NETWORK LAYER: " + network);
        datalink = "DH" + network;
        System.out.println("DATALINK LAYER: " + datalink);
        physical = datalink;
        System.out.println("MESSAGE ENTERED INTO PHYSICAL LAYER AND TRANSMITTED");
    }
    public void Receiver(){
        System.out.println("\nRECEIVER");
        System.out.println("MESSAGE ENTERED INTO PHYSICAL LAYER");
        datalink = physical;
        System.out.println("DATALINK LAYER: " + datalink);
        network = datalink.substring(2);
        System.out.println("NETWORK LAYER: " + network);
        transport = network.substring(2);
        System.out.println("TRANSPORT LAYER: " + transport);
        session = transport.substring(2);
        System.out.println("SESSION LAYER: " + session);
        presentation = session.substring(2);
        System.out.println("PRESENTATION LAYER: " + presentation);
        application = presentation.substring(2);
        System.out.println("APPLICATION LAYER: " + application);
        System.out.println("RECEIVED MESSAGE: " +
        application.substring(3));
    }
}