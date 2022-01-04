package com.example.maya2;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ModifyMenuController extends SearchMenuController {
    @FXML
    public void GoToEdit(ActionEvent actionEvent) throws Exception {
        if (!SearchTable.getSelectionModel().isEmpty()) {
            ObservableList<String> list = SearchTable.getSelectionModel().getSelectedItem();
            main.GoToEdit(list, ID);
        }
    }

    @FXML
    public void GoToCreate(ActionEvent actionEvent) throws Exception {
        main.GoToCreate(ID);
    }

    @FXML
    public void Delete(ActionEvent actionEvent){
        if (!SearchTable.getSelectionModel().isEmpty()) {
            ObservableList<String> list = SearchTable.getSelectionModel().getSelectedItem();
            DBConnector dbConnector = new DBConnector();
            dbConnector.DeleteQuery(list.get(0), list.get(1), list.get(2));
            SearchTable.getItems().removeAll(SearchTable.getSelectionModel().getSelectedItems());
        }
    }
}

