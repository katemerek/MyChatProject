package com.github.katemerek.myChatProject.clients;

import com.github.katemerek.myChatProject.controllers.LoginController;
import com.github.katemerek.myChatProject.message.Message;
import com.github.katemerek.myChatProject.models.Person;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    private Socket socket;
    private PrintWriter buffWriter;
    private BufferedReader buffReader;
    private Person person;
    private Message message;
    private LoginController loginController;

    public Client(String name, Socket socket) {
        try {
        this.socket = socket;
        this.buffWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            closeAll(socket, buffReader, buffWriter);
        }
    }

    // method to send messages using thread
    public void sendMessage(){
        buffWriter.write(loginController.username());
        buffWriter.println();
        buffWriter.flush();

        while(socket.isConnected()){
            String messageToSend = message.getMsg();
            buffWriter.write(person.getName() + ": " + messageToSend);
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

}