package com.example.maya2;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.util.*;

public class RegisterMenuController implements Initializable {
    private MainApplication main;
    @FXML private Text MailPrefix;
    @FXML private Text IDText;
    @FXML private TextField realName;
    @FXML private TextField email;
    @FXML private TextField ID;
    @FXML private PasswordField password;
    @FXML private PasswordField cpassword;
    @FXML private ChoiceBox<String> programme;
    @FXML private ChoiceBox<Integer> muet;
    @FXML private RadioButton Student;
    @FXML private RadioButton Staff;
    @FXML private Label IDLabel;
    @FXML private Label ErrorLabel;


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
        IDText.setText("ID:");
        IDLabel.setText("*U followed by 7 numbers");
        MailPrefix.setText("@siswa.um.edu.my");
        programme.setDisable(false);
        muet.setDisable(false);
    }

    @FXML
    public void SelectStaff(ActionEvent actionEvent){
        Student.setSelected(false);
        Staff.setSelected(true);
        IDText.setText("Username:");
        IDLabel.setText("");
        MailPrefix.setText("@um.edu.my");
        programme.setDisable(true);
        muet.setDisable(true);
    }

    @FXML
    public void Register(ActionEvent actionEvent) throws Exception {
        DBConnector dbConnector = new DBConnector();
        ResultSet studentIDList = dbConnector.FindStudent(ID.getText());
        ResultSet staffIDList = dbConnector.FindStaff(ID.getText());
        ErrorLabel.setText("");

        if(studentIDList.isBeforeFirst() || staffIDList.isBeforeFirst()){
            ErrorLabel.setText("ID or username is already taken.");
            return;
        }
        if(realName.getText().isEmpty() || email.getText().isEmpty() || ID.getText().isEmpty() || password.getText().isEmpty() || cpassword.getText().isEmpty()){
            ErrorLabel.setText("Any of the field cannot be empty.");
           return;
        }
        if((ID.getText().charAt(0) != 'U' || ID.getText().length() != 8) && Student.isSelected()){
            ErrorLabel.setText("Student ID is not in the form of U followed by 7 numbers eg: U1234567.");
            return;
        }
        if(!password.getText().equals(cpassword.getText())){
            ErrorLabel.setText("Password and confirm password is not the same.");
            return;
        }
        if(password.getText().length() < 8){
            ErrorLabel.setText("Password is too short, minimum is 8 characters long.");
            return;
        }
        if(email.getText().contains("@")){
            ErrorLabel.setText("Email field cannot contains '@'.");
            return;
        }
        if(Student.isSelected()){
            dbConnector.StudentRegisterUpdate(ID.getText(), password.getText(), email.getText().toLowerCase() + "@siswa.um.edu.my", programme.getValue(), muet.getValue(),realName.getText());
            main.GoToLogin();
        }
        if(Staff.isSelected()){
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
        ErrorLabel.setText("");
    }
}
