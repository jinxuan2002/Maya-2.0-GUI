package com.example.maya2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewStaffModuleController extends SearchMenuController {
    public void InitializeModule(){
        DBConnector dbConnector = new DBConnector();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        ResultSet idList = dbConnector.StaffIDQuery(ID);

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

    @FXML
    public void GoToViewStudentList(ActionEvent actionEvent) throws Exception {
        if(!SearchTable.getSelectionModel().isEmpty()){
            ObservableList<String> selected = SearchTable.getSelectionModel().getSelectedItem();
            main.GoToViewStudentList(ID, selected);
        }
    }
}
