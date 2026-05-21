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

    @FXML private TableView<Visitante>            tablaVisitantes;
    @FXML private TableColumn<Visitante, String>  colNombre;
    @FXML private TableColumn<Visitante, String>  colDocumento;
    @FXML private TableColumn<Visitante, Integer> colEdad;
    @FXML private TableColumn<Visitante, String>  colTelefono;
    @FXML private TableColumn<Visitante, String>  colDireccion;
    @FXML private TableColumn<Visitante, Double>  colEstatura;
    @FXML private TableColumn<Visitante, Double>  colSaldo;

    @FXML private ComboBox<TipoTicket> cbTipoTicket;
    @FXML private TextField            txtPrecioTicket;
    @FXML private Label                lblTicketMsg;

    private ParqueDeAtraccion parque = Parque.get();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colEstatura.setCellValueFactory(new PropertyValueFactory<>("estatura"));
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldoVirtual"));

        cbTipoTicket.setItems(FXCollections.observableArrayList(TipoTicket.values()));

        cargarTabla();
    }

    @FXML
    private void guardar() {
        try {
            String nombre    = txtNombre.getText();
            String documento = txtDocumento.getText();
            int    edad      = Integer.parseInt(txtEdad.getText());
            double estatura  = Double.parseDouble(txtEstatura.getText());
            double saldo     = Double.parseDouble(txtSaldo.getText());
            String telefono  = txtTelefono.getText();
            String direccion = txtDireccion.getText();

            Visitante v = new Visitante(nombre, documento, edad, estatura, saldo, telefono, direccion);
            if (parque.agregarVisitante(v)) {
                lblMensaje.setText("Visitante registrado.");
                cargarTabla();
                limpiarCampos();
            } else {
                lblMensaje.setText("Documento ya existe o parque lleno.");
            }
        } catch (NumberFormatException e) {
            lblMensaje.setText("Edad, estatura y saldo deben ser numericos.");
        }
    }

    @FXML
    private void actualizar() {
        Visitante sel = tablaVisitantes.getSelectionModel().getSelectedItem();
        if (sel == null) {
            lblMensaje.setText("Seleccione un visitante.");
            return;
        }
        try {
            String nombre    = txtNombre.getText();
            int    edad      = Integer.parseInt(txtEdad.getText());
            double estatura  = Double.parseDouble(txtEstatura.getText());
            String telefono  = txtTelefono.getText();
            String direccion = txtDireccion.getText();

            parque.actualizarVisitante(sel.getDocumento(), nombre, edad, estatura, telefono, direccion);
            lblMensaje.setText("Visitante actualizado.");
            cargarTabla();
        } catch (NumberFormatException e) {
            lblMensaje.setText("Datos numericos invalidos.");
        }
    }

    @FXML
    private void eliminar() {
        Visitante sel = tablaVisitantes.getSelectionModel().getSelectedItem();
        if (sel == null) {
            lblMensaje.setText("Seleccione un visitante.");
            return;
        }
        parque.eliminarVisitante(sel.getDocumento());
        lblMensaje.setText("Visitante eliminado.");
        cargarTabla();
        limpiarCampos();
    }

    @FXML
    private void comprarTicket() {
        Visitante sel = tablaVisitantes.getSelectionModel().getSelectedItem();
        if (sel == null || cbTipoTicket.getValue() == null) {
            lblTicketMsg.setText("Seleccione visitante y tipo de ticket.");
            return;
        }
        try {
            double precio = Double.parseDouble(txtPrecioTicket.getText());
            String res = parque.venderTicket(sel.getDocumento(), cbTipoTicket.getValue(), precio, 4);
            lblTicketMsg.setText(res);
            cargarTabla();
        } catch (NumberFormatException e) {
            lblTicketMsg.setText("Precio invalido.");
        }
    }

    @FXML
    private void volver() {
        HelloApplication.mostrarMenuPrincipal();
    }

    private void cargarTabla() {
        tablaVisitantes.setItems(FXCollections.observableArrayList(parque.getListVisitante()));
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDocumento.clear();
        txtEdad.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtEstatura.clear();
        txtSaldo.clear();
    }
}
