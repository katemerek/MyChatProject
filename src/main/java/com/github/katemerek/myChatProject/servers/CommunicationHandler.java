package com.github.katemerek.myChatProject.servers;

import lombok.Data;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

@Data
public class CommunicationHandler implements Runnable {

    private String name;
    private BufferedReader buffReader;
    private PrintWriter buffWriter;
    public Socket socket;
    private boolean loggingStatus;
    public static ArrayList<CommunicationHandler> clients = new ArrayList<>();



    public CommunicationHandler(Socket socket) {
        try {
            this.socket = socket;
            this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.buffWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.name = buffReader.readLine();
            this.loggingStatus = true;
            clients.add(this);

            broadcastMessage("Hello,  " + name + "! You have connected to chat!");
            checkOnlineClients();
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
                if (messageFromClient == null){
                    loggingStatus = false;
                    closeAll(socket, buffReader, buffWriter);
                    break;
                }
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
        checkOnlineClients();
    }


    public void broadcastMessage(String messageToSend){
        for(CommunicationHandler client: clients){
            if(!client.name.equals(name)){
                client.buffWriter.println(messageToSend);
                client.buffWriter.flush();
            }
        }
    }

    public void checkOnlineClients() {
        ArrayList<String> clientsOnline = new ArrayList<>();
        String clientsOnlineString = "";
        for (CommunicationHandler client : clients) {
            if (client.loggingStatus == true) {
                clientsOnline.add(client.name);
            }
        }
        if (clientsOnline.size() == 1) {
            clientsOnlineString = ("Sorry, but you're the only one in the chat right now");
        }else clientsOnlineString = ("Now in chat online:" + clientsOnline);
        for(CommunicationHandler client: clients) {
            client.buffWriter.println(clientsOnlineString);
            client.buffWriter.flush();
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
