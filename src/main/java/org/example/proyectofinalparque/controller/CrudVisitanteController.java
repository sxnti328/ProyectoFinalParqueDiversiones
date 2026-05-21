package org.example.proyectofinalparque.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.proyectofinalparque.HelloApplication;
import org.example.proyectofinalparque.Parque;
import org.example.proyectofinalparque.model.clases.*;
import org.example.proyectofinalparque.model.enums.TipoTicket;

import java.net.URL;
import java.util.ResourceBundle;

public class CrudVisitanteController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtEdad;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtEstatura;
    @FXML private TextField txtSaldo;
    @FXML private Label     lblMensaje;

    @FXML private TableView<Visitante>              tablaVisitantes;
    @FXML private TableColumn<Visitante, String>    colNombre;
    @FXML private TableColumn<Visitante, String>    colDocumento;
    @FXML private TableColumn<Visitante, Integer>   colEdad;
    @FXML private TableColumn<Visitante, String>    colTelefono;
    @FXML private TableColumn<Visitante, String>    colDireccion;
    @FXML private TableColumn<Visitante, Double>    colEstatura;
    @FXML private TableColumn<Visitante, Double>    colSaldo;

    @FXML private ComboBox<TipoTicket> cbTipoTicket;
    @FXML private TextField            txtPrecioTicket;
    @FXML private Label                lblTicketMsg;

    private final ParqueDeAtraccion parque = Parque.get();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colEstatura.setCellValueFactory(new PropertyValueFactory<>("estatura"));
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldoVirtual"));

        if (cbTipoTicket != null)
            cbTipoTicket.setItems(FXCollections.observableArrayList(TipoTicket.values()));

        cargarTabla();

        tablaVisitantes.getSelectionModel().selectedItemProperty()
                .addListener((obs, old, sel) -> { if (sel != null) poblarFormulario(sel); });
    }

    @FXML private void guardar() {
        try {
            Visitante v = new Visitante(
                    txtNombre.getText().trim(),
                    txtDocumento.getText().trim(),
                    Integer.parseInt(txtEdad.getText().trim()),
                    Double.parseDouble(txtEstatura.getText().trim()),
                    Double.parseDouble(txtSaldo.getText().trim()),
                    txtTelefono.getText().trim(),
                    txtDireccion.getText().trim());
            if (parque.agregarVisitante(v)) {
                msg(lblMensaje, "✓ Visitante registrado.", false);
                cargarTabla(); limpiarCampos();
            } else {
                msg(lblMensaje, "✗ Documento ya existe o parque lleno.", true);
            }
        } catch (NumberFormatException e) {
            msg(lblMensaje, "✗ Edad, estatura y saldo deben ser numéricos.", true);
        }
    }

    @FXML private void actualizar() {
        Visitante sel = tablaVisitantes.getSelectionModel().getSelectedItem();
        if (sel == null) { msg(lblMensaje, "✗ Seleccione un visitante.", true); return; }
        try {
            parque.actualizarVisitante(sel.getDocumento(),
                    txtNombre.getText().trim(),
                    Integer.parseInt(txtEdad.getText().trim()),
                    Double.parseDouble(txtEstatura.getText().trim()),
                    txtTelefono.getText().trim(),
                    txtDireccion.getText().trim());
            msg(lblMensaje, "✓ Visitante actualizado.", false);
            cargarTabla();
        } catch (NumberFormatException e) {
            msg(lblMensaje, "✗ Datos numéricos inválidos.", true);
        }
    }

    @FXML private void eliminar() {
        Visitante sel = tablaVisitantes.getSelectionModel().getSelectedItem();
        if (sel == null) { msg(lblMensaje, "✗ Seleccione un visitante.", true); return; }
        parque.eliminarVisitante(sel.getDocumento());
        msg(lblMensaje, "✓ Visitante eliminado.", false);
        cargarTabla(); limpiarCampos();
    }

    @FXML private void comprarTicket() {
        Visitante sel = tablaVisitantes.getSelectionModel().getSelectedItem();
        if (sel == null || cbTipoTicket == null || cbTipoTicket.getValue() == null) {
            if (lblTicketMsg != null) msg(lblTicketMsg, "✗ Seleccione visitante y tipo.", true);
            return;
        }
        try {
            double precio = Double.parseDouble(txtPrecioTicket.getText().trim());
            String res = parque.venderTicket(sel.getDocumento(), cbTipoTicket.getValue(), precio, 4);
            if (lblTicketMsg != null) msg(lblTicketMsg, res, res.startsWith("Saldo") || res.startsWith("Visitante"));
            cargarTabla();
        } catch (NumberFormatException e) {
            if (lblTicketMsg != null) msg(lblTicketMsg, "✗ Precio inválido.", true);
        }
    }

    @FXML private void volver() { HelloApplication.mostrarMenuPrincipal(); }

    private void cargarTabla() {
        tablaVisitantes.setItems(FXCollections.observableArrayList(parque.getListVisitante()));
    }
    private void poblarFormulario(Visitante v) {
        txtNombre.setText(v.getNombre());
        txtDocumento.setText(v.getDocumento());
        txtEdad.setText(String.valueOf(v.getEdad()));
        txtTelefono.setText(v.getTelefono());
        txtDireccion.setText(v.getDireccion());
        txtEstatura.setText(String.valueOf(v.getEstatura()));
        txtSaldo.setText(String.valueOf(v.getSaldoVirtual()));
    }
    private void limpiarCampos() {
        txtNombre.clear(); txtDocumento.clear(); txtEdad.clear();
        txtTelefono.clear(); txtDireccion.clear(); txtEstatura.clear(); txtSaldo.clear();
    }
    private void msg(Label lbl, String text, boolean err) {
        if (lbl == null) return;
        lbl.setText(text);
        lbl.setStyle(err ? "-fx-text-fill:#c0392b;" : "-fx-text-fill:#27ae60;");
    }
}
