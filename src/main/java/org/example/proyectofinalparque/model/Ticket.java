package org.example.proyectofinalparque.model;

import java.time.LocalDate;

public abstract class Ticket {
    protected String id;
    protected TipoTicket tipo;
    protected double precio;
    protected boolean activo;
    protected java.time.LocalDate fechaCompra;

    public Ticket(String id, TipoTicket tipo, double precio) {
        this.id = id;
        this.tipo = tipo;
        this.precio = precio;
        this.activo = true;
        this.fechaCompra = LocalDate.now();
    }


    public String getId() {
        return id;
    }

    public TipoTicket getTipo() {
        return tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isActivo() {
        return activo;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    //METODO CALCULAR DESCUENTO ABSTRACTO
    public abstract double calcDescuento();

    @Override
    public String toString() {return "Ticket[" + tipo + "] id=" + id + " activo=" + activo;}


}
