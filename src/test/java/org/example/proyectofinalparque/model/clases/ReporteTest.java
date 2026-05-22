package org.example.proyectofinalparque.model.clases;

import org.example.proyectofinalparque.model.enums.TipoAtraccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReporteTest {

    private Reporte reporte;

    @BeforeEach
    public void setUp() {
        reporte = new Reporte("REPORTE DIARIO - Tech-Park UQ");
    }

    @Test
    public void testCrearReporte() {
        assertEquals("REPORTE DIARIO - Tech-Park UQ", reporte.getTitulo());
        assertNotNull(reporte.getFecha());
        assertEquals(0.0, reporte.getIngresosDiarios());
        assertEquals(0, reporte.getTotalVisitantes());
    }

    @Test
    public void testSetIngresosDiarios() {
        reporte.setIngresosDiarios(1500.50);
        assertEquals(1500.50, reporte.getIngresosDiarios());
    }

    @Test
    public void testSetTotalVisitantes() {
        reporte.setTotalVisitantes(120);
        assertEquals(120, reporte.getTotalVisitantes());
    }

    @Test
    public void testSetTiempoPromedio() {
        reporte.setTiempoPromedioEspera(15.5);
        assertEquals(15.5, reporte.getTiempoPromedioEspera());
    }

    @Test
    public void testListasInicializadasVacias() {
        assertNotNull(reporte.getAtraccionesMasVisitadas());
        assertNotNull(reporte.getAtraccionesEnMantenimiento());
        assertNotNull(reporte.getAtraccionesCerradasPorClima());
        assertTrue(reporte.getAtraccionesMasVisitadas().isEmpty());
    }

    @Test
    public void testSetListaAtraccionesMasVisitadas() {
        List<Atraccion> lista = new ArrayList<>();
        Atraccion a = new Atraccion("ATR1", "Montaña", TipoAtraccion.MECANICA_ALTURA, 50, 1.40, 10, 5.0);
        lista.add(a);
        reporte.setAtraccionesMasVisitadas(lista);
        assertEquals(1, reporte.getAtraccionesMasVisitadas().size());
    }

    @Test
    public void testFormatearReporteSinDatos() {
        String texto = reporte.formatearReporte();
        assertTrue(texto.contains("REPORTE DIARIO"));
        assertTrue(texto.contains("INGRESOS DIARIOS"));
        assertTrue(texto.contains("VISITANTES"));
        assertTrue(texto.contains("ATRACCIONES MAS VISITADAS"));
        assertTrue(texto.contains("ninguna"));
    }

    @Test
    public void testFormatearReporteConDatos() {
        reporte.setIngresosDiarios(500.0);
        reporte.setTotalVisitantes(25);
        reporte.setTiempoPromedioEspera(10.0);

        Atraccion a = new Atraccion("ATR1", "Montaña Rusa", TipoAtraccion.MECANICA_ALTURA, 50, 1.40, 10, 5.0);
        a.setContadorVisitantes(15);
        List<Atraccion> top = new ArrayList<>();
        top.add(a);
        reporte.setAtraccionesMasVisitadas(top);

        String texto = reporte.formatearReporte();
        assertTrue(texto.contains("500.0"));
        assertTrue(texto.contains("25"));
        assertTrue(texto.contains("Montaña Rusa"));
        assertTrue(texto.contains("15 visitantes"));
    }

    @Test
    public void testGestorReportesGeneraReporte() {
        ParqueDeAtraccion parque = new ParqueDeAtraccion("Tech-Park", "123", "Calle", 100);
        Zona zona = new Zona("Z1", "Aventura", "Mecanicas", 500);
        parque.agregarZona(zona);
        parque.agregarAtraccionAZona("Z1",
            new Atraccion("ATR1", "Montaña", TipoAtraccion.MECANICA_ALTURA, 50, 1.40, 10, 5.0));

        Visitante v = new Visitante("Juan", "111", 20, 1.70, 100.0);
        parque.agregarVisitante(v);

        GestorReportes gestor = new GestorReportes(parque);
        Reporte r = gestor.generarReporte();

        assertNotNull(r);
        assertEquals("REPORTE DIARIO - Tech-Park", r.getTitulo());
        assertEquals(1, r.getTotalVisitantes());
    }

    @Test
    public void testToStringReporte() {
        String texto = reporte.toString();
        assertEquals(reporte.formatearReporte(), texto);
    }
}
