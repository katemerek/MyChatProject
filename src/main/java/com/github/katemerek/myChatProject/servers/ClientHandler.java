package com.github.katemerek.myChatProject.servers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static com.github.katemerek.myChatProject.servers.Server.broadcast;
import static com.github.katemerek.myChatProject.servers.Server.clients;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String Username; // Use Username consistently

    // Constructor
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            // Create input and output streams for communication
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Run method to handle client communication
    @Override
    public void run() {
        try {
            // Get the username from the client
            Username = getUsername(); // Use Username consistently
            System.out.println("User " + Username + " connected."); // Use Username consistently

            out.println("Welcome to the chat, " + Username + "!"); // Use Username consistently
            out.println("Type Your Message");
            String inputLine;

            // Continue receiving messages from the client
            while ((inputLine = in.readLine()) != null) {
                System.out.println("[" + Username + "]: " + inputLine); // Use Username consistently

                // Broadcast the message to all clients
                broadcast("[" + Username + "]: " + inputLine, this); // Use Username consistently
            }
            // Remove the client handler from the list
            clients.remove(this);

            // Close the input and output streams and the client socket
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get the username from the client
    private String getUsername() throws IOException {
        out.println("Enter your username:");
        return in.readLine();
    }

    public void sendMessage(String message) {
        out.println(message);
        out.println("Type Your Message");
    }
}
