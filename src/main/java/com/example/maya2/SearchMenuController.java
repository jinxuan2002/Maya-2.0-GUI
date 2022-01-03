package com.example.maya2;

import javafx.event.ActionEvent;
import javafx.fxml.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchMenuController implements Initializable {
    private MainApplication main;
    private String ID;
    private String session;
    public void setApp(MainApplication main){
        this.main = main;
    }

    public void setIDSession(String ID, String session){
        this.ID = ID;
        this.session = session;
    }

    @FXML
    public void Back(ActionEvent actionEvent) throws Exception {
        main.GoToMaya(ID, session);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
