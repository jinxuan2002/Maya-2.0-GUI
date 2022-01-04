package com.example.maya2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateMenuController implements Initializable {
    private MainApplication main;
    private String ID;
    @FXML private ChoiceBox<String> FacultyBox;
    @FXML private TextField ModuleCode;
    @FXML private TextField ModuleName;
    @FXML private TextField OccText;
    @FXML private ChoiceBox<String> ModeBox;
    @FXML private ChoiceBox<String> DayBox;
    @FXML private TextField StartText;
    @FXML private TextField EndText;
    @FXML private TextField LecturerText;
    @FXML private TextField TargetText;
    @FXML private TextField ActualText;
    @FXML private Text ErrorText;

    public void setApp(MainApplication main){
        this.main = main;
    }

    public void setID(String ID){
        this.ID = ID;
    }

    public void Create(){
       String faculty = FacultyBox.getValue();
       String module = String.format("%s - %s", ModuleCode.getText(), ModuleName.getText());
       String occ = OccText.getText();
       String mode = ModeBox.getValue();
       String day = DayBox.getValue();
       String start = StartText.getText();
       String end = EndText.getText();
       if(start.isEmpty() || end.isEmpty()){
           start = "N/A";
           end = "N/A";
       }
       String lecturer = LecturerText.getText();
       int target = Integer.parseInt(TargetText.getText());
       if(Integer.parseInt(start) < Integer.parseInt(end) && target > 0 && ModuleCode.getText().length() <= 8 && Integer.parseInt(start) <= 2400 && Integer.parseInt(end) <= 2400){
           DBConnector dbConnector = new DBConnector();
           ResultSet rs = dbConnector.SearchQuery(ModuleCode.getText());
           boolean same = false;
           try{
               if(rs.isBeforeFirst()){
                   while(rs.next()){
                       if(rs.getString("Occurrence").equals(occ) && rs.getString("Mode").equals(mode)){
                           same = true;
                           break;
                       }
                   }
               }
           } catch(SQLException e){
               e.printStackTrace();
           }
            if(!same){
                dbConnector.InsertQuery(faculty, module, occ, mode, day, start, end, lecturer, target);
                ErrorText.setText("Entry added successfully!");
            } else{
                ErrorText.setText("Entry already exist, failed to create entry.");
            }
       }
    }

    @FXML
    public void Back(ActionEvent actionEvent) throws Exception {
        main.GoToModify(ID, "Staff");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> day = FXCollections.observableArrayList("Monday", "Tuesday", "Wedenesday", "Thursday"
                , "Friday", "Saturday", "Sunday", "N/A");
        ObservableList<String> faculty = FXCollections.observableArrayList("Faculty of Computer Science and Information Technology"
                , "Faculty of Language and Linguistics", "University");
        ObservableList<String> mode = FXCollections.observableArrayList("TUTORIAL", "ONLINE");
        DayBox.setItems(day);
        DayBox.setValue("Monday");
        FacultyBox.setItems(faculty);
        FacultyBox.setValue("Faculty of Computer Science and Information Technology");
        ModeBox.setItems(mode);
        ModeBox.setValue("TUTORIAL");
        ActualText.setText("0");

        TargetText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    TargetText.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        StartText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    StartText.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        EndText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    EndText.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
}
