package com.misa.store.ui;

import com.misa.store.db.Connect;
import com.misa.store.db.DataBaseProperties;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;

public class CheckStatusDialogController {

    @FXML
    private TextField hostTF;
    @FXML
    private TextField urlTF;
    @FXML
    private TextField portTF;
    @FXML
    private TextField pathTF;
    @FXML
    private Button pingBtn;
    @FXML
    private CheckBox checkBox;

    public void initialize(){
        loadProperties();
    }

    private void loadProperties() {
        DataBaseProperties prop = new DataBaseProperties();
        try {
            prop.getProperties();
        } catch (IOException ioe){
            System.out.println("Error while reading properties.");
            return;
        }
        urlTF.setText(prop.getUrl());
        hostTF.setText(prop.getHost());
        portTF.setText(String.valueOf(prop.getPort()));
        pathTF.setText(prop.getPath());
    }

    public void saveProperties() {
        if(!checkBox.isSelected())
            return;

        DataBaseProperties prop = new DataBaseProperties();
        prop.setUrl(urlTF.getText());
        prop.setHost(hostTF.getText());
        prop.setPath(pathTF.getText());
        prop.setPort(Integer.parseInt(portTF.getText()));

        try {
            prop.setProperties();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void checkStatus() {
        Connect.ping(hostTF.getText(), Integer.parseInt(portTF.getText()));
    }
}
