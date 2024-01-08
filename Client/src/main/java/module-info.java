module dev.xelond.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens dev.xelond.client to javafx.fxml;
    exports dev.xelond.client;
}