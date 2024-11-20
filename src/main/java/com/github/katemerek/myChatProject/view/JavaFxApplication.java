package com.github.katemerek.myChatProject.view;

import com.github.katemerek.myChatProject.MyChatProjectApplication;
import com.github.katemerek.myChatProject.controllers.RegistrationController;
import com.github.katemerek.myChatProject.mapper.PersonMapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class JavaFxApplication extends Application {
    private ConfigurableApplicationContext applicationContext;


    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(MyChatProjectApplication.class)
                .run(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(RegistrationController.class);
        Scene scene = new Scene(root);
       primaryStage.setScene(scene);
        primaryStage.show();
    }
    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}
