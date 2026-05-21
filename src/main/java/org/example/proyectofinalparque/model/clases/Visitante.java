package org.example.proyectofinalparque.model.clases;

import org.example.proyectofinalparque.model.clasesAbstractas.Persona;
import java.util.ArrayList;

public class Visitante extends Persona {
    private double estatura;
    private double saldoVirtual;
    private ArrayList<Ticket> listTickets;

    public Visitante(String nombre, String documento, int edad, double estatura, double saldoVirtual) {
        super(nombre, documento, edad);
        this.estatura = estatura;
        this.saldoVirtual = saldoVirtual;
        this.listTickets = new ArrayList<>();
    }

    public boolean comprarTicket(Ticket ticket) {
        double precioFinal = ticket.getPrecio() - ticket.calcDescuento();
        if (saldoVirtual < precioFinal) return false;
        saldoVirtual -= precioFinal;
        listTickets.add(ticket);
        return true;
    }

    public double getEstatura() {
        return estatura;
    }
    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }
    public double getSaldoVirtual() {
        return saldoVirtual;
    }
    public void setSaldoVirtual(double saldoVirtual) {
        this.saldoVirtual = saldoVirtual;
    }
    public ArrayList<Ticket> getListTickets() {
        return listTickets;
    }
    public void setListTickets(ArrayList<Ticket> listTickets) {
        this.listTickets = listTickets;
    }

    @Override
    public String toString() {
        return super.toString() + " | Estatura: " + estatura + "m | Saldo: $" + saldoVirtual;
    }
}
