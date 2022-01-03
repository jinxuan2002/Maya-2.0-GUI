package com.example.maya2;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.sql.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginMenuController implements Initializable {
    @FXML private TextField LoginID;
    @FXML private TextField LoginPassword;
    @FXML private Button LoginButton;
    @FXML private Button RegisterButton;
    @FXML private Label LoginErrorMsg;
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
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maya","root","testing");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM maya.student where studentid = ? AND password = ?");
            System.out.println(LoginID.getText());
            System.out.println(LoginPassword.getText());
            statement.setString(1, LoginID.getText());
            statement.setString(2, LoginPassword.getText());
            ResultSet rs = statement.executeQuery();
            //if rs.isBeforeFirst is true, the results is not empty
            if(!rs.isBeforeFirst()){
                LoginErrorMsg.setText("Wrong ID or Password");
            } else {
                rs.next();
                main.GoToMaya();
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
