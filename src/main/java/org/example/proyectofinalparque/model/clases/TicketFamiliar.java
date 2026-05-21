package org.example.proyectofinalparque.model.clases;

import org.example.proyectofinalparque.model.enums.TipoTicket;

public class TicketFamiliar extends Ticket {
    private int numIntegrantes;
    private String condicionesDescuento;

    public TicketFamiliar(String id, double precio, int numIntegrantes, String condicionesDescuento) {
        super(id, TipoTicket.FAMILIAR, precio);
        this.numIntegrantes = numIntegrantes;
        this.condicionesDescuento = condicionesDescuento;
    }

    public int getNumIntegrantes() { return numIntegrantes; }
    public void setNumIntegrantes(int numIntegrantes) { this.numIntegrantes = numIntegrantes; }
    public String getCondicionesDescuento() { return condicionesDescuento; }
    public void setCondicionesDescuento(String condicionesDescuento) { this.condicionesDescuento = condicionesDescuento; }

    @Override
    public double calcDescuento() {
        return precio * 0.15;
    }
}
