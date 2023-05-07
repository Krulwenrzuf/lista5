module com.example.lista5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lista5 to javafx.fxml;
    exports com.example.lista5;
}