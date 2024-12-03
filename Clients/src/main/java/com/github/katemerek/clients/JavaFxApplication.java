package com.github.katemerek.clients;

import com.github.katemerek.clients.controllers.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
                .sources(ClientsApplication.class)
                .run(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(LoginController.class, "com.github.katemerek.clients.controllers.LoginController.fxml");
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
