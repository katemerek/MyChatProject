//package com.github.katemerek.clients.controllers;
//
//import com.github.katemerek.clients.clients.Client;
//import javafx.application.Platform;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import lombok.RequiredArgsConstructor;
//import net.rgielen.fxweaver.core.FxmlView;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//@FxmlView
//public class ChatController {
//
//        @FXML private TextArea messageBox;
//        @FXML ListView chatPane;
//        @FXML Button buttonSend;
//
//        private Client client;
//    ObservableList<String> chatMessages = FXCollections.observableArrayList();
//
//        public ChatController(Client client) {
//            this.client = client;
//        }
//
//        public void sendMessage() throws IOException {
//            String msg = messageBox.getText();
//            if (!messageBox.getText().isEmpty()) {
//                client.sendMessage(msg);
//                messageBox.clear();
//                chatPane.getItems().add(messageBox.getText());
//            }
//        }
//
//
//
//        @FXML
//        public void closeApplication() {
//            Platform.exit();
//            System.exit(0);
//        }
//
//    //Method use to handle button press that submits the 1st user's text to the listview.
//    @FXML
//    private void handleUser1SubmitMessage(ActionEvent event) {
//        chatMessages.add("User 1: " + messageBox.getText());//get 1st user's text from his/her textfield and add message to observablelist
//        messageBox.setText("");//clear 1st user's textfield
//    }
//
////        public void logoutScene() {
////            Platform.runLater(() -> {
////                FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
////                Parent window = null;
////                try {
////                    window = (Pane) fmxlLoader.load();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////                Stage stage = MainLauncher.getPrimaryStage();
////                Scene scene = new Scene(window);
////                stage.setMaxWidth(350);
////                stage.setMaxHeight(420);
////                stage.setResizable(false);
////                stage.setScene(scene);
////                stage.centerOnScreen();
////            });
////        }
//    }
