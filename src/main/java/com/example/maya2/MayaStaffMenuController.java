package com.example.maya2;

import javafx.fxml.*;
import javafx.scene.text.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MayaStaffMenuController implements Initializable {
    private MainApplication main;
    @FXML private Text WelcomeText;
    private String ID;

    public void setApp(MainApplication main){
        this.main = main;
    }

    public void setID(String ID){
        this.ID = ID;
    }

    public void setWelcomeText(){
        WelcomeText.setText("Welcome to Maya, " + ID);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
