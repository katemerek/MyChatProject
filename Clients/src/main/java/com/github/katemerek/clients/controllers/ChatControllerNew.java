package com.github.katemerek.clients.controllers;

import com.github.katemerek.dto.dto.PersonDto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;


@Component
@FxmlView
@Data
@RequiredArgsConstructor
public class ChatControllerNew {

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    LoginController loginController;

    @Setter
    PersonDto personDto;

    private Socket socket;


    @FXML
    public TextArea ta;
    @FXML
    public TextField tf;
    @FXML
    Button buttonSend;
    @FXML
    Button buttonBegin;


    public ChatControllerNew(PersonDto personDto) {
        this.personDto = personDto;
    }

    @FXML
    public void writeMessage() throws IOException {
//        sendMessageAnother();
        updateTextArea("kuku");
    }

    @FXML
    public synchronized void beginClientChat() throws IOException {
        processInput(personDto);
//        buttonBegin.setDisable(true);
    }

    public String getInputFieldValue() {
        return tf.getText();
    }

    public void processInput(PersonDto personDto) throws IOException {

        try (Socket socket = new Socket("localhost", 9001);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        ) {
            // Поток для чтения сообщений от сервера
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = bufferedReader.readLine()) != null) {
                        System.out.println("Сообщение от сервера: " + serverMessage);
//                        String finalMessage = serverMessage;
//                        Platform.runLater(() -> {
//                            ta.appendText(finalMessage);
//                        });
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка при чтении сообщений от сервера: " + e.getMessage() + LocalDateTime.now());
                    closeAll(socket, bufferedReader, bufferedWriter);
                }
            }).start();

            // Запрашиваем имя пользователя
            if (personDto != null) {
                String userName = personDto.getName();
                ta.setStyle("-fx-text-fill: red;");
                updateTextArea("Добро пожаловать, " + userName + "\r\n");
                ta.appendText("Добро пожаловать, " + userName + "\r\n"); // Обновляем UI
                bufferedWriter.write(userName + "\r\n");
                bufferedWriter.flush();
            }
        }
    }


//             Отправка сообщений на сервер
//            String inputText;
//            while ((inputText = getInputFieldValue()) != null) {
//                bufferedWriter.write(inputText);
//                bufferedWriter.newLine();
//                bufferedWriter.flush();
//                updateTextArea();
//            }

    // Метод для отправки сообщения
    private void sendMessageAnother() {
        String message = getInputFieldValue();
        if (message != null && !message.isEmpty()) {
            try {
                bufferedWriter.write(message + "\r\n");
                bufferedWriter.flush();
                tf.setText(""); // Очищаем поле ввода
            } catch (IOException e) {
                ta.appendText("Ошибка при отправке сообщения: " + e.getMessage() + "\r\n");
            }
        }
    }


    public void closeAll(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }


    public synchronized void updateTextArea(String serverMessage) throws IOException {
        Platform.runLater(() -> {
            ta.appendText(serverMessage + "\r\n");
        });
    }
}

