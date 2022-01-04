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

    public void setApp(MainApplication main){
        this.main = main;
    }

    public void setIDList(String ID, ObservableList<String> list){
        this.ID = ID;
        this.list = list;
    }

    public void initializeTextAndBox(){
        ObservableList<String> Day = FXCollections.observableArrayList("Monday", "Tuesday", "Wedenesday", "Thursday", "Friday", "Saturday", "Sunday");
        DayBox.setItems(Day);
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
        int start = Integer.parseInt(StartText.getText());
        int end = Integer.parseInt(EndText.getText());
        if(start < end){
            dbConnector.EditQuery(list, DayBox.getValue(), StartText.getText(),
                    EndText.getText(), LecturerText.getText(), Integer.parseInt(TargetText.getText()));
        }
    }

    @FXML
    public void Back(ActionEvent actionEvent) throws Exception {
        main.GoToModify(ID,  "Staff");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
