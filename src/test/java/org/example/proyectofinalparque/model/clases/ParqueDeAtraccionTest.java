package org.example.proyectofinalparque.model.clases;

import org.example.proyectofinalparque.model.enums.TipoAtraccion;
import org.example.proyectofinalparque.model.enums.TipoTicket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParqueDeAtraccionTest {

    private ParqueDeAtraccion parque;
    private Visitante visitante;
    private Operador operador;
    private Administrador admin;
    private Zona zona;
    private Atraccion atraccion;

    @BeforeEach
    public void setUp() {
        parque = new ParqueDeAtraccion("Tech-Park UQ", "123456789", "Calle Principal 100", 500);

        visitante = new Visitante("Carlos Diaz", "11111", 20, 1.70, 200.0);
        operador = new Operador("Operador1", "22222", 30, "EMP001", "Día", "ZONA1");
        admin = new Administrador("Admin1", "33333", 40, "ADM001");
        zona = new Zona("ZONA1", "Aventura", "Mecánicas", 500);
        atraccion = new Atraccion("ATR001", "Montaña Rusa", TipoAtraccion.MECANICA_ALTURA, 50, 1.40, 10, 5.0);
    }

    @Test
    public void testCrearParque() {
        assertEquals("Tech-Park UQ", parque.getNombre());
        assertEquals("123456789", parque.getNit());
        assertEquals("Calle Principal 100", parque.getDireccion());
        assertEquals(500, parque.getCapacidadMax());
    }

    @Test
    public void testAgregarVisitante() {
        boolean resultado = parque.agregarVisitante(visitante);
        assertTrue(resultado);
        assertEquals(1, parque.getListVisitante().size());
    }

    @Test
    public void testNoAgregarVisitanteDuplicado() {
        parque.agregarVisitante(visitante);
        boolean resultado = parque.agregarVisitante(visitante);
        assertFalse(resultado);
        assertEquals(1, parque.getListVisitante().size());
    }

    @Test
    public void testNoAgregarVisitanteSiCapacidadLlena() {
        parque = new ParqueDeAtraccion("Park", "999", "Calle", 1);
        Visitante v1 = new Visitante("V1", "11", 20, 1.70, 100.0);
        Visitante v2 = new Visitante("V2", "22", 25, 1.75, 150.0);

        parque.agregarVisitante(v1);
        boolean resultado = parque.agregarVisitante(v2);
        assertFalse(resultado);
        assertEquals(1, parque.getListVisitante().size());
    }

    @Test
    public void testBuscarVisitante() {
        parque.agregarVisitante(visitante);
        Visitante encontrado = parque.buscarVisitante("11111");
        assertNotNull(encontrado);
        assertEquals("Carlos Diaz", encontrado.getNombre());
    }

    @Test
    public void testBuscarVisitanteNoExistente() {
        Visitante encontrado = parque.buscarVisitante("99999");
        assertNull(encontrado);
    }

    @Test
    public void testActualizarVisitante() {
        parque.agregarVisitante(visitante);
        boolean resultado = parque.actualizarVisitante("11111", "Nuevo Nombre", 25, 1.75, "555-1234", "Nueva Calle");
        assertTrue(resultado);
        assertEquals("Nuevo Nombre", visitante.getNombre());
        assertEquals(25, visitante.getEdad());
    }

    @Test
    public void testActualizarVisitanteNoExistente() {
        boolean resultado = parque.actualizarVisitante("99999", "Nombre", 30, 1.80, "555", "Calle");
        assertFalse(resultado);
    }

    @Test
    public void testEliminarVisitante() {
        parque.agregarVisitante(visitante);
        boolean resultado = parque.eliminarVisitante("11111");
        assertTrue(resultado);
        assertEquals(0, parque.getListVisitante().size());
    }

    @Test
    public void testEliminarVisitanteNoExistente() {
        boolean resultado = parque.eliminarVisitante("99999");
        assertFalse(resultado);
    }

    @Test
    public void testAgregarOperador() {
        boolean resultado = parque.agregarOperador(operador);
        assertTrue(resultado);
        assertEquals(1, parque.getListOperador().size());
    }

    @Test
    public void testBuscarOperador() {
        parque.agregarOperador(operador);
        Operador encontrado = parque.buscarOperador("22222");
        assertNotNull(encontrado);
        assertEquals("Operador1", encontrado.getNombre());
    }

    @Test
    public void testEliminarOperador() {
        parque.agregarOperador(operador);
        boolean resultado = parque.eliminarOperador("22222");
        assertTrue(resultado);
        assertEquals(0, parque.getListOperador().size());
    }

    @Test
    public void testAgregarAdministrador() {
        boolean resultado = parque.agregarAdministrador(admin);
        assertTrue(resultado);
        assertEquals(1, parque.getListAdmin().size());
    }

    @Test
    public void testBuscarAdministrador() {
        parque.agregarAdministrador(admin);
        Administrador encontrado = parque.buscarAdministrador("33333");
        assertNotNull(encontrado);
        assertEquals("Admin1", encontrado.getNombre());
    }

    @Test
    public void testEliminarAdministrador() {
        parque.agregarAdministrador(admin);
        boolean resultado = parque.eliminarAdministrador("33333");
        assertTrue(resultado);
        assertEquals(0, parque.getListAdmin().size());
    }

    @Test
    public void testAgregarZona() {
        boolean resultado = parque.agregarZona(zona);
        assertTrue(resultado);
        assertEquals(1, parque.getListZona().size());
    }

    @Test
    public void testBuscarZona() {
        parque.agregarZona(zona);
        Zona encontrada = parque.buscarZona("ZONA1");
        assertNotNull(encontrada);
        assertEquals("Aventura", encontrada.getNombre());
    }

    @Test
    public void testEliminarZona() {
        parque.agregarZona(zona);
        boolean resultado = parque.eliminarZona("ZONA1");
        assertTrue(resultado);
        assertEquals(0, parque.getListZona().size());
    }

    @Test
    public void testAgregarAtraccionAZona() {
        parque.agregarZona(zona);
        boolean resultado = parque.agregarAtraccionAZona("ZONA1", atraccion);
        assertTrue(resultado);
        assertEquals(1, zona.getListAtraccion().size());
    }

    @Test
    public void testBuscarAtraccion() {
        parque.agregarZona(zona);
        parque.agregarAtraccionAZona("ZONA1", atraccion);
        Atraccion encontrada = parque.buscarAtraccion("ATR001");
        assertNotNull(encontrada);
        assertEquals("Montaña Rusa", encontrada.getNombre());
    }

    @Test
    public void testEliminarAtraccionDeZona() {
        parque.agregarZona(zona);
        parque.agregarAtraccionAZona("ZONA1", atraccion);
        boolean resultado = parque.eliminarAtraccionDeZona("ZONA1", "ATR001");
        assertTrue(resultado);
        assertEquals(0, zona.getListAtraccion().size());
    }

    @Test
    public void testGetTodasLasAtracciones() {
        parque.agregarZona(zona);
        Atraccion atr2 = new Atraccion("ATR002", "Tobogán", TipoAtraccion.ACUATICA, 80, 0.0, 5, 3.0);
        parque.agregarAtraccionAZona("ZONA1", atraccion);
        parque.agregarAtraccionAZona("ZONA1", atr2);

        List<Atraccion> todas = parque.getTodasLasAtracciones();
        assertEquals(2, todas.size());
    }

    @Test
    public void testRecargarSaldoVisitante() {
        parque.agregarVisitante(visitante);
        double saldoAntes = visitante.getSaldoVirtual();
        String resultado = parque.recargarSaldoVisitante("11111", 100.0);
        assertTrue(resultado.contains("recargado"));
        assertEquals(saldoAntes + 100.0, visitante.getSaldoVirtual());
    }

    @Test
    public void testRecargarSaldoMontoInvalido() {
        parque.agregarVisitante(visitante);
        String resultado = parque.recargarSaldoVisitante("11111", -50.0);
        assertTrue(resultado.contains("mayor a cero"));
    }

    @Test
    public void testRecargarSaldoVisitanteNoExistente() {
        String resultado = parque.recargarSaldoVisitante("99999", 50.0);
        assertTrue(resultado.contains("no encontrado"));
    }

    @Test
    public void testVenderTicket() {
        parque.agregarVisitante(visitante);
        String resultado = parque.venderTicket("11111", TipoTicket.GENERAL, 100.0, 1);
        assertTrue(resultado.contains("comprado"));
        assertEquals(100.0, visitante.getSaldoVirtual());
    }

    @Test
    public void testVenderTicketParqueControl() {
        parque = new ParqueDeAtraccion("Park", "999", "Calle", 1);
        Visitante v1 = new Visitante("V1", "11", 20, 1.70, 100.0);
        Visitante v2 = new Visitante("V2", "22", 25, 1.75, 150.0);
        parque.agregarVisitante(v1);
        parque.agregarVisitante(v2);

        String resultado = parque.venderTicket("22", TipoTicket.GENERAL, 50.0, 1);
        assertTrue(resultado.contains("aforo maximo"));
    }

    @Test
    public void testIngresarAAtraccion() {
        parque.agregarZona(zona);
        parque.agregarAtraccionAZona("ZONA1", atraccion);
        parque.agregarVisitante(visitante);

        Ticket ticket = new TicketGeneral("TK001", 100.0);
        visitante.comprarTicket(ticket);

        String resultado = parque.ingresarAAtraccion("11111", "ATR001");
        assertTrue(resultado.contains("Acceso"));
    }

    @Test
    public void testIngresarAAtraccionSinTicket() {
        parque.agregarZona(zona);
        parque.agregarAtraccionAZona("ZONA1", atraccion);
        parque.agregarVisitante(visitante);

        String resultado = parque.ingresarAAtraccion("11111", "ATR001");
        assertTrue(resultado.contains("no tiene ticket"));
    }

    @Test
    public void testActivarAlertaClimatica() {
        parque.agregarZona(zona);
        Atraccion acuatica = new Atraccion("ATR002", "Piscina", TipoAtraccion.ACUATICA, 100, 0.0, 0, 0.0);
        parque.agregarAtraccionAZona("ZONA1", acuatica);

        var notificaciones = parque.activarAlertaClimatica();
        assertTrue(notificaciones.size() > 0);
        assertEquals(org.example.proyectofinalparque.model.enums.EstadoActual.CERRADA, acuatica.getEstado());
    }

    @Test
    public void testDesactivarAlertaClimatica() {
        parque.agregarZona(zona);
        Atraccion acuatica = new Atraccion("ATR002", "Piscina", TipoAtraccion.ACUATICA, 100, 0.0, 0, 0.0);
        parque.agregarAtraccionAZona("ZONA1", acuatica);

        parque.activarAlertaClimatica();
        parque.desactivarAlertaClimatica();
        assertEquals(org.example.proyectofinalparque.model.enums.EstadoActual.ACTIVA, acuatica.getEstado());
    }

    @Test
    public void testGenerarReporteDiario() {
        parque.agregarZona(zona);
        parque.agregarAtraccionAZona("ZONA1", atraccion);
        parque.agregarVisitante(visitante);
        parque.agregarOperador(operador);

        String reporte = parque.generarReporteDiario();
        assertNotNull(reporte);
        assertTrue(reporte.length() > 0);
    }
}
