package org.example.proyectofinalparque.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.proyectofinalparque.HelloApplication;
import org.example.proyectofinalparque.Parque;
import org.example.proyectofinalparque.model.clases.*;
import org.example.proyectofinalparque.model.enums.TipoAtraccion;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    // ── Tab Operadores ──────────────────────────────────────────────────────
    @FXML private TextField txtOpNombre;
    @FXML private TextField txtOpDocumento;
    @FXML private TextField txtOpEdad;
    @FXML private TextField txtOpIdEmp;
    @FXML private TextField txtOpTurno;
    @FXML private ComboBox<Zona> cbOpZona;
    @FXML private Label     lblOpMsg;
    @FXML private TableView<Operador>            tablaOperadores;
    @FXML private TableColumn<Operador, String>  colOpNombre;
    @FXML private TableColumn<Operador, String>  colOpDoc;
    @FXML private TableColumn<Operador, String>  colOpTurno;
    @FXML private TableColumn<Operador, String>  colOpZona;

    // ── Tab Zonas ───────────────────────────────────────────────────────────
    @FXML private TextField txtZonaId;
    @FXML private TextField txtZonaNombre;
    @FXML private TextField txtZonaDesc;
    @FXML private TextField txtZonaCap;
    @FXML private Label     lblZonaMsg;
    @FXML private TableView<Zona>            tablaZonas;
    @FXML private TableColumn<Zona, String>  colZonaId;
    @FXML private TableColumn<Zona, String>  colZonaNombre;
    @FXML private TableColumn<Zona, Integer> colZonaCap;

    // ── Tab Atracciones ─────────────────────────────────────────────────────
    @FXML private TextField txtAtrId;
    @FXML private TextField txtAtrNombre;
    @FXML private ComboBox<TipoAtraccion> cbAtrTipo;
    @FXML private TextField txtAtrCap;
    @FXML private TextField txtAtrAltura;
    @FXML private TextField txtAtrEdad;
    @FXML private TextField txtAtrCosto;
    @FXML private ComboBox<Zona> cbAtrZona;
    @FXML private Label     lblAtrMsg;
    @FXML private TableView<Atraccion>            tablaAtracciones;
    @FXML private TableColumn<Atraccion, String>  colAtrNombre;
    @FXML private TableColumn<Atraccion, String>  colAtrTipo;
    @FXML private TableColumn<Atraccion, String>  colAtrEstado;
    @FXML private TableColumn<Atraccion, Integer> colAtrVisitantes;

    // ── Tab Alertas / Reportes ───────────────────────────────────────────────
    @FXML private Label    lblAlertaMsg;
    @FXML private TextArea txtReporte;

    private final ParqueDeAtraccion parque = Parque.get();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Operadores
        if (colOpNombre   != null) colOpNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colOpDoc      != null) colOpDoc.setCellValueFactory(new PropertyValueFactory<>("documento"));
        if (colOpTurno    != null) colOpTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        if (colOpZona     != null) colOpZona.setCellValueFactory(new PropertyValueFactory<>("idZona"));
        if (cbOpZona      != null) cbOpZona.setItems(FXCollections.observableArrayList(parque.getListZona()));

        // Zonas
        if (colZonaId     != null) colZonaId.setCellValueFactory(new PropertyValueFactory<>("idZona"));
        if (colZonaNombre != null) colZonaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colZonaCap    != null) colZonaCap.setCellValueFactory(new PropertyValueFactory<>("capacidadMax"));

        // Atracciones
        if (colAtrNombre    != null) colAtrNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colAtrTipo      != null) colAtrTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        if (colAtrEstado    != null) colAtrEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        if (colAtrVisitantes!= null) colAtrVisitantes.setCellValueFactory(new PropertyValueFactory<>("contadorVisitantes"));
        if (cbAtrTipo       != null) cbAtrTipo.setItems(FXCollections.observableArrayList(TipoAtraccion.values()));
        if (cbAtrZona       != null) cbAtrZona.setItems(FXCollections.observableArrayList(parque.getListZona()));

        cargarTodas();
    }

    // ── Operadores ──────────────────────────────────────────────────────────

    @FXML private void guardarOperador() {
        try {
            Zona z = cbOpZona != null ? cbOpZona.getValue() : null;
            String idZona = z != null ? z.getIdZona() : "";
            Operador op = new Operador(
                    txtOpNombre.getText().trim(), txtOpDocumento.getText().trim(),
                    Integer.parseInt(txtOpEdad.getText().trim()),
                    txtOpIdEmp.getText().trim(),
                    txtOpTurno.getText().trim(), idZona);
            if (parque.agregarOperador(op)) {
                if (z != null) z.agregarOperador(op);
                msg(lblOpMsg, "✓ Operador agregado.", false);
                cargarTodas();
                limpiarOp();
            } else {
                msg(lblOpMsg, "✗ Ya existe ese documento.", true);
            }
        } catch (NumberFormatException e) {
            msg(lblOpMsg, "✗ Edad inválida.", true);
        }
    }

    @FXML private void eliminarOperador() {
        Operador sel = tablaOperadores != null
                ? tablaOperadores.getSelectionModel().getSelectedItem() : null;
        if (sel == null) { msg(lblOpMsg, "✗ Seleccione un operador.", true); return; }
        parque.eliminarOperador(sel.getDocumento());
        msg(lblOpMsg, "✓ Operador eliminado.", false);
        cargarTodas();
    }

    // ── Zonas ───────────────────────────────────────────────────────────────

    @FXML private void guardarZona() {
        try {
            Zona z = new Zona(txtZonaId.getText().trim(), txtZonaNombre.getText().trim(),
                    txtZonaDesc.getText().trim(), Integer.parseInt(txtZonaCap.getText().trim()));
            if (parque.agregarZona(z)) {
                msg(lblZonaMsg, "✓ Zona agregada.", false);
                cargarTodas();
                actualizarCombosZona();
                limpiarZona();
            } else {
                msg(lblZonaMsg, "✗ Ya existe esa zona.", true);
            }
        } catch (NumberFormatException e) {
            msg(lblZonaMsg, "✗ Capacidad inválida.", true);
        }
    }

    @FXML private void eliminarZona() {
        Zona sel = tablaZonas != null
                ? tablaZonas.getSelectionModel().getSelectedItem() : null;
        if (sel == null) { msg(lblZonaMsg, "✗ Seleccione una zona.", true); return; }
        parque.eliminarZona(sel.getIdZona());
        msg(lblZonaMsg, "✓ Zona eliminada.", false);
        cargarTodas();
    }

    // ── Atracciones ─────────────────────────────────────────────────────────

    @FXML private void guardarAtraccion() {
        try {
            Zona z = cbAtrZona != null ? cbAtrZona.getValue() : null;
            TipoAtraccion tipo = cbAtrTipo != null ? cbAtrTipo.getValue() : TipoAtraccion.FAMILIAR;
            if (z == null || tipo == null) { msg(lblAtrMsg, "✗ Seleccione zona y tipo.", true); return; }
            Atraccion a = new Atraccion(
                    txtAtrId.getText().trim(), txtAtrNombre.getText().trim(), tipo,
                    Integer.parseInt(txtAtrCap.getText().trim()),
                    Double.parseDouble(txtAtrAltura.getText().trim()),
                    Integer.parseInt(txtAtrEdad.getText().trim()),
                    Double.parseDouble(txtAtrCosto.getText().trim()));
            parque.agregarAtraccionAZona(z.getIdZona(), a);
            msg(lblAtrMsg, "✓ Atraccion agregada a zona " + z.getNombre(), false);
            cargarTodas();
            limpiarAtr();
        } catch (NumberFormatException e) {
            msg(lblAtrMsg, "✗ Verifique los campos numéricos.", true);
        }
    }

    @FXML private void eliminarAtraccion() {
        Atraccion sel = tablaAtracciones != null
                ? tablaAtracciones.getSelectionModel().getSelectedItem() : null;
        if (sel == null || sel.getZona() == null) {
            msg(lblAtrMsg, "✗ Seleccione una atracción.", true); return;
        }
        parque.eliminarAtraccionDeZona(sel.getZona().getIdZona(), sel.getId());
        msg(lblAtrMsg, "✓ Atraccion eliminada.", false);
        cargarTodas();
    }

    // ── Alertas / Reportes ───────────────────────────────────────────────────

    @FXML private void activarAlertaClimatica() {
        var notifs = parque.activarAlertaClimatica();
        if (lblAlertaMsg != null)
            msg(lblAlertaMsg,
                    "⚡ Alerta climática activada. " + notifs.size() + " zona(s) afectadas.",
                    false);
        cargarTodas();
    }

    @FXML private void desactivarAlertaClimatica() {
        parque.desactivarAlertaClimatica();
        if (lblAlertaMsg != null)
            msg(lblAlertaMsg, "✓ Alerta climática desactivada.", false);
        cargarTodas();
    }

    @FXML private void generarReporte() {
        if (txtReporte != null)
            txtReporte.setText(parque.generarReporteDiario());
    }

    @FXML private void volver() {
        HelloApplication.mostrarMenuPrincipal();
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private void cargarTodas() {
        if (tablaOperadores  != null)
            tablaOperadores.setItems(FXCollections.observableArrayList(parque.getListOperador()));
        if (tablaZonas       != null)
            tablaZonas.setItems(FXCollections.observableArrayList(parque.getListZona()));
        if (tablaAtracciones != null)
            tablaAtracciones.setItems(FXCollections.observableArrayList(parque.getTodasLasAtracciones()));
    }

    private void actualizarCombosZona() {
        var zonas = FXCollections.observableArrayList(parque.getListZona());
        if (cbOpZona  != null) cbOpZona.setItems(zonas);
        if (cbAtrZona != null) cbAtrZona.setItems(zonas);
    }

    private void msg(Label lbl, String text, boolean err) {
        if (lbl == null) return;
        lbl.setText(text);
        lbl.setStyle(err ? "-fx-text-fill:#c0392b;" : "-fx-text-fill:#27ae60;");
    }

    private void limpiarOp()  { txtOpNombre.clear(); txtOpDocumento.clear(); txtOpEdad.clear(); txtOpIdEmp.clear(); txtOpTurno.clear(); }
    private void limpiarZona(){ txtZonaId.clear(); txtZonaNombre.clear(); txtZonaDesc.clear(); txtZonaCap.clear(); }
    private void limpiarAtr() { txtAtrId.clear(); txtAtrNombre.clear(); txtAtrCap.clear(); txtAtrAltura.clear(); txtAtrEdad.clear(); txtAtrCosto.clear(); }
}
