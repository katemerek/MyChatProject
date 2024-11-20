package com.github.katemerek.myChatProject.controllers;

import com.github.katemerek.myChatProject.config.Settings;
import com.github.katemerek.myChatProject.dto.PersonDto;
import com.github.katemerek.myChatProject.mapper.PersonMapper;
import com.github.katemerek.myChatProject.models.Person;
import com.github.katemerek.myChatProject.services.RegistrationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;

@Component
@FxmlView
public class RegistrationController  {
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtYearOfBirth;
    @FXML
    private TextField txtPassword;
    @FXML
    Button buttonRegister;

    private final PersonMapper personMapper;
    private final RegistrationService registrationService;

    public RegistrationController(PersonMapper personMapper, RegistrationService registrationService) {
        this.personMapper = personMapper;
        this.registrationService = registrationService;
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
    }
    @FXML
    void initialize() {
        assert buttonRegister != null : "fx:id=\"button\" was not injected: check your FXML file 'RegistrationController.fxml'.";
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
