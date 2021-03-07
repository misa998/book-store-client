module BookStoreClient {
    requires org.apache.httpcomponents.httpclient;
    requires java.desktop;
    requires javafx.fxml;
    requires javafx.controls;
    requires org.apache.httpcomponents.httpcore;
    requires com.fasterxml.jackson.databind;

    opens com.misa.store.common;
    opens com.misa.store.ui;

    exports com.misa.store;
}