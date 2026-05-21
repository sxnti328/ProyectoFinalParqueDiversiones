package org.example.proyectofinalparque.model.clases;

import org.example.proyectofinalparque.model.enums.TipoTicket;
import java.time.LocalDate;

public abstract class Ticket {

    protected String     id;
    protected TipoTicket tipo;
    protected double     precio;
    protected boolean    activo;
    protected LocalDate  fechaCompra;

    public Ticket(String id, TipoTicket tipo, double precio) {
        this.id          = id;
        this.tipo        = tipo;
        this.precio      = precio;
        this.activo      = true;
        this.fechaCompra = LocalDate.now();
    }

    public abstract double calcDescuento();

    public double getPrecioFinal() {
        return precio - calcDescuento();
    }

    public String getId()           { return id; }
    public void   setId(String id)  { this.id = id; }
    public TipoTicket getTipo()     { return tipo; }
    public double getPrecio()       { return precio; }
    public void   setPrecio(double p) { this.precio = p; }
    public boolean isActivo()       { return activo; }
    public void    setActivo(boolean a) { this.activo = a; }
    public LocalDate getFechaCompra() { return fechaCompra; }

    @Override
    public String toString() {
        return "Ticket[" + tipo + "] id=" + id + " precio=$" + precio + " activo=" + activo;
    }
}
