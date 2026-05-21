package org.example.proyectofinalparque.controller;

import org.example.proyectofinalparque.model.clases.Operador;
import org.example.proyectofinalparque.model.clases.ParqueDeAtraccion;
import org.example.proyectofinalparque.model.clases.Zona;

import java.util.ArrayList;

// Controlador del operador - solo logica de negocio
public class OperadorController {

    private ParqueDeAtraccion parque;

    public OperadorController(ParqueDeAtraccion parque) {
        this.parque = parque;
    }

    public ArrayList<Operador> obtenerListaOperadores() {
        return parque.getListOperador();
    }

    public Zona obtenerZonaDelOperador(Operador op) {
        if (op == null) return null;
        return parque.buscarZona(op.getIdZona());
    }

    public String validarAcceso(String documentoVisitante, String idAtraccion) {
        return parque.ingresarAAtraccion(documentoVisitante, idAtraccion);
    }

    public String registrarRevision(String documentoOperador, String idAtraccion, String descripcion) {
        return parque.registrarRevisionTecnica(documentoOperador, idAtraccion, descripcion);
    }

    public String recargarSaldo(String documentoVisitante, double monto) {
        return parque.recargarSaldoVisitante(documentoVisitante, monto);
    }
}
