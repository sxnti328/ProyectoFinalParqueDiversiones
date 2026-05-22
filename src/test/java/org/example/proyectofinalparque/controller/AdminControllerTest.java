package org.example.proyectofinalparque.controller;

import org.example.proyectofinalparque.model.clases.*;
import org.example.proyectofinalparque.model.enums.TipoAtraccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdminControllerTest {

    private AdminController controller;
    private ParqueDeAtraccion parque;

    @BeforeEach
    public void setUp() {
        parque = new ParqueDeAtraccion("Tech-Park UQ", "123456789", "Carrera 10 #25", 1000);
        controller = new AdminController(parque);
    }

    @Test
    public void testAgregarOperador() {
        Zona zona = new Zona("ZONA1", "Aventura", "Atracciones mecánicas", 500);
        parque.agregarZona(zona);

        boolean resultado = controller.agregarOperador("Juan Cortez", "77777", 35, "EMP001", "Mañana", zona);
        assertTrue(resultado);
        assertEquals(1, controller.obtenerListaOperadores().size());
    }

    @Test
    public void testAgregarOperadorSinZona() {
        boolean resultado = controller.agregarOperador("Maria", "88888", 30, "EMP002", "Tarde", null);
        assertTrue(resultado);
        assertEquals(1, controller.obtenerListaOperadores().size());
    }

    @Test
    public void testAgregarOperadorDuplicado() {
        Zona zona = new Zona("ZONA1", "Aventura", "Atracciones", 500);
        parque.agregarZona(zona);

        controller.agregarOperador("Juan", "77777", 35, "EMP001", "Mañana", zona);
        boolean resultado = controller.agregarOperador("Juan", "77777", 35, "EMP001", "Mañana", zona);
        assertFalse(resultado);
        assertEquals(1, controller.obtenerListaOperadores().size());
    }

    @Test
    public void testEliminarOperador() {
        Zona zona = new Zona("ZONA1", "Aventura", "Atracciones", 500);
        parque.agregarZona(zona);
        controller.agregarOperador("Juan", "77777", 35, "EMP001", "Mañana", zona);

        boolean resultado = controller.eliminarOperador("77777");
        assertTrue(resultado);
        assertEquals(0, controller.obtenerListaOperadores().size());
    }

    @Test
    public void testEliminarOperadorNoExistente() {
        boolean resultado = controller.eliminarOperador("99999");
        assertFalse(resultado);
    }

    @Test
    public void testObtenerListaOperadores() {
        Zona zona = new Zona("ZONA1", "Aventura", "Atracciones", 500);
        parque.agregarZona(zona);
        controller.agregarOperador("Operador1", "11111", 30, "EMP001", "Día", zona);
        controller.agregarOperador("Operador2", "22222", 35, "EMP002", "Noche", zona);

        ArrayList<Operador> lista = controller.obtenerListaOperadores();
        assertEquals(2, lista.size());
    }

    @Test
    public void testAgregarZona() {
        boolean resultado = controller.agregarZona("ZONA1", "Diversión Acuática", "Piscinas y toboganes", 600);
        assertTrue(resultado);
        assertEquals(1, controller.obtenerListaZonas().size());
    }

    @Test
    public void testAgregarZonaDuplicada() {
        controller.agregarZona("ZONA1", "Diversión Acuática", "Piscinas", 600);
        boolean resultado = controller.agregarZona("ZONA1", "Otra Zona", "Otra descripción", 800);
        assertFalse(resultado);
        assertEquals(1, controller.obtenerListaZonas().size());
    }

    @Test
    public void testEliminarZona() {
        controller.agregarZona("ZONA1", "Diversión", "Descripción", 600);
        boolean resultado = controller.eliminarZona("ZONA1");
        assertTrue(resultado);
        assertEquals(0, controller.obtenerListaZonas().size());
    }

    @Test
    public void testEliminarZonaNoExistente() {
        boolean resultado = controller.eliminarZona("ZONA999");
        assertFalse(resultado);
    }

    @Test
    public void testObtenerListaZonas() {
        controller.agregarZona("ZONA1", "Aventura", "Mecánicas", 500);
        controller.agregarZona("ZONA2", "Acuática", "Piscinas", 600);
        controller.agregarZona("ZONA3", "Infantil", "Para niños", 300);

        ArrayList<Zona> lista = controller.obtenerListaZonas();
        assertEquals(3, lista.size());
    }

    @Test
    public void testAgregarAtraccion() {
        Zona zona = new Zona("ZONA1", "Aventura", "Mecánicas", 500);
        parque.agregarZona(zona);

        boolean resultado = controller.agregarAtraccion(zona, TipoAtraccion.MECANICA_ALTURA,
                                                       "ATR001", "Montaña Rusa",
                                                       50, 1.40, 12, 10.0);
        assertTrue(resultado);
        assertEquals(1, controller.obtenerListaAtracciones().size());
    }

    @Test
    public void testAgregarAtraccionSinZona() {
        boolean resultado = controller.agregarAtraccion(null, TipoAtraccion.MECANICA_ALTURA,
                                                       "ATR001", "Montaña", 50, 1.40, 12, 10.0);
        assertFalse(resultado);
    }

    @Test
    public void testEliminarAtraccion() {
        Zona zona = new Zona("ZONA1", "Aventura", "Mecánicas", 500);
        parque.agregarZona(zona);
        Atraccion atr = new Atraccion("ATR001", "Montaña", TipoAtraccion.MECANICA_ALTURA, 50, 1.40, 12, 10.0);
        parque.agregarAtraccionAZona("ZONA1", atr);

        boolean resultado = controller.eliminarAtraccion(atr);
        assertTrue(resultado);
        assertEquals(0, controller.obtenerListaAtracciones().size());
    }

    @Test
    public void testEliminarAtraccionNula() {
        boolean resultado = controller.eliminarAtraccion(null);
        assertFalse(resultado);
    }

    @Test
    public void testObtenerListaAtracciones() {
        Zona zona = new Zona("ZONA1", "Aventura", "Mecánicas", 500);
        parque.agregarZona(zona);

        controller.agregarAtraccion(zona, TipoAtraccion.MECANICA_ALTURA, "ATR001", "Montaña", 50, 1.40, 12, 10.0);
        controller.agregarAtraccion(zona, TipoAtraccion.ACUATICA, "ATR002", "Tobogán", 80, 0.0, 5, 5.0);

        List<Atraccion> lista = controller.obtenerListaAtracciones();
        assertEquals(2, lista.size());
    }

    @Test
    public void testActivarAlertaClimatica() {
        Zona zona = new Zona("ZONA1", "Acuática", "Piscinas", 500);
        parque.agregarZona(zona);
        Atraccion atr = new Atraccion("ATR001", "Piscina", TipoAtraccion.ACUATICA, 100, 0.0, 0, 0.0);
        zona.agregarAtraccion(atr);

        controller.activarAlertaClimatica();
        // La atracción acuática debería estar cerrada
        assertEquals(org.example.proyectofinalparque.model.enums.EstadoActual.CERRADA, atr.getEstado());
    }

    @Test
    public void testDesactivarAlertaClimatica() {
        Zona zona = new Zona("ZONA1", "Acuática", "Piscinas", 500);
        parque.agregarZona(zona);
        Atraccion atr = new Atraccion("ATR001", "Piscina", TipoAtraccion.ACUATICA, 100, 0.0, 0, 0.0);
        zona.agregarAtraccion(atr);

        controller.activarAlertaClimatica();
        controller.desactivarAlertaClimatica();
        // Debería estar abierta de nuevo
        assertEquals(org.example.proyectofinalparque.model.enums.EstadoActual.ACTIVA, atr.getEstado());
    }

    @Test
    public void testGenerarReporte() {
        controller.agregarZona("ZONA1", "Aventura", "Mecánicas", 500);
        String reporte = controller.generarReporte();
        assertNotNull(reporte);
        assertTrue(reporte.length() > 0);
    }

    @Test
    public void testAgregarMultiplesAtracciones() {
        Zona z1 = new Zona("ZONA1", "Aventura", "Mecánicas", 500);
        Zona z2 = new Zona("ZONA2", "Acuática", "Piscinas", 600);
        parque.agregarZona(z1);
        parque.agregarZona(z2);

        controller.agregarAtraccion(z1, TipoAtraccion.MECANICA_ALTURA, "ATR001", "Montaña", 50, 1.40, 12, 10.0);
        controller.agregarAtraccion(z1, TipoAtraccion.FAMILIAR, "ATR002", "Tren", 40, 0.8, 4, 5.0);
        controller.agregarAtraccion(z2, TipoAtraccion.ACUATICA, "ATR003", "Piscina", 100, 0.0, 0, 0.0);

        assertEquals(3, controller.obtenerListaAtracciones().size());
    }
}
