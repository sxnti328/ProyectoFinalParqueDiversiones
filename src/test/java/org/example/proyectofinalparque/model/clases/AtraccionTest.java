package org.example.proyectofinalparque.model.clases;

import org.example.proyectofinalparque.model.enums.EstadoActual;
import org.example.proyectofinalparque.model.enums.MotivoCierre;
import org.example.proyectofinalparque.model.enums.TipoAtraccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AtraccionTest {

    private Atraccion atraccion;
    private Visitante visitante;

    @BeforeEach
    public void setUp() {
        atraccion = new Atraccion("ATR001", "Montaña Rusa", TipoAtraccion.MECANICA_ALTURA,
                                  50, 1.40, 10, 5.0);
        visitante = new Visitante("Carlos", "11111", 16, 1.60, 100.0);
    }

    @Test
    public void testCrearAtraccion() {
        assertEquals("ATR001", atraccion.getId());
        assertEquals("Montaña Rusa", atraccion.getNombre());
        assertEquals(TipoAtraccion.MECANICA_ALTURA, atraccion.getTipo());
        assertEquals(EstadoActual.ACTIVA, atraccion.getEstado());
    }

    @Test
    public void testVerificarAccesoExito() {
        assertTrue(atraccion.verificarAcceso(visitante));
    }

    @Test
    public void testVerificarAccesoPorEstatura() {
        Visitante niño = new Visitante("Pedrito", "22222", 8, 1.20, 50.0);
        assertFalse(atraccion.verificarAcceso(niño));
    }

    @Test
    public void testVerificarAccesoPorEdad() {
        Visitante joven = new Visitante("Juanito", "33333", 8, 1.60, 50.0);
        assertFalse(atraccion.verificarAcceso(joven));
    }

    @Test
    public void testVerificarAccesoCerrada() {
        atraccion.cambiarEstado(EstadoActual.CERRADA, MotivoCierre.CLIMA);
        assertFalse(atraccion.verificarAcceso(visitante));
    }

    @Test
    public void testIncrementarContador() {
        assertEquals(0, atraccion.getContadorVisitantes());
        atraccion.incrementarContador();
        assertEquals(1, atraccion.getContadorVisitantes());
    }

    @Test
    public void testMantenimientoPreventivo() {
        for (int i = 0; i < 500; i++) {
            atraccion.incrementarContador();
        }
        assertEquals(EstadoActual.EN_MANTENIMIENTO, atraccion.getEstado());
        assertEquals(MotivoCierre.REVISION_TECNICA, atraccion.getMotivoCierre());
    }

    @Test
    public void testRegistrarRevisionTecnica() {
        atraccion.cambiarEstado(EstadoActual.EN_MANTENIMIENTO, MotivoCierre.REVISION_TECNICA);
        Operador operador = new Operador("Tecnico1", "44444", 35, "EMP001", "Día", "ZONA1");

        atraccion.registrarRevisionTecnica(operador, "Revisión rutinaria completada");

        assertEquals(EstadoActual.ACTIVA, atraccion.getEstado());
        assertNull(atraccion.getMotivoCierre());
        assertEquals(1, atraccion.getRevisiones().size());
    }

    @Test
    public void testRequiereCierreClimatico() {
        Atraccion acu = new Atraccion("ATR002", "Piscina", TipoAtraccion.ACUATICA, 100, 0.0, 0, 0.0);
        assertTrue(acu.requiereCierreClimatico());
        assertFalse(atraccion.requiereCierreClimatico());
    }

    @Test
    public void testCerrarPorClima() {
        Atraccion acuatica = new Atraccion("ATR003", "Agua Loca", TipoAtraccion.ACUATICA, 80, 0.0, 5, 3.0);
        acuatica.cerrarPorClima();
        assertEquals(EstadoActual.CERRADA, acuatica.getEstado());
        assertEquals(MotivoCierre.CLIMA, acuatica.getMotivoCierre());
    }

    @Test
    public void testCambiarEstado() {
        atraccion.cambiarEstado(EstadoActual.EN_MANTENIMIENTO, MotivoCierre.FALLO_TECNICO);
        assertEquals(EstadoActual.EN_MANTENIMIENTO, atraccion.getEstado());
        assertEquals(MotivoCierre.FALLO_TECNICO, atraccion.getMotivoCierre());
    }

    @Test
    public void testCalcularTiempoEspera() {
        int tiempoInicial = atraccion.calcularTiempoEspera();
        assertTrue(tiempoInicial > 0);
        assertTrue(tiempoInicial <= 20);
    }

    @Test
    public void testCostoAdicional() {
        assertEquals(5.0, atraccion.getCostoAdicional());
    }

    @Test
    public void testRegistrarIngresoConCosto() {
        String resultado = atraccion.registrarIngreso(visitante);
        assertTrue(resultado.contains("Acceso autorizado"));
        assertEquals(95.0, visitante.getSaldoVirtual());
    }

    @Test
    public void testRegistrarIngresoSinAcceso() {
        Visitante bajo = new Visitante("Chaparro", "55555", 15, 1.30, 100.0);
        String resultado = atraccion.registrarIngreso(bajo);
        assertTrue(resultado.contains("Acceso denegado"));
    }

    @Test
    public void testColaVirtualNoNula() {
        assertNotNull(atraccion.getColaVirtual());
    }

    @Test
    public void testSetNombre() {
        atraccion.setNombre("Nueva Montaña");
        assertEquals("Nueva Montaña", atraccion.getNombre());
    }

    @Test
    public void testSetCapacidad() {
        atraccion.setCapacidadMaxima(100);
        assertEquals(100, atraccion.getCapacidadMaxima());
    }

    @Test
    public void testGetRevisiones() {
        assertTrue(atraccion.getRevisiones().isEmpty());
    }
}
