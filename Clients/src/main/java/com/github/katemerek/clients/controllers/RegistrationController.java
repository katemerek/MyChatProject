package com.github.katemerek.clients.controllers;

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
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class RegistrationController  {

    private final FxWeaver fxWeaver;
    private final PersonMapper personMapper;
    private final RegistrationService registrationService;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtYearOfBirth;
    @FXML
    private TextField txtPassword;
    @FXML
    Button buttonRegister;
    @FXML
    Button buttonReturn;


    @FXML
    public void registerUser() {
        PersonDto personDto = new PersonDto();
        personDto.setName(this.txtName.getText());
        personDto.setPassword(this.txtPassword.getText());
        personDto.setYearOfBirth(Integer.parseInt(this.txtYearOfBirth.getText()));
        System.out.println(personDto);
        Person personAdd = personMapper.toPerson(personDto);
        System.out.println(personAdd);
        registrationService.register(personAdd);
        returnToLogin();
    }


    public void returnToLogin() {
        Parent root = fxWeaver.loadView(LoginController.class);
        Stage secondaryStage = (Stage) buttonReturn.getScene().getWindow();
        secondaryStage.setScene(new Scene(root));
        secondaryStage.show();
    }
}
