package com.github.katemerek.server.servers;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static com.github.katemerek.server.servers.Server.*;


@Data
@RequiredArgsConstructor
public class CommunicationHandler implements Runnable {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;


    public CommunicationHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        System.out.println("Запуск потока для чтения сообщений от клиента");
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

            while (true) {
                String clientMessage = this.bufferedReader.readLine();
                if (clientMessage == null) {
                    return;
                }
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

    public void broadcastMessage(String clientMessage, CommunicationHandler sender) throws IOException {
        for (CommunicationHandler client : clients) {
//            if (client !=sender) {
            client.sendMessage(clientMessage);
//            }
        }
    }


    public void sendMessage(String message) throws IOException {
        bufferedWriter.write(message + "\n");
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }


    public void closeAll(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        System.out.println("Закрываем все открытые соединения");
        try {
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
