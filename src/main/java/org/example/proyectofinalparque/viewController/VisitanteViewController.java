package org.example.proyectofinalparque.viewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.proyectofinalparque.App;
import org.example.proyectofinalparque.controller.VisitanteController;
import org.example.proyectofinalparque.model.clases.Visitante;
import org.example.proyectofinalparque.model.enums.TipoTicket;

public class VisitanteViewController {

    private App app;
    private VisitanteController visitanteController;
    private ObservableList<Visitante> listVisitantes = FXCollections.observableArrayList();
    private Visitante visitanteSeleccionado;

    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtEdad;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtEstatura;
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
    @FXML private Label                lblNumIntegrantes;
    @FXML private TextField            txtNumIntegrantes;
    @FXML private Label                lblDescuentoInfo;
    @FXML private Label                lblTicketMsg;

    public void setApp(App app) {
        this.app = app;
        this.visitanteController = new VisitanteController(app.parque);
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerVisitantes();
        tablaVisitantes.setItems(listVisitantes);
        listenerSeleccion();
        cbTipoTicket.setItems(FXCollections.observableArrayList(TipoTicket.values()));
    }

    private void initDataBinding() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colEstatura.setCellValueFactory(new PropertyValueFactory<>("estatura"));
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldoVirtual"));
    }

    private void obtenerVisitantes() {
        listVisitantes.clear();
        listVisitantes.addAll(visitanteController.obtenerListaVisitantes());
    }

    private void listenerSeleccion() {
        tablaVisitantes.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    visitanteSeleccionado = newSel;
                    mostrarInformacion(newSel);
                });
    }

    private void mostrarInformacion(Visitante v) {
        if (v != null) {
            txtNombre.setText(v.getNombre());
            txtDocumento.setText(v.getDocumento());
            txtEdad.setText(String.valueOf(v.getEdad()));
            txtTelefono.setText(v.getTelefono());
            txtDireccion.setText(v.getDireccion());
            txtEstatura.setText(String.valueOf(v.getEstatura()));
        }
    }

    @FXML
    private void onGuardar() {
        try {
            String nombre    = txtNombre.getText();
            String documento = txtDocumento.getText();
            int    edad      = Integer.parseInt(txtEdad.getText());
            double estatura  = Double.parseDouble(txtEstatura.getText());
            String telefono  = txtTelefono.getText();
            String direccion = txtDireccion.getText();

            // El visitante se registra con saldo 0. Lo recarga el operador.
            Visitante v = new Visitante(nombre, documento, edad, estatura, 0.0, telefono, direccion);
            if (visitanteController.crearVisitante(v)) {
                listVisitantes.add(v);
                lblMensaje.setText("Visitante registrado. Saldo inicial = 0.");
                limpiarCampos();
            } else {
                lblMensaje.setText("Documento ya existe o parque lleno.");
            }
        } catch (NumberFormatException e) {
            lblMensaje.setText("Edad y estatura deben ser numericos.");
        }
    }

    @FXML
    private void onActualizar() {
        if (visitanteSeleccionado == null) {
            lblMensaje.setText("Seleccione un visitante.");
            return;
        }
        try {
            String nombre    = txtNombre.getText();
            int    edad      = Integer.parseInt(txtEdad.getText());
            double estatura  = Double.parseDouble(txtEstatura.getText());
            String telefono  = txtTelefono.getText();
            String direccion = txtDireccion.getText();

            visitanteController.actualizarVisitante(
                    visitanteSeleccionado.getDocumento(), nombre, edad, estatura, telefono, direccion);
            lblMensaje.setText("Visitante actualizado.");
            recargarTabla();
        } catch (NumberFormatException e) {
            lblMensaje.setText("Datos numericos invalidos.");
        }
    }

    @FXML
    private void onEliminar() {
        if (visitanteSeleccionado == null) {
            lblMensaje.setText("Seleccione un visitante.");
            return;
        }
        visitanteController.eliminarVisitante(visitanteSeleccionado.getDocumento());
        listVisitantes.remove(visitanteSeleccionado);
        lblMensaje.setText("Visitante eliminado.");
        limpiarCampos();
    }

    @FXML
    private void onCambiarTipoTicket() {
        TipoTicket tipo = cbTipoTicket.getValue();
        boolean esFamiliar = (tipo == TipoTicket.FAMILIAR);

        // Mostrar u ocultar el campo de integrantes segun el tipo
        lblNumIntegrantes.setVisible(esFamiliar);
        lblNumIntegrantes.setManaged(esFamiliar);
        txtNumIntegrantes.setVisible(esFamiliar);
        txtNumIntegrantes.setManaged(esFamiliar);

        // Actualizar la info del descuento si ya hay precio ingresado
        actualizarInfoDescuento();
    }

    // Muestra el descuento calculado cuando el tipo es FAMILIAR
    private void actualizarInfoDescuento() {
        TipoTicket tipo = cbTipoTicket.getValue();
        if (tipo != TipoTicket.FAMILIAR) {
            lblDescuentoInfo.setText("");
            return;
        }
        try {
            double precio = Double.parseDouble(txtPrecioTicket.getText());
            int integrantes = Integer.parseInt(txtNumIntegrantes.getText());
            double porcentaje;
            if (integrantes <= 2) {
                porcentaje = 5;
            } else if (integrantes == 3) {
                porcentaje = 10;
            } else {
                porcentaje = 15;
            }
            double descuento = precio * (porcentaje / 100);
            double precioFinal = precio - descuento;
            lblDescuentoInfo.setText("Descuento: " + (int) porcentaje + "% ($"
                    + String.format("%.0f", descuento) + ") → Precio final: $"
                    + String.format("%.0f", precioFinal));
        } catch (NumberFormatException e) {
            lblDescuentoInfo.setText("");
        }
    }

    @FXML
    private void onComprarTicket() {
        if (visitanteSeleccionado == null || cbTipoTicket.getValue() == null) {
            lblTicketMsg.setText("Seleccione visitante y tipo de ticket.");
            return;
        }
        try {
            double precio = Double.parseDouble(txtPrecioTicket.getText());
            TipoTicket tipo = cbTipoTicket.getValue();

            int numIntegrantes = 1;
            if (tipo == TipoTicket.FAMILIAR) {
                String textoInt = txtNumIntegrantes.getText().trim();
                if (textoInt.isEmpty()) {
                    lblTicketMsg.setText("Ingrese el numero de integrantes.");
                    return;
                }
                numIntegrantes = Integer.parseInt(textoInt);
                if (numIntegrantes < 2) {
                    lblTicketMsg.setText("El ticket familiar requiere al menos 2 integrantes.");
                    return;
                }
            }

            String res = visitanteController.comprarTicket(
                    visitanteSeleccionado.getDocumento(), tipo, precio, numIntegrantes);
            lblTicketMsg.setText(res);
            lblDescuentoInfo.setText("");
            // Recargamos la tabla para que el saldo se vea actualizado
            recargarTabla();
        } catch (NumberFormatException e) {
            lblTicketMsg.setText("Valores numericos invalidos.");
        }
    }

    @FXML
    private void onVolver() {
        app.mostrarMenuPrincipal();
    }

    // refresca la tabla con los datos actualizados del modelo
    private void recargarTabla() {
        obtenerVisitantes();
        tablaVisitantes.refresh();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDocumento.clear();
        txtEdad.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtEstatura.clear();
        tablaVisitantes.getSelectionModel().clearSelection();
    }
}
