//package com.github.katemerek.clients.clients;
//
//import com.github.katemerek.clients.controllers.ChatControllerNew;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.io.*;
//import java.net.Socket;
//
//@Component
//@RequiredArgsConstructor
//
//public class Client implements Runnable {
//    private String name;
//    private ChatControllerNew chatControllerNew;
//    Logger logger = LoggerFactory.getLogger(Client.class);
//
//    public void run() {
//        try (Socket socket = new Socket("localhost", 9001);
//                   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                   BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
//            // Поток для чтения сообщений от сервера
//            new Thread(() -> {
//                try {
//                    String serverMessage;
//                    while ((serverMessage = bufferedReader.readLine()) != null) {
//                        System.out.println("Сообщение от сервера: " + serverMessage);
//                    }
//                } catch (IOException e) {
//                    System.out.println("Ошибка при чтении сообщений от сервера: " + e.getMessage());
//                }
//            }).start();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    public static void sendMessage(String message) throws IOException {
//        bufferedWriter.write(message);
//        bufferedWriter.newLine();
//        bufferedWriter.flush();
//    }
//
//    public void closeAll(Socket socket, BufferedReader in, PrintWriter out) {
//        try {
//            if (in != null) {
//                in.close();
//            }
//            if (out != null) {
//                out.close();
//            }
//            if (socket != null) {
//                socket.close();
//            }
//        } catch (IOException e) {
//            e.getStackTrace();
//        }
//    }
//}