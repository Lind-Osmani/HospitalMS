package com.hospitalms.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class SceneUtil {

    private static final double DEFAULT_WIDTH = 900;
    private static final double DEFAULT_HEIGHT = 600;

    private SceneUtil() {
    }

    public static void changeScene(Stage stage, String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    SceneUtil.class.getResource(fxmlPath)
            );

            Scene scene = new Scene(fxmlLoader.load(), DEFAULT_WIDTH, DEFAULT_HEIGHT);

            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}