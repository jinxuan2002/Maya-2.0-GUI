package com.example.maya2;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.text.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MayaStudentMenuController implements Initializable {
    protected MainApplication main;
    @FXML protected Text WelcomeText;
    protected String ID;
    protected String session;

    public void setApp(MainApplication main){
        this.main = main;
    }

    public void setIDSession(String ID, String session){
        this.ID = ID;
        this.session = session;
    }

    public void setWelcomeText(){
        WelcomeText.setText("Welcome to Maya, " + ID);
    }

    @FXML
    public void GoToSearch(ActionEvent actionEvent) throws Exception {
        main.GoToSearch(ID, session);
    }

    @FXML
    public void GoToModuleRegister() throws Exception {
        main.GoToModuleRegister(ID);
    }

    @FXML
    public void GoToTimetable() throws  Exception{
        main.GoToTimetable(ID);
    }

    @FXML
    public void GoToViewStudentModule() throws Exception{
        main.GoToViewStudentModule(ID, session);
    }

    @FXML
    public void Back(ActionEvent actionEvent) throws Exception {
        main.GoToLogin();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
