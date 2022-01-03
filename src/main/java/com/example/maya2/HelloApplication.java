package com.example.maya2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root,420,420);
        SceneController sceneController = new SceneController(scene);
        sceneController.addScene("hello", FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml")));
        sceneController.activate("hello");

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}