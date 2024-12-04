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


@Data
@RequiredArgsConstructor
public class CommunicationHandler extends Thread {
    private Person person;
    private BufferedReader in;
    private BufferedWriter out;
    public Socket socket;
    public static ArrayList <Person> clientsOnline = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(CommunicationHandler.class);
    private RegistrationService registrationService;
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
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String firstMessage = in.readLine();
            clientsOnline = registrationService.checkTrueLoggingStatus();
            broadcastMessage("Hello,  " + firstMessage + "! You have connected to chat!");
            Person person = new Person();
            person.setName(firstMessage);
            String messageFromClient;
            while (socket.isConnected()) {
                messageFromClient = in.readLine();
                logger.info(firstMessage + messageFromClient);
                broadcastMessage(messageFromClient);
            }
    } catch (
    SocketException socketException) {
        logger.error("Socket Exception for user " + person.getName(), socketException);
    } catch (Exception e){
        logger.error("Duplicate Username : " + person.getName(), e);
    } finally {
        closeAll(socket, in, out);
    }
    }


    public void removeClientHandler() throws IOException {
        clientsOnline.remove(this);
        broadcastMessage("User " + person.getName() + " left the chat");
    }


    public void broadcastMessage(String messageToSend) throws IOException {
        for(Person client: clientsOnline){
            if(!client.getName().equals(person.getName())){
                out.write(messageToSend);
                out.flush();
            }
        }
    }


    public synchronized void closeAll(Socket socket, BufferedReader in, BufferedWriter out) {
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
