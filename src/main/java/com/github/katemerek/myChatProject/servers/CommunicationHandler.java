package com.github.katemerek.myChatProject.servers;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class CommunicationHandler implements Runnable {
    private int messageCount = 0;

    private String name = null;
    public BufferedReader clientInputStream = null;
    public PrintWriter clientOutputStream = null;
    private Socket clientSocket;
    private static List<CommunicationHandler> clients = new ArrayList<>();

    private CommunicationHandler(String name, boolean loggedInStatus) {
        this.name = name;
    }

    public CommunicationHandler(Socket clientSocket) {
        this.name = "Client";
        this.clientSocket = clientSocket;

        try {
            this.clientInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.clientOutputStream = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {

        String inputMessageFromClient = "", recipientName = "", messageToReceiver = "";

        while (true) {
            try {
                inputMessageFromClient = clientInputStream.readLine();
                if (inputMessageFromClient == null) {
                    System.err.println("Client disconnected");
                    break;
                }
                while (inputMessageFromClient != null) {
                    // Broadcast message to all clients
                    for (CommunicationHandler aClient : clients) {
                        aClient.clientOutputStream.println(inputMessageFromClient);
                    }
                }
            }catch (IOException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                } finally {
                    try {
                        clientInputStream.close();
                        clientOutputStream.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        System.out.println(name +" has disconnected. Was handled by "+Thread.currentThread().getName() + " --"+ LocalDateTime.now());
    }

    @Override
    public String toString() {
        return name;
    }
}
