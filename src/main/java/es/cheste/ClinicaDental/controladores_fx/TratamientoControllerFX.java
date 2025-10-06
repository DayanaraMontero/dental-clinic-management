package es.cheste.ClinicaDental.controladores_fx;

import es.cheste.ClinicaDental.config.Validador;
import es.cheste.ClinicaDental.controladores_cliente.TratamientoRestClient;
import es.cheste.ClinicaDental.entidades.Dentista;
import es.cheste.ClinicaDental.entidades.Paciente;
import es.cheste.ClinicaDental.entidades.Tratamiento;
import es.cheste.ClinicaDental.enums.EstadoTratamiento;
import es.cheste.ClinicaDental.repositorios.DentistaRepository;
import es.cheste.ClinicaDental.repositorios.PacienteRepository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class TratamientoControllerFX implements Initializable {
    private static final String TITULO_AGREGAR = "Agregar Tratamiento";
    private static final String MENSAJE_AGREGAR = "¿Está seguro de que desea agregar este tratamiento?";
    private static final String TITULO_EDITAR = "Actualizar Tratamiento";
    private static final String MENSAJE_EDITAR = "¿Está seguro de que desea actualizar este tratamiento?";
    private static final String TITULO_ELIMINAR = "Eliminar Tratamiento";
    private static final String MENSAJE_ELIMINAR = "¿Está seguro de que desea eliminar este tratamiento?";

    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TratamientoRestClient tratamientoRestClient;
    private Tratamiento fila;

    @FXML private TableView<Tratamiento> tableTratamientos;

    @FXML private TableColumn<Tratamiento, Long> colTratamientoId;

    @FXML private TableColumn<Tratamiento, String> colTratamientoNombre;

    @FXML private TableColumn<Tratamiento, Paciente> colTratamientoIdPaciente;

    @FXML private TableColumn<Tratamiento, Dentista> colTratamientoIdDentista;

    @FXML private TableColumn<Tratamiento, LocalDate> colTratamientoFecha;

    @FXML private TableColumn<Tratamiento, EstadoTratamiento> colTratamientoEstado;

    @FXML private TableColumn<Tratamiento, String> colTratamientoObservaciones;

    @FXML private TextField txtTratamientoId;

    @FXML private TextField txtTratamientoNombre;

    @FXML private ComboBox<Paciente> cmbTratamientoPaciente;

    @FXML private ComboBox<Dentista> cmbTratamientoDentista;

    @FXML private DatePicker dpTratamientoFecha;

    @FXML private ComboBox<EstadoTratamiento> cmbTratamientoEstado;

    @FXML private TextArea txtTratamientoObservaciones;

    @FXML private Button btnAgregarTratamiento;

    @FXML private Button btnEditarTratamiento;

    @FXML private Button btnEliminarTratamiento;

    @FXML private Button btnLimpiarTratamiento;

    @FXML private Label lblTotal;

    public TratamientoControllerFX() { }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::configurarInterfaz);
        configurarColumnas();
        mostrarTablaTratamiento();
        configurarEventos();
    }

    private void configurarInterfaz() {
        txtTratamientoId.setDisable(true);
        cmbTratamientoEstado.setItems(FXCollections.observableArrayList(EstadoTratamiento.values()));
        cargarDatosCombo();
        limpiar();
    }

    private void cargarDatosCombo() {
        // Cargar pacientes
        List<Paciente> pacientes = pacienteRepository.findAll();
        cmbTratamientoPaciente.setItems(FXCollections.observableArrayList(pacientes));
        cmbTratamientoPaciente.setCellFactory(param -> new ListCell<Paciente>() {
            @Override
            protected void updateItem(Paciente paciente, boolean empty) {
                super.updateItem(paciente, empty);
                if (empty || paciente == null) {
                    setText(null);
                } else {
                    setText(paciente.getNombre()); // Mostrar el nombre del paciente
                }
            }
        });
        cmbTratamientoPaciente.setButtonCell(new ListCell<Paciente>() {
            @Override
            protected void updateItem(Paciente paciente, boolean empty) {
                super.updateItem(paciente, empty);
                if (empty || paciente == null) {
                    setText(null);
                } else {
                    setText(paciente.getNombre()); // Mostrar el nombre del paciente en el botón del ComboBox
                }
            }
        });

        // Cargar dentistas
        List<Dentista> dentistas = dentistaRepository.findAll();
        cmbTratamientoDentista.setItems(FXCollections.observableArrayList(dentistas));
        cmbTratamientoDentista.setCellFactory(param -> new ListCell<Dentista>() {
            @Override
            protected void updateItem(Dentista dentista, boolean empty) {
                super.updateItem(dentista, empty);
                if (empty || dentista == null) {
                    setText(null);
                } else {
                    setText(dentista.getNombre()); // Mostrar el nombre del dentista
                }
            }
        });
        cmbTratamientoDentista.setButtonCell(new ListCell<Dentista>() {
            @Override
            protected void updateItem(Dentista dentista, boolean empty) {
                super.updateItem(dentista, empty);
                if (empty || dentista == null) {
                    setText(null);
                } else {
                    setText(dentista.getNombre()); // Mostrar el nombre del dentista en el botón del ComboBox
                }
            }
        });
    }

    private void configurarColumnas() {
        colTratamientoId.setCellValueFactory(new PropertyValueFactory<>("id")); // Se usa el nombre del atributo
        colTratamientoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Configurar la columna del dentista para mostrar solo el nombre
        colTratamientoIdPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
        colTratamientoIdPaciente.setCellFactory(column -> {
            return new TableCell<Tratamiento, Paciente>() {
                @Override
                protected void updateItem(Paciente paciente, boolean empty) {
                    super.updateItem(paciente, empty);
                    if (empty || paciente == null) {
                        setText(null); // Si no hay dentista, no mostrar nada
                    } else {
                        setText(paciente.getNombre()); // Mostrar solo el nombre del dentista
                    }
                }
            };
        });

        // Configurar la columna del dentista para mostrar solo el nombre
        colTratamientoIdDentista.setCellValueFactory(new PropertyValueFactory<>("dentista"));
        colTratamientoIdDentista.setCellFactory(column -> {
            return new TableCell<Tratamiento, Dentista>() {
                @Override
                protected void updateItem(Dentista dentista, boolean empty) {
                    super.updateItem(dentista, empty);
                    if (empty || dentista == null) {
                        setText(null); // Si no hay dentista, no mostrar nada
                    } else {
                        setText(dentista.getNombre()); // Mostrar solo el nombre del dentista
                    }
                }
            };
        });

        colTratamientoFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colTratamientoEstado.setCellValueFactory(new PropertyValueFactory<>("estadoTratamiento"));
        colTratamientoObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
    }

    private void configurarEventos() {
        tableTratamientos.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                cargarDatos(newValue);
            }
        });
    }

    private void cargarDatos(Tratamiento tratamiento) {
        this.fila = tratamiento;
        txtTratamientoId.setText(String.valueOf(fila.getId()));
        txtTratamientoNombre.setText(fila.getNombre());
        cmbTratamientoPaciente.setValue(fila.getPaciente());
        cmbTratamientoDentista.setValue(fila.getDentista());
        dpTratamientoFecha.setValue(fila.getFecha());
        cmbTratamientoEstado.setValue(fila.getEstadoTratamiento());
        txtTratamientoObservaciones.setText(fila.getObservaciones());

        btnAgregarTratamiento.setVisible(false);
        btnLimpiarTratamiento.setVisible(true);
        btnEditarTratamiento.setVisible(true);
        btnEliminarTratamiento.setVisible(true);
    }

    public void mostrarTablaTratamiento() {
        tableTratamientos.setItems(FXCollections.observableArrayList(tratamientoRestClient.listarTratamientos()));
        // Mostrar todos los registros
        lblTotal.setText(String.valueOf(tableTratamientos.getItems().size()));
    }

    @FXML
    public void agregarTratamiento() {
        if (validarCampos()) {
            mostrarConfirmacion(TITULO_AGREGAR, MENSAJE_AGREGAR, () -> {
                Tratamiento tratamiento = crearTratamiento();
                tratamientoRestClient.agregarTratamiento(tratamiento);
                actualizarVista();
                mostrarMensajeExito("Éxito", "Tratamiento agregado correctamente.");
            });
        }
    }

    @FXML
    public void editarTratamiento() {
        if (fila == null) {
            mostrarMensajeError("Error", "No se ha seleccionado un tratamiento para editar.");
            return;
        }

        if (validarCampos()) {
            mostrarConfirmacion(TITULO_EDITAR, MENSAJE_EDITAR, () -> {
                actualizarTratamientoFormulario(fila);
                tratamientoRestClient.actualizarTratamiento(fila);
                actualizarVista();
                mostrarMensajeExito("Éxito", "Tratamiento editado correctamente.");
            });
        }
    }

    @FXML
    public void eliminarTratamiento() {
        if (fila != null) {
            mostrarConfirmacion(TITULO_ELIMINAR, MENSAJE_ELIMINAR, () -> {
                tratamientoRestClient.eliminarTratamiento(fila.getId());
                actualizarVista();
                mostrarMensajeExito("Éxito", "Tratamiento eliminado correctamente.");
            });
        }
    }

    @FXML
    public void limpiar() {
        txtTratamientoId.clear();
        txtTratamientoNombre.clear();
        cmbTratamientoPaciente.setValue(null);
        cmbTratamientoDentista.setValue(null);
        dpTratamientoFecha.setValue(null);
        cmbTratamientoEstado.setValue(null);
        txtTratamientoObservaciones.clear();

        btnAgregarTratamiento.setVisible(true);
        btnLimpiarTratamiento.setVisible(false);
        btnEditarTratamiento.setVisible(false);
        btnEliminarTratamiento.setVisible(false);
    }

    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        if (txtTratamientoNombre.getText().trim().isEmpty()) {
            errores.append("- El nombre es obligatorio.\n");
        } else if (!Validador.validarTexto(txtTratamientoNombre.getText().trim())) {
            errores.append("- El nombre debe contener al menos 3 letras y solo caracteres válidos.\n");
        }

        if (cmbTratamientoPaciente.getValue() == null) {
            errores.append("- Debe seleccionar un paciente.\n");
        }

        if (cmbTratamientoDentista.getValue() == null) {
            errores.append("- Debe seleccionar un dentista.\n");
        }

        if (dpTratamientoFecha.getValue() == null) {
            errores.append("- La fecha del tratamiento es obligatoria.\n");
        }

        if (cmbTratamientoEstado.getValue() == null) {
            errores.append("- El estado del tratamiento es obligatorio.\n");
        }

        String observacionesText = txtTratamientoObservaciones.getText();
        if (observacionesText != null && !observacionesText.trim().isEmpty()) {
            if (!Validador.validarTexto(observacionesText.trim())) {
                errores.append("- Las observaciones deben contener al menos 3 letras y solo caracteres válidos.\n");
            }
        }

        if (!errores.isEmpty()) {
            mostrarMensajeError("Error de validación", errores.toString());
            return false;
        }
        return true;
    }

    private Tratamiento crearTratamiento() {
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setNombre(txtTratamientoNombre.getText());
        tratamiento.setPaciente(cmbTratamientoPaciente.getValue());
        tratamiento.setDentista(cmbTratamientoDentista.getValue());
        tratamiento.setFecha(dpTratamientoFecha.getValue());
        tratamiento.setEstadoTratamiento(cmbTratamientoEstado.getValue());
        tratamiento.setObservaciones(txtTratamientoObservaciones.getText().trim());

        return tratamiento;
    }

    private void actualizarTratamientoFormulario(Tratamiento tratamiento) {
        tratamiento.setNombre(txtTratamientoNombre.getText());
        tratamiento.setPaciente(cmbTratamientoPaciente.getValue()); // Obtener el paciente seleccionado
        tratamiento.setDentista(cmbTratamientoDentista.getValue()); // Obtener el dentista seleccionado
        tratamiento.setFecha(dpTratamientoFecha.getValue());
        tratamiento.setEstadoTratamiento(cmbTratamientoEstado.getValue());
        tratamiento.setObservaciones(txtTratamientoObservaciones.getText().trim());
    }

    private void actualizarVista() {
        mostrarTablaTratamiento();
        tableTratamientos.refresh();
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
