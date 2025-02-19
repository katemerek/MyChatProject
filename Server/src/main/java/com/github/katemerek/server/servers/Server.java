package com.github.katemerek.server.servers;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
@Data
public class Server {
    private static final int portNumber = 9001;
    static final Set<CommunicationHandler> clients = Collections.synchronizedSet(new HashSet<>());

    public void startServer() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Сервер запущен! Ожидает соединение...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Новый клиент подключен: " + socket);

                CommunicationHandler communicationHandler = new CommunicationHandler(socket);
                clients.add(communicationHandler);
                new Thread(communicationHandler).start();
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }


    public static void removeClient(CommunicationHandler client) {
        clients.remove(client);
        System.out.println("Клиент отключен: " + client.getSocket());
    }


    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.startServer();
    }
}
