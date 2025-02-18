package com.github.katemerek.server.servers;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    static Logger logger = LoggerFactory.getLogger(Server.class);
    private static final Set<CommunicationHandler> clients = Collections.synchronizedSet(new HashSet<>()); // Хранит всех подключенных клиентов



    public void startServer() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(portNumber)) {
            logger.info("Server started! Waiting for connections...");
            try {
                while (true) {
                    Socket socket = serverSocket.accept(); // Ожидание подключения клиента
                    System.out.println("Новый клиент подключен: " + socket);

                    // Создаем обработчик для клиента
                    CommunicationHandler communicationHandler = new CommunicationHandler(socket);
                    clients.add(communicationHandler); // Добавляем клиента в список
                    new Thread(communicationHandler).start(); // Запускаем в отдельном потоке
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Метод для рассылки сообщений всем клиентам
    public static void broadcastMessage(String message, CommunicationHandler sender) throws IOException {
        for (CommunicationHandler client : clients) {
            synchronized (clients) {
//                if (client != sender) { // Не отправляем сообщение отправителю
                    client.sendMessage(message + "\r\n");
//                }
            }
        }
    }

    // Удаление клиента из списка при отключении
    public static void removeClient(CommunicationHandler client) {
        clients.remove(client);
        System.out.println("Клиент отключен: " + client.getSocket());
    }
    public void closeServer() {
        System.out.println("Server closed!");
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.startServer();
    }
}
