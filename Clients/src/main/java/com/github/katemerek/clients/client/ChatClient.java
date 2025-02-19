package com.github.katemerek.clients.client;

import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

@Component
public class ChatClient {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    @Setter
    private Consumer<String> onMessageReceived;


    public void connect(String host, int port, String userName) throws IOException {
        socket = new Socket(host, port);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        sendMessage("К чату присоединился пользователь: " + userName + "!");

        new Thread(this::listenForMessages).start();
    }


    public void sendMessage(String message) throws IOException {
        if (bufferedWriter != null) {
            bufferedWriter.write(message + "\n");
            bufferedWriter.flush();
        }
    }


    private void listenForMessages() {
        try {
            String messageFromServer;
            while ((messageFromServer = bufferedReader.readLine()) != null) {
                if (onMessageReceived != null) {
                    onMessageReceived.accept(messageFromServer);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении сообщений от сервера: " + e.getMessage());
        } finally {
            closeAll();
        }
    }


    public void closeAll() {
        try {
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
