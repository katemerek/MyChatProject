package com.github.katemerek.clients;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan("com.github.katemerek")
    public class ClientsApplication {
        public static void main(String[] args) {
            Application.launch(JavaFxApplication.class, args);
        }
    }
