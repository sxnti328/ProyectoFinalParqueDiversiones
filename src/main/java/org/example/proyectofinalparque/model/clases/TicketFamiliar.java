package org.example.proyectofinalparque.model.clases;

import org.example.proyectofinalparque.model.enums.TipoTicket;

public class TicketFamiliar extends Ticket {

    private int    numIntegrantes;
    private String condicionesDescuento;

    public TicketFamiliar(String id, double precio, int numIntegrantes, String condicionesDescuento) {
        super(id, TipoTicket.FAMILIAR, precio);
        this.numIntegrantes      = numIntegrantes;
        this.condicionesDescuento = condicionesDescuento;
    }

    @Override
    public double calcDescuento() {
        // El descuento depende del numero de integrantes
        // 2 integrantes: 5%, 3: 10%, 4+: 15%
        double porcentaje;
        if (numIntegrantes <= 2) {
            porcentaje = 0.05;
        } else if (numIntegrantes == 3) {
            porcentaje = 0.10;
        } else {
            porcentaje = 0.15;
        }
        return precio * porcentaje;
    }

    public int    getNumIntegrantes()      { return numIntegrantes; }
    public void   setNumIntegrantes(int n) { this.numIntegrantes = n; }
    public String getCondicionesDescuento()               { return condicionesDescuento; }
    public void   setCondicionesDescuento(String cond)    { this.condicionesDescuento = cond; }

    @Override
    public String toString() {
        double descuento = calcDescuento();
        double porcentajeDescuento = (descuento / precio) * 100;
        return super.toString() + " | Integrantes=" + numIntegrantes
                + " | Descuento=" + String.format("%.0f", porcentajeDescuento) + "% ($"
                + String.format("%.0f", descuento) + ")";
    }
}
