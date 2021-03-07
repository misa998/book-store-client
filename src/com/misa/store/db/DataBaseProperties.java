package com.misa.store.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DataBaseProperties {
    private String url = null;
    private String host = null;
    private int port;
    private String path = null;
    private String pathToFile = "resources/config.properties";

    FileInputStream fis;

    public void getProperties() throws IOException {
        try{
            fis = new FileInputStream("resources/config.properties");
            Properties properties = new Properties();

            if(fis != null){
                properties.load(fis);
            } else{
                throw new NullPointerException("InputStream is null");
            }

            setUrl(properties.getProperty("url"));
            setHost(properties.getProperty("host"));
            setPort(Integer.parseInt(properties.getProperty("port")));
            setPath(properties.getProperty("path"));

            if(url == null || port == 0 || path == null)
                throw new NullPointerException("Empty");

        } catch (NullPointerException npe){
            System.out.println("File is empty");
            npe.printStackTrace();
        } catch (FileNotFoundException fnfe){
            System.out.println("Config file not found");
        } catch (IOException ioe){
            System.out.println("Error while reading configuration file.");
        } finally {
            if(fis != null)
                fis.close();
        }
    }

    public void setProperties() {
        try (FileInputStream input = new FileInputStream(pathToFile)) {
            Properties properties = new Properties();
            properties.load(input);

            properties.replace("url", getUrl());
            properties.replace("host", getHost());
            properties.replace("port", String.valueOf(getPort()));
            properties.replace("path", getPath());

            properties.store(new FileOutputStream(pathToFile), "server props");
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            System.out.println("Config file not found");
        } catch (IOException ioe) {
            System.out.println("Error while reading configuration file.");
        }
    }

    public DataBaseProperties()  {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "DataBaseProperties{" +
                "url='" + url + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", path='" + path +
                '}';
    }
}
