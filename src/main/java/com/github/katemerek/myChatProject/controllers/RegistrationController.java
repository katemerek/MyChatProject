package com.github.katemerek.myChatProject.controllers;

import com.github.katemerek.myChatProject.config.Settings;
import com.github.katemerek.myChatProject.dto.PersonDto;
import com.github.katemerek.myChatProject.mapper.PersonMapper;
import com.github.katemerek.myChatProject.models.Person;
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

import java.beans.XMLEncoder;
import java.io.FileOutputStream;

@Component
@FxmlView
public class RegistrationController  {
    private final FxWeaver fxWeaver;
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

    private final PersonMapper personMapper;
    private final RegistrationService registrationService;

    public RegistrationController(PersonMapper personMapper, RegistrationService registrationService, FxWeaver fxWeaver) {
        this.personMapper = personMapper;
        this.registrationService = registrationService;
        this.fxWeaver = fxWeaver;
    }

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
    public void onStageClose() {
        // создали экземпляр класс
        Settings settings = new Settings();
        // зафиксировали значения полей
        settings.txtName= txtName.getText();
        settings.txtYearOfBird = txtYearOfBirth.getText();
        settings.txtPassword = txtPassword.getText();
        // добавляем
        try {
            // создаем поток для записи в файл experiment.xml
            FileOutputStream fos = new FileOutputStream("settings.xml");
            // создали энкодер, которые будет писать в поток
            XMLEncoder encoder = new XMLEncoder(fos);

            // записали настройки
            encoder.writeObject(settings);

            // закрыли энкодер и поток для записи
            // если не закрыть, то файл будет пустой
            encoder.close();
            fos.close();
        } catch (Exception e) {
            // на случай ошибки
            e.printStackTrace();
        }
    }
}
