package com.github.katemerek.server.servers;

import com.github.katemerek.dto.models.Person;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

import static com.github.katemerek.server.servers.Server.broadcastMessage;
import static com.github.katemerek.server.servers.Server.removeClient;


@Data
@RequiredArgsConstructor
public class CommunicationHandler implements Runnable {
    private Person person;
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private Logger logger = LoggerFactory.getLogger(CommunicationHandler.class);


    public CommunicationHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        logger.info("Starting communication thread");
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

            String clientMessage;
            while ((clientMessage = bufferedReader.readLine()) != null) {
                System.out.println("Получено сообщение от клиента: " + clientMessage);
                broadcastMessage(clientMessage, this);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при работе с клиентом: " + e.getMessage() + LocalDateTime.now());
        } finally {
            closeAll(socket, bufferedReader, bufferedWriter);
            removeClient(this);
        }
    }


    public void sendMessage(String message) throws IOException {
        bufferedWriter.write(message + "\r\n");
        bufferedWriter.newLine();
        bufferedWriter.flush();
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
