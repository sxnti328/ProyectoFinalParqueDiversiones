package org.example.proyectofinalparque.model;

public class Atraccion {
    protected String id;
    protected String nombre;
    protected TipoAtraccion tipo;
    protected int capacidadMaxima;
    protected double alturaMinima;
    protected int edadMinima;
    protected double costoAdicional;
    protected int contadorVisitantes;
    protected int tiempoEspera;
   // protected EstadoAtraccion estado;
    protected String motivoCierre;
    protected Zona zona;
   // protected ColaVirtual colaVirtual;

    public Atraccion(String id, String nombre, TipoAtraccion tipo, int capacidadMaxima, double alturaMinima, int edadMinima, double costoAdicional) {

        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidadMaxima = capacidadMaxima;
        this.alturaMinima = alturaMinima;
        this.edadMinima = edadMinima;
        this.costoAdicional = costoAdicional;

        this.contadorVisitantes = 0;
        this.tiempoEspera = 0;
      //  this.estado = EstadoActraccion.ACTIVA;
        this.motivoCierre = "";
        //this.colaVirtual = new ColaVirtual();
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

    public double getCostoAdicional() {
        return costoAdicional;
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

    public EstadoAtraccion getEstado() {
      return estado;
    }

    public void setEstado(EstadoAtraccion estado) {
  this.estado = estado;
  }

    public String getMotivoCierre() {
        return motivoCierre;
    }

    public void setMotivoCierre(String motivoCierre) {
        this.motivoCierre = motivoCierre;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public ColaVirtual getColaVirtual() {
        return colaVirtual;
    }

    public void setColaVirtual(ColaVirtual colaVirtual) {
        this.colaVirtual = colaVirtual;
    }


    // ── Métodos abstractos ──
    public abstract void verificarSeguridad(Visitante v);
    public abstract int calcularTiempoEspera();

    @Override
    public String toString() {
        return "Atraccion[" + nombre + "] estado=" + estado + " visitantes=" + contadorVisitantes;
    }
}

