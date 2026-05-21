module org.example.proyectofinalparque {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.proyectofinalparque            to javafx.fxml;
    opens org.example.proyectofinalparque.controller to javafx.fxml;
    exports org.example.proyectofinalparque;
}
