package org.example.proyectofinalparque;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Parque de Diversiones");
        mostrarMenuPrincipal();
        primaryStage.show();
    }

    public static void mostrarMenuPrincipal() {
        cambiarEscena("menu-principal.fxml");
    }

    public static void mostrarVisitante() {
        cambiarEscena("CrudVisitante.fxml");
    }

    public static void mostrarOperador() {
        cambiarEscena("operador-view.fxml");
    }

    public static void mostrarAdmin() {
        cambiarEscena("admin-view.fxml");
    }

    private static void cambiarEscena(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxml));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
