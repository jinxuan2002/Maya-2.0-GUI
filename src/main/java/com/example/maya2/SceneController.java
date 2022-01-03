package com.example.maya2;

import javafx.scene.*;
import javafx.scene.layout.*;
import java.util.*;

public class SceneController {
    private HashMap<String, Pane> sceneMap = new HashMap<>();
    private Scene scene;

    public SceneController(Scene scene) {
        this.scene = scene;
    }

    protected void addScene(String name, Pane pane){
        sceneMap.put(name, pane);
    }

    protected void removeScene(String name){
        sceneMap.remove(name);
    }

    protected void activate(String name){
        scene.setRoot( sceneMap.get(name) );
    }
}
