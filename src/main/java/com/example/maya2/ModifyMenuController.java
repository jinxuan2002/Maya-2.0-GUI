package com.example.maya2;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ModifyMenuController extends SearchMenuController {
    public void GoToEdit(ActionEvent actionEvent) throws Exception {
        if (!SearchTable.getSelectionModel().isEmpty()) {
            ObservableList<String> list = SearchTable.getSelectionModel().getSelectedItem();
            main.GoToEdit(list, ID);
        }
    }
}

