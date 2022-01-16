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
import java.util.ResourceBundle;

public class ViewStudentListController implements Initializable {
    private MainApplication main;
    private String ID;
    private ObservableList<String> list;
    @FXML private TableView<ObservableList<String>> StudentTable;
    @FXML private TableColumn<ObservableList<String>, String> ColumnNo;
    @FXML private TableColumn<ObservableList<String>, String> IDColumn;
    @FXML private TableColumn<ObservableList<String>, String> NameColumn;

    public void setApp(MainApplication main) {
        this.main = main;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setList(ObservableList<String> list) {
        this.list = list;
    }

    //Initialize the student list table with data from the database based on the selected values from the previous page
    public void InitializeStudentList(){
        DBConnector dbConnector = new DBConnector();
        ObservableList<String> row = FXCollections.observableArrayList();
        String module = list.get(0);
        String occ = list.get(1);
        ResultSet rs = dbConnector.SearchRegistered(module, occ);
        try{
            while(rs.next()){
                String studentID = rs.getString("studentid");
                ResultSet student = dbConnector.FindStudent(studentID);
                student.next();
                String fullName = student.getString("fullname");
                row.addAll(Integer.toString(1), studentID, fullName);
                StudentTable.getItems().add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Back() throws Exception {
        main.GoToViewStaffModule(ID, "Staff");
    }

    //Initialize the table column
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ColumnNo.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        IDColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
        NameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));
    }
}
