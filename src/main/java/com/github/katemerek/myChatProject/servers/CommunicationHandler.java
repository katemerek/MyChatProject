package com.github.katemerek.myChatProject.servers;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class CommunicationHandler implements Runnable {

    private String name;
    private BufferedReader clientInputStream;
    private BufferedWriter clientOutputStream;
    public Socket socket;
    public static ArrayList<CommunicationHandler> clients = new ArrayList<>();


    public CommunicationHandler(Socket socket) {
        try {
            this.socket = socket;
            this.clientInputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientOutputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.name = clientInputStream.readLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {

        String messageFromClient;

        while(socket.isConnected()){
            try {
                messageFromClient = clientInputStream.readLine();
                broadcastMessage(messageFromClient);
            } catch(IOException e){
                closeAll(socket, clientInputStream,  clientOutputStream);
                break;
            }
        }
    }


    public void removeClientHandler(){
        clients.remove(this);
        broadcastMessage("User " + name + " left the chat");
    }


    public void broadcastMessage(String messageToSend){
        for(CommunicationHandler communicationHandler: clients){
            try{
                if(!communicationHandler.name.equals(name)){
                    communicationHandler.clientOutputStream.newLine();
                    communicationHandler.clientOutputStream.flush();
                }
            } catch(IOException e){
                closeAll(socket,clientInputStream, clientOutputStream);

            }
        }
    }


    public void closeAll(Socket socket, BufferedReader clientInputStream, BufferedWriter clientOutputStream){

        removeClientHandler();
        try{
            if(clientInputStream!= null){
                clientInputStream.close();
            }
            if(clientOutputStream != null){
                clientOutputStream.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.getStackTrace();
        }
    }
}
