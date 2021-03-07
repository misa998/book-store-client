package com.misa.store;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader;
        try {
            loader = new FXMLLoader(getClass().getResource("ui/gui.fxml"));
        } catch (Exception e){
            e.printStackTrace();
            return;
        }

        Parent root = loader.load();
        primaryStage.setTitle("Rest desktop client");
        primaryStage.setScene(new Scene(root, 700, 290));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
