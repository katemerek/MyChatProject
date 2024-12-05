package com.github.katemerek.clients.controllers;

import com.github.katemerek.clients.clients.Client;
import com.github.katemerek.dto.dto.PersonDto;
import com.github.katemerek.dto.mapper.PersonMapper;
import com.github.katemerek.dto.models.Person;
import com.github.katemerek.dto.services.RegistrationService;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@FxmlView
@RequiredArgsConstructor
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
    private final PasswordEncoder passwordEncoder;
    public Client client;


    @FXML
    void initialize() {
        assert buttonLogin != null : "fx:id=\"button\" was not injected: check your FXML file 'LoginController.fxml'.";
    }

    public void loginUser() throws IOException {
        PersonDto p = new PersonDto();
        p.setName(txtName.getText());
        p.setPassword(txtPassword.getText());
        Person pLogin = personMapper.toPerson(p);
        registrationService.loadUserByName(pLogin.getName());
        pLogin.setStatus(true);
        client = new Client(pLogin.getName());
        Thread thread = new Thread(client);
        thread.start();
        switchOnChat(client);


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

    public void switchOnChat(Client client) {
        Parent root = fxWeaver.loadView(ChatController.class);
        Stage tertiaryStage = (Stage) buttonLogin.getScene().getWindow();
        tertiaryStage.setScene(new Scene(root));
        tertiaryStage.show();
    }

}
