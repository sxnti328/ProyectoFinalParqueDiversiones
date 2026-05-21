package org.example.proyectofinalparque.controller;

import org.example.proyectofinalparque.HelloApplication;

public class MainController {

    public void irAVisitante() {
        HelloApplication.mostrarVisitante();
    }

    public void irAOperador() {
        HelloApplication.mostrarOperador();
    }

    public void irAAdmin() {
        HelloApplication.mostrarAdmin();
    }
}
