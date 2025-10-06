package es.cheste.ClinicaDental.controladores_fx;

import es.cheste.ClinicaDental.config.Validador;
import es.cheste.ClinicaDental.controladores_cliente.CitaRestClient;
import es.cheste.ClinicaDental.entidades.*;
import es.cheste.ClinicaDental.enums.EstadoCita;
import es.cheste.ClinicaDental.repositorios.DentistaRepository;
import es.cheste.ClinicaDental.repositorios.PacienteRepository;
import es.cheste.ClinicaDental.repositorios.ConsultaRepository;
import es.cheste.ClinicaDental.repositorios.TratamientoRepository;
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
public class CitaControllerFX implements Initializable {
    private static final String TITULO_AGREGAR = "Agregar Cita";
    private static final String MENSAJE_AGREGAR = "¿Está seguro de que desea agregar esta cita?";

    private static final String TITULO_EDITAR = "Actualizar Cita";
    private static final String MENSAJE_EDITAR = "¿Está seguro de que desea actualizar esta cita?";

    private static final String TITULO_ELIMINAR = "Eliminar Cita";
    private static final String MENSAJE_ELIMINAR = "¿Está seguro de que desea eliminar esta cita?";

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private ConsultaRepository salaRepository;

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private CitaRestClient citaRestClient;
    private Cita fila;

    @FXML private TableView<Cita> tableCitas;

    @FXML private TableColumn<Cita, Long> colCitaId;

    @FXML private TableColumn<Cita, LocalDate> colCitaFecha;

    @FXML private TableColumn<Cita, Paciente> colCitaIdPaciente;

    @FXML private TableColumn<Cita, Dentista> colCitaIdDentista;

    @FXML private TableColumn<Cita, Consulta> colCitaIdSala;

    @FXML private TableColumn<Cita, Tratamiento> colCitaIdTratamiento;

    @FXML private TableColumn<Cita, EstadoCita> colCitaEstado;

    @FXML private TableColumn<Cita, String> colCitaObservaciones;

    @FXML private TextField txtCitaId;

    @FXML private DatePicker dpCitaFecha;

    @FXML private ComboBox<Paciente> cmbCitaPaciente;

    @FXML private ComboBox<Dentista> cmbCitaDentista;

    @FXML private ComboBox<Consulta> cmbCitaConsulta;

    @FXML private ComboBox<Tratamiento> cmbCitaTratamiento;

    @FXML private ComboBox<EstadoCita> cmbCitaEstado;

    @FXML private TextArea txtCitaObservaciones;

    @FXML private Button btnAgregarCita;

    @FXML private Button btnEditarCita;

    @FXML private Button btnEliminarCita;

    @FXML private Button btnLimpiarCita;

    @FXML private Label lblTotal;

    public CitaControllerFX() { }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::configurarInterfaz);
        configurarColumnas();
        mostrarTablaCitas();
        configurarEventos();
    }

    private void configurarInterfaz() {
        txtCitaId.setDisable(true);
        cmbCitaEstado.setItems(FXCollections.observableArrayList(EstadoCita.values()));
        cargarDatosCombo();
        limpiar();
    }

    private void cargarDatosCombo() {
        // Cargar pacientes
        List<Paciente> pacientes = pacienteRepository.findAll();
        cmbCitaPaciente.setItems(FXCollections.observableArrayList(pacientes));
        cmbCitaPaciente.setCellFactory(param -> new ListCell<Paciente>() {
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
        cmbCitaPaciente.setButtonCell(new ListCell<Paciente>() {
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
        cmbCitaDentista.setItems(FXCollections.observableArrayList(dentistas));
        cmbCitaDentista.setCellFactory(param -> new ListCell<Dentista>() {
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
        cmbCitaDentista.setButtonCell(new ListCell<Dentista>() {
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

        // Cargar salas
        List<Consulta> salas = salaRepository.findAll();
        cmbCitaConsulta.setItems(FXCollections.observableArrayList(salas));
        cmbCitaConsulta.setCellFactory(param -> new ListCell<Consulta>() {
            @Override
            protected void updateItem(Consulta sala, boolean empty) {
                super.updateItem(sala, empty);
                if (empty || sala == null) {
                    setText(null);
                } else {
                    setText(sala.getNombre()); // Mostrar el nombre de la sala
                }
            }
        });

        cmbCitaConsulta.setButtonCell(new ListCell<Consulta>() {
            @Override
            protected void updateItem(Consulta sala, boolean empty) {
                super.updateItem(sala, empty);
                if (empty || sala == null) {
                    setText(null);
                } else {
                    setText(sala.getNombre()); // Mostrar el nombre de la sala en el botón del ComboBox
                }
            }
        });

        // Cargar tratamientos
        List<Tratamiento> tratamientos = tratamientoRepository.findAll();
        cmbCitaTratamiento.setItems(FXCollections.observableArrayList(tratamientos));
        cmbCitaTratamiento.setCellFactory(param -> new ListCell<Tratamiento>() {
            @Override
            protected void updateItem(Tratamiento tratamiento, boolean empty) {
                super.updateItem(tratamiento, empty);
                if (empty || tratamiento == null) {
                    setText(null);
                } else {
                    setText(tratamiento.getNombre()); // Mostrar el nombre del tratamiento
                }
            }
        });
        cmbCitaTratamiento.setButtonCell(new ListCell<Tratamiento>() {
            @Override
            protected void updateItem(Tratamiento tratamiento, boolean empty) {
                super.updateItem(tratamiento, empty);
                if (empty || tratamiento == null) {
                    setText(null);
                } else {
                    setText(tratamiento.getNombre()); // Mostrar el nombre del tratamiento en el botón del ComboBox
                }
            }
        });
    }

    private void configurarColumnas() {
        colCitaId.setCellValueFactory(new PropertyValueFactory<>("id")); // Se usa el nombre del atributo
        colCitaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        colCitaIdPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
        colCitaIdPaciente.setCellFactory(column -> {
            return new TableCell<Cita, Paciente>() {
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

        colCitaIdDentista.setCellValueFactory(new PropertyValueFactory<>("dentista"));
        colCitaIdDentista.setCellFactory(column -> {
            return new TableCell<Cita, Dentista>() {
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

        colCitaIdSala.setCellValueFactory(new PropertyValueFactory<>("sala"));
        colCitaIdSala.setCellFactory(column -> {
            return new TableCell<Cita, Consulta>() {
                @Override
                protected void updateItem(Consulta consulta, boolean empty) {
                    super.updateItem(consulta, empty);
                    if (empty || consulta == null) {
                        setText(null); // Si no hay dentista, no mostrar nada
                    } else {
                        setText(consulta.getNombre()); // Mostrar solo el nombre del dentista
                    }
                }
            };
        });

        colCitaIdTratamiento.setCellValueFactory(new PropertyValueFactory<>("tratamiento"));
        colCitaIdTratamiento.setCellFactory(column -> {
            return new TableCell<Cita, Tratamiento>() {
                @Override
                protected void updateItem(Tratamiento tratamiento, boolean empty) {
                    super.updateItem(tratamiento, empty);
                    if (empty || tratamiento == null) {
                        setText(null); // Si no hay dentista, no mostrar nada
                    } else {
                        setText(tratamiento.getNombre()); // Mostrar solo el nombre del dentista
                    }
                }
            };
        });

        colCitaEstado.setCellValueFactory(new PropertyValueFactory<>("estadoCita"));
        colCitaObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
    }

    private void configurarEventos() {
        tableCitas.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                cargarDatos(newValue);
            }
        });
    }

    private void cargarDatos(Cita cita) {
        this.fila = cita;
        txtCitaId.setText(String.valueOf(fila.getId()));
        dpCitaFecha.setValue(fila.getFecha());
        cmbCitaPaciente.setValue(fila.getPaciente());
        cmbCitaDentista.setValue(fila.getDentista());
        cmbCitaConsulta.setValue(fila.getSala());
        cmbCitaTratamiento.setValue(fila.getTratamiento());
        cmbCitaEstado.setValue(fila.getEstadoCita());
        txtCitaObservaciones.setText(fila.getObservaciones());

        btnAgregarCita.setVisible(false);
        btnLimpiarCita.setVisible(true);
        btnEditarCita.setVisible(true);
        btnEliminarCita.setVisible(true);
    }

    public void mostrarTablaCitas() {
        tableCitas.setItems(FXCollections.observableArrayList(citaRestClient.listarCitas()));
        // Mostrar todos los registros
        lblTotal.setText(String.valueOf(tableCitas.getItems().size()));
    }

    @FXML
    public void agregarCita() {
        if (validarCampos()) {
            mostrarConfirmacion(TITULO_AGREGAR, MENSAJE_AGREGAR, () -> {
                Cita cita = crearCita();
                citaRestClient.agregarCita(cita);
                actualizarVista();
                mostrarMensajeExito("Éxito", "Cita agregada correctamente.");
            });
        }
    }

    @FXML
    public void editarCita() {
        if (fila == null) {
            mostrarMensajeError("Error", "No se ha seleccionado una cita para editar.");
            return;
        }

        if (validarCampos()) {
            mostrarConfirmacion(TITULO_EDITAR, MENSAJE_EDITAR, () -> {
                actualizarCitaFormulario(fila);
                citaRestClient.actualizarCita(fila);
                actualizarVista();
                mostrarMensajeExito("Éxito", "Cita editada correctamente.");
            });
        }
    }

    @FXML
    public void eliminarCita() {
        if (fila != null) {
            mostrarConfirmacion(TITULO_ELIMINAR, MENSAJE_ELIMINAR, () -> {
                citaRestClient.eliminarCita(fila.getId());
                actualizarVista();
                mostrarMensajeExito("Éxito", "Cita eliminada correctamente.");
            });
        }
    }

    @FXML
    public void limpiar() {
        txtCitaId.clear();
        dpCitaFecha.setValue(null);
        cmbCitaPaciente.setValue(null);
        cmbCitaDentista.setValue(null);
        cmbCitaConsulta.setValue(null);
        cmbCitaTratamiento.setValue(null);
        cmbCitaEstado.setValue(null);
        txtCitaObservaciones.clear();

        btnAgregarCita.setVisible(true);
        btnLimpiarCita.setVisible(false);
        btnEditarCita.setVisible(false);
        btnEliminarCita.setVisible(false);
    }

    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        if (dpCitaFecha.getValue() == null) {
            errores.append("- La fecha de cita es obligatoria.\n");
        }

        if (cmbCitaPaciente.getValue() == null) {
            errores.append("- Debe seleccionar un paciente.\n");
        }

        if (cmbCitaDentista.getValue() == null) {
            errores.append("- Debe seleccionar un dentista.\n");
        }

        if (cmbCitaConsulta.getValue() == null) {
            errores.append("- Debe seleccionar una sala.\n");
        }

        if (cmbCitaTratamiento.getValue() == null) {
            errores.append("- Debe seleccionar un tratamiento.\n");
        }

        if (cmbCitaEstado.getValue() == null) {
            errores.append("- El estado es obligatorio.\n");
        }

        String observacionText = txtCitaObservaciones.getText();
        if (observacionText != null && !observacionText.trim().isEmpty()) {
            if (!Validador.validarTexto(observacionText.trim())) {
                errores.append("- Las observaciones deben contener al menos 3 letras y solo caracteres válidos.\n");
            }
        }

        if (!errores.isEmpty()) {
            mostrarMensajeError("Error de validación", errores.toString());
            return false;
        }
        return true;
    }

    private Cita crearCita() {
        Cita cita = new Cita();

        cita.setFecha(dpCitaFecha.getValue());

        cita.setPaciente(cmbCitaPaciente.getValue());
        cita.setDentista(cmbCitaDentista.getValue());
        cita.setSala(cmbCitaConsulta.getValue());
        cita.setTratamiento(cmbCitaTratamiento.getValue());

        cita.setEstadoCita(cmbCitaEstado.getValue());

        String observaciones = txtCitaObservaciones.getText(); // Obtener el texto
        cita.setObservaciones(observaciones == null || observaciones.trim().isEmpty() ? null : observaciones.trim());

        return cita;
    }

    private void actualizarCitaFormulario(Cita cita) {
        cita.setFecha(dpCitaFecha.getValue());
        cita.setPaciente(cmbCitaPaciente.getValue());
        cita.setDentista(cmbCitaDentista.getValue());
        cita.setSala(cmbCitaConsulta.getValue());
        cita.setTratamiento(cmbCitaTratamiento.getValue());
        cita.setEstadoCita(cmbCitaEstado.getValue());
        cita.setObservaciones(txtCitaObservaciones.getText().trim());
    }

    private void actualizarVista() {
        mostrarTablaCitas();
        tableCitas.refresh();
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

