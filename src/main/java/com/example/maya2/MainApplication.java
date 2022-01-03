package com.example.maya2;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.Stage;

public class MainApplication extends Application {
    Group root = new Group();
    Scene scene = new Scene(root);

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Maya 2.0");
        stage.setScene(scene);
        GoToLogin();
        stage.show();
    }

    public void GoToLogin() throws Exception {
        LoginMenuController loginMenuController = (LoginMenuController) loadScene("LoginMenu.fxml");
        loginMenuController.setApp(this);
    }

    public void GoToRegister() throws Exception {
        RegisterMenuController registerMenuController = (RegisterMenuController) loadScene("RegisterMenu.fxml");
        registerMenuController.setApp(this);
    }

    public void GoToMaya() throws Exception {
        MayaMenuController mayaMenuController = (MayaMenuController) loadScene("MayaMenu.fxml");
        mayaMenuController.setApp(this);
    }

    private Initializable loadScene(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        scene.setRoot(loader.load(MainApplication.class.getResourceAsStream(fxml)));
        return (Initializable) loader.getController();
    }

    public static void main(String[] args) {
        launch();
    }
}