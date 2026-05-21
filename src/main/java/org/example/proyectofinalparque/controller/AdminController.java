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

    // Tab operadores
    @FXML private TextField txtOpNombre;
    @FXML private TextField txtOpDocumento;
    @FXML private TextField txtOpEdad;
    @FXML private TextField txtOpIdEmp;
    @FXML private TextField txtOpTurno;
    @FXML private ComboBox<Zona> cbOpZona;
    @FXML private Label lblOpMsg;
    @FXML private TableView<Operador> tablaOperadores;
    @FXML private TableColumn<Operador, String> colOpNombre;
    @FXML private TableColumn<Operador, String> colOpDoc;
    @FXML private TableColumn<Operador, String> colOpTurno;
    @FXML private TableColumn<Operador, String> colOpZona;

    // Tab zonas
    @FXML private TextField txtZonaId;
    @FXML private TextField txtZonaNombre;
    @FXML private TextField txtZonaDesc;
    @FXML private TextField txtZonaCap;
    @FXML private Label lblZonaMsg;
    @FXML private TableView<Zona> tablaZonas;
    @FXML private TableColumn<Zona, String>  colZonaId;
    @FXML private TableColumn<Zona, String>  colZonaNombre;
    @FXML private TableColumn<Zona, Integer> colZonaCap;

    // Tab atracciones
    @FXML private TextField txtAtrId;
    @FXML private TextField txtAtrNombre;
    @FXML private ComboBox<TipoAtraccion> cbAtrTipo;
    @FXML private TextField txtAtrCap;
    @FXML private TextField txtAtrAltura;
    @FXML private TextField txtAtrEdad;
    @FXML private TextField txtAtrCosto;
    @FXML private ComboBox<Zona> cbAtrZona;
    @FXML private Label lblAtrMsg;
    @FXML private TableView<Atraccion> tablaAtracciones;
    @FXML private TableColumn<Atraccion, String>  colAtrNombre;
    @FXML private TableColumn<Atraccion, String>  colAtrTipo;
    @FXML private TableColumn<Atraccion, String>  colAtrEstado;
    @FXML private TableColumn<Atraccion, Integer> colAtrVisitantes;

    // Alertas y reportes
    @FXML private Label    lblAlertaMsg;
    @FXML private TextArea txtReporte;

    private ParqueDeAtraccion parque = Parque.get();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Operadores
        colOpNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colOpDoc.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colOpTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        colOpZona.setCellValueFactory(new PropertyValueFactory<>("idZona"));
        cbOpZona.setItems(FXCollections.observableArrayList(parque.getListZona()));

        // Zonas
        colZonaId.setCellValueFactory(new PropertyValueFactory<>("idZona"));
        colZonaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colZonaCap.setCellValueFactory(new PropertyValueFactory<>("capacidadMax"));

        // Atracciones
        colAtrNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colAtrTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colAtrEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colAtrVisitantes.setCellValueFactory(new PropertyValueFactory<>("contadorVisitantes"));
        cbAtrTipo.setItems(FXCollections.observableArrayList(TipoAtraccion.values()));
        cbAtrZona.setItems(FXCollections.observableArrayList(parque.getListZona()));

        cargarTodas();
    }

    // ----------------- Operadores -----------------

    @FXML
    private void guardarOperador() {
        try {
            Zona z = cbOpZona.getValue();
            String idZona = (z != null) ? z.getIdZona() : "";
            String nombre = txtOpNombre.getText();
            String doc    = txtOpDocumento.getText();
            int edad      = Integer.parseInt(txtOpEdad.getText());
            String idEmp  = txtOpIdEmp.getText();
            String turno  = txtOpTurno.getText();

            Operador op = new Operador(nombre, doc, edad, idEmp, turno, idZona);
            if (parque.agregarOperador(op)) {
                if (z != null) z.agregarOperador(op);
                lblOpMsg.setText("Operador agregado.");
                cargarTodas();
            } else {
                lblOpMsg.setText("Ya existe ese documento.");
            }
        } catch (NumberFormatException e) {
            lblOpMsg.setText("Edad invalida.");
        }
    }

    @FXML
    private void eliminarOperador() {
        Operador sel = tablaOperadores.getSelectionModel().getSelectedItem();
        if (sel == null) {
            lblOpMsg.setText("Seleccione un operador.");
            return;
        }
        parque.eliminarOperador(sel.getDocumento());
        lblOpMsg.setText("Operador eliminado.");
        cargarTodas();
    }

    // ----------------- Zonas -----------------

    @FXML
    private void guardarZona() {
        try {
            String id     = txtZonaId.getText();
            String nombre = txtZonaNombre.getText();
            String desc   = txtZonaDesc.getText();
            int cap       = Integer.parseInt(txtZonaCap.getText());

            Zona z = new Zona(id, nombre, desc, cap);
            if (parque.agregarZona(z)) {
                lblZonaMsg.setText("Zona agregada.");
                cargarTodas();
                cbOpZona.setItems(FXCollections.observableArrayList(parque.getListZona()));
                cbAtrZona.setItems(FXCollections.observableArrayList(parque.getListZona()));
            } else {
                lblZonaMsg.setText("Ya existe esa zona.");
            }
        } catch (NumberFormatException e) {
            lblZonaMsg.setText("Capacidad invalida.");
        }
    }

    @FXML
    private void eliminarZona() {
        Zona sel = tablaZonas.getSelectionModel().getSelectedItem();
        if (sel == null) {
            lblZonaMsg.setText("Seleccione una zona.");
            return;
        }
        parque.eliminarZona(sel.getIdZona());
        lblZonaMsg.setText("Zona eliminada.");
        cargarTodas();
    }

    // ----------------- Atracciones -----------------

    @FXML
    private void guardarAtraccion() {
        try {
            Zona z = cbAtrZona.getValue();
            TipoAtraccion tipo = cbAtrTipo.getValue();
            if (z == null || tipo == null) {
                lblAtrMsg.setText("Seleccione zona y tipo.");
                return;
            }
            String id      = txtAtrId.getText();
            String nombre  = txtAtrNombre.getText();
            int cap        = Integer.parseInt(txtAtrCap.getText());
            double altura  = Double.parseDouble(txtAtrAltura.getText());
            int edad       = Integer.parseInt(txtAtrEdad.getText());
            double costo   = Double.parseDouble(txtAtrCosto.getText());

            Atraccion a = new Atraccion(id, nombre, tipo, cap, altura, edad, costo);
            parque.agregarAtraccionAZona(z.getIdZona(), a);
            lblAtrMsg.setText("Atraccion agregada a zona " + z.getNombre());
            cargarTodas();
        } catch (NumberFormatException e) {
            lblAtrMsg.setText("Revise los datos numericos.");
        }
    }

    @FXML
    private void eliminarAtraccion() {
        Atraccion sel = tablaAtracciones.getSelectionModel().getSelectedItem();
        if (sel == null || sel.getZona() == null) {
            lblAtrMsg.setText("Seleccione una atraccion.");
            return;
        }
        parque.eliminarAtraccionDeZona(sel.getZona().getIdZona(), sel.getId());
        lblAtrMsg.setText("Atraccion eliminada.");
        cargarTodas();
    }

    // ----------------- Alertas y reportes -----------------

    @FXML
    private void activarAlertaClimatica() {
        parque.activarAlertaClimatica();
        lblAlertaMsg.setText("Alerta climatica activada.");
        cargarTodas();
    }

    @FXML
    private void desactivarAlertaClimatica() {
        parque.desactivarAlertaClimatica();
        lblAlertaMsg.setText("Alerta climatica desactivada.");
        cargarTodas();
    }

    @FXML
    private void generarReporte() {
        txtReporte.setText(parque.generarReporteDiario());
    }

    @FXML
    private void volver() {
        HelloApplication.mostrarMenuPrincipal();
    }

    private void cargarTodas() {
        tablaOperadores.setItems(FXCollections.observableArrayList(parque.getListOperador()));
        tablaZonas.setItems(FXCollections.observableArrayList(parque.getListZona()));
        tablaAtracciones.setItems(FXCollections.observableArrayList(parque.getTodasLasAtracciones()));
    }
}
