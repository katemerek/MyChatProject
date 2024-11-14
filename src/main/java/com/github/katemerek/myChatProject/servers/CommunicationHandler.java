package com.github.katemerek.myChatProject.servers;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class CommunicationHandler implements Runnable {

    private String name;
    private BufferedReader buffReader;
    private PrintWriter buffWriter;
    public Socket socket;
    public static ArrayList<CommunicationHandler> clients = new ArrayList<>();


    public CommunicationHandler(Socket socket) {
        try {
            this.socket = socket;
            this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.buffWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.name = buffReader.readLine();
            clients.add(this);
            broadcastMessage("Hello,  " + name + "! You have connected to chat!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {

        String messageFromClient;

        while(socket.isConnected()){
            try {
                messageFromClient = buffReader.readLine();
                broadcastMessage(messageFromClient);
            } catch(IOException e){
                closeAll(socket, buffReader, buffWriter);
                break;
            }
        }
    }


    public void removeClientHandler(){
        clients.remove(this);
        broadcastMessage("User " + name + " left the chat");
    }


    public void broadcastMessage(String messageToSend){
        for(CommunicationHandler client: clients){
            if(!client.name.equals(name)){
                client.buffWriter.println(messageToSend);
                client.buffWriter.flush();
            }
        }
    }


    public void closeAll(Socket socket, BufferedReader buffReader, PrintWriter buffWriter){

        removeClientHandler();
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
