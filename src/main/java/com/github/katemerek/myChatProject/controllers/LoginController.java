package com.github.katemerek.myChatProject.controllers;

import com.github.katemerek.myChatProject.clients.Client;
import com.github.katemerek.myChatProject.mapper.PersonMapper;
import com.github.katemerek.myChatProject.services.RegistrationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.Socket;

@Component
@FxmlView
public class LoginController {
    private final FxWeaver fxWeaver;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPassword;
    @FXML
    Button buttonRegistration;
    @FXML
    Button buttonLogin;

    private RegistrationController registrationController;
    private final PersonMapper personMapper;
    private final RegistrationService registrationService;

    public LoginController(PersonMapper personMapper, RegistrationService registrationService, RegistrationController registrationController, FxWeaver fxWeaver) {
        this.personMapper = personMapper;
        this.registrationService = registrationService;
        this.registrationController = registrationController;
        this.fxWeaver = fxWeaver;
    }

    @FXML
    void initialize() {
        assert buttonLogin != null : "fx:id=\"button\" was not injected: check your FXML file 'LoginController.fxml'.";
    }

    public void loginUser() throws IOException {
        registrationService.loadUserByName(username());
        switchOnChat();
        Client client1 = new Client(username(), new Socket("localhost", 8888));
        client1.readMessage();
        client1.sendMessage();
        Client client2 = new Client(username(), new Socket("localhost", 8888));
        client2.readMessage();
        client2.sendMessage();

    }
    public String username(){
        return txtName.getText();
    }

    public void addNewUser() {
        Parent root = fxWeaver.loadView(RegistrationController.class);
        Stage secondaryStage = (Stage) buttonRegistration.getScene().getWindow();
        secondaryStage.setScene(new Scene(root));
        secondaryStage.show();
    }

    public void switchOnChat() {
        Parent root = fxWeaver.loadView(ChatController.class);
        Stage secondaryStage = (Stage) buttonRegistration.getScene().getWindow();
        secondaryStage.setScene(new Scene(root));
        secondaryStage.show();
    }

}
