package com.example.maya2;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.Stage;

public class MainApplication extends Application {
    Group root = new Group();
    Scene scene = new Scene(root);
    Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Maya 2.0");
        stage.setScene(scene);
        this.stage = stage;
        GoToLogin();
        stage.show();
        System.out.println(stage.getWidth());
        System.out.println(stage.getHeight());
    }

    public void GoToLogin() throws Exception {
        LoginMenuController loginMenuController = (LoginMenuController) loadScene("LoginMenu.fxml");
        loginMenuController.setApp(this);
        stage.setWidth(614);
        stage.setHeight(437);
    }

    public void GoToRegister() throws Exception {
        RegisterMenuController registerMenuController = (RegisterMenuController) loadScene("RegisterMenu.fxml");
        registerMenuController.setApp(this);
        stage.setWidth(614);
        stage.setHeight(437);
    }

    public void GoToMaya(String ID, String session) throws Exception {
        if(session.equals("Student")){
            MayaStudentMenuController mayaMenuController = (MayaStudentMenuController) loadScene("MayaStudentMenu.fxml");
            mayaMenuController.setIDSession(ID,session);
            mayaMenuController.setWelcomeText();
            mayaMenuController.setApp(this);
        } else if(session.equals("Staff")){
            MayaStaffMenuController mayaMenuController = (MayaStaffMenuController) loadScene("MayaStaffMenu.fxml");
            mayaMenuController.setIDSession(ID, session);
            mayaMenuController.setWelcomeText();
            mayaMenuController.setApp(this);
        }
        stage.setWidth(614);
        stage.setHeight(437);
    }

    public void GoToSearch(String ID, String session) throws Exception {
        SearchMenuController searchMenuController = (SearchMenuController) loadScene("SearchMenu.fxml");
        searchMenuController.setIDSession(ID, session);
        searchMenuController.setApp(this);
        stage.setWidth(1020);
        stage.setHeight(700);
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