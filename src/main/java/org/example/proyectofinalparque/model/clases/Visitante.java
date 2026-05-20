package org.example.proyectofinalparque.model.clases;
import org.example.proyectofinalparque.model.clasesAbstractas.Persona;
public class Visitante extends Persona {

    private double estatura;
    private double saldoVirtual;


    public Visitante(String nombre, String documento, int edad, double estatura, double saldoVirtual) {
        super(nombre, documento, edad);
        this.estatura = estatura;
        this.saldoVirtual = saldoVirtual;
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

    @Override
    public String toString() {
        return super.toString() + " | Estatura: " + estatura + "m | Saldo: $" + saldoVirtual;
    }
}


