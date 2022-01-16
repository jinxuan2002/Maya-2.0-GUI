package com.example.maya2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    //Create Module based on the text box values
    public void Create(){
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
           try{
               if(rs.isBeforeFirst()){
                   while(rs.next()){
                       if(rs.getString("Occurrence").equals(occ) && rs.getString("Mode").equals(mode)){
                           ErrorText.setText("Entry already exist, failed to create entry.");
                           return;
                       }
                   }
               }
           } catch(SQLException e){
               e.printStackTrace();
           }
           dbConnector.InsertQuery(module, occ, mode, day, start, end, lecturer, target);
           ErrorText.setText("Entry added successfully!");
       } else{
           ErrorText.setText("Please enter a valid time in 24 hour format.");
       }

    }

    @FXML
    public void Back() throws Exception {
        main.GoToModify(ID, "Staff");
    }

    //Set the initial text box and choice box values and only allow integers for specific text box
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> day = FXCollections.observableArrayList("Monday", "Tuesday", "Wedenesday", "Thursday"
                , "Friday", "Saturday", "Sunday", "N/A");
        ObservableList<String> faculty = FXCollections.observableArrayList("Faculty of Computer Science and Information Technology"
                , "Faculty of Language and Linguistics", "University");
        ObservableList<String> mode = FXCollections.observableArrayList("TUTORIAL", "ONLINE");
        DayBox.setItems(day);
        DayBox.setValue("Monday");
        ModeBox.setItems(mode);
        ModeBox.setValue("TUTORIAL");
        ActualText.setText("0");
        ErrorText.setText("");

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
