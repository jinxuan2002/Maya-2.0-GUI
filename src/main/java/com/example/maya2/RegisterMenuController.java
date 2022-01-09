package com.example.maya2;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class RegisterMenuController implements Initializable {
    private MainApplication main;
    @FXML private Text MailPrefix;
    @FXML private TextField realName;
    @FXML private TextField email;
    @FXML private TextField ID;
    @FXML private PasswordField password;
    @FXML private PasswordField cpassword;
    @FXML private ChoiceBox<String> programme;
    @FXML private ChoiceBox<Integer> muet;
    @FXML private RadioButton Student;
    @FXML private RadioButton Staff;


    public void setApp(MainApplication main){
        this.main = main;
    }

    @FXML
    public void Back(ActionEvent actionEvent) throws Exception {
        main.GoToLogin();
    }

    @FXML
    public void SelectStudent(ActionEvent actionEvent){
        Student.setSelected(true);
        Staff.setSelected(false);
        MailPrefix.setText("@siswa.um.edu.my");
        programme.setDisable(false);
        muet.setDisable(false);
    }

    @FXML
    public void SelectStaff(ActionEvent actionEvent){
        Student.setSelected(false);
        Staff.setSelected(true);
        MailPrefix.setText("@um.edu.my");
        programme.setDisable(true);
        muet.setDisable(true);
    }

    @FXML
    public void Register(ActionEvent actionEvent) throws Exception {
        boolean validity = true;
        if(realName.getText().isEmpty()){
           validity = false;
        }
        if((ID.getText().charAt(0) != 'U' || ID.getText().length() != 8) && Student.isSelected()){
            validity = false;
        }
        if(!password.getText().equals(cpassword.getText()) || password.getText().length() == 0){
            validity = false;
        }
        if(email.getText().contains("@")){
            validity = false;
        }

        if(validity && Student.isSelected()){
            DBConnector dbConnector = new DBConnector();
            dbConnector.StudentRegisterUpdate(ID.getText(), password.getText(), email.getText().toLowerCase() + "@siswa.um.edu.my", programme.getValue(), muet.getValue(),realName.getText());
            main.GoToLogin();
        }
        if(validity && Staff.isSelected()){
            DBConnector dbConnector = new DBConnector();
            dbConnector.StaffRegisterUpdate(ID.getText(), email.getText().toLowerCase() + "@um.edu.my", password.getText(), realName.getText().toUpperCase());
            main.GoToLogin();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> muetList = FXCollections.observableArrayList(1,2,3,4,5,6);
        ObservableList<String> programmeList = FXCollections.observableArrayList("SE", "AI", "DS", "IS", "CN", "MM");
        programme.setItems(programmeList);
        muet.setItems(muetList);
        programme.setValue("SE");
        muet.setValue(1);
        Student.setSelected(true);
    }
}
