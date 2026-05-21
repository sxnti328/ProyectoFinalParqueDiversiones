package org.example.proyectofinalparque;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.proyectofinalparque.model.clases.ParqueDeAtraccion;
import org.example.proyectofinalparque.viewController.AdminViewController;
import org.example.proyectofinalparque.viewController.MainViewController;
import org.example.proyectofinalparque.viewController.OperadorViewController;
import org.example.proyectofinalparque.viewController.VisitanteViewController;

public class App extends Application {

    private Stage primaryStage;

    // Modelo principal compartido por todas las vistas
    public ParqueDeAtraccion parque;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Parque de Diversiones - Tech-Park UQ");
        inicializarData();
        mostrarMenuPrincipal();
        this.primaryStage.show();
    }

    private void inicializarData() {
        this.parque = Parque.get();
    }

    public void mostrarMenuPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("menu-principal.fxml"));
            Parent root = loader.load();
            MainViewController vc = loader.getController();
            vc.setApp(this);
            primaryStage.setScene(new Scene(root));
            primaryStage.sizeToScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarVisitante() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("CrudVisitante.fxml"));
            Parent root = loader.load();
            VisitanteViewController vc = loader.getController();
            vc.setApp(this);
            primaryStage.setScene(new Scene(root));
            primaryStage.sizeToScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarOperador() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("operador-view.fxml"));
            Parent root = loader.load();
            OperadorViewController vc = loader.getController();
            vc.setApp(this);
            primaryStage.setScene(new Scene(root));
            primaryStage.sizeToScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("admin-view.fxml"));
            Parent root = loader.load();
            AdminViewController vc = loader.getController();
            vc.setApp(this);
            primaryStage.setScene(new Scene(root));
            primaryStage.sizeToScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
