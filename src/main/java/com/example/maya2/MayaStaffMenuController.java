package com.example.maya2;

import javafx.fxml.FXML;

//Extends from maya student menu controller since they are the same anyway
public class MayaStaffMenuController extends MayaStudentMenuController {
    @FXML
    public void GoToModify() throws Exception {
        main.GoToModify(ID,session);
    }

    @FXML
    public void GoToViewStaffModule() throws Exception {
        main.GoToViewStaffModule(ID, session);
    }

    @FXML
    public void GoToStatsMenu() throws Exception{
        main.GoToStatsMenu(ID);
    }
}
