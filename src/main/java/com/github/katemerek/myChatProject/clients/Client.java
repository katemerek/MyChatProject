package com.github.katemerek.myChatProject.clients;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client implements Runnable {
    private String name;
    private Socket socket;
    private PrintWriter buffWriter;
    private BufferedReader buffReader;
    private Scanner inputStream;

    public Client(String name, Socket socket) {
        try {
        this.name = name;
        this.socket = socket;
        this.buffWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        inputStream = new Scanner(System.in);

        }catch (IOException e){
            closeAll(socket, buffReader, buffWriter);
        }
    }


    public Thread sendMessage(){
        return new Thread(() -> {
            String line = "";
            while (true) {
                line = inputStream.nextLine();
                if (line.equalsIgnoreCase("bye")) { //closes the chat
                    closeAll(socket, buffReader, buffWriter);
                    Thread.currentThread().interrupt();
                    break;
                }
                buffWriter.println (name+":"+line+"\n");
                buffWriter.flush(); //Prevents buffering of the message. Sends out the message immediately
            }
        });
    }
    public Thread readMessage() {
        return new Thread(() -> {
            String line = "";
            while (true) {

                try {
                    line = buffReader.readLine();
                    if (line == null)
                        break;

                    System.out.println(line.replaceFirst(name, "You"));
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    System.exit(-1);
                    break;
                }
            }
        });
    }

//    public void readMessage(){
//        new Thread( new Runnable() {
//
//            @Override
//            public void run() {
//                String msg = "";
//
//                while(!msg.equals("exit")){
//                    try{
//                        System.out.println(name + " please, print the message:");
//                        msg = inputStream.nextLine();
//                        System.out.println(msg);
//                    } catch (IOException e){
//                        closeAll(socket, buffReader, buffWriter);
//                    }
//
//                }
//
//            }
//
//        }).start();
//    }
    // method to close everything in the socket
    public void closeAll(Socket socket, BufferedReader buffReader, PrintWriter buffWriter){
        try{
            if(buffReader!= null){
                buffReader.close();
            }
            if(buffWriter != null){
                buffWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.getStackTrace();
        }
    }

    @Override
    public void run() {
Thread sendMessage = sendMessage();
        try { //Get the response
            buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
Thread readMessage = readMessage();
        sendMessage.start();
        readMessage.start();
        try {
            sendMessage.join();
            readMessage.join();
            closeAll(socket, buffReader, buffWriter);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {

        String name = null;
        System.out.print("Please, enter your name here: ");
        Scanner in = new Scanner(System.in);

        while (true) {
            name = in.nextLine();
            if (!name.isBlank()) break;
            System.err.println("Name cannot be blank. Please enter your name");
        }
        Socket socket = new Socket("localhost", 8888);
         Client client = new Client(name, socket);
         Thread readMessage = client.readMessage();
         readMessage.start();


    }
}