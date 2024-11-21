package com.github.katemerek.myChatProject.controllers;

import com.github.katemerek.myChatProject.servers.CommunicationHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView
public class ChatController {

        @FXML private TextArea messageBox;
        @FXML ListView chatPane;
        @FXML
        BorderPane borderPane;
        @FXML
        ComboBox statusComboBox;
        @FXML ImageView microphoneImageView;
        @FXML Button buttonSend;

        private CommunicationHandler communicationHandler;

//        private double xOffset;
//        private double yOffset;
//        Logger logger = LoggerFactory.getLogger(ChatController.class);


        public void sendMessage() throws IOException {
            String msg = messageBox.getText();
            if (!messageBox.getText().isEmpty()) {
                communicationHandler.sendMessage(msg);
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
