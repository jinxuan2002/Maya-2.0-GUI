package com.example.maya2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    @FXML private TableView<ObservableList<String>> RegisteredTable;
    @FXML private TableColumn<ObservableList<String>, String> SearchModuleColumn;
    @FXML private TableColumn<ObservableList<String>, String> SearchOccColumn;
    @FXML private TableColumn<ObservableList<String>, String> RegisteredModule;
    @FXML private TableColumn<ObservableList<String>, String> RegisteredOcc;

    public void setApp(MainApplication main){
        this.main = main;
    }

    public void setID(String ID){
        this.ID = ID;
    }

    public void Back(ActionEvent actionEvent) throws Exception {
        main.GoToMaya(ID, "Student");
    }

    @FXML
    public void Search(ActionEvent actionEvent){
        DBConnector dbConnector = new DBConnector();
        ResultSet rs = dbConnector.SearchDistinctQuery(SearchField.getText());
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

    @FXML
    public void Add(){
        if (!SearchTable.getSelectionModel().isEmpty()) {
            DBConnector dbConnector = new DBConnector();
            ObservableList<String> selected = SearchTable.getSelectionModel().getSelectedItem();
            ResultSet rs = dbConnector.SearchQuery(selected.get(0));
            ResultSet student = dbConnector.FindStudent(ID);

            boolean valid = true;
            for(int i = 0; i < RegisteredTable.getItems().size(); i++){
                if(RegisteredTable.getItems().get(i).get(0).equals(selected.get(0))){
                    valid = false;
                    break;
                }
            }

            try{
                if(rs.isBeforeFirst()) {
                    rs.next();
                    student.next();
                    String programme = rs.getString("Programme");
                    int studentMuet = student.getInt("muet");
                    int muet = rs.getInt("Muet");
                    if (!student.getString("programme").equals(programme) && programme != null) {
                        valid = false;
                    } else if(muet == 5 && !(studentMuet >= muet)){
                        valid = false;
                    } else if(studentMuet != muet && muet != 0){
                        valid = false;
                    }
                }
            } catch (SQLException e){
                e.printStackTrace();
            }

            if(valid){
                RegisteredTable.getItems().add(selected);
            }
        }
    }

    @FXML
    public void Save(ActionEvent event){
        ObservableList<ObservableList<String>> list =  RegisteredTable.getItems();
        DBConnector dbConnector = new DBConnector();
        dbConnector.DeleteModuelForID(ID);
        for(int i = 0; i < list.size(); i++){
            String module = list.get(i).get(0);
            String occ = list.get(i).get(1);
            dbConnector.AddModuleForID(ID, module, occ);
        }
    }

    @FXML
    public void Drop(){
        if (!RegisteredTable.getSelectionModel().isEmpty()) {
            RegisteredTable.getItems().removeAll(RegisteredTable.getSelectionModel().getSelectedItems());
        }
    }

    public void InitializeDisplayTable(){
        DBConnector dbConnector = new DBConnector();
        ResultSet rs = dbConnector.SearchRegistered(ID);

        try{
            while(rs.next()){
                ObservableList<String> list = FXCollections.observableArrayList();
                list.add(rs.getString(1));
                list.add(rs.getString(2));
                RegisteredTable.getItems().add(list);

            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RegisteredModule.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        RegisteredOcc.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
    }
}
