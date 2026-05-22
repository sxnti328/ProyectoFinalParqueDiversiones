package org.example.proyectofinalparque.model.interfaces;

import org.example.proyectofinalparque.model.clases.Visitante;
import org.example.proyectofinalparque.model.enums.EstadoActual;

public interface IAccesible {
    boolean verificarAcceso(Visitante visitante);
    double getCostoAdicional();
    EstadoActual getEstado();
    // Registra el ingreso del visitante y retorna un mensaje con el resultado
    String registrarIngreso(Visitante visitante);
}
