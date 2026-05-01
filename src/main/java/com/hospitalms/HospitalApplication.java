package com.hospitalms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HospitalApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HospitalApplication.class.getResource("/com/hospitalms/fxml/auth/login-view.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}