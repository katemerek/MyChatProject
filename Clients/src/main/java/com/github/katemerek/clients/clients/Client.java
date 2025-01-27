package com.github.katemerek.clients.clients;

import com.github.katemerek.clients.controllers.ChatController;
import com.github.katemerek.clients.controllers.ChatControllerNew;
import com.github.katemerek.clients.controllers.LoginController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

@Component
@RequiredArgsConstructor

public class Client extends Thread {

    private Socket socket;
    private BufferedReader in;
    private static PrintWriter out;
    private String name;
    Logger logger = LoggerFactory.getLogger(Client.class);

    public Client( String name) {
            this.name = name;
            try {
                this.socket = new Socket("localhost", 9001);
                this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.out = new PrintWriter(socket.getOutputStream(), true);
            }catch(IOException e) {
                logger.error(e.getMessage());}
    }

    public void run() {
        try {
//        sendMessage(name);
        logger.info("Connection accepted " + socket.getPort());
        logger.info("Sockets in and out ready!");
        while (socket.isConnected()) {
            String messageFromClient = in.readLine();
            sendMessage( messageFromClient);
        }
        } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        public static void sendMessage(String message) throws IOException {
        out.write(message);
            out.println();
//            dataOutputStream. writeByte(1);
//            dataOutputStream.writeUTF(message);
//            dataOutputStream.flush();
        }

    public void closeAll(Socket socket, BufferedReader in, PrintWriter out){
        try{
            if(in!= null){
                in.close();
            }
            if(out != null){
                out.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.getStackTrace();
        }
    }
}