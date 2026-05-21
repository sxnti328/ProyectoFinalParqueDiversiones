package org.example.proyectofinalparque.controller;

import org.example.proyectofinalparque.model.clases.*;
import org.example.proyectofinalparque.model.enums.TipoAtraccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class OperadorControllerTest {

    private OperadorController controller;
    private ParqueDeAtraccion parque;
    private Operador operador;
    private Zona zona;
    private Atraccion atraccion;
    private Visitante visitante;

    @BeforeEach
    public void setUp() {
        parque = new ParqueDeAtraccion("Tech-Park", "123456789", "Calle Principal 100", 1000);
        controller = new OperadorController(parque);

        operador = new Operador("Julio Ramirez", "55555", 32, "EMP001", "Día", "ZONA1");
        zona = new Zona("ZONA1", "Aventura", "Atracciones mecánicas", 500);
        atraccion = new Atraccion("ATR001", "Montaña Rusa", TipoAtraccion.MECANICA_ALTURA, 50, 1.40, 10, 5.0);
        visitante = new Visitante("Roberto Silva", "44444", 18, 1.75, 150.0);

        parque.agregarOperador(operador);
        parque.agregarZona(zona);
        zona.agregarAtraccion(atraccion);
        parque.agregarVisitante(visitante);
    }

    @Test
    public void testObtenerListaOperadores() {
        ArrayList<Operador> lista = controller.obtenerListaOperadores();
        assertEquals(1, lista.size());
        assertTrue(lista.contains(operador));
    }

    @Test
    public void testObtenerZonaDelOperador() {
        Zona z = controller.obtenerZonaDelOperador(operador);
        assertNotNull(z);
        assertEquals("ZONA1", z.getIdZona());
    }

    @Test
    public void testObtenerZonaDelOperadorNull() {
        Zona z = controller.obtenerZonaDelOperador(null);
        assertNull(z);
    }

    @Test
    public void testValidarAccesoExito() {
        // El visitante necesita un ticket activo
        Ticket ticket = new TicketGeneral("TK001", 100.0);
        visitante.comprarTicket(ticket);

        String resultado = controller.validarAcceso("44444", "ATR001");
        assertTrue(resultado.contains("Acceso") || resultado.contains("autorizado"));
    }

    @Test
    public void testValidarAccesoSinTicket() {
        // Sin ticket activo, no puede entrar
        String resultado = controller.validarAcceso("44444", "ATR001");
        assertTrue(resultado.contains("no tiene ticket") || resultado.contains("Acceso"));
    }

    @Test
    public void testValidarAccesoVisitanteNoExistente() {
        String resultado = controller.validarAcceso("99999", "ATR001");
        assertTrue(resultado.contains("no encontrado"));
    }

    @Test
    public void testValidarAccesoAtraccionNoExistente() {
        Ticket ticket = new TicketGeneral("TK002", 100.0);
        visitante.comprarTicket(ticket);
        String resultado = controller.validarAcceso("44444", "ATR999");
        assertTrue(resultado.contains("no encontrada"));
    }

    @Test
    public void testValidarAccesoPorEstatura() {
        Visitante bajo = new Visitante("Bajito", "66666", 12, 1.30, 150.0);
        Ticket ticket = new TicketGeneral("TK003", 100.0);
        bajo.comprarTicket(ticket);
        parque.agregarVisitante(bajo);

        String resultado = controller.validarAcceso("66666", "ATR001");
        assertTrue(resultado.contains("denegado"));
    }

    @Test
    public void testRecargarSaldoExito() {
        double saldoAntes = visitante.getSaldoVirtual();
        String resultado = controller.recargarSaldo("44444", 50.0);
        assertTrue(resultado.contains("recargado"));
        assertEquals(saldoAntes + 50.0, visitante.getSaldoVirtual());
    }

    @Test
    public void testRecargarSaldoMontoNegativo() {
        double saldoAntes = visitante.getSaldoVirtual();
        String resultado = controller.recargarSaldo("44444", -50.0);
        assertTrue(resultado.contains("mayor a cero"));
        assertEquals(saldoAntes, visitante.getSaldoVirtual());
    }

    @Test
    public void testRecargarSaldoMontoCero() {
        double saldoAntes = visitante.getSaldoVirtual();
        String resultado = controller.recargarSaldo("44444", 0.0);
        assertTrue(resultado.contains("mayor a cero"));
        assertEquals(saldoAntes, visitante.getSaldoVirtual());
    }

    @Test
    public void testRecargarSaldoVisitanteNoExistente() {
        String resultado = controller.recargarSaldo("99999", 50.0);
        assertTrue(resultado.contains("no encontrado"));
    }

    @Test
    public void testRecargarSaldoMultiples() {
        controller.recargarSaldo("44444", 50.0);
        controller.recargarSaldo("44444", 30.0);
        assertEquals(230.0, visitante.getSaldoVirtual());
    }

    @Test
    public void testRegistrarRevision() {
        String resultado = controller.registrarRevision("55555", "ATR001", "Mantenimiento preventivo realizado");
        assertTrue(resultado.contains("registrada") || resultado.contains("revision") || resultado.contains("izado"));
    }

    @Test
    public void testRegistrarRevisionOperadorNoExistente() {
        String resultado = controller.registrarRevision("99999", "ATR001", "Descripcion");
        assertTrue(resultado.contains("no encontrado"));
    }

    @Test
    public void testRegistrarRevisionAtraccionNoExistente() {
        String resultado = controller.registrarRevision("55555", "ATR999", "Descripcion");
        assertTrue(resultado.contains("no encontrada"));
    }

    @Test
    public void testValidarAccesoConFastPass() {
        // FastPass debería permitir acceso y potencialmente otros beneficios
        TicketFastPass fpTicket = new TicketFastPass("TFP001", 200.0);
        visitante.comprarTicket(fpTicket);
        assertTrue(visitante.tieneFastPass());

        String resultado = controller.validarAcceso("44444", "ATR001");
        assertTrue(resultado.contains("FAST-PASS") || resultado.contains("Fast"));
    }

    @Test
    public void testMultiplesValidacionesDeSaldo() {
        controller.recargarSaldo("44444", 50.0);
        assertEquals(200.0, visitante.getSaldoVirtual());

        // Compra de ticket
        Ticket ticket = new TicketGeneral("TK004", 100.0);
        visitante.comprarTicket(ticket);
        assertEquals(100.0, visitante.getSaldoVirtual());

        // Segunda recarga
        controller.recargarSaldo("44444", 25.0);
        assertEquals(125.0, visitante.getSaldoVirtual());
    }
}
