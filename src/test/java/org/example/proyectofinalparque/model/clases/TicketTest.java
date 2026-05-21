package org.example.proyectofinalparque.model.clases;

import org.example.proyectofinalparque.model.enums.TipoTicket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {

    private TicketGeneral ticketGeneral;
    private TicketFamiliar ticketFamiliar;
    private TicketFastPass ticketFastPass;

    @BeforeEach
    public void setUp() {
        ticketGeneral = new TicketGeneral("TG001", 100.0);
        ticketFamiliar = new TicketFamiliar("TF001", 300.0);
        ticketFastPass = new TicketFastPass("TFP001", 200.0);
    }

    @Test
    public void testCrearTicketGeneral() {
        assertEquals("TG001", ticketGeneral.getId());
        assertEquals(100.0, ticketGeneral.getPrecioBase());
        assertEquals(TipoTicket.GENERAL, ticketGeneral.getTipo());
    }

    @Test
    public void testCrearTicketFamiliar() {
        assertEquals("TF001", ticketFamiliar.getId());
        assertEquals(300.0, ticketFamiliar.getPrecioBase());
        assertEquals(TipoTicket.FAMILIAR, ticketFamiliar.getTipo());
    }

    @Test
    public void testCrearTicketFastPass() {
        assertEquals("TFP001", ticketFastPass.getId());
        assertEquals(200.0, ticketFastPass.getPrecioBase());
        assertEquals(TipoTicket.FASTPASS, ticketFastPass.getTipo());
    }

    @Test
    public void testTicketGeneralSinDescuento() {
        assertEquals(0.0, ticketGeneral.calcDescuento());
        assertEquals(100.0, ticketGeneral.getPrecioFinal());
    }

    @Test
    public void testTicketFamiliarConDescuento() {
        double descuento = ticketFamiliar.calcDescuento();
        assertTrue(descuento > 0);
        assertTrue(ticketFamiliar.getPrecioFinal() < ticketFamiliar.getPrecioBase());
    }

    @Test
    public void testTicketFastPassConDescuento() {
        double descuento = ticketFastPass.calcDescuento();
        assertTrue(descuento > 0);
        assertTrue(ticketFastPass.getPrecioFinal() < ticketFastPass.getPrecioBase());
    }

    @Test
    public void testTicketActivoAlCrear() {
        assertTrue(ticketGeneral.isActivo());
    }

    @Test
    public void testInactivarTicket() {
        ticketGeneral.setActivo(false);
        assertFalse(ticketGeneral.isActivo());
    }

    @Test
    public void testTicketDiferentesIds() {
        assertNotEquals(ticketGeneral.getId(), ticketFamiliar.getId());
        assertNotEquals(ticketGeneral.getId(), ticketFastPass.getId());
    }

    @Test
    public void testPrecioFinalGeneral() {
        assertEquals(100.0, ticketGeneral.getPrecioFinal());
    }

    @Test
    public void testPrecioFinalFamiliar() {
        assertTrue(ticketFamiliar.getPrecioFinal() > 0);
        assertTrue(ticketFamiliar.getPrecioFinal() < 300.0);
    }

    @Test
    public void testPrecioFinalFastPass() {
        assertTrue(ticketFastPass.getPrecioFinal() > 0);
        assertTrue(ticketFastPass.getPrecioFinal() < 200.0);
    }
}
