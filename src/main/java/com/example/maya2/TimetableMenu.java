package com.example.maya2;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TimetableMenu implements Initializable {
    private MainApplication main;
    private String ID;
    @FXML private TableView<ObservableList<String>> Timetable;
    @FXML private TableColumn<ObservableList<String>, String> TimeColumn;
    @FXML private TableColumn<ObservableList<String>, String> Monday;
    @FXML private TableColumn<ObservableList<String>, String> Tuesday;
    @FXML private TableColumn<ObservableList<String>, String> Wednesday;
    @FXML private TableColumn<ObservableList<String>, String> Thursday;
    @FXML private TableColumn<ObservableList<String>, String> Friday;
    @FXML private TableColumn<ObservableList<String>, String> Saturday;
    @FXML private TableColumn<ObservableList<String>, String> Sunday;

    public void setApp(MainApplication main){
        this.main = main;
    }

    public void setID(String ID){
        this.ID = ID;
    }

    //Initialize the timetable based on the registered module
    public void initializeTimetable(){
        DBConnector dbConnector = new DBConnector();
        ResultSet rs = dbConnector.SearchRegistered(ID);
        ArrayList<ResultSet> resultSetArrayList = new ArrayList<ResultSet>();
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        try{
            if (rs != null)
            {
                while(rs.next()){
                    resultSetArrayList.add(dbConnector.SearchQuery(rs.getString("Module"), rs.getString("Occurrence")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            for(int i = 800; i <= 2200; i += 100) {
                ObservableList<String> row = FXCollections.observableArrayList();
                if (i <= 1100) {
                    row.add(i / 100 + ":00 am");
                } else if (i == 1200){
                    row.add(i / 100 + ":00 pm");
                } else {
                    row.add((i - 1200) / 100 + ":00 pm");
                }
                for(int j = 0; j <= 6; j++){
                    boolean found = false;
                    for(ResultSet time : resultSetArrayList){
                        while(time.next()){
                            if(!time.getString("Start").equals("N/A")){
                                int start = time.getInt("Start");
                                int end = time.getInt("End");
                                String day = time.getString("Day");
                                String module = time.getString("Code");
                                if(((i >= start && i < end) || (i + 100 <= end && i + 100 > start)) && day.equals(days[j])){
                                    row.add(module);
                                    found = true;
                                }
                            }
                        }
                        time.beforeFirst();
                        if(found){
                            break;
                        }
                    }
                    if(!found){
                        row.add("");
                    }
                }
                Timetable.getItems().add(row);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void Back() throws Exception {
        main.GoToMaya(ID, "Student");
    }

    //Initialize the timetable column
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TimeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        Monday.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
        Tuesday.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));
        Wednesday.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(3)));
        Thursday.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(4)));
        Friday.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(5)));
        Saturday.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(6)));
        Sunday.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(7)));
    }
}
