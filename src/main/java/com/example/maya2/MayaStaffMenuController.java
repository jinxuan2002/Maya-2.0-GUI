package com.example.maya2;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.text.*;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.util.ResourceBundle;

public class MayaStaffMenuController extends MayaStudentMenuController {
    @FXML
    public void GoToModify(ActionEvent actionEvent) throws Exception {
        main.GoToModify(ID,session);
    }

    @FXML
    public void GoToViewStaffModule(ActionEvent actionEvent) throws Exception {
        main.GoToViewStaffModule(ID, session);
    }
}
