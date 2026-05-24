package com.hospitalms.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneUtil {
    public static void changeScene(Stage stage, String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    SceneUtil.class.getResource(fxmlPath)
            );

            Scene scene = new Scene(fxmlLoader.load(), 900, 600);

            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}