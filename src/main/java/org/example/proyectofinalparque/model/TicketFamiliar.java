package org.example.proyectofinalparque.model;

public class TicketFamiliar extends Ticket {
    private int numIntegrantes;
    private String condicionesDescuento;

    public TicketFamiliar(String id, double precio, int numIntegrantes, String condicionesDescuento) {
        super(id, TipoTicket.FAMILIAR, precio);
        this.numIntegrantes = numIntegrantes;
        this.condicionesDescuento = condicionesDescuento;
    }

    public int getNumIntegrantes() {
        return numIntegrantes;
    }

    public void setNumIntegrantes(int numIntegrantes) {
        this.numIntegrantes = numIntegrantes;
    }

    public String getCondicionesDescuento() {
        return condicionesDescuento;
    }

    public void setCondicionesDescuento(String condicionesDescuento) {
        this.condicionesDescuento = condicionesDescuento;
    }

    @Override
    public double calcDescuento() {
        return precio * 0.15;
    }


    public void cambiarEstado(EstadoAtraccion estado) {
        this.estado = estado;

    }

    public void incrementarContador() {
        this.contadorVisitantes++;
        verificarMantenimiento();

        public void verificarMantenimiento () {
            if (contadorVisitantes >= 500) {
                this.estado = EstadoAtraccion.EN_MANTENIMIENTO;
                this.motivoCierre = "Mantenimiento preventivo: 500 visitantes alcanzados";
            }
        }
    }
}
