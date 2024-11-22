package com.github.katemerek.server.servers;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

@Component
@Data
public class Server {
    private static final int portNumber = 8888;

    public void startServer() {
        try(ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server started!");

            System.out.println("Waiting for a client to connect...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New Client connected." + socket + LocalDateTime.now());
                System.out.println("---------------------");

                //Thread to handle client messages
                Thread client = new Thread(new CommunicationHandler(socket));
//
//                client.start();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void closeServer() {
        System.out.println("Server closed!");
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.startServer();
    }
}
