package com.github.katemerek.server.servers;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;

@Component
@Data
public class Server {
    private static final int portNumber = 9001;
    static Logger logger = LoggerFactory.getLogger(Server.class);


    public void startServer() {
        try(ServerSocket socket = new ServerSocket(portNumber)) {
            logger.info("Server started! Waiting for connections...");
            System.out.println("Server started!");

            System.out.println("Waiting for a client to connect...");
            try {
                while (true) {
                    new CommunicationHandler(socket.accept()).start();
                    logger.info("New Client connected");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                socket.close();
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
