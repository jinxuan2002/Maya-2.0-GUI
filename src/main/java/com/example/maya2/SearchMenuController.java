package com.example.maya2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchMenuController implements Initializable {
    protected MainApplication main;
    protected String ID;
    private String session;
    @FXML private TextField SearchField;
    @FXML protected TableView<ObservableList<String>> SearchTable;
    @FXML protected TableColumn TableModule;
    @FXML protected TableColumn TableOcc;
    @FXML protected TableColumn TableMode;
    @FXML protected TableColumn TableDay;
    @FXML protected TableColumn TableStart;
    @FXML protected TableColumn TableEnd;
    @FXML protected TableColumn TableLecturer;
    @FXML protected TableColumn TableTarget;
    @FXML protected TableColumn TableActual;



    public void setApp(MainApplication main){
        this.main = main;
    }

    public void setIDSession(String ID, String session){
        this.ID = ID;
        this.session = session;
    }

    @FXML
    public void Back() throws Exception {
        main.GoToMaya(ID, session);
    }

    //Search the database based on the search text field and add them to the table
    @FXML
    public void Search(){
        DBConnector dbConnector = new DBConnector();
        ResultSet rs = dbConnector.SearchQuery(SearchField.getText());
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        try{
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
            tableColumns[i].setCellValueFactory((Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param
                    -> new SimpleStringProperty(param.getValue().get(j).toString()));
        }

        SearchTable.setItems(data);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
