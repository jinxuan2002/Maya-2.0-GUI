package com.example.maya2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    @FXML private Label CreditLabel;
    @FXML private Label ErrorLabel;

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
        ResultSet rs = dbConnector.SearchDistinctModuleOcc(SearchField.getText());
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
            ObservableList<ObservableList<String>> registered = RegisteredTable.getItems();
            ResultSet module = dbConnector.SearchQuery(selected.get(0), selected.get(1));
            ResultSet student = dbConnector.FindStudent(ID);
            int credit = Integer.parseInt(CreditLabel.getText().split(" ")[1]);
            ErrorLabel.setText("");

            for(int i = 0; i < RegisteredTable.getItems().size(); i++){
                if(RegisteredTable.getItems().get(i).get(0).equals(selected.get(0))){
                    ErrorLabel.setText("You already registered for the same type of module.");
                    return;
                }
            }

            try{
                for(ObservableList<String> r : registered){
                    ResultSet rs = dbConnector.SearchQuery(r.get(0), r.get(1));
                    while(rs.next()){
                        while(module.next()){
                            if(rs.getString("Day").equals("N/A") || module.getString("Day").equals("N/A")){
                                continue;
                            }
                            int start = module.getInt("Start");
                            int end = module.getInt("End");
                            int registeredStart = rs.getInt("Start");
                            int registeredEnd = rs.getInt("End");
                            if(rs.getString("Day").equals(module.getString("Day")) && ((start >= registeredStart && start < registeredEnd) || (end > registeredStart && end <= registeredEnd))){
                                ErrorLabel.setText("Unable to add module, time clashes with existing registered module.");
                                return;
                            }
                        }
                        module.beforeFirst();
                    }
                }

                module.next();
                student.next();
                String programme = module.getString("Programme");
                int studentMuet = student.getInt("muet");
                int muet = module.getInt("Muet");
                int target = module.getInt("Target");
                int actual = module.getInt("Actual");
                ObservableList<ObservableList<String>> list = FXCollections.observableArrayList();
                list.add(selected);
                if (!student.getString("programme").equals(programme) && !programme.isBlank()) {
                    ErrorLabel.setText("Unable to add module, the selected module requires you to be under a different programme.");
                    return;
                } else if(muet == 5 && !(studentMuet >= muet)){
                    ErrorLabel.setText("Your MUET band does not match with the requirements of the selected module.");
                    return;
                } else if(studentMuet != muet && muet != 0){
                    ErrorLabel.setText("Your MUET band does not match with the requirements of the selected module.");
                    return;
                } else if(CalculateCredit(list) + credit > 22){
                    ErrorLabel.setText("Unable to add module, maximum credit hours will be exceed");
                    return;
                } else if(actual + 1 > target){
                    ErrorLabel.setText("Unable to add moudule, module already reached the target number of students.");
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            RegisteredTable.getItems().add(selected);
            CreditLabel.setText("Credit: " + CalculateCredit(RegisteredTable.getItems()));
        }
    }

    public int CalculateCredit(ObservableList<ObservableList<String>> list){
        DBConnector dbConnector = new DBConnector();
        int credit = 0;
        try{
            for(ObservableList<String> l : list){
                ResultSet rs = dbConnector.SearchQuery(l.get(0), l.get(1));
                while(rs.next()){
                    if(rs.getString("Start").equals("N/A")){
                        credit += 2;
                        continue;
                    }
                    int start = rs.getInt("Start");
                    int end = rs.getInt("End");
                    credit += (int) Math.ceil((end - start) / 100.0);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return credit;
    }

    @FXML
    public void Save(ActionEvent event){
        ObservableList<ObservableList<String>> list =  RegisteredTable.getItems();
        DBConnector dbConnector = new DBConnector();
        ResultSet registered = dbConnector.SearchRegistered(ID);
        try{
            while(registered.next()){
                String module = registered.getString("Module");
                String occ = registered.getString("Occurrence");
                dbConnector.RemoveFromActual(module, occ);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dbConnector.DeleteModuelForID(ID);
        for (ObservableList<String> l : list) {
            String module = l.get(0);
            String occ = l.get(1);
            dbConnector.AddModuleForID(ID, module, occ);
            dbConnector.AddToActual(module, occ);
        }
    }

    @FXML
    public void Drop(){
        if (!RegisteredTable.getSelectionModel().isEmpty()) {
            RegisteredTable.getItems().removeAll(RegisteredTable.getSelectionModel().getSelectedItems());
            CreditLabel.setText("Credit: " + CalculateCredit(RegisteredTable.getItems()));
        }
    }

    public void InitializeDisplayTable(){
        DBConnector dbConnector = new DBConnector();
        ResultSet rs = dbConnector.SearchRegistered(ID);
        try{
            while(rs.next()){
                ObservableList<String> list = FXCollections.observableArrayList();
                list.add(rs.getString(2));
                list.add(rs.getString(3));
                RegisteredTable.getItems().add(list);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        CreditLabel.setText("Credit: " + CalculateCredit(RegisteredTable.getItems()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RegisteredModule.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        RegisteredOcc.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
        ErrorLabel.setText("");
    }
}
