package org.example.proyectofinalparque.model.clases;

import org.example.proyectofinalparque.model.interfaces.IGestionable;
import java.util.ArrayList;

public class Zona implements IGestionable {
    private String idZona;
    private String nombre;
    private String descripcion;
    private int capacidadMax;
    private int visitantesActuales;
    private ArrayList<Atraccion> listAtraccion;
    private ArrayList<Operador> listOperador;

    public Zona(String idZona, String nombre, String descripcion, int capacidadMax) {
        this.idZona = idZona;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.capacidadMax = capacidadMax;
        this.visitantesActuales = 0;
        this.listAtraccion = new ArrayList<>();
        this.listOperador = new ArrayList<>();
    }

    @Override
    public void agregar(Object elemento) {
        if (elemento instanceof Atraccion) {
            listAtraccion.add((Atraccion) elemento);
        } else if (elemento instanceof Operador) {
            listOperador.add((Operador) elemento);
        }
    }

    @Override
    public boolean eliminar(String id) {
        boolean seElimino = false;

        for (int i = 0; i < listAtraccion.size(); i++) {
            if (listAtraccion.get(i).getId().equals(id)) {
                listAtraccion.remove(i);
                seElimino = true;
                break;
            }
        }

        for (int i = 0; i < listOperador.size(); i++) {
            if (listOperador.get(i).getIdEmpleado().equals(id)) {
                listOperador.remove(i);
                seElimino = true;
                break;
            }
        }

        return seElimino;
    }

    @Override
    public int getTotalElementos() {
        return listAtraccion.size() + listOperador.size();
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
        this.listOperador = listOperador; }

    @Override
    public String toString() {
        return "Zona:" + nombre + " capacidad=" + capacidadMax + " visitantes=" + visitantesActuales;
    }
}
