package org.example.proyectofinalparque.model.clases;

import org.example.proyectofinalparque.model.enums.EstadoActual;
import org.example.proyectofinalparque.model.enums.MotivoCierre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorReportes {

    private ParqueDeAtraccion parque;

    public GestorReportes(ParqueDeAtraccion parque) {
        this.parque = parque;
    }

    public Reporte generarReporte() {
        return new Reporte("REPORTE DIARIO - Tech-Park UQ", generarReporteDiario());
    }

    public String generarReporteDiario() {
        String texto = "";
        texto += "========================================\n";
        texto += "   REPORTE DIARIO - Tech-Park UQ\n";
        texto += "   Fecha: " + LocalDate.now() + "\n";
        texto += "========================================\n\n";

        texto += "-- INGRESOS DIARIOS --\n";
        texto += "   Total: $" + calcularIngresosDiarios() + "\n\n";

        texto += "-- VISITANTES --\n";
        texto += "   Registrados: " + parque.getListVisitante().size() + "\n\n";

        texto += "-- ATRACCIONES MAS VISITADAS --\n";
        List<Atraccion> top = getAtraccionesMasVisitadas();
        for (int i = 0; i < top.size() && i < 5; i++) {
            Atraccion a = top.get(i);
            texto += "   " + (i + 1) + ". " + a.getNombre()
                    + " - " + a.getContadorVisitantes() + " visitantes\n";
        }

        texto += "\n-- ATRACCIONES EN MANTENIMIENTO --\n";
        for (Atraccion a : getAtraccionesEnMantenimiento()) {
            texto += "   - " + a.getNombre() + " (" + a.getMotivoCierre() + ")\n";
        }

        texto += "\n-- CIERRES POR CLIMA --\n";
        for (Atraccion a : getAtraccionesCerradasPorClima()) {
            texto += "   - " + a.getNombre() + "\n";
        }

        texto += "\n-- TIEMPO PROMEDIO DE ESPERA --\n";
        texto += "   " + calcularTiempoPromedioEspera() + " min\n";

        texto += "\n========================================\n";
        return texto;
    }

    public double calcularIngresosDiarios() {
        double total = 0;
        for (Visitante v : parque.getListVisitante()) {
            for (Ticket t : v.getListTickets()) {
                total += t.getPrecioFinal();
            }
        }
        return total;
    }

    // ordena las atracciones de mayor a menor por visitantes (bubble sort sencillo)
    public List<Atraccion> getAtraccionesMasVisitadas() {
        List<Atraccion> todas = new ArrayList<>(parque.getTodasLasAtracciones());
        for (int i = 0; i < todas.size() - 1; i++) {
            for (int j = 0; j < todas.size() - 1 - i; j++) {
                if (todas.get(j).getContadorVisitantes() < todas.get(j + 1).getContadorVisitantes()) {
                    Atraccion tmp = todas.get(j);
                    todas.set(j, todas.get(j + 1));
                    todas.set(j + 1, tmp);
                }
            }
        }
        return todas;
    }

    public List<Atraccion> getAtraccionesEnMantenimiento() {
        List<Atraccion> lista = new ArrayList<>();
        for (Atraccion a : parque.getTodasLasAtracciones()) {
            if (a.getEstado() == EstadoActual.EN_MANTENIMIENTO) {
                lista.add(a);
            }
        }
        return lista;
    }

    public List<Atraccion> getAtraccionesCerradasPorClima() {
        List<Atraccion> lista = new ArrayList<>();
        for (Atraccion a : parque.getTodasLasAtracciones()) {
            if (a.getMotivoCierre() == MotivoCierre.CLIMA) {
                lista.add(a);
            }
        }
        return lista;
    }

    public double calcularTiempoPromedioEspera() {
        int suma = 0;
        int cantidad = 0;
        for (Atraccion a : parque.getTodasLasAtracciones()) {
            if (a.getEstado() == EstadoActual.ACTIVA) {
                suma += a.getTiempoEspera();
                cantidad++;
            }
        }
        if (cantidad == 0) return 0;
        return (double) suma / cantidad;
    }
}
