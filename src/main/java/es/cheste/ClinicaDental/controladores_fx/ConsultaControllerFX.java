package es.cheste.ClinicaDental.controladores_fx;

import es.cheste.ClinicaDental.config.Validador;
import es.cheste.ClinicaDental.controladores_cliente.ConsultaRestClient;
import es.cheste.ClinicaDental.entidades.Consulta;
import es.cheste.ClinicaDental.enums.EstadoConsulta;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class ConsultaControllerFX implements Initializable {
    private static final String TITULO_AGREGAR = "Agregar Sala";
    private static final String MENSAJE_AGREGAR = "¿Está seguro de que desea agregar esta sala?";
    private static final String TITULO_EDITAR = "Actualizar Sala";
    private static final String MENSAJE_EDITAR = "¿Está seguro de que desea actualizar esta sala?";
    private static final String TITULO_ELIMINAR = "Eliminar Sala";
    private static final String MENSAJE_ELIMINAR = "¿Está seguro de que desea eliminar esta sala?";

    @Autowired
    private ConsultaRestClient salaRestClient;
    private Consulta fila;

    @FXML private TableView<Consulta> tableSalas;

    @FXML private TableColumn<Consulta, Long> colSalasId;

    @FXML private TableColumn<Consulta, Integer> colSalasNumero;

    @FXML private TableColumn<Consulta, String> colSalasNombre;

    @FXML private TableColumn<Consulta, EstadoConsulta> colSalasEstado;

    @FXML private TableColumn<Consulta, String> colSalasUbicacion;

    @FXML private TextField txtSalaId;

    @FXML private TextField txtSalaNum;

    @FXML private TextField txtSalaNombre;

    @FXML private ComboBox<EstadoConsulta> cmbSalaEstado;

    @FXML private TextArea txtSalaUbicacion;

    @FXML private Button btnAgregarSala;

    @FXML private Button btnEditarSala;

    @FXML private Button btnEliminarSala;

    @FXML private Button btnLimpiarSala;

    @FXML private Label lblTotal;

    public ConsultaControllerFX() { }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::configurarInterfaz);
        configurarColumnas();
        mostrarTablaSalas();
        configurarEventos();
    }

    private void configurarInterfaz() {
        txtSalaId.setDisable(true);
        cmbSalaEstado.setItems(FXCollections.observableArrayList(EstadoConsulta.values()));
        limpiar();
    }

    private void configurarColumnas() {
        colSalasId.setCellValueFactory(new PropertyValueFactory<>("id")); // Se usa el nombre del atributo
        colSalasNumero.setCellValueFactory(new PropertyValueFactory<>("numSala"));
        colSalasNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSalasEstado.setCellValueFactory(new PropertyValueFactory<>("estadoSala"));
        colSalasUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
    }

    private void configurarEventos() {
        tableSalas.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                cargarDatos(newValue);
            }
        });
    }

    private void cargarDatos(Consulta sala) {
        this.fila = sala;
        txtSalaId.setText(String.valueOf(fila.getId()));
        txtSalaNum.setText(String.valueOf(fila.getNumSala()));
        txtSalaNombre.setText(fila.getNombre());
        cmbSalaEstado.setValue(fila.getEstadoSala());
        txtSalaUbicacion.setText(fila.getUbicacion());

        btnAgregarSala.setVisible(false);
        btnLimpiarSala.setVisible(true);
        btnEditarSala.setVisible(true);
        btnEliminarSala.setVisible(true);
    }

    public void mostrarTablaSalas() {
        tableSalas.setItems(FXCollections.observableArrayList(salaRestClient.listarSalas()));
        // Mostrar todos los registros
        lblTotal.setText(String.valueOf(tableSalas.getItems().size()));
    }

    @FXML
    public void agregarSala() {
        if (validarCampos()) {
            mostrarConfirmacion(TITULO_AGREGAR, MENSAJE_AGREGAR, () -> {
                Consulta sala = crearSala();
                salaRestClient.agregarSala(sala);
                actualizarVista();
                mostrarMensajeExito("Éxito", "Sala agregada correctamente.");
            });
        }
    }

    @FXML
    public void editarSala() {
        if (fila == null) {
            mostrarMensajeError("Error", "No se ha seleccionado una sala para editar.");
            return;
        }

        if (validarCampos()) {
            mostrarConfirmacion(TITULO_EDITAR, MENSAJE_EDITAR, () -> {
                actualizarSalaFormulario(fila);
                salaRestClient.actualizarSala(fila);
                actualizarVista();
                mostrarMensajeExito("Éxito", "Sala editada correctamente.");
            });
        }
    }

    @FXML
    public void eliminarSala() {
        if (fila != null) {
            mostrarConfirmacion(TITULO_ELIMINAR, MENSAJE_ELIMINAR, () -> {
                salaRestClient.eliminarSala(fila.getId());
                actualizarVista();
                mostrarMensajeExito("Éxito", "Sala eliminada correctamente.");
            });
        }
    }

    @FXML
    public void limpiar() {
        // Mejorar con un bucle for each
        for (TextField field : new TextField[]{
                txtSalaId, txtSalaNum, txtSalaNombre
        }) {
            field.clear();
        }
        cmbSalaEstado.setValue(null);

        txtSalaUbicacion.clear();

        btnAgregarSala.setVisible(true);
        btnLimpiarSala.setVisible(false);
        btnEditarSala.setVisible(false);
        btnEliminarSala.setVisible(false);
    }

    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        // Verificar que los campos no sean null
        if (txtSalaNum == null || txtSalaNombre == null || cmbSalaEstado == null ||
                txtSalaUbicacion == null) {
            errores.append("- Error: Algunos campos del formulario no están inicializados correctamente.\n");
            mostrarMensajeError("Error de validación", errores.toString());
            return false;
        }

        if (txtSalaNum.getText().trim().isEmpty()) {
            errores.append("- El número de sala es obligatorio.\n");
        } else {
            try {
                Integer.parseInt(txtSalaNum.getText().trim());
            } catch (NumberFormatException e) {
                errores.append("- El número de sala debe ser un número válido");
            }
        }

        // Validar nombre
        if (txtSalaNombre.getText().trim().isEmpty()) {
            errores.append("- El nombre es obligatorio.\n");
        } else if (!Validador.validarTexto(txtSalaNombre.getText().trim())) {
            errores.append("- El nombre debe contener al menos 3 letras y solo caracteres válidos.\n");
        }

        // Validar especialidad
        if (cmbSalaEstado.getValue() == null) {
            errores.append("- El estado de la sala es obligatoria.\n");
        }

        // Validar email (opcional)
        String ubicacionText = txtSalaUbicacion.getText(); // Obtener el texto
        if (ubicacionText != null && !ubicacionText.trim().isEmpty()) {
            // Solo validar el email si no está vacío
            if (!Validador.validarTexto(ubicacionText.trim())) {
                errores.append("- La ubicación debe contener al menos 3 letras y solo caracteres válidos.\n");
            }
        }

        // Mostrar errores si los hay
        if (!errores.isEmpty()) {
            mostrarMensajeError("Error de validación", errores.toString());
            return false;
        }
        return true;
    }

    private Consulta crearSala() {
        Consulta sala = new Consulta();
        sala.setNumSala(Integer.parseInt(txtSalaNum.getText()));
        sala.setNombre(txtSalaNombre.getText());
        sala.setEstadoSala(cmbSalaEstado.getValue());

        // Manejar el caso en que el correo electrónico sea null o esté vacío
        String ubicacion = txtSalaUbicacion.getText(); // Obtener el texto
        sala.setUbicacion(ubicacion == null || ubicacion.trim().isEmpty() ? null : ubicacion.trim());

        return sala;
    }

    private void actualizarSalaFormulario(Consulta sala) {
        // Obtener el texto de cada campo, manejando el caso en que sea null
        String numText = txtSalaNum.getText();
        String nombreText = txtSalaNombre.getText();
        String ubicacionText = txtSalaUbicacion.getText();

        // Actualizar el objeto Dentista
        sala.setNumSala(numText == null || numText.trim().isEmpty() ? 0 : Integer.parseInt(numText.trim()));
        sala.setNombre(nombreText == null || nombreText.trim().isEmpty() ? "" : nombreText.trim());
        sala.setEstadoSala(cmbSalaEstado.getValue());
        sala.setUbicacion(ubicacionText == null || ubicacionText.trim().isEmpty() ? null : ubicacionText.trim());
    }

    private void actualizarVista() {
        mostrarTablaSalas();
        tableSalas.refresh();
        limpiar();
    }

    private void mostrarConfirmacion(String titulo, String mensaje, Runnable accion) {
        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.setTitle(titulo);
        confirmar.setHeaderText(null);
        confirmar.setContentText(mensaje);

        Optional<ButtonType> resultado = confirmar.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            accion.run();
        }
    }

    private void mostrarMensajeExito(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarMensajeError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
