package com.github.katemerek.server.servers;

import com.github.katemerek.dto.models.Person;
import com.github.katemerek.dto.services.RegistrationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;


@Data
@RequiredArgsConstructor
public class CommunicationHandler extends Thread {
    private Person person;
    private BufferedReader in;
    private PrintWriter out;
//DataInputStream dataInputStream;
//DataOutputStream dataOutputStream;
    public Socket socket;
    private static HashSet<PrintWriter> writers = new HashSet<>();
    public static ArrayList<CommunicationHandler> clients = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(CommunicationHandler.class);
    private RegistrationService registrationService;
    public String user;
//    private ServerController serverController;
    public CommunicationHandler (Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        logger.info("Starting communication thread");
        try{
//             dataInputStream = new DataInputStream(socket.getInputStream());
//            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
//            String firstMessage = in.readLine();
            writers.add(out);
//            if (writers.size() == 1) {
//                System.out.println("Now you only one in chat");
//            }else writeMessage("Hello " + firstMessage+ "! You have connected to chat!");
//            user = firstMessage;
////            clients.add(this);
//            System.out.println(firstMessage);
//            broadcastMessage("Hello,  " + user + "! You have connected to chat!");
//            Person person = new Person();
//            person.setName(user);
            String messageFromClient;
            while (socket.isConnected()) {
                messageFromClient = in.readLine();
                if (messageFromClient != null) {
                    logger.info(user + ": " + messageFromClient);
                    writeMessage(user + ": " + messageFromClient);
                }
            }
    } catch (IOException e) {
            throw new RuntimeException(e);
//        } finally {
//        closeAll(socket, in, out);
    }
    }


    public void removeClientHandler() throws IOException {
        clients.remove(this);
        writeMessage("User " + user + " left the chat");
    }
    public void writeMessage(String message) throws IOException {
        for (PrintWriter writer : writers) {
                writer.println();
                writer.flush();
        }
    }

    public void addUserToChat(Person person) {

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
