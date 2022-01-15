package com.example.maya2;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class ModifyMenuController extends SearchMenuController {
    @FXML
    public void GoToEdit() throws Exception {
        if (!SearchTable.getSelectionModel().isEmpty()) {
            ObservableList<String> list = SearchTable.getSelectionModel().getSelectedItem();
            main.GoToEdit(list, ID);
        }
    }

    @FXML
    public void GoToCreate() throws Exception {
        main.GoToCreate(ID);
    }

    @FXML
    public void Delete(){
        if (!SearchTable.getSelectionModel().isEmpty()) {
            ObservableList<String> list = SearchTable.getSelectionModel().getSelectedItem();
            DBConnector dbConnector = new DBConnector();
            dbConnector.DeleteQuery(list.get(0), list.get(1), list.get(2));
            SearchTable.getItems().removeAll(SearchTable.getSelectionModel().getSelectedItems());
        }
    }
}

