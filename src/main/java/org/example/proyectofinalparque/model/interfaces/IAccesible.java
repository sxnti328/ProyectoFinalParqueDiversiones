package org.example.proyectofinalparque.model.interfaces;

import org.example.proyectofinalparque.model.clases.Visitante;
import org.example.proyectofinalparque.model.enums.EstadoActual;

public interface IAccesible {
    boolean verificarAcceso(Visitante visitante);
    double getCostoAdicional();
    EstadoActual getEstado();
}
