package com.github.katemerek.clients.clients;

import com.github.katemerek.clients.controllers.LoginController;

import java.io.*;
import java.net.Socket;


public class Client {

    private Socket socket;
    private PrintWriter buffWriter;
    private BufferedReader buffReader;
    private InputStream inputStream;
    private LoginController loginController;
    private String name = "Liza";

    public Client( Socket socket) {
        try {
            this.socket = socket;
            this.buffWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            inputStream = socket.getInputStream();

        }catch (IOException e){
            closeAll(socket, buffReader, buffWriter);
        }
    }

    // method to send messages using thread
    public void sendMessage(){
        buffWriter.write(name);
        buffWriter.println();
        buffWriter.flush();

        while(socket.isConnected()){
            String messageToSend = inputStream.toString();
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
                        System.out.println(msfFromGroupChat);
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
        Socket socket = new Socket("localhost", 9001);
        Client client = new Client(socket);
        client.readMessage();
        client.sendMessage();


    }
}