package com.github.katemerek.myChatProject.clients;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    private String name;
    private Socket socket;
    private PrintWriter buffWriter;
    private BufferedReader buffReader;

    public Client(String name, Socket socket) {
        try {
        this.name = name;
        this.socket = socket;
        this.buffWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            closeAll(socket, buffReader, buffWriter);
        }
    }

    // method to send messages using thread
    public void sendMessage(){
        buffWriter.write(name);
        buffWriter.println();
        buffWriter.flush();

        Scanner sc = new Scanner(System.in);

        while(socket.isConnected()){
            String messageToSend = sc.nextLine();
            buffWriter.write(name + ": " + messageToSend);
            buffWriter.println();
            buffWriter.flush();

        }
    }
    // method to read messages using thread
    public void readMessage(){
        new Thread( new Runnable() {

            @Override
            public void run() {
                String msfFromGroupChat;

                while(socket.isConnected()){
                    try{
                        msfFromGroupChat = buffReader.readLine();
//                        if(msfFromGroupChat.equals(name + ": bye")){ ДОДЕЛАТЬ, как выйти из чата???
//                            closeAll(socket, buffReader, buffWriter);
//                            Thread.currentThread().interrupt();
//                            break;
//                        }
                        System.out.println( msfFromGroupChat);
                    } catch (IOException e){
                        closeAll(socket, buffReader, buffWriter);
                    }

                }

            }

        }).start();
    }
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
         client.readMessage();
         client.sendMessage();


    }
}