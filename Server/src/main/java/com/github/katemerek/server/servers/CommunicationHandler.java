package com.github.katemerek.server.servers;

import com.github.katemerek.dto.models.Message;
import com.github.katemerek.dto.models.Person;
import com.github.katemerek.dto.services.RegistrationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;


@Data
@RequiredArgsConstructor
public class CommunicationHandler extends Thread {
    private Person person;
    private BufferedReader in;
    private PrintWriter out;
    public Socket socket;
    public static ArrayList<CommunicationHandler> clients = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(CommunicationHandler.class);
    private RegistrationService registrationService;
    public String user;
    public CommunicationHandler (Socket socket) {
        this.socket = socket;
    }



//    public CommunicationHandler(Socket socket) {
//        try {
//            this.socket = socket;
//            this.buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            this.buffWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
//            this.name = buffReader.readLine();
//            clients.add(this);
//            broadcastMessage("Hello,  " + name + "! You have connected to chat!");
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//        }
//    }


    @Override
    public void run() {
        logger.info("Starting communication thread");
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            String firstMessage = in.readLine();
            user = firstMessage;
            clients.add(this);
            System.out.println(firstMessage);
            broadcastMessage("Hello,  " + user + "! You have connected to chat!");
            Person person = new Person();
            person.setName(user);
            String messageFromClient;
            while (socket.isConnected()) {
                messageFromClient = in.readLine();
                logger.info(user + ": " + messageFromClient);
                broadcastMessage(user + ": " + messageFromClient);
            }
    } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        closeAll(socket, in, out);
    }
    }


    public void removeClientHandler() throws IOException {
        clients.remove(this);
        broadcastMessage("User " + person.getName() + " left the chat");
    }


    public void broadcastMessage(String messageToSend) throws IOException {
        for(CommunicationHandler client: clients){
            if(!client.getName().equals(user)){
                out.write(messageToSend);
                out.flush();
            }
        }
    }


    public synchronized void closeAll(Socket socket, BufferedReader in, PrintWriter out) {
        logger.debug("All connections are starting to close");
        try {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
