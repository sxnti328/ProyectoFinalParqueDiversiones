package org.example.proyectofinalparque.model.clases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VisitanteTest {

    private Visitante visitante;

    @BeforeEach
    public void setUp() {
        visitante = new Visitante("Juan Perez", "12345", 25, 1.75, 100.0);
    }

    @Test
    public void testCrearVisitante() {
        assertEquals("Juan Perez", visitante.getNombre());
        assertEquals("12345", visitante.getDocumento());
        assertEquals(25, visitante.getEdad());
        assertEquals(1.75, visitante.getEstatura());
        assertEquals(100.0, visitante.getSaldoVirtual());
    }

    @Test
    public void testCrearVisitanteConTelefonoYDireccion() {
        Visitante v2 = new Visitante("Maria Lopez", "67890", 30, 1.65, 150.0, "555-1234", "Calle Principal 123");
        assertEquals("555-1234", v2.getTelefono());
        assertEquals("Calle Principal 123", v2.getDireccion());
    }

    @Test
    public void testComprarTicketExito() {
        Ticket ticket = new TicketGeneral("TICKET001", 50.0);
        boolean resultado = visitante.comprarTicket(ticket);
        assertTrue(resultado);
        assertEquals(50.0, visitante.getSaldoVirtual());
        assertEquals(1, visitante.getListTickets().size());
    }

    @Test
    public void testComprarTicketSinSaldo() {
        Ticket ticket = new TicketGeneral("TICKET002", 150.0);
        boolean resultado = visitante.comprarTicket(ticket);
        assertFalse(resultado);
        assertEquals(100.0, visitante.getSaldoVirtual());
        assertEquals(0, visitante.getListTickets().size());
    }

    @Test
    public void testDescontarSaldoExito() {
        boolean resultado = visitante.descontarSaldo(30.0);
        assertTrue(resultado);
        assertEquals(70.0, visitante.getSaldoVirtual());
    }

    @Test
    public void testDescontarSaldoInsuficiente() {
        boolean resultado = visitante.descontarSaldo(150.0);
        assertFalse(resultado);
        assertEquals(100.0, visitante.getSaldoVirtual());
    }

    @Test
    public void testRecargarSaldo() {
        visitante.recargarSaldo(50.0);
        assertEquals(150.0, visitante.getSaldoVirtual());
    }

    @Test
    public void testRecargarSaldoNegativo() {
        visitante.recargarSaldo(-50.0);
        assertEquals(100.0, visitante.getSaldoVirtual());
    }

    @Test
    public void testRecargarSaldoCero() {
        visitante.recargarSaldo(0.0);
        assertEquals(100.0, visitante.getSaldoVirtual());
    }

    @Test
    public void testAgregarFavorita() {
        visitante.agregarFavorita("ATRACCION001");
        assertTrue(visitante.tieneFavorita("ATRACCION001"));
        assertEquals(1, visitante.getListaFavoritas().size());
    }

    @Test
    public void testNoAgregarFavoritaDuplicada() {
        visitante.agregarFavorita("ATRACCION001");
        visitante.agregarFavorita("ATRACCION001");
        assertEquals(1, visitante.getListaFavoritas().size());
    }

    @Test
    public void testEliminarFavorita() {
        visitante.agregarFavorita("ATRACCION001");
        visitante.eliminarFavorita("ATRACCION001");
        assertFalse(visitante.tieneFavorita("ATRACCION001"));
        assertEquals(0, visitante.getListaFavoritas().size());
    }

    @Test
    public void testSetEstatura() {
        visitante.setEstatura(1.80);
        assertEquals(1.80, visitante.getEstatura());
    }

    @Test
    public void testSetTelefono() {
        visitante.setTelefono("555-9999");
        assertEquals("555-9999", visitante.getTelefono());
    }

    @Test
    public void testSetDireccion() {
        visitante.setDireccion("Calle Nueva 456");
        assertEquals("Calle Nueva 456", visitante.getDireccion());
    }

    @Test
    public void testTieneFastPassFalse() {
        assertFalse(visitante.tieneFastPass());
    }

    @Test
    public void testGetTicketActivoNull() {
        assertNull(visitante.getTicketActivo());
    }

    @Test
    public void testMultiplesCompras() {
        Ticket t1 = new TicketGeneral("T1", 25.0);
        Ticket t2 = new TicketGeneral("T2", 25.0);
        visitante.comprarTicket(t1);
        visitante.comprarTicket(t2);
        assertEquals(50.0, visitante.getSaldoVirtual());
        assertEquals(2, visitante.getListTickets().size());
    }
}