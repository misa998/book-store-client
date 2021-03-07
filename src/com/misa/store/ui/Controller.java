package com.misa.store.ui;

import com.misa.store.db.DataBaseProperties;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.misa.store.common.Book;
import com.misa.store.db.Connect;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
import javafx.scene.layout.BorderPane;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

public class Controller {
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, Integer> tableId;
    @FXML
    private TableColumn<Book, String> tableName;
    @FXML
    private TableColumn<Book, String> tableAuthor;
    @FXML
    private TableColumn<Book, Integer> tableYear;
    @FXML
    private TableColumn<Book, String> tableLanguage;
    @FXML
    private TableColumn<Book, Double> tableScore;
    @FXML
    private TableColumn<Book, String> tableGenre;
    @FXML
    private Button btn;
    @FXML
    private TextField txtFid;
    @FXML
    private TextField txtFname;
    @FXML
    private TextField txtFyear;
    @FXML
    private TextField txtFscore;
    @FXML
    private TextField txtFauthor;
    @FXML
    private TextField txtFgenre;
    @FXML
    private TextField txtFlang;
    @FXML
    private Label statusLabel;
    @FXML
    private BorderPane mainBP;

    private String url = null;
    private String host = null;
    private int port = 0;
    private String path = null;

    public void initialize(){
        setupTableView();
        loadProperties();
    }

    @FXML
    public void retrieve(){
        tableView.getItems().clear();

        Thread th = new Thread("Get method") {
            public void run() {
                try {
                    ObjectMapper om = new ObjectMapper();
                    List<Book> books = om.readValue(Connect.get(url + ":" + port + path), new TypeReference<List<Book>>(){});

                    for(Book book : books){
                        tableView.getItems().add(book);
                    }
                } catch (ConnectException ce){
                    JOptionPane.showMessageDialog(null, "Server is down",
                            "Error", JOptionPane.WARNING_MESSAGE);
                } catch (MalformedURLException malfurl){
                    JOptionPane.showMessageDialog(null, "Bad url",
                            "Error", JOptionPane.WARNING_MESSAGE);
                } catch (IOException ioe){
                    JOptionPane.showMessageDialog(null, "Error while reading",
                            "Error", JOptionPane.WARNING_MESSAGE);
                    ioe.printStackTrace();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getClass(),
                            "Error", JOptionPane.WARNING_MESSAGE);
                    e.printStackTrace();
                }
            }
        };
        th.start();
    }

    @FXML
    public void send(){
        String payload = getPayload();

        Thread th = new Thread("Post method") {
            public void run() {
                try {
                    String response = Connect.post(url + ":" + port + path, payload);
                    ObjectMapper objectMapper = new ObjectMapper();
                    Book book = objectMapper.readValue(response, Book.class);
                    tableView.getItems().add(book);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getClass(),
                            "Error", JOptionPane.WARNING_MESSAGE);
                    e.printStackTrace();
                }
            }
        };
        th.start();
    }

    @FXML
    public void delete() {
        Book book = tableView.getSelectionModel().getSelectedItem();
        if(book == null) {
            JOptionPane.showMessageDialog(null, "Select in item.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try{
            String response = Connect.delete(url + ":" + port + path + "/" + book.getId());
            if(response.equals("OK")){
                JOptionPane.showMessageDialog(null, "Book successfully deleted. Refresh the table.",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getClass(),
                    "Error", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }
    }

    @FXML
    public void put(){
        Book book = tableView.getSelectionModel().getSelectedItem();
        if(book == null) {
            JOptionPane.showMessageDialog(null, "Select in item.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Book bookReceived;
        try {
            bookReceived = objectMapper.readValue(Connect.get(url + ":" + port + path + "/" + book.getId()), Book.class);
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, "Error while reading. Refresh the table.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(bookReceived != null)
            fillTheFields(bookReceived);
    }

    private void fillTheFields(Book book){
        txtFid.setText(String.valueOf(book.getId()));
        txtFname.setText(book.getName());
        txtFyear.setText(String.valueOf(book.getReleaseYear()));
        txtFscore.setText(String.valueOf(book.getUserScore()));
        txtFauthor.setText(book.getAuthor());
        txtFlang.setText(book.getLanguage());
        txtFgenre.setText(book.getGenre());
    }

    private String getPayload(){
        String payload;
        payload = "{" + "\"id\":\"" + txtFid.getText() + "\"," + "\"name\":\"" + txtFname.getText() + "\","
                    + "\"releaseYear\":\"" + txtFyear.getText() + "\"," + "\"userScore\":\"" + txtFscore.getText() + "\","
                    + "\"author\":\"" + txtFauthor.getText() + "\"," + "\"genre\":\"" + txtFgenre.getText() + "\","
                    + "\"language\":\"" + txtFlang.getText() + "\"" + "}";
        return payload;
    }

    private void setupTableView() {
        tableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableYear.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        tableScore.setCellValueFactory(new PropertyValueFactory<>("userScore"));
        tableAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        tableGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        tableLanguage.setCellValueFactory(new PropertyValueFactory<>("language"));
    }

    private void loadProperties() {
        DataBaseProperties prop = new DataBaseProperties();
        try {
            prop.getProperties();
        } catch (IOException ioe){
            System.out.println("Error while reading properties.");
            return;
        }
        url = prop.getUrl();
        host = prop.getHost();
        port = prop.getPort();
        path = prop.getPath();
    }

    @FXML
    public void checkStatusDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBP.getScene().getWindow());
        dialog.setTitle("Ping server.");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("checkStatusDialog.fxml"));
        try{
            dialog.getDialogPane().setContent(loader.load());
        }catch (IOException ioe){
            JOptionPane.showMessageDialog(null, "Unable to load dialog.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            CheckStatusDialogController controller = loader.getController();
            controller.saveProperties();
        }
    }

    @FXML
    public void close(){
        Platform.exit();
    }

    @FXML
    public void showAbout(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBP.getScene().getWindow());
        dialog.setTitle("About.");
        dialog.setHeaderText("Made by Milos. 03.2021.");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }
}
