package org.example.proyectofinalparque;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Tech-Park UQ - Sistema de Gestión");
        primaryStage.setResizable(false);
        mostrarMenuPrincipal();
        primaryStage.show();
    }

    public static void mostrarMenuPrincipal() {
        cambiarEscena("menu-principal.fxml", 600, 480);
    }

    public static void mostrarVisitante() {
        cambiarEscena("CrudVisitante.fxml", 650, 760);
    }

    public static void mostrarOperador() {
        cambiarEscena("operador-view.fxml", 900, 680);
    }

    public static void mostrarAdmin() {
        cambiarEscena("admin-view.fxml", 1000, 740);
    }

    private static void cambiarEscena(String fxml, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(fxml));
            Scene scene = new Scene(loader.load(), width, height);
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
        } catch (IOException e) {
            System.err.println("Error cargando escena " + fxml + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
