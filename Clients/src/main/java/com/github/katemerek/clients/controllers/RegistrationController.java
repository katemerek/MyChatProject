package com.github.katemerek.clients.controllers;

import com.github.katemerek.clients.config.Settings;
import com.github.katemerek.dto.dto.PersonDto;
import com.github.katemerek.dto.mapper.PersonMapper;
import com.github.katemerek.dto.models.Person;
import com.github.katemerek.dto.repositories.PeopleRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Component;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;

@Component
@FxmlView
@RequiredArgsConstructor
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
