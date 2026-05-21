package org.example.proyectofinalparque.model.clases;

import java.util.ArrayList;

public class ParqueDeAtraccion {
    private String nombre;
    private String nit;
    private String direccion;
    private ArrayList<Visitante> listVisitante;
    private ArrayList<Administrador> listAdmin;
    private ArrayList<Zona> listZona;

    public ParqueDeAtraccion(String nombre, String nit, String direccion) {
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.listVisitante = new ArrayList<>();
        this.listAdmin = new ArrayList<>();
        this.listZona = new ArrayList<>();
    }

    // CRUD Visitante
    public Visitante buscarVisitante(String documento) {
        for (Visitante v : listVisitante) {
            if (v.getDocumento().equals(documento)) return v;
        }
        return null;
    }

    public boolean agregarVisitante(Visitante visitante) {
        if (buscarVisitante(visitante.getDocumento()) != null) return false;
        listVisitante.add(visitante);
        return true;
    }

    public boolean actualizarVisitante(String documento, String nombre, int edad, double estatura) {
        Visitante v = buscarVisitante(documento);
        if (v == null) return false;
        v.setNombre(nombre);
        v.setEdad(edad);
        v.setEstatura(estatura);
        return true;
    }

    public boolean eliminarVisitante(String documento) {
        Visitante v = buscarVisitante(documento);
        if (v == null) return false;
        listVisitante.remove(v);
        return true;
    }

    // CRUD Administrador
    public Administrador buscarAdministrador(String documento) {
        for (Administrador a : listAdmin) {
            if (a.getDocumento().equals(documento)) return a;
        }
        return null;
    }

    public boolean agregarAdministrador(Administrador admin) {
        if (buscarAdministrador(admin.getDocumento()) != null) return false;
        listAdmin.add(admin);
        return true;
    }

    public boolean eliminarAdministrador(String documento) {
        Administrador a = buscarAdministrador(documento);
        if (a == null) return false;
        listAdmin.remove(a);
        return true;
    }

    // CRUD Zona
    public Zona buscarZona(String idZona) {
        for (Zona z : listZona) {
            if (z.getIdZona().equals(idZona)) return z;
        }
        return null;
    }

    public boolean agregarZona(Zona zona) {
        if (buscarZona(zona.getIdZona()) != null) return false;
        listZona.add(zona);
        return true;
    }

    public boolean eliminarZona(String idZona) {
        Zona z = buscarZona(idZona);
        if (z == null) return false;
        listZona.remove(z);
        return true;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNit() {
        return nit;
    }
    public void setNit(String nit) {
        this.nit = nit;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Visitante> getListVisitante() {
        return listVisitante;
    }
    public void setListVisitante(ArrayList<Visitante> listVisitante) {
        this.listVisitante = listVisitante;
    }
    public ArrayList<Administrador> getListAdmin() {
        return listAdmin;
    }
    public void setListAdmin(ArrayList<Administrador> listAdmin) {
        this.listAdmin = listAdmin;
    }
    public ArrayList<Zona> getListZona() {
        return listZona;
    }
    public void setListZona(ArrayList<Zona> listZona) {
        this.listZona = listZona;
    }

    @Override
    public String toString() {
        return "ParqueDeAtraccion: " + nombre + " nit=" + nit + " zonas=" + listZona.size();
    }
}
