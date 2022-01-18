package com.example.maya2;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class StatsMenuController implements Initializable {
    private MainApplication main;
    private String ID;
    @FXML private TextField SearchField;
    @FXML private TableView<ObservableList<String>> SearchTable;
    @FXML private TableColumn<ObservableList<String>, String> ModuleColumn;
    @FXML private BarChart<String, Number> barChart;

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setApp(MainApplication main) {
        this.main = main;
    }

    //Search the database for distinct module type and add them to the table
    public void Search(){
        DBConnector dbConnector = new DBConnector();
        ResultSet rs = dbConnector.SearchDistinctModule(SearchField.getText());
        SearchTable.getItems().clear();
        try{
            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                String module = rs.getString("Module");
                row.addAll(module);
                SearchTable.getItems().add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Display data on the bar chart based on the row selected in the table
    public void DisplayData(ObservableList<String> list){
        DBConnector dbConnector = new DBConnector();
        String module = list.get(0);
        ResultSet rs = dbConnector.SearchQuery(module);
        Collection<XYChart.Data<String, Number>> tempList = new ArrayList<>();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().clear();
        barChart.getData().clear();
        barChart.setBarGap(0.0);
        try{
            series.setName(module);
            while(rs.next()){
                String occ = rs.getString("Occurrence");
                int actual = rs.getInt("Actual");
                tempList.add((new XYChart.Data<>(occ, actual)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        series.getData().addAll(tempList);
        barChart.getData().add(series);
    }

    @FXML
    public void Back() throws Exception {
        main.GoToMaya(ID, "Staff");
    }

    //Initialize bar chart and table
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ModuleColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        barChart.getXAxis().setLabel("Occurrence");
        barChart.getYAxis().setLabel("Actual");
        barChart.setAnimated(false);

        SearchTable.setRowFactory(tv -> {
            TableRow<ObservableList<String>> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton()== MouseButton.PRIMARY) {

                    ObservableList<String> module = row.getItem();
                    DisplayData(module);
                }
            });
            return row;
        });
    }
}
