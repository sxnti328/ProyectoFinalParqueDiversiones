package org.example.proyectofinalparque.model;

import java.util.ArrayList;

public class Zona {

    private String idZona;
    private String nombre;
    private String descripcion;
    private int capacidadMax;
    private int visitantesActuales;
    private ArrayList<Atraccion> listAtraccion;
    private ArrayList<Operador> listOperador;


    public Zona(String idZona, String nombre, String descripcion, int capacidadMax, int visitantesActuales, ArrayList<Atraccion> listAtraccion, ArrayList<Operador> listOperador) {
        this.idZona = idZona;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.capacidadMax = capacidadMax;
        this.visitantesActuales = visitantesActuales;
        this.listAtraccion = listAtraccion;
        this.listOperador = listOperador;
    }

    public String getIdZona() {
        return idZona;
    }

    public void setIdZona(String idZona) {
        this.idZona = idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCapacidadMax() {
        return capacidadMax;
    }

    public void setCapacidadMax(int capacidadMax) {
        this.capacidadMax = capacidadMax;
    }

    public int getVisitantesActuales() {
        return visitantesActuales;
    }

    public void setVisitantesActuales(int visitantesActuales) {
        this.visitantesActuales = visitantesActuales;
    }

    public ArrayList<Atraccion> getListAtraccion() {
        return listAtraccion;
    }

    public void setListAtraccion(ArrayList<Atraccion> listAtraccion) {
        this.listAtraccion = listAtraccion;
    }

    public ArrayList<Operador> getListOperador() {
        return listOperador;
    }

    public void setListOperador(ArrayList<Operador> listOperador) {
        this.listOperador = listOperador;
    }
}
