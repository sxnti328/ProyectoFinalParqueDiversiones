package org.example.proyectofinalparque.model.records;

import org.example.proyectofinalparque.model.clases.*;
import org.example.proyectofinalparque.model.enums.TipoAtraccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NotificacionTest {

    private Notificacion notif;

    @BeforeEach
    public void setUp() {
        notif = new Notificacion("CLIMA", "Atracciones cerradas por lluvia", LocalDate.now());
    }

    @Test
    public void testCrearNotificacion() {
        assertEquals("CLIMA", notif.tipo());
        assertEquals("Atracciones cerradas por lluvia", notif.mensaje());
        assertEquals(LocalDate.now(), notif.fecha());
    }

    @Test
    public void testToString() {
        String texto = notif.toString();
        assertTrue(texto.contains("CLIMA"));
        assertTrue(texto.contains("Atracciones cerradas por lluvia"));
    }

    @Test
    public void testEnviarNotificacionAVisitante() {
        Visitante visitante = new Visitante("Ana", "11111", 20, 1.65, 100.0);
        ParqueDeAtraccion parque = new ParqueDeAtraccion("Park", "999", "Calle", 100);

        parque.enviarNotificacion(visitante, notif);

        assertEquals(1, visitante.getListNotificaciones().size());
        assertEquals("CLIMA", visitante.getListNotificaciones().get(0).tipo());
    }

    @Test
    public void testEnviarNotificacionVisitanteNulo() {
        ParqueDeAtraccion parque = new ParqueDeAtraccion("Park", "999", "Calle", 100);
        // No debe lanzar excepcion
        parque.enviarNotificacion(null, notif);
        // El metodo simplemente ignora el envio
        assertTrue(true);
    }

    @Test
    public void testVisitanteRecibeNotificacionPorAlertaClimatica() {
        // Cuando el admin activa la alerta climatica, los visitantes con ticket
        // activo reciben una notificacion
        ParqueDeAtraccion parque = new ParqueDeAtraccion("Tech-Park", "123", "Calle", 100);
        Zona zona = new Zona("Z1", "Acuatica", "Piscinas", 500);
        Atraccion piscina = new Atraccion("ATR1", "Piscina", TipoAtraccion.ACUATICA, 100, 0.0, 0, 0.0);
        zona.agregarAtraccion(piscina);
        parque.agregarZona(zona);

        Visitante visitante = new Visitante("Carlos", "12345", 20, 1.70, 100.0);
        parque.agregarVisitante(visitante);

        // El visitante necesita ticket activo para recibir notificacion
        Ticket ticket = new TicketGeneral("TK1", 50.0);
        visitante.comprarTicket(ticket);

        // El admin activa la alerta
        List<Notificacion> notifs = parque.activarAlertaClimatica();

        // Se cerraron atracciones
        assertTrue(notifs.size() > 0);

        // El visitante recibio la notificacion
        assertEquals(1, visitante.getListNotificaciones().size());
        Notificacion recibida = visitante.getListNotificaciones().get(0);
        assertEquals("CLIMA", recibida.tipo());
        assertTrue(recibida.mensaje().contains("Alerta climatica"));
    }

    @Test
    public void testVisitanteSinTicketNoRecibeNotificacion() {
        ParqueDeAtraccion parque = new ParqueDeAtraccion("Tech-Park", "123", "Calle", 100);
        Zona zona = new Zona("Z1", "Acuatica", "Piscinas", 500);
        Atraccion piscina = new Atraccion("ATR1", "Piscina", TipoAtraccion.ACUATICA, 100, 0.0, 0, 0.0);
        zona.agregarAtraccion(piscina);
        parque.agregarZona(zona);

        Visitante visitante = new Visitante("Carlos", "12345", 20, 1.70, 100.0);
        parque.agregarVisitante(visitante);
        // No compra ticket

        parque.activarAlertaClimatica();

        // No recibe notificacion porque no tiene ticket activo
        assertEquals(0, visitante.getListNotificaciones().size());
    }

    @Test
    public void testNotificacionPorRevisionTecnica() {
        // Cuando un operador hace una revision tecnica, los visitantes
        // que tienen esa atraccion como favorita reciben notificacion
        ParqueDeAtraccion parque = new ParqueDeAtraccion("Tech-Park", "123", "Calle", 100);
        Zona zona = new Zona("Z1", "Aventura", "Mecanicas", 500);
        Atraccion atr = new Atraccion("ATR1", "Montaña", TipoAtraccion.MECANICA_ALTURA, 50, 1.40, 10, 5.0);
        zona.agregarAtraccion(atr);
        parque.agregarZona(zona);

        Operador op = new Operador("Luis", "55555", 30, "EMP1", "Día", "Z1");
        zona.agregarOperador(op);
        parque.agregarOperador(op);

        Visitante visitante = new Visitante("Ana", "11111", 20, 1.65, 100.0);
        visitante.agregarFavorita("ATR1");
        parque.agregarVisitante(visitante);

        // El operador registra la revision
        parque.registrarRevisionTecnica("55555", "ATR1", "Revision OK");

        // El visitante con favorita debe haber recibido notificacion
        assertEquals(1, visitante.getListNotificaciones().size());
        assertEquals("REVISION", visitante.getListNotificaciones().get(0).tipo());
    }

    @Test
    public void testMultiplesNotificaciones() {
        Visitante visitante = new Visitante("Pedro", "22222", 25, 1.75, 100.0);
        ParqueDeAtraccion parque = new ParqueDeAtraccion("Park", "999", "Calle", 100);

        Notificacion n1 = new Notificacion("CLIMA", "Mensaje 1", LocalDate.now());
        Notificacion n2 = new Notificacion("REVISION", "Mensaje 2", LocalDate.now());
        Notificacion n3 = new Notificacion("INFO", "Mensaje 3", LocalDate.now());

        parque.enviarNotificacion(visitante, n1);
        parque.enviarNotificacion(visitante, n2);
        parque.enviarNotificacion(visitante, n3);

        assertEquals(3, visitante.getListNotificaciones().size());
    }
}
