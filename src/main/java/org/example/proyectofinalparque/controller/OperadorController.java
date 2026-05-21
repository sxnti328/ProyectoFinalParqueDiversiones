package org.example.proyectofinalparque.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.proyectofinalparque.HelloApplication;
import org.example.proyectofinalparque.Parque;
import org.example.proyectofinalparque.model.clases.*;
import org.example.proyectofinalparque.model.enums.EstadoActual;
import org.example.proyectofinalparque.model.enums.MotivoCierre;

import java.net.URL;
import java.util.ResourceBundle;

public class OperadorController implements Initializable {

    @FXML private ComboBox<Operador>  cbOperador;
    @FXML private Label               lblZona;
    @FXML private Label               lblInfoOperador;

    @FXML private TableView<Atraccion>            tablaAtracciones;
    @FXML private TableColumn<Atraccion, String>  colNombreA;
    @FXML private TableColumn<Atraccion, String>  colTipoA;
    @FXML private TableColumn<Atraccion, String>  colEstadoA;
    @FXML private TableColumn<Atraccion, Integer> colVisitantesA;
    @FXML private TableColumn<Atraccion, Integer> colEsperaA;

    // Sección validación acceso
    @FXML private TextField txtDocVisitante;
    @FXML private ComboBox<Atraccion> cbAtraccionAcceso;
    @FXML private Label               lblResultadoAcceso;

    // Sección cambiar estado
    @FXML private ComboBox<Atraccion>    cbAtraccionEstado;
    @FXML private ComboBox<EstadoActual> cbNuevoEstado;
    @FXML private ComboBox<MotivoCierre> cbMotivo;
    @FXML private Label                  lblResultadoEstado;

    // Sección revisión técnica
    @FXML private ComboBox<Atraccion> cbAtraccionRevision;
    @FXML private TextArea            txtDescripcion;
    @FXML private Label               lblResultadoRevision;

    private final ParqueDeAtraccion parque = Parque.get();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNombreA.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipoA.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colEstadoA.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colVisitantesA.setCellValueFactory(new PropertyValueFactory<>("contadorVisitantes"));
        colEsperaA.setCellValueFactory(new PropertyValueFactory<>("tiempoEspera"));

        cbOperador.setItems(FXCollections.observableArrayList(parque.getListOperador()));
        cbNuevoEstado.setItems(FXCollections.observableArrayList(EstadoActual.values()));
        cbMotivo.setItems(FXCollections.observableArrayList(MotivoCierre.values()));
    }

    @FXML
    private void seleccionarOperador() {
        Operador op = cbOperador.getValue();
        if (op == null) return;
        Zona zona = parque.buscarZona(op.getIdZona());
        lblInfoOperador.setText("Operador: " + op.getNombre() + " | Turno: " + op.getTurno());
        lblZona.setText(zona != null ? "Zona: " + zona.getNombre() : "Sin zona asignada");

        if (zona != null) {
            var lista = FXCollections.observableArrayList(zona.getListAtraccion());
            tablaAtracciones.setItems(lista);
            cbAtraccionAcceso.setItems(lista);
            cbAtraccionEstado.setItems(lista);
            cbAtraccionRevision.setItems(lista);
        }
    }

    @FXML
    private void validarAcceso() {
        String doc = txtDocVisitante.getText().trim();
        Atraccion a = cbAtraccionAcceso.getValue();
        if (doc.isEmpty() || a == null) {
            lblResultadoAcceso.setText("✗ Complete documento y atracción.");
            lblResultadoAcceso.setStyle("-fx-text-fill:#c0392b;");
            return;
        }
        String resultado = parque.ingresarAAtraccion(doc, a.getId());
        boolean ok = resultado.startsWith("Acceso");
        lblResultadoAcceso.setText(resultado);
        lblResultadoAcceso.setStyle(ok ? "-fx-text-fill:#27ae60;" : "-fx-text-fill:#c0392b;");
        refrescarTabla();
    }

    @FXML
    private void cambiarEstado() {
        Operador  op     = cbOperador.getValue();
        Atraccion a      = cbAtraccionEstado.getValue();
        EstadoActual est = cbNuevoEstado.getValue();
        MotivoCierre mot = cbMotivo.getValue();
        if (op == null || a == null || est == null) {
            lblResultadoEstado.setText("✗ Seleccione operador, atracción y estado.");
            lblResultadoEstado.setStyle("-fx-text-fill:#c0392b;");
            return;
        }
        a.cambiarEstado(est, mot);
        lblResultadoEstado.setText("✓ Estado de '" + a.getNombre() + "' cambiado a " + est);
        lblResultadoEstado.setStyle("-fx-text-fill:#27ae60;");
        refrescarTabla();
    }

    @FXML
    private void registrarRevision() {
        Operador  op  = cbOperador.getValue();
        Atraccion a   = cbAtraccionRevision.getValue();
        String    desc = txtDescripcion != null ? txtDescripcion.getText().trim() : "Sin descripcion";
        if (op == null || a == null) {
            lblResultadoRevision.setText("✗ Seleccione operador y atracción.");
            lblResultadoRevision.setStyle("-fx-text-fill:#c0392b;");
            return;
        }
        String resultado = parque.registrarRevisionTecnica(op.getDocumento(), a.getId(), desc);
        boolean ok = resultado.startsWith("Revision");
        lblResultadoRevision.setText(resultado);
        lblResultadoRevision.setStyle(ok ? "-fx-text-fill:#27ae60;" : "-fx-text-fill:#c0392b;");
        refrescarTabla();
    }

    @FXML
    private void volver() {
        HelloApplication.mostrarMenuPrincipal();
    }

    private void refrescarTabla() {
        tablaAtracciones.refresh();
    }
}
