package org.example.proyectofinalparque.model.clases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GestorReportes {

    private final ParqueDeAtraccion parque;

    public GestorReportes(ParqueDeAtraccion parque) {
        this.parque = parque;
    }

    public Reporte generarReporte() {
        return new Reporte("REPORTE DIARIO - Tech-Park UQ", generarReporteDiario());
    }

    public String generarReporteDiario() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("   REPORTE DIARIO - Tech-Park UQ\n");
        sb.append("   Fecha: ").append(LocalDate.now()).append("\n");
        sb.append("========================================\n\n");

        sb.append("-- INGRESOS DIARIOS --\n");
        sb.append("   Total: $").append(String.format("%.2f", calcularIngresosDiarios())).append("\n\n");

        sb.append("-- VISITANTES --\n");
        sb.append("   Registrados: ").append(parque.getListVisitante().size()).append("\n\n");

        sb.append("-- ATRACCIONES MAS VISITADAS --\n");
        List<Atraccion> top = getAtraccionesMasVisitadas();
        int rank = 1;
        for (Atraccion a : top) {
            sb.append("   ").append(rank++).append(". ").append(a.getNombre())
              .append(" - ").append(a.getContadorVisitantes()).append(" visitantes\n");
            if (rank > 5) break;
        }

        sb.append("\n-- ATRACCIONES EN MANTENIMIENTO --\n");
        for (Atraccion a : getAtraccionesEnMantenimiento())
            sb.append("   - ").append(a.getNombre())
              .append(" (").append(a.getMotivoCierre()).append(")\n");

        sb.append("\n-- CIERRES POR CLIMA --\n");
        for (Atraccion a : getAtraccionesCerradasPorClima())
            sb.append("   - ").append(a.getNombre()).append("\n");

        sb.append("\n-- TIEMPO PROMEDIO DE ESPERA --\n");
        sb.append("   ").append(String.format("%.1f", calcularTiempoPromedioEspera()))
          .append(" min\n");

        sb.append("\n========================================\n");
        return sb.toString();
    }

    public double calcularIngresosDiarios() {
        double total = 0;
        for (Visitante v : parque.getListVisitante())
            for (Ticket t : v.getListTickets())
                total += t.getPrecioFinal();
        return total;
    }

    public List<Atraccion> getAtraccionesMasVisitadas() {
        List<Atraccion> todas = new ArrayList<>(parque.getTodasLasAtracciones());
        todas.sort(Comparator.comparingInt(Atraccion::getContadorVisitantes).reversed());
        return todas;
    }

    public List<Atraccion> getAtraccionesEnMantenimiento() {
        List<Atraccion> r = new ArrayList<>();
        for (Atraccion a : parque.getTodasLasAtracciones())
            if (a.getEstado() == org.example.proyectofinalparque.model.enums.EstadoActual.EN_MANTENIMIENTO)
                r.add(a);
        return r;
    }

    public List<Atraccion> getAtraccionesCerradasPorClima() {
        List<Atraccion> r = new ArrayList<>();
        for (Atraccion a : parque.getTodasLasAtracciones())
            if (a.getMotivoCierre() == org.example.proyectofinalparque.model.enums.MotivoCierre.CLIMA)
                r.add(a);
        return r;
    }

    public double calcularTiempoPromedioEspera() {
        return parque.getTodasLasAtracciones().stream()
                .filter(a -> a.getEstado() == org.example.proyectofinalparque.model.enums.EstadoActual.ACTIVA)
                .mapToInt(Atraccion::getTiempoEspera)
                .average().orElse(0);
    }
}
