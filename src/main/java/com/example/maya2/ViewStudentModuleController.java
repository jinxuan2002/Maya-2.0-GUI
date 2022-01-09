package com.example.maya2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import javax.xml.transform.Result;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewStudentModuleController extends SearchMenuController{
    public void initializeModule() {
        DBConnector dbConnector = new DBConnector();
        ResultSet rs = dbConnector.SearchRegistered(ID);
        ArrayList<ResultSet> resultSetArrayList = new ArrayList<ResultSet>();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
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
            for(ResultSet resultSet : resultSetArrayList){
                while(resultSet.next()){
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for(int i = 2 ; i <= resultSet.getMetaData().getColumnCount() - 2; i++){
                        row.add(resultSet.getString(i));
                    }
                    data.add(row);
                }
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
}