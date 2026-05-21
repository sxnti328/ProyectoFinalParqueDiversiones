package org.example.proyectofinalparque.model.clases;

import org.example.proyectofinalparque.model.enums.TipoTicket;
import org.example.proyectofinalparque.model.records.Notificacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ParqueDeAtraccion {

    private String nombre;
    private String nit;
    private String direccion;
    private int    capacidadMax;
    private int    visitantesActuales;

    private ArrayList<Visitante>     listVisitante;
    private ArrayList<Administrador> listAdmin;
    private ArrayList<Operador>      listOperador;
    private ArrayList<Zona>          listZona;

    private GestorReportes gestorReportes;

    public ParqueDeAtraccion(String nombre, String nit, String direccion, int capacidadMax) {
        this.nombre             = nombre;
        this.nit                = nit;
        this.direccion          = direccion;
        this.capacidadMax       = capacidadMax;
        this.visitantesActuales = 0;
        this.listVisitante      = new ArrayList<>();
        this.listAdmin          = new ArrayList<>();
        this.listOperador       = new ArrayList<>();
        this.listZona           = new ArrayList<>();
        this.gestorReportes     = new GestorReportes(this);
    }

    // ── CRUD Visitante ───────────────────────────────────────────────────────

    public boolean agregarVisitante(Visitante v) {
        if (visitantesActuales >= capacidadMax) return false;
        if (buscarVisitante(v.getDocumento()) != null) return false;
        listVisitante.add(v);
        visitantesActuales++;
        return true;
    }

    public Visitante buscarVisitante(String documento) {
        for (Visitante v : listVisitante)
            if (v.getDocumento().equals(documento)) return v;
        return null;
    }

    public boolean actualizarVisitante(String documento, String nombre, int edad,
                                       double estatura, String telefono, String direccion) {
        Visitante v = buscarVisitante(documento);
        if (v == null) return false;
        v.setNombre(nombre);
        v.setEdad(edad);
        v.setEstatura(estatura);
        v.setTelefono(telefono);
        v.setDireccion(direccion);
        return true;
    }

    public boolean eliminarVisitante(String documento) {
        Visitante v = buscarVisitante(documento);
        if (v == null) return false;
        listVisitante.remove(v);
        visitantesActuales--;
        return true;
    }

    // ── CRUD Administrador ───────────────────────────────────────────────────

    public boolean agregarAdministrador(Administrador a) {
        if (buscarAdministrador(a.getDocumento()) != null) return false;
        listAdmin.add(a);
        return true;
    }

    public Administrador buscarAdministrador(String documento) {
        for (Administrador a : listAdmin)
            if (a.getDocumento().equals(documento)) return a;
        return null;
    }

    public boolean eliminarAdministrador(String documento) {
        Administrador a = buscarAdministrador(documento);
        if (a == null) return false;
        listAdmin.remove(a);
        return true;
    }

    // ── CRUD Operador ────────────────────────────────────────────────────────

    public boolean agregarOperador(Operador o) {
        if (buscarOperador(o.getDocumento()) != null) return false;
        listOperador.add(o);
        return true;
    }

    public Operador buscarOperador(String documento) {
        for (Operador o : listOperador)
            if (o.getDocumento().equals(documento)) return o;
        return null;
    }

    public Operador buscarOperadorPorId(String idEmpleado) {
        for (Operador o : listOperador)
            if (o.getIdEmpleado().equals(idEmpleado)) return o;
        return null;
    }

    public boolean eliminarOperador(String documento) {
        Operador o = buscarOperador(documento);
        if (o == null) return false;
        listOperador.remove(o);
        // remove from zone too
        for (Zona z : listZona)
            z.eliminar(o.getIdEmpleado());
        return true;
    }

    public boolean asignarOperadorAZona(String idEmpleado, String idZona) {
        Operador o = buscarOperadorPorId(idEmpleado);
        Zona     z = buscarZona(idZona);
        if (o == null || z == null) return false;
        // remove from previous zone
        for (Zona zona : listZona)
            zona.getListOperador().removeIf(op -> op.getIdEmpleado().equals(idEmpleado));
        z.agregarOperador(o);
        o.setIdZona(idZona);
        return true;
    }

    // ── CRUD Zona ────────────────────────────────────────────────────────────

    public boolean agregarZona(Zona z) {
        if (buscarZona(z.getIdZona()) != null) return false;
        listZona.add(z);
        return true;
    }

    public Zona buscarZona(String idZona) {
        for (Zona z : listZona)
            if (z.getIdZona().equals(idZona)) return z;
        return null;
    }

    public boolean eliminarZona(String idZona) {
        Zona z = buscarZona(idZona);
        if (z == null) return false;
        listZona.remove(z);
        return true;
    }

    // ── CRUD Atraccion ───────────────────────────────────────────────────────

    public boolean agregarAtraccionAZona(String idZona, Atraccion a) {
        Zona z = buscarZona(idZona);
        if (z == null) return false;
        z.agregarAtraccion(a);
        return true;
    }

    public Atraccion buscarAtraccion(String idAtraccion) {
        for (Zona z : listZona) {
            Atraccion a = z.buscarAtraccion(idAtraccion);
            if (a != null) return a;
        }
        return null;
    }

    public boolean eliminarAtraccionDeZona(String idZona, String idAtraccion) {
        Zona z = buscarZona(idZona);
        if (z == null) return false;
        return z.eliminar(idAtraccion);
    }

    public List<Atraccion> getTodasLasAtracciones() {
        List<Atraccion> todas = new ArrayList<>();
        for (Zona z : listZona)
            todas.addAll(z.getListAtraccion());
        return todas;
    }

    // ── Reglas de negocio ────────────────────────────────────────────────────

    public String recargarSaldoVisitante(String documentoVisitante, double monto) {
        Visitante v = buscarVisitante(documentoVisitante);
        if (v == null) return "Visitante no encontrado.";
        if (monto <= 0) return "El monto debe ser mayor a cero.";
        v.recargarSaldo(monto);
        return "Saldo recargado. Nuevo saldo: $" + v.getSaldoVirtual();
    }

    public String venderTicket(String documentoVisitante, TipoTicket tipo,
                               double precio, int numIntegrantes) {
        Visitante v = buscarVisitante(documentoVisitante);
        if (v == null) return "Visitante no encontrado.";
        if (visitantesActuales >= capacidadMax) return "El parque ha alcanzado su aforo maximo.";

        String idTicket = "TK-" + documentoVisitante + "-" + System.currentTimeMillis();
        Ticket ticket;
        switch (tipo) {
            case FAMILIAR  -> ticket = new TicketFamiliar(idTicket, precio, numIntegrantes, "15% descuento");
            case FAST_PASS -> ticket = new TicketFastPass(idTicket, precio);
            default        -> ticket = new TicketGeneral(idTicket, precio);
        }

        if (v.comprarTicket(ticket))
            return "Ticket " + tipo + " comprado. Precio final: $"
                    + String.format("%.0f", ticket.getPrecioFinal());
        return "Saldo insuficiente. Necesita $" + String.format("%.0f", ticket.getPrecioFinal());
    }

    public String ingresarAAtraccion(String documentoVisitante, String idAtraccion) {
        Visitante v = buscarVisitante(documentoVisitante);
        if (v == null)              return "Visitante no encontrado.";
        if (v.getTicketActivo() == null) return "El visitante no tiene ticket activo.";

        Atraccion a = buscarAtraccion(idAtraccion);
        if (a == null)              return "Atraccion no encontrada.";

        return a.registrarIngreso(v);
    }

    public List<Notificacion> activarAlertaClimatica() {
        List<Notificacion> notifs = new ArrayList<>();

        // 1) cerrar atracciones acuaticas/altura en cada zona
        for (Zona z : listZona) {
            Notificacion n = z.activarAlertaClimatica();
            if (n != null) notifs.add(n);
        }

        // 2) enviar notificacion simple a cada visitante con ticket activo
        Notificacion aviso = new Notificacion("CLIMA",
                "Alerta climatica activa. Algunas atracciones han sido cerradas.",
                LocalDate.now());
        for (Visitante v : listVisitante) {
            if (v.getTicketActivo() != null) {
                enviarNotificacion(v, aviso);
            }
        }
        return notifs;
    }

    // Envia una notificacion simple a un visitante
    public void enviarNotificacion(Visitante visitante, Notificacion notificacion) {
        if (visitante != null && notificacion != null) {
            visitante.agregarNotificacion(notificacion);
        }
    }

    public void desactivarAlertaClimatica() {
        for (Zona z : listZona)
            z.desactivarAlertaClimatica();
    }

    public String registrarRevisionTecnica(String documentoOperador,
                                           String idAtraccion, String descripcion) {
        Operador o = buscarOperador(documentoOperador);
        if (o == null) return "Operador no encontrado.";

        Atraccion a = buscarAtraccion(idAtraccion);
        if (a == null) return "Atraccion no encontrada.";

        if (!o.puedeGestionarAtraccion(a))
            return "El operador no puede gestionar esta atraccion (zona incorrecta).";

        Notificacion n = a.registrarRevisionTecnica(o, descripcion);
        for (Visitante v : listVisitante)
            if (v.tieneFavorita(idAtraccion))
                v.agregarNotificacion(n);
        return "Revision registrada. " + a.getNombre() + " esta nuevamente ACTIVA.";
    }

    public void notificarVisitantes(Notificacion n) {
        for (Visitante v : listVisitante)
            v.agregarNotificacion(n);
    }

    // ── Reportes (delegados a GestorReportes) ────────────────────────────────

    public String generarReporteDiario()              { return gestorReportes.generarReporteDiario(); }
    public double calcularIngresosDiarios()           { return gestorReportes.calcularIngresosDiarios(); }
    public List<Atraccion> getAtraccionesMasVisitadas(){ return gestorReportes.getAtraccionesMasVisitadas(); }
    public List<Atraccion> getAtraccionesEnMantenimiento(){ return gestorReportes.getAtraccionesEnMantenimiento(); }
    public List<Atraccion> getAtraccionesCerradasPorClima(){ return gestorReportes.getAtraccionesCerradasPorClima(); }

    // ── Getters / Setters ────────────────────────────────────────────────────

    public String getNombre()                          { return nombre; }
    public void   setNombre(String n)                  { this.nombre = n; }
    public String getNit()                             { return nit; }
    public void   setNit(String n)                     { this.nit = n; }
    public String getDireccion()                       { return direccion; }
    public void   setDireccion(String d)               { this.direccion = d; }
    public int    getCapacidadMax()                    { return capacidadMax; }
    public void   setCapacidadMax(int c)               { this.capacidadMax = c; }
    public int    getVisitantesActuales()               { return visitantesActuales; }

    public ArrayList<Visitante>     getListVisitante() { return listVisitante; }
    public ArrayList<Administrador> getListAdmin()     { return listAdmin; }
    public ArrayList<Operador>      getListOperador()  { return listOperador; }
    public ArrayList<Zona>          getListZona()      { return listZona; }

    public void setListVisitante(ArrayList<Visitante> l)     { this.listVisitante = l; }
    public void setListAdmin(ArrayList<Administrador> l)     { this.listAdmin = l; }
    public void setListZona(ArrayList<Zona> l)               { this.listZona = l; }

    @Override
    public String toString() {
        return "ParqueDeAtraccion: " + nombre + " | NIT: " + nit
                + " | Visitantes: " + visitantesActuales + "/" + capacidadMax
                + " | Zonas: " + listZona.size();
    }
}
