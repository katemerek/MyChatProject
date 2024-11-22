package com.github.katemerek.clients;

import com.github.katemerek.clients.view.JavaFxApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

    @SpringBootApplication
    public class ClientsApplication {
        public static void main(String[] args) {
            Application.launch(JavaFxApplication.class, args);
        }
    }
