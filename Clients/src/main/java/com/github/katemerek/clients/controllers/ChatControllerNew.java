package com.github.katemerek.clients.controllers;

import com.github.katemerek.clients.clients.Client;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.Date;

@Component
@FxmlView
@Data
@RequiredArgsConstructor
public class ChatControllerNew {

    private BufferedReader in;
    private static PrintWriter out;
    String messageFromServer;

    private Socket socket;


        @FXML
        public TextArea ta;
        @FXML
        public TextField tf;
        @FXML
        Button buttonSend;

        private Client client;

        public ChatControllerNew(Client client) {
            this.client = client;
        }

        @FXML
        public synchronized void writeMessage() throws IOException {
            writeData(out, tf);
//            dataOutputStream. writeByte(1);
//            dataOutputStream.writeUTF(messageFromServer);
//            dataOutputStream.flush();
            updateTextArea();
        }

        public void writeData (PrintWriter out, TextField tf) {
//    Поток аут равен нулю, поэтому он не записывает туда данные
            messageFromServer = tf.getText();
            out.println(messageFromServer);
            tf.clear();
        }

        public synchronized void updateTextArea () throws IOException {
            Platform.runLater(() -> {
                ta.appendText(messageFromServer + "\n");
            });
        }
    }

