package org.example.proyectofinalparque.model.records;

import java.time.LocalDate;

public record Notificacion(String tipo, String mensaje, LocalDate fecha) {
}
