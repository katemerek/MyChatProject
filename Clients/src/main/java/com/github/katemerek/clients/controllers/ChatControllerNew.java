package com.github.katemerek.clients.controllers;

import com.github.katemerek.clients.client.ChatClient;
import com.github.katemerek.clients.config.PersonDtoService;
import com.github.katemerek.dto.dto.PersonDto;
import jakarta.annotation.PostConstruct;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;


@Component
@FxmlView
@Data
@RequiredArgsConstructor
public class ChatControllerNew {

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private final ChatClient chatClient;
    private LoginController loginController;
    private Socket socket;
    private PersonDto personDto;
    private final PersonDtoService personDtoService;

    @FXML
    public TextArea ta;
    @FXML
    public TextField tf;
    @FXML
    Button buttonSend;
    @FXML
    Button buttonBegin;


    @PostConstruct
    public void init() {
        // Настройка обработчика сообщений.
        chatClient.setOnMessageReceived(this::appendMessage);
    }


    @FXML
    private void startChat() throws IOException {
        personDto = personDtoService.getCurrentUser();
        chatClient.connect("localhost", 9001, personDto.getName());
        appendMessage("Добро пожаловать в чат, " + personDto.getName() + "! Приятного общения\n");
        buttonBegin.setDisable(true);
    }


    @FXML
    private void sendMessage() {
        String messageClient = tf.getText();
        if (messageClient != null && !messageClient.isEmpty()) {
            try {
                chatClient.sendMessage(personDto.getName() + ": " + messageClient);
                tf.clear();
            } catch (IOException e) {
                appendMessage("Ошибка при отправке сообщения: " + e.getMessage());
            }
        }
    }


    private void appendMessage(String message) {
        Platform.runLater(() -> ta.appendText(message + "\n"));
    }
}

