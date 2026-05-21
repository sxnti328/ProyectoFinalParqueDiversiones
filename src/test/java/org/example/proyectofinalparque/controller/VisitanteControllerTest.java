package org.example.proyectofinalparque.controller;

import org.example.proyectofinalparque.model.clases.ParqueDeAtraccion;
import org.example.proyectofinalparque.model.clases.Visitante;
import org.example.proyectofinalparque.model.enums.TipoTicket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class VisitanteControllerTest {

    private VisitanteController controller;
    private ParqueDeAtraccion parque;
    private Visitante visitante1;
    private Visitante visitante2;

    @BeforeEach
    public void setUp() {
        parque = new ParqueDeAtraccion("Tech-Park", "123456789", "Calle Principal 100", 1000);
        controller = new VisitanteController(parque);
        visitante1 = new Visitante("Ana Garcia", "11111", 22, 1.70, 200.0);
        visitante2 = new Visitante("Pedro Lopez", "22222", 25, 1.80, 150.0);
    }

    @Test
    public void testCrearVisitante() {
        boolean resultado = controller.crearVisitante(visitante1);
        assertTrue(resultado);
        assertEquals(1, controller.obtenerListaVisitantes().size());
    }

    @Test
    public void testNoCrearVisitanteDuplicado() {
        controller.crearVisitante(visitante1);
        boolean resultado = controller.crearVisitante(visitante1);
        assertFalse(resultado);
        assertEquals(1, controller.obtenerListaVisitantes().size());
    }

    @Test
    public void testCrearMultiplesVisitantes() {
        controller.crearVisitante(visitante1);
        controller.crearVisitante(visitante2);
        assertEquals(2, controller.obtenerListaVisitantes().size());
    }

    @Test
    public void testObtenerListaVisitantes() {
        controller.crearVisitante(visitante1);
        controller.crearVisitante(visitante2);
        ArrayList<Visitante> lista = controller.obtenerListaVisitantes();
        assertEquals(2, lista.size());
        assertTrue(lista.contains(visitante1));
        assertTrue(lista.contains(visitante2));
    }

    @Test
    public void testEliminarVisitante() {
        controller.crearVisitante(visitante1);
        controller.crearVisitante(visitante2);
        boolean resultado = controller.eliminarVisitante("11111");
        assertTrue(resultado);
        assertEquals(1, controller.obtenerListaVisitantes().size());
    }

    @Test
    public void testEliminarVisitanteNoExistente() {
        boolean resultado = controller.eliminarVisitante("99999");
        assertFalse(resultado);
    }

    @Test
    public void testActualizarVisitante() {
        controller.crearVisitante(visitante1);
        boolean resultado = controller.actualizarVisitante("11111", "Ana Maria Garcia", 23, 1.72, "555-1234", "Calle Nueva 50");
        assertTrue(resultado);
        Visitante actualizado = parque.buscarVisitante("11111");
        assertEquals("Ana Maria Garcia", actualizado.getNombre());
        assertEquals(23, actualizado.getEdad());
        assertEquals(1.72, actualizado.getEstatura());
        assertEquals("555-1234", actualizado.getTelefono());
        assertEquals("Calle Nueva 50", actualizado.getDireccion());
    }

    @Test
    public void testActualizarVisitanteNoExistente() {
        boolean resultado = controller.actualizarVisitante("99999", "Nombre", 30, 1.80, "555", "Calle");
        assertFalse(resultado);
    }

    @Test
    public void testComprarTicket() {
        controller.crearVisitante(visitante1);
        String resultado = controller.comprarTicket("11111", TipoTicket.GENERAL, 100.0);
        assertTrue(resultado.contains("comprado") || resultado.contains("Ticket"));
        assertEquals(100.0, visitante1.getSaldoVirtual());
    }

    @Test
    public void testComprarTicketSinSaldo() {
        Visitante pobre = new Visitante("Pobre", "33333", 20, 1.60, 50.0);
        controller.crearVisitante(pobre);
        String resultado = controller.comprarTicket("33333", TipoTicket.GENERAL, 100.0);
        assertTrue(resultado.contains("insuficiente"));
        assertEquals(50.0, pobre.getSaldoVirtual());
    }

    @Test
    public void testComprarTicketVisitanteNoExistente() {
        String resultado = controller.comprarTicket("99999", TipoTicket.GENERAL, 50.0);
        assertTrue(resultado.contains("no encontrado"));
    }

    @Test
    public void testComprarMultiplesTickets() {
        controller.crearVisitante(visitante1);
        controller.comprarTicket("11111", TipoTicket.GENERAL, 100.0);
        controller.comprarTicket("11111", TipoTicket.GENERAL, 50.0);
        assertEquals(50.0, visitante1.getSaldoVirtual());
        assertEquals(2, visitante1.getListTickets().size());
    }

    @Test
    public void testComprarTicketFamiliar() {
        controller.crearVisitante(visitante1);
        String resultado = controller.comprarTicket("11111", TipoTicket.FAMILIAR, 300.0);
        assertTrue(resultado.contains("comprado") || resultado.contains("FAMILIAR"));
    }

    @Test
    public void testComprarTicketFastPass() {
        controller.crearVisitante(visitante1);
        String resultado = controller.comprarTicket("11111", TipoTicket.FAST_PASS, 200.0);
        assertTrue(resultado.contains("comprado") || resultado.contains("FAST"));
    }
}
