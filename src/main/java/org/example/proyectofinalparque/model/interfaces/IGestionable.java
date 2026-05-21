package org.example.proyectofinalparque.model.interfaces;

public interface IGestionable {
    void agregar(Object elemento);
    boolean eliminar(String id);
    int getTotalElementos();
}
