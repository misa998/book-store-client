package com.misa.store.db;

import java.io.IOException;
import java.net.*;

import javafx.fxml.FXML;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Connect {
    public static String get(String urlToRead) throws IOException{
        StringBuilder result = new StringBuilder();

        URL url = new URL(urlToRead);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while((line = br.readLine()) != null) {
            result.append(line);
        }

        br.close();
        return result.toString();
    }

    public static String post(String url, String payload) throws Exception{
        try(CloseableHttpClient client = HttpClientBuilder.create().build()){
            HttpPost request = new HttpPost(url);
            request.setHeader("User-Agent", "Java client");
            request.setHeader("Content-type", "application/json");
            request.setEntity(new StringEntity(payload));
            HttpResponse response = client.execute(request);

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            return sb.toString();
        }
    }

    public static String delete(String urlToRead) throws IOException {
        StringBuilder result = new StringBuilder();

        URL url = new URL(urlToRead);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while((line = br.readLine()) != null) {
            result.append(line);
        }

        br.close();
        return result.toString();
    }

    public static void ping(String host, int port){
        new Thread("Ping thread") {
            @Override
            public void run() {
                try (Socket socket = new Socket()) {
                    socket.connect(new InetSocketAddress(host, port));
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Server is down",
                            "Error", JOptionPane.WARNING_MESSAGE);
                } catch (IllegalArgumentException iae) {
                    JOptionPane.showMessageDialog(null, "Invalid arguments",
                            "Error", JOptionPane.WARNING_MESSAGE);
                } catch (SecurityException se) {
                    JOptionPane.showMessageDialog(null, "Access denied",
                            "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }.start();
    }
}
