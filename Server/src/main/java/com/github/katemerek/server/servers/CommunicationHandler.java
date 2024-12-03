package com.github.katemerek.server.servers;

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
    private InputStream inputStream;
    private OutputStream outputStream;
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
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            String firstMessage = String.valueOf(inputStream.read());
            registrationService.loadUserByName(firstMessage);
            clientsOnline = registrationService.checkTrueLoggingStatus();
            broadcastMessage("Hello,  " + firstMessage + "! You have connected to chat!");
            Person person = new Person();
            person.setName(firstMessage);
            String messageFromClient;
            while (socket.isConnected()) {
                messageFromClient = String.valueOf(inputStream.read());
                logger.info(firstMessage + messageFromClient);
                broadcastMessage(messageFromClient);
            }
    } catch (
    SocketException socketException) {
        logger.error("Socket Exception for user " + person.getName(), socketException);
    } catch (Exception e){
        logger.error("Duplicate Username : " + person.getName(), e);
    } finally {
        closeAll(socket, inputStream, outputStream);
    }
    }


    public void removeClientHandler() throws IOException {
        clientsOnline.remove(this);
        broadcastMessage("User " + person.getName() + " left the chat");
    }


    public void broadcastMessage(String messageToSend) throws IOException {
        for(Person client: clientsOnline){
            if(!client.getName().equals(person.getName())){
                outputStream.write(messageToSend.getBytes());
                outputStream.flush();
            }
        }
    }


    public synchronized void closeAll(Socket socket, InputStream inputStream, OutputStream outputStream) {
        logger.debug("All connections are starting to close");
        try {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
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
