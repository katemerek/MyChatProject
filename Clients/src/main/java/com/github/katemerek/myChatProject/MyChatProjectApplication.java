package com.github.katemerek.myChatProject;

import com.github.katemerek.myChatProject.servers.Server;
import com.github.katemerek.myChatProject.view.JavaFxApplication;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyChatProjectApplication {

	public static void main(String[] args) {
		Application.launch(JavaFxApplication.class, args);
	}

}
