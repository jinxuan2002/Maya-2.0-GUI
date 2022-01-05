package com.example.maya2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterModuleController implements Initializable {
    private MainApplication main;
    private String ID;
    @FXML private TextField SearchField;
    @FXML private TableView<ObservableList<String>> SearchTable;
    @FXML private TableColumn SearchModuleColumn;
    @FXML private TableColumn SearchOccColumn;

    public void setApp(MainApplication main){
        this.main = main;
    }

    public void setID(String I){
        this.ID = ID;
    }

    public void Back(ActionEvent actionEvent) throws Exception {
        main.GoToMaya(ID, "Student");
    }

    @FXML
    public void Search(ActionEvent actionEvent){
        DBConnector dbConnector = new DBConnector();
        ResultSet rs = dbConnector.SearchQuery(SearchField.getText());
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        try{
            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1 ; i <= rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }

        TableColumn[] tableColumns = {SearchModuleColumn, SearchOccColumn};
        for(int i = 0; i < tableColumns.length; i++) {
            final int j = i;
            tableColumns[i].setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param
                    -> new SimpleStringProperty(param.getValue().get(j).toString()));
        }

        SearchTable.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
