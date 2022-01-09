package com.example.maya2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditMenuController implements Initializable {
    private MainApplication main;
    private String ID;
    private ObservableList<String> list;
    @FXML private TextField ModuleText;
    @FXML private TextField OccText;
    @FXML private TextField ModeText;
    @FXML private ChoiceBox<String> DayBox;
    @FXML private TextField StartText;
    @FXML private TextField EndText;
    @FXML private TextField LecturerText;
    @FXML private TextField TargetText;
    @FXML private TextField ActualText;
    @FXML private Label ErrorLabel;

    public void setApp(MainApplication main){
        this.main = main;
    }

    public void setIDList(String ID, ObservableList<String> list){
        this.ID = ID;
        this.list = list;
    }

    public void initializeTextAndBox(){
        ModuleText.setText(list.get(0));
        OccText.setText(list.get(1));
        ModeText.setText(list.get(2));
        DayBox.setValue(list.get(3));
        StartText.setText(list.get(4));
        EndText.setText(list.get(5));
        LecturerText.setText(list.get(6));
        TargetText.setText(list.get(7));
        ActualText.setText(list.get(8));
    }



    @FXML
    public void Update(ActionEvent actionEvent){
        DBConnector dbConnector = new DBConnector();
        ErrorLabel.setText("");
        String start = StartText.getText();
        String end = EndText.getText();
        int target = Integer.parseInt(TargetText.getText());
        if(start.isEmpty() || end.isEmpty()){
            start = "N/A";
            end = "N/A";
        }
        if(Integer.parseInt(start) < Integer.parseInt(end) && target > 0 && Integer.parseInt(start) <= 2400 && Integer.parseInt(end) <= 2400){
            dbConnector.EditQuery(list, DayBox.getValue(), start,
                    end, LecturerText.getText(), target);
        } else{
            ErrorLabel.setText("Please enter a valid time in 24 hour format.");
        }
    }

    @FXML
    public void Back(ActionEvent actionEvent) throws Exception {
        main.GoToModify(ID,  "Staff");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> day = FXCollections.observableArrayList("Monday", "Tuesday", "Wedenesday", "Thursday", "Friday", "Saturday", "Sunday", "N/A");
        DayBox.setItems(day);
        ErrorLabel.setText("");

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
