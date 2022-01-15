package com.example.maya2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginMenuController implements Initializable {
    @FXML private TextField LoginID;
    @FXML private PasswordField LoginPassword;
    @FXML private Button LoginButton;
    @FXML private Button RegisterButton;
    @FXML private Label LoginErrorMsg;
    private MainApplication main;

    public void setApp(MainApplication main){
        this.main = main;
    }

    @FXML
    public void GoToRegister() throws Exception {
        main.GoToRegister();
    }

    @FXML
    public void Login() throws Exception {
        try {
            DBConnector dbConnector = new DBConnector();
            ResultSet student = dbConnector.StudentLoginQuery(LoginID.getText(), LoginPassword.getText());
            ResultSet staff = dbConnector.StaffLoginQuery(LoginID.getText(), LoginPassword.getText());
            //if rs.isBeforeFirst is true, the results is not empty
            if(student.isBeforeFirst()){
                student.next();
                main.GoToMaya(student.getString(1), "Student");
            } else if(staff.isBeforeFirst()){
                staff.next();
                main.GoToMaya(staff.getString(1),"Staff");
            } else{
                LoginErrorMsg.setText("Wrong ID or Password!");
            }
        }catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoginErrorMsg.setText("");
    }
}
