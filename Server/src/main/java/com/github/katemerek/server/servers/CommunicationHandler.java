package com.github.katemerek.server.servers;

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
    private boolean status;
    public static ArrayList clients;



    public CommunicationHandler(Socket socket) {
        try {
            this.socket = socket;
            this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.buffWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            sendMessage("Hello,  " + name + "! You have connected to chat!");
//            checkOnlineClients();
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
                    status = false;
                    closeAll(socket, buffReader, buffWriter);
                    break;
                }
                sendMessage(messageFromClient);
            } catch(IOException e){
                closeAll(socket, buffReader, buffWriter);
                break;
            }
        }
    }


    public void removeClientHandler(){
        clients.remove(this);
        sendMessage("User " + name + " left the chat");
//        checkOnlineClients();
    }


    public void sendMessage(String msg){
        for (Object client: clients) {
            if (!client.equals(name)) {
                buffWriter.println(msg);
                buffWriter.flush();
            }
        }
    }

//    public void checkOnlineClients() {
//        registrationService.checkTrueLoggingStatus();
//        String clientsOnlineString = "";
//        if (clientsOnline.size() == 1) {
//            clientsOnlineString = ("Sorry, but you're the only one in the chat right now");
//        }else clientsOnlineString = ("Now in chat online:" + clientsOnline);
//        for(CommunicationHandler client: clients) {
//            client.buffWriter.println(clientsOnlineString);
//            client.buffWriter.flush();
//        }
//    }

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
