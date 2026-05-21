package org.example.proyectofinalparque.model.clases;

import org.example.proyectofinalparque.model.enums.TipoAtraccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OperadorTest {

    private Operador operador;
    private Zona zona;
    private Atraccion atraccion;

    @BeforeEach
    public void setUp() {
        operador = new Operador("Carlos Mendez", "99999", 35, "EMP001", "Mañana", "ZONA1");
        zona = new Zona("ZONA1", "Zona de Diversión", "Principal", 500);
        atraccion = new Atraccion("ATR001", "Montaña", TipoAtraccion.MECANICA_ALTURA, 50, 1.40, 10, 5.0);
        zona.agregarAtraccion(atraccion);
    }

    @Test
    public void testCrearOperador() {
        assertEquals("Carlos Mendez", operador.getNombre());
        assertEquals("99999", operador.getDocumento());
        assertEquals(35, operador.getEdad());
        assertEquals("EMP001", operador.getIdEmpleado());
        assertEquals("Mañana", operador.getTurno());
        assertEquals("ZONA1", operador.getIdZona());
    }

    @Test
    public void testPuedeGestionarAtraccionSiEsDelZona() {
        assertTrue(operador.puedeGestionarAtraccion(atraccion));
    }

    @Test
    public void testNoPuedeGestionarAtraccionSiEsOtraZona() {
        Operador op2 = new Operador("Juan", "88888", 30, "EMP002", "Tarde", "ZONA2");
        assertFalse(op2.puedeGestionarAtraccion(atraccion));
    }

    @Test
    public void testNoPuedeGestionarAtraccionSinZona() {
        Atraccion sinZona = new Atraccion("ATR002", "Otra", TipoAtraccion.MECANICA_ALTURA, 50, 1.40, 10, 5.0);
        assertFalse(operador.puedeGestionarAtraccion(sinZona));
    }

    @Test
    public void testSetTurno() {
        operador.setTurno("Tarde");
        assertEquals("Tarde", operador.getTurno());
    }

    @Test
    public void testSetZona() {
        operador.setIdZona("ZONA2");
        assertEquals("ZONA2", operador.getIdZona());
    }

    @Test
    public void testToString() {
        String info = operador.toString();
        assertTrue(info.contains("Carlos Mendez"));
        assertTrue(info.contains("Mañana"));
        assertTrue(info.contains("ZONA1"));
    }
}
