package org.example.proyectofinalparque.model.clases;

import java.time.LocalDate;

public class Reporte {

    private String titulo;
    private String contenido;
    private LocalDate fecha;

    public Reporte(String titulo, String contenido) {
        this.titulo    = titulo;
        this.contenido = contenido;
        this.fecha     = LocalDate.now();
    }

    public Reporte(String titulo, String contenido, LocalDate fecha) {
        this.titulo    = titulo;
        this.contenido = contenido;
        this.fecha     = fecha;
    }

    public String    getTitulo()                  { return titulo; }
    public void      setTitulo(String titulo)     { this.titulo = titulo; }
    public String    getContenido()               { return contenido; }
    public void      setContenido(String c)       { this.contenido = c; }
    public LocalDate getFecha()                   { return fecha; }
    public void      setFecha(LocalDate fecha)    { this.fecha = fecha; }

    @Override
    public String toString() {
        return "=== " + titulo + " (" + fecha + ") ===\n" + contenido;
    }
}
