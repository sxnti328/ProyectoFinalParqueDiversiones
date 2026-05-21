package org.example.proyectofinalparque;

import org.example.proyectofinalparque.model.clases.*;
import org.example.proyectofinalparque.model.enums.TipoAtraccion;

public class Parque {

    private static ParqueDeAtraccion instancia;

    private Parque() {}

    public static ParqueDeAtraccion get() {
        if (instancia == null) {
            instancia = new ParqueDeAtraccion("Tech-Park UQ", "NIT-123-456", "Calle 100 # 50-30", 500);
            cargarDatosDemostracion();
        }
        return instancia;
    }

    private static void cargarDatosDemostracion() {
        // ── Zonas ──────────────────────────────────────────────────────────
        Zona zonaAventura = new Zona("Z001", "Zona Aventura",
                "Atracciones de alta emocion para adultos", 150);
        Zona zonaInfantil = new Zona("Z002", "Zona Infantil",
                "Atracciones para los mas pequeños", 100);
        Zona zonaAcuatica = new Zona("Z003", "Zona Acuatica",
                "Piscinas y toboganes acuaticos", 80);

        // ── Atracciones ───────────────────────────────────────────────────
        Atraccion montanaRusa = new Atraccion(
                "A001", "Montana Rusa Extrema",
                TipoAtraccion.MECANICA_ALTURA, 20, 1.40, 12, 5000.0);
        Atraccion tobogan = new Atraccion(
                "A004", "Tobogan Gigante",
                TipoAtraccion.ACUATICA, 10, 1.10, 6, 4000.0);
        Atraccion piscinaOlas = new Atraccion(
                "A005", "Piscina de Olas",
                TipoAtraccion.ACUATICA, 50, 0.0, 0, 2000.0);

        zonaAventura.agregarAtraccion(montanaRusa);
        zonaAcuatica.agregarAtraccion(tobogan);
        zonaAcuatica.agregarAtraccion(piscinaOlas);

        instancia.agregarZona(zonaAventura);
        instancia.agregarZona(zonaInfantil);
        instancia.agregarZona(zonaAcuatica);

        // ── Administrador ────────────────────────────────────────────────
        Administrador admin = new Administrador("Ana Garcia", "1001001", 35, "EMP001", "Gerencia");
        instancia.agregarAdministrador(admin);

        // ── Operadores ───────────────────────────────────────────────────
        Operador op1 = new Operador("Carlos Lopez",  "2001001", 28, "EMP002", "Manana", "Z001");
        Operador op2 = new Operador("Maria Ruiz",    "2002002", 25, "EMP003", "Tarde",  "Z002");
        Operador op3 = new Operador("Pedro Suarez",  "2003003", 30, "EMP004", "Manana", "Z003");

        zonaAventura.agregarOperador(op1);
        zonaInfantil.agregarOperador(op2);
        zonaAcuatica.agregarOperador(op3);

        instancia.agregarOperador(op1);
        instancia.agregarOperador(op2);
        instancia.agregarOperador(op3);

        // ── Visitantes ───────────────────────────────────────────────────
        Visitante v1 = new Visitante("Juan Perez",    "3001001", 25, 1.75, 80000.0,
                "3001234567", "Calle 10 # 5-30");
        Visitante v2 = new Visitante("Sofia Torres",  "3002002", 16, 1.55, 50000.0,
                "3009876543", "Cra 20 # 15-40");
        Visitante v3 = new Visitante("Luis Gomez",    "3003003", 10, 1.20, 30000.0,
                "3015555555", "Av 30 # 22-10");

        instancia.getListVisitante().add(v1);
        instancia.getListVisitante().add(v2);
        instancia.getListVisitante().add(v3);
    }
}
