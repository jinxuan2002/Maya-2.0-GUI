package com.example.maya2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewStaffModuleController extends SearchMenuController {

    //Initialize the table with the module that matches the staff name
    public void InitializeModule(){
        DBConnector dbConnector = new DBConnector();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        ResultSet idList = dbConnector.FindStaff(ID);

        try{
            idList.next();
            String fullName = idList.getString("fullName");
            ResultSet rs = dbConnector.SearchLecturer(fullName);
            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 2 ; i <= rs.getMetaData().getColumnCount() - 2; i++){
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }

        TableColumn[] tableColumns = {TableModule, TableOcc, TableMode, TableDay, TableStart, TableEnd, TableLecturer, TableTarget, TableActual};
        for(int i = 0; i < tableColumns.length; i++) {
            final int j = i;
            tableColumns[i].setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param
                    -> new SimpleStringProperty(param.getValue().get(j).toString()));
        }

        SearchTable.setItems(data);
    }

    //Transfer the data from the selected row to the view student list menu
    @FXML
    public void GoToViewStudentList() throws Exception {
        if(!SearchTable.getSelectionModel().isEmpty()){
            ObservableList<String> selected = SearchTable.getSelectionModel().getSelectedItem();
            main.GoToViewStudentList(ID, selected);
        }
    }
}
