package com.example.maya2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginMenuController implements Initializable {
    @FXML private TextField LoginID;
    @FXML private TextField LoginPassword;
    @FXML private Button LoginButton;
    @FXML private Button RegisterButton;
    private MainApplication main;

    public void setApp(MainApplication main){
        this.main = main;
    }

    @FXML
    public void GoToRegister(ActionEvent event) throws Exception {
        main.GoToRegister();
    }

    @FXML
    public void Login(ActionEvent event) throws Exception {
        main.GoToMaya();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
