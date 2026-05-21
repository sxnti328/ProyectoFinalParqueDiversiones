package org.example.proyectofinalparque.viewController;

import javafx.fxml.FXML;
import org.example.proyectofinalparque.App;

public class MainViewController {

    private App app;

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    private void irAVisitante() {
        app.mostrarVisitante();
    }

    @FXML
    private void irAOperador() {
        app.mostrarOperador();
    }

    @FXML
    private void irAAdmin() {
        app.mostrarAdmin();
    }
}
