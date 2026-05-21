package org.example.proyectofinalparque.model.clases;

import org.example.proyectofinalparque.model.enums.EstadoActual;
import org.example.proyectofinalparque.model.enums.MotivoCierre;
import org.example.proyectofinalparque.model.enums.TipoAtraccion;
import org.example.proyectofinalparque.model.interfaces.IAccesible;

public class Atraccion implements IAccesible {
    private String id;
    private String nombre;
    private TipoAtraccion tipo;
    private int capacidadMaxima;
    private double alturaMinima;
    private int edadMinima;
    private double costoAdicional;
    private int contadorVisitantes;
    private int tiempoEspera;
    private EstadoActual estado;
    private MotivoCierre motivoCierre;
    private Zona zona;

    public Atraccion(String id, String nombre, TipoAtraccion tipo, int capacidadMaxima,
                     double alturaMinima, int edadMinima, double costoAdicional) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidadMaxima = capacidadMaxima;
        this.alturaMinima = alturaMinima;
        this.edadMinima = edadMinima;
        this.costoAdicional = costoAdicional;
        this.contadorVisitantes = 0;
        this.tiempoEspera = 0;
        this.estado = EstadoActual.ACTIVA;
        this.motivoCierre = null;
    }

    @Override
    public boolean verificarAcceso(Visitante visitante) {
        return visitante.getEstatura() >= alturaMinima
                && visitante.getEdad() >= edadMinima
                && estado == EstadoActual.ACTIVA;
    }

    @Override
    public double getCostoAdicional() {

        return costoAdicional;
    }

    @Override
    public EstadoActual getEstado() {

        return estado;
    }

    public void cambiarEstado(EstadoActual nuevoEstado, MotivoCierre motivo) {
        this.estado = nuevoEstado;
        this.motivoCierre = motivo;
    }

    public void incrementarContador() {
        this.contadorVisitantes++;
        if (contadorVisitantes >= 500) {
            this.estado = EstadoActual.EN_MANTENIMIENTO;
        }
    }

    public int calcularTiempoEspera() {
        if (capacidadMaxima == 0) return 0;
        return (contadorVisitantes / capacidadMaxima) * tiempoEspera;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public TipoAtraccion getTipo() {
        return tipo;
    }
    public void setTipo(TipoAtraccion tipo) {
        this.tipo = tipo;
    }
    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }
    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }
    public double getAlturaMinima() {
        return alturaMinima;
    }
    public void setAlturaMinima(double alturaMinima) {
        this.alturaMinima = alturaMinima;
    }
    public int getEdadMinima() {
        return edadMinima;
    }
    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }
    public void setCostoAdicional(double costoAdicional) {
        this.costoAdicional = costoAdicional;
    }
    public int getContadorVisitantes() {
        return contadorVisitantes;
    }
    public void setContadorVisitantes(int contadorVisitantes) {
        this.contadorVisitantes = contadorVisitantes;
    }
    public int getTiempoEspera() {
        return tiempoEspera;
    }
    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }
    public void setEstado(EstadoActual estado) {
        this.estado = estado;
    }
    public MotivoCierre getMotivoCierre() {
        return motivoCierre;
    }
    public void setMotivoCierre(MotivoCierre motivoCierre) {
        this.motivoCierre = motivoCierre;
    }
    public Zona getZona() {
        return zona;
    }
    public void setZona(Zona zona) {
        this.zona = zona;
    }

    @Override
    public String toString() {
        return "Atraccion[" + nombre + "] estado=" + estado + " visitantes=" + contadorVisitantes;
    }
}
