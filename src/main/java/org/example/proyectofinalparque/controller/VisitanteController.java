package org.example.proyectofinalparque.controller;

import org.example.proyectofinalparque.model.clases.ParqueDeAtraccion;
import org.example.proyectofinalparque.model.clases.Visitante;
import org.example.proyectofinalparque.model.enums.TipoTicket;

import java.util.ArrayList;

// Controlador del visitante - solo logica de negocio (sin JavaFX)
public class VisitanteController {

    private ParqueDeAtraccion parque;

    public VisitanteController(ParqueDeAtraccion parque) {
        this.parque = parque;
    }

    public boolean crearVisitante(Visitante v) {
        return parque.agregarVisitante(v);
    }

    public ArrayList<Visitante> obtenerListaVisitantes() {
        return parque.getListVisitante();
    }

    public boolean eliminarVisitante(String documento) {
        return parque.eliminarVisitante(documento);
    }

    public boolean actualizarVisitante(String documento, String nombre, int edad,
                                       double estatura, String telefono, String direccion) {
        return parque.actualizarVisitante(documento, nombre, edad, estatura, telefono, direccion);
    }

    public String comprarTicket(String documento, TipoTicket tipo, double precio) {
        return parque.venderTicket(documento, tipo, precio, 4);
    }
}
