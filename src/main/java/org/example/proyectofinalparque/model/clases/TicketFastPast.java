package org.example.proyectofinalparque.model.clases;

import org.example.proyectofinalparque.model.enums.TipoTicket;
import java.util.ArrayList;
import java.util.List;

public class TicketFastPast extends Ticket {
    private List<String> atraccHabilitadas;
    private int prioridadCola;

    public TicketFastPast(String id, double precio) {
        super(id, TipoTicket.FAST_PASS, precio);
        this.atraccHabilitadas = new ArrayList<>();
        this.prioridadCola = 1;
    }

    public List<String> getAtraccHabilitadas() { return atraccHabilitadas; }
    public void setAtraccHabilitadas(List<String> atraccHabilitadas) { this.atraccHabilitadas = atraccHabilitadas; }
    public int getPrioridadCola() { return prioridadCola; }
    public void setPrioridadCola(int prioridadCola) { this.prioridadCola = prioridadCola; }

    public boolean tienePrioridad(String idAtraccion) {
        return atraccHabilitadas.contains(idAtraccion);
    }

    public void agregarAtraccionHabilitada(String idAtraccion) {
        atraccHabilitadas.add(idAtraccion);
    }

    @Override
    public double calcDescuento() { return 0.0; }
}
