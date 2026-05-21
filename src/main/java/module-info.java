module org.example.proyectofinalparque {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.proyectofinalparque                            to javafx.fxml;
    opens org.example.proyectofinalparque.viewController             to javafx.fxml;
    opens org.example.proyectofinalparque.controller                 to javafx.fxml;
    opens org.example.proyectofinalparque.model.clases               to javafx.base;
    opens org.example.proyectofinalparque.model.clasesAbstractas     to javafx.base;
    opens org.example.proyectofinalparque.model.enums                to javafx.base;
    opens org.example.proyectofinalparque.model.records              to javafx.base;

    exports org.example.proyectofinalparque;
    exports org.example.proyectofinalparque.viewController;
    exports org.example.proyectofinalparque.controller;
}
