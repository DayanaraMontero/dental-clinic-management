package es.cheste.ClinicaDental.controladores_fx;

import es.cheste.ClinicaDental.config.Validador;
import es.cheste.ClinicaDental.controladores_cliente.FacturaRestClient;
import es.cheste.ClinicaDental.entidades.Factura;
import es.cheste.ClinicaDental.entidades.Paciente;
import es.cheste.ClinicaDental.entidades.Tratamiento;
import es.cheste.ClinicaDental.enums.EstadoFactura;
import es.cheste.ClinicaDental.enums.EstadoTratamiento;
import es.cheste.ClinicaDental.enums.MetodoPago;
import es.cheste.ClinicaDental.repositorios.PacienteRepository;
import es.cheste.ClinicaDental.repositorios.TratamientoRepository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class FacturaControllerFX implements Initializable {
    private static final String TITULO_AGREGAR = "Agregar Factura";
    private static final String MENSAJE_AGREGAR = "¿Está seguro de que desea agregar esta factura?";
    private static final String TITULO_EDITAR = "Actualizar Factura";
    private static final String MENSAJE_EDITAR = "¿Está seguro de que desea actualizar esta factura?";
    private static final String TITULO_ELIMINAR = "Eliminar Factura";
    private static final String MENSAJE_ELIMINAR = "¿Está seguro de que desea eliminar esta factura?";

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private FacturaRestClient facturaRestClient;
    private Factura fila;

    @FXML
    private TableView<Factura> tableFacturas;

    @FXML
    private TableColumn<Factura, Long> colFacturaId;

    @FXML
    private TableColumn<Factura, Paciente> colFacturaIdPaciente;

    @FXML
    private TableColumn<Factura, Tratamiento> colFacturaIdTratamiento;

    @FXML
    private TableColumn<Factura, BigDecimal> colFacturaTotal;

    @FXML
    private TableColumn<Factura, LocalDate> colFacturaFecha;

    @FXML
    private TableColumn<Factura, EstadoTratamiento> colFacturaEstado;

    @FXML
    private TableColumn<Factura, MetodoPago> colFacturaMetodoPago;

    @FXML
    private TextField txtFacturaId;

    @FXML
    private ComboBox<Paciente> cmbFacturaPaciente;

    @FXML
    private ComboBox<Tratamiento> cmbFacturaTratamiento;

    @FXML
    private TextField txtfacturaTotal;

    @FXML
    private DatePicker dpFacturaFecha;

    @FXML
    private ComboBox<EstadoFactura> cmbFacturaEstado;

    @FXML
    private ComboBox<MetodoPago> cmbFacturaMetodoPago;

    @FXML
    private Button btnAgregarFactura;

    @FXML
    private Button btnEditarFactura;

    @FXML
    private Button btnEliminarFactura;

    @FXML
    private Button btnLimpiarFactura;

    @FXML
    private Label lblTotal;

    public FacturaControllerFX() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::configurarInterfaz);
        configurarColumnas();
        mostrarTablaFactura();
        configurarEventos();
    }

    private void configurarInterfaz() {
        txtFacturaId.setDisable(true);
        cmbFacturaEstado.setItems(FXCollections.observableArrayList(EstadoFactura.values()));
        cmbFacturaMetodoPago.setItems(FXCollections.observableArrayList(MetodoPago.values()));
        cargarDatosCombo();
        limpiar();
    }

    private void cargarDatosCombo() {
        // Cargar pacientes
        List<Paciente> pacientes = pacienteRepository.findAll();
        cmbFacturaPaciente.setItems(FXCollections.observableArrayList(pacientes));
        cmbFacturaPaciente.setCellFactory(param -> new ListCell<Paciente>() {
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
        cmbFacturaPaciente.setButtonCell(new ListCell<Paciente>() {
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

        // Cargar tratamientos
        List<Tratamiento> tratamientos = tratamientoRepository.findAll();
        cmbFacturaTratamiento.setItems(FXCollections.observableArrayList(tratamientos));
        cmbFacturaTratamiento.setCellFactory(param -> new ListCell<Tratamiento>() {
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
        cmbFacturaTratamiento.setButtonCell(new ListCell<Tratamiento>() {
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
        colFacturaId.setCellValueFactory(new PropertyValueFactory<>("id"));// Se usa el nombre del atributo

        colFacturaIdPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
        colFacturaIdPaciente.setCellFactory(column -> {
            return new TableCell<Factura, Paciente>() {
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

        colFacturaIdTratamiento.setCellValueFactory(new PropertyValueFactory<>("tratamiento"));
        colFacturaIdTratamiento.setCellFactory(column -> {
            return new TableCell<Factura, Tratamiento>() {
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

        colFacturaTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colFacturaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colFacturaEstado.setCellValueFactory(new PropertyValueFactory<>("estadoFactura"));
        colFacturaMetodoPago.setCellValueFactory(new PropertyValueFactory<>("metodoPago"));
    }

    private void configurarEventos() {
        tableFacturas.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                cargarDatos(newValue);
            }
        });
    }

    private void cargarDatos(Factura factura) {
        this.fila = factura;
        txtFacturaId.setText(String.valueOf(fila.getId()));
        cmbFacturaPaciente.setValue(fila.getPaciente());
        cmbFacturaTratamiento.setValue(fila.getTratamiento());
        txtfacturaTotal.setText(fila.getTotal().toString());
        dpFacturaFecha.setValue(fila.getFecha());
        cmbFacturaEstado.setValue(fila.getEstadoFactura());
        cmbFacturaMetodoPago.setValue(fila.getMetodoPago());

        btnAgregarFactura.setVisible(false);
        btnLimpiarFactura.setVisible(true);
        btnEditarFactura.setVisible(true);
        btnEliminarFactura.setVisible(true);
    }

    public void mostrarTablaFactura() {
        tableFacturas.setItems(FXCollections.observableArrayList(facturaRestClient.listarFacturas()));
        // Mostrar todos los registros
        lblTotal.setText(String.valueOf(tableFacturas.getItems().size()));
    }

    @FXML
    public void agregarFactura() {
        if (validarCampos()) {
            mostrarConfirmacion(TITULO_AGREGAR, MENSAJE_AGREGAR, () -> {
                Factura factura = crearFactura();
                facturaRestClient.agregarFactura(factura);
                actualizarVista();
                mostrarMensajeExito("Éxito", "Factura agregada correctamente.");
            });
        }
    }

    @FXML
    public void editarFactura() {
        if (fila == null) {
            mostrarMensajeError("Error", "No se ha seleccionado una factura para editar.");
            return;
        }

        if (validarCampos()) {
            mostrarConfirmacion(TITULO_EDITAR, MENSAJE_EDITAR, () -> {
                actualizarFacturaFormulario(fila);
                facturaRestClient.actualizarFactura(fila);
                actualizarVista();
                mostrarMensajeExito("Éxito", "Factura editada correctamente.");
            });
        }
    }

    @FXML
    public void eliminarFactura() {
        if (fila != null) {
            mostrarConfirmacion(TITULO_ELIMINAR, MENSAJE_ELIMINAR, () -> {
                facturaRestClient.eliminarFactura(fila.getId());
                actualizarVista();
                mostrarMensajeExito("Éxito", "Factura eliminada correctamente.");
            });
        }
    }

    @FXML
    public void limpiar() {
        txtFacturaId.clear();
        cmbFacturaPaciente.setValue(null);
        cmbFacturaTratamiento.setValue(null);
        txtfacturaTotal.clear();
        dpFacturaFecha.setValue(null);
        cmbFacturaEstado.setValue(null);
        cmbFacturaMetodoPago.setValue(null);

        btnAgregarFactura.setVisible(true);
        btnLimpiarFactura.setVisible(false);
        btnEditarFactura.setVisible(false);
        btnEliminarFactura.setVisible(false);
    }

    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        if (cmbFacturaPaciente.getValue() == null) {
            errores.append("- Debe seleccionar un paciente.\n");
        }

        if (cmbFacturaTratamiento.getValue() == null) {
            errores.append("- Debe seleccionar un tratamiento.\n");
        }

        if (txtfacturaTotal.getText().trim().isEmpty()) {
            errores.append("- El total de la factura es obligatorio.\n");
        } else if (!Validador.validarBigDecimal(txtfacturaTotal.getText().trim())) {
            errores.append("- El total de la factura debe ser un número válido (ejemplo: 100.50).\n");
        }

        if (dpFacturaFecha.getValue() == null) {
            errores.append("- La fecha es obligatoria.\n");
        }

        if (cmbFacturaEstado.getValue() == null) {
            errores.append("- El estado de la factura es obligatorio.\n");
        }

        if (cmbFacturaMetodoPago.getValue() == null) {
            errores.append("- El método de pago es obligatorio.\n");
        }

        if (!errores.isEmpty()) {
            mostrarMensajeError("Error de validación", errores.toString());
            return false;
        }
        return true;
    }

    private Factura crearFactura() {
        Factura factura = new Factura();
        factura.setPaciente(cmbFacturaPaciente.getValue()); // Obtener el paciente seleccionado
        factura.setTratamiento(cmbFacturaTratamiento.getValue()); // Obtener el tratamiento seleccionado

        // Obtener y asignar el total de la factura (BigDecimal)
        String totalText = txtfacturaTotal.getText().trim();
        if (!totalText.isEmpty()) {
            try {
                BigDecimal total = new BigDecimal(totalText);
                factura.setTotal(total);
            } catch (NumberFormatException e) {
                System.err.println("El total de la factura no es un número válido: " + totalText);
                factura.setTotal(BigDecimal.ZERO);
            }
        } else {
            factura.setTotal(BigDecimal.ZERO);
        }

        factura.setFecha(dpFacturaFecha.getValue());
        factura.setEstadoFactura(cmbFacturaEstado.getValue());
        factura.setMetodoPago(cmbFacturaMetodoPago.getValue());

        return factura;
    }

    private void actualizarFacturaFormulario(Factura factura) {
        factura.setPaciente(cmbFacturaPaciente.getValue());
        factura.setTratamiento(cmbFacturaTratamiento.getValue());

        String totalText = txtfacturaTotal.getText().trim();
        if (!totalText.isEmpty()) {
            try {
                BigDecimal total = new BigDecimal(totalText);
                factura.setTotal(total);
            } catch (NumberFormatException e) {
                System.err.println("El total de la factura no es un número válido: " + totalText);
                factura.setTotal(BigDecimal.ZERO);
            }
        } else {
            factura.setTotal(BigDecimal.ZERO);
        }

        factura.setFecha(dpFacturaFecha.getValue());

        factura.setEstadoFactura(cmbFacturaEstado.getValue());

        factura.setMetodoPago(cmbFacturaMetodoPago.getValue());
    }

    private void actualizarVista() {
        mostrarTablaFactura();
        tableFacturas.refresh();
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
