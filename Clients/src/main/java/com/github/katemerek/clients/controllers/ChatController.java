package com.github.katemerek.clients.controllers;

import com.github.katemerek.clients.clients.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@FxmlView
public class ChatController {

        @FXML private TextArea messageBox;
        @FXML ListView chatPane;
        @FXML Button buttonSend;

        private Client client;
        private LoginController loginController;

        public ChatController(Client client) {
            this.client = client;
        }


        public void sendMessage() throws IOException {
            String msg = messageBox.getText();
            if (!messageBox.getText().isEmpty()) {
                Client.sendMessage(msg);
                messageBox.clear();
            }
        }

        @FXML
        public void closeApplication() {
            Platform.exit();
            System.exit(0);
        }


//        public void logoutScene() {
//            Platform.runLater(() -> {
//                FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
//                Parent window = null;
//                try {
//                    window = (Pane) fmxlLoader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Stage stage = MainLauncher.getPrimaryStage();
//                Scene scene = new Scene(window);
//                stage.setMaxWidth(350);
//                stage.setMaxHeight(420);
//                stage.setResizable(false);
//                stage.setScene(scene);
//                stage.centerOnScreen();
//            });
//        }
    }
