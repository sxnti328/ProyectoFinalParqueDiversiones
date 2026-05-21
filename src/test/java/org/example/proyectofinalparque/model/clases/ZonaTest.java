package org.example.proyectofinalparque.model.clases;

import org.example.proyectofinalparque.model.enums.TipoAtraccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ZonaTest {

    private Zona zona;
    private Atraccion atraccion;
    private Operador operador;

    @BeforeEach
    public void setUp() {
        zona = new Zona("ZONA1", "Diversión Acuática", "Zona de atracciones acuáticas", 500);
        atraccion = new Atraccion("ATR001", "Piscina Loca", TipoAtraccion.ACUATICA, 100, 0.0, 5, 3.0);
        operador = new Operador("Luis", "77777", 30, "EMP001", "Día", "ZONA1");
    }

    @Test
    public void testCrearZona() {
        assertEquals("ZONA1", zona.getIdZona());
        assertEquals("Diversión Acuática", zona.getNombre());
        assertEquals("Zona de atracciones acuáticas", zona.getDescripcion());
        assertEquals(500, zona.getCapacidadMax());
    }

    @Test
    public void testAgregarAtraccion() {
        zona.agregarAtraccion(atraccion);
        assertEquals(1, zona.getListAtraccion().size());
        assertEquals(zona, atraccion.getZona());
    }

    @Test
    public void testBuscarAtraccion() {
        zona.agregarAtraccion(atraccion);
        Atraccion encontrada = zona.buscarAtraccion("ATR001");
        assertNotNull(encontrada);
        assertEquals("Piscina Loca", encontrada.getNombre());
    }

    @Test
    public void testBuscarAtraccionNoExistente() {
        Atraccion encontrada = zona.buscarAtraccion("ATR999");
        assertNull(encontrada);
    }

    @Test
    public void testAgregarOperador() {
        zona.agregarOperador(operador);
        assertEquals(1, zona.getListOperador().size());
        assertEquals("ZONA1", operador.getIdZona());
    }

    @Test
    public void testBuscarOperador() {
        zona.agregarOperador(operador);
        Operador encontrado = zona.buscarOperador("EMP001");
        assertNotNull(encontrado);
        assertEquals("Luis", encontrado.getNombre());
    }

    @Test
    public void testBuscarOperadorNoExistente() {
        Operador encontrado = zona.buscarOperador("EMP999");
        assertNull(encontrado);
    }

    @Test
    public void testTieneOperadores() {
        assertFalse(zona.tieneOperadores());
        zona.agregarOperador(operador);
        assertTrue(zona.tieneOperadores());
    }

    @Test
    public void testEliminarAtraccion() {
        zona.agregarAtraccion(atraccion);
        boolean resultado = zona.eliminar("ATR001");
        assertTrue(resultado);
        assertEquals(0, zona.getListAtraccion().size());
    }

    @Test
    public void testEliminarOperador() {
        zona.agregarOperador(operador);
        boolean resultado = zona.eliminar("EMP001");
        assertTrue(resultado);
        assertEquals(0, zona.getListOperador().size());
    }

    @Test
    public void testActivarAlertaClimatica() {
        zona.agregarAtraccion(atraccion);
        var notif = zona.activarAlertaClimatica();
        assertNotNull(notif);
        assertTrue(notif.getDescripcion().contains("cerradas"));
    }

    @Test
    public void testDesactivarAlertaClimatica() {
        zona.agregarAtraccion(atraccion);
        zona.activarAlertaClimatica();
        zona.desactivarAlertaClimatica();
        // Verificar que se reabrió
        assertEquals(org.example.proyectofinalparque.model.enums.EstadoActual.ACTIVA, atraccion.getEstado());
    }

    @Test
    public void testEstaLlena() {
        zona.setVisitantesActuales(499);
        assertFalse(zona.estaLlena());
        zona.setVisitantesActuales(500);
        assertTrue(zona.estaLlena());
    }

    @Test
    public void testObtenerDatosReporte() {
        zona.agregarAtraccion(atraccion);
        zona.agregarOperador(operador);
        String reporte = zona.obtenerDatosReporte();
        assertTrue(reporte.contains("Diversión Acuática"));
        assertTrue(reporte.contains("Atracciones"));
        assertTrue(reporte.contains("Operadores"));
    }

    @Test
    public void testGetTotalElementos() {
        zona.agregarAtraccion(atraccion);
        zona.agregarOperador(operador);
        assertEquals(2, zona.getTotalElementos());
    }

    @Test
    public void testSetNombre() {
        zona.setNombre("Nueva Zona");
        assertEquals("Nueva Zona", zona.getNombre());
    }

    @Test
    public void testSetCapacidad() {
        zona.setCapacidadMax(1000);
        assertEquals(1000, zona.getCapacidadMax());
    }

    @Test
    public void testSetVisitantesActuales() {
        zona.setVisitantesActuales(250);
        assertEquals(250, zona.getVisitantesActuales());
    }
}
