package org.example.proyectofinalparque.viewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.proyectofinalparque.App;
import org.example.proyectofinalparque.controller.OperadorController;
import org.example.proyectofinalparque.model.clases.Atraccion;
import org.example.proyectofinalparque.model.clases.Operador;
import org.example.proyectofinalparque.model.clases.Zona;
import org.example.proyectofinalparque.model.enums.EstadoActual;
import org.example.proyectofinalparque.model.enums.MotivoCierre;

public class OperadorViewController {

    private App app;
    private OperadorController operadorController;

    @FXML private ComboBox<Operador> cbOperador;
    @FXML private Label              lblZona;
    @FXML private Label              lblInfoOperador;

    @FXML private TableView<Atraccion>            tablaAtracciones;
    @FXML private TableColumn<Atraccion, String>  colNombreA;
    @FXML private TableColumn<Atraccion, String>  colTipoA;
    @FXML private TableColumn<Atraccion, String>  colEstadoA;
    @FXML private TableColumn<Atraccion, Integer> colVisitantesA;
    @FXML private TableColumn<Atraccion, Integer> colEsperaA;

    @FXML private TextField txtDocVisitante;
    @FXML private ComboBox<Atraccion> cbAtraccionAcceso;
    @FXML private Label lblResultadoAcceso;

    @FXML private ComboBox<Atraccion>    cbAtraccionEstado;
    @FXML private ComboBox<EstadoActual> cbNuevoEstado;
    @FXML private ComboBox<MotivoCierre> cbMotivo;
    @FXML private Label lblResultadoEstado;

    @FXML private ComboBox<Atraccion> cbAtraccionRevision;
    @FXML private TextArea txtDescripcion;
    @FXML private Label lblResultadoRevision;

    // Recargar saldo del visitante (solo lo puede hacer un operador)
    @FXML private TextField txtDocSaldo;
    @FXML private TextField txtMontoSaldo;
    @FXML private Label lblResultadoSaldo;

    public void setApp(App app) {
        this.app = app;
        this.operadorController = new OperadorController(app.parque);
        initView();
    }

    private void initView() {
        colNombreA.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipoA.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colEstadoA.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colVisitantesA.setCellValueFactory(new PropertyValueFactory<>("contadorVisitantes"));
        colEsperaA.setCellValueFactory(new PropertyValueFactory<>("tiempoEspera"));

        cbOperador.setItems(FXCollections.observableArrayList(operadorController.obtenerListaOperadores()));
        cbNuevoEstado.setItems(FXCollections.observableArrayList(EstadoActual.values()));
        cbMotivo.setItems(FXCollections.observableArrayList(MotivoCierre.values()));
    }

    @FXML
    private void onSeleccionarOperador() {
        Operador op = cbOperador.getValue();
        if (op == null) return;

        Zona zona = operadorController.obtenerZonaDelOperador(op);
        lblInfoOperador.setText("Operador: " + op.getNombre() + " | Turno: " + op.getTurno());

        if (zona != null) {
            lblZona.setText("Zona: " + zona.getNombre());
            ObservableList<Atraccion> lista = FXCollections.observableArrayList(zona.getListAtraccion());
            tablaAtracciones.setItems(lista);
            cbAtraccionAcceso.setItems(lista);
            cbAtraccionEstado.setItems(lista);
            cbAtraccionRevision.setItems(lista);
        } else {
            lblZona.setText("Sin zona asignada");
        }
    }

    @FXML
    private void onValidarAcceso() {
        String doc = txtDocVisitante.getText();
        Atraccion a = cbAtraccionAcceso.getValue();
        if (doc.isEmpty() || a == null) {
            lblResultadoAcceso.setText("Complete documento y atraccion.");
            return;
        }
        String resultado = operadorController.validarAcceso(doc, a.getId());
        lblResultadoAcceso.setText(resultado);
        tablaAtracciones.refresh();
    }

    @FXML
    private void onCambiarEstado() {
        Atraccion a = cbAtraccionEstado.getValue();
        EstadoActual est = cbNuevoEstado.getValue();
        MotivoCierre mot = cbMotivo.getValue();
        if (a == null || est == null) {
            lblResultadoEstado.setText("Seleccione atraccion y estado.");
            return;
        }
        a.cambiarEstado(est, mot);
        lblResultadoEstado.setText("Estado de " + a.getNombre() + " cambiado a " + est);
        tablaAtracciones.refresh();
    }

    @FXML
    private void onRegistrarRevision() {
        Operador op = cbOperador.getValue();
        Atraccion a = cbAtraccionRevision.getValue();
        String desc = txtDescripcion.getText();
        if (op == null || a == null) {
            lblResultadoRevision.setText("Seleccione operador y atraccion.");
            return;
        }
        String resultado = operadorController.registrarRevision(op.getDocumento(), a.getId(), desc);
        lblResultadoRevision.setText(resultado);
        tablaAtracciones.refresh();
    }

    @FXML
    private void onRecargarSaldo() {
        String doc = txtDocSaldo.getText();
        if (doc.isEmpty()) {
            lblResultadoSaldo.setText("Ingrese el documento del visitante.");
            return;
        }
        try {
            double monto = Double.parseDouble(txtMontoSaldo.getText());
            String resultado = operadorController.recargarSaldo(doc, monto);
            lblResultadoSaldo.setText(resultado);
        } catch (NumberFormatException e) {
            lblResultadoSaldo.setText("Monto invalido.");
        }
    }

    @FXML
    private void onVolver() {
        app.mostrarMenuPrincipal();
    }
}
