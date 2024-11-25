package com.github.katemerek.server.servers;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

@Component
@Data
public class Server {
    private static final int portNumber = 9001;
    static Logger logger = LoggerFactory.getLogger(Server.class);


    public void startServer() {
        try(ServerSocket listener = new ServerSocket(portNumber)) {
            logger.info("Server started! Waiting for connections...");
            System.out.println("Server started!");

            System.out.println("Waiting for a client to connect...");
            try {
                while (true) {
                    logger.info("New Client connected");
                    new CommunicationHandler(listener.accept()).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                listener.close();
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
