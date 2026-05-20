package org.example.proyectofinalparque.model;

import org.example.proyectofinalparque.model.clases.Administrador;
import org.example.proyectofinalparque.model.clases.Visitante;

import java.util.ArrayList;

public class ParqueDeDiversion {
    private String nombre;
    private String nit;
    private String direccion;
    private ArrayList<Visitante> listVisitante;
    private ArrayList<Administrador> listAdmin;
    private ArrayList<Zona> listZona;

    public ParqueDeDiversion(String nombre, String nit, String direccion, ArrayList<Visitante> listVisitante, ArrayList<Administrador> listAdmin, ArrayList<Zona> listZona) {
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.listVisitante = listVisitante;
        this.listAdmin = listAdmin;
        this.listZona= listZona;
    }

    //CRUD DE VISITANTE
    //CREATE

    /*public Visitante buscarVisitante(String documento){
        for(Visitante v: listVisitante){
            if(v.getDocumento().equals(documento)){
                return v;
            }

        }
        return null;

    }

    public boolean agregarVisitante(String nombre, String documento, int edad, double estatura, double saldoVirtual){
        for(Visitante v: listVisitante){
            if(v.getDocumento().equals(documento)){
                return false;
            }
        }
        listVisitante.add(new Visitante(nombre, documento, edad, estatura, saldoVirtual));
        return true;

    }

    //UPDATE
    public boolean actualizarVisitante(String nombre, String documento, int edad, double estatura){
        Visitante visitante= buscarVisitante(documento);

        if(visitante != null){
            visitante.setNombre(nombre);
            visitante.setEdad(edad);
            visitante.setEstatura(estatura);

            return true;
        }
        return false;
    }

    //DELETE
    public boolean eliminarVisitante(String documento){
        Visitante visitante=buscarVisitante(documento);

        if(visitante != null){
            listVisitante.remove(visitante);
            return true;
        }
        return false;
    }

    //CRUD DE ADMINISTARDOR
    //CREATE
    public Administrador buscarAdministradorByDocumento(String documento){

        for(Administrador d: listAdmin){
            if(d.getDocumento().equals(documento)){
                return d;

            }
        }
        return null;
    }
    public boolean crearAdministrador(){

    }


*/


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
}
