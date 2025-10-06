package es.cheste.ClinicaDental.controladores_fx;

import es.cheste.ClinicaDental.config.Validador;
import es.cheste.ClinicaDental.controladores_cliente.PacienteRestClient;
import es.cheste.ClinicaDental.entidades.Dentista;
import es.cheste.ClinicaDental.entidades.Paciente;
import es.cheste.ClinicaDental.enums.Genero;
import es.cheste.ClinicaDental.repositorios.DentistaRepository;
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
public class PacienteControllerFX implements Initializable {
    private static final String TITULO_AGREGAR = "Agregar Paciente";
    private static final String MENSAJE_AGREGAR = "¿Está seguro de que desea agregar este paciente?";

    private static final String TITULO_EDITAR = "Actualizar Paciente";
    private static final String MENSAJE_EDITAR = "¿Está seguro de que desea actualizar este paciente?";

    private static final String TITULO_ELIMINAR = "Eliminar Paciente";
    private static final String MENSAJE_ELIMINAR = "¿Está seguro de que desea eliminar este paciente?";

    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private PacienteRestClient pacienteRestClient;
    private Paciente fila;

    @FXML private TableView<Paciente> tablePacientes;

    @FXML private TableColumn<Paciente, Long> colPacientesId;

    @FXML private TableColumn<Paciente, String> colPacientesNombre;

    @FXML private TableColumn<Paciente, String> colPacientesApellido;

    @FXML private TableColumn<Paciente, Dentista> colPacientesIdDentista;

    @FXML private TableColumn<Paciente, LocalDate> colPacientesFechaNac;

    @FXML private TableColumn<Paciente, Genero> colPacientesGenero;

    @FXML private TableColumn<Paciente, String> colPacientesTelefono;

    @FXML private TableColumn<Paciente, String> colPacientesEmail;

    @FXML private TableColumn<Paciente, String> colPacientesDireccion;

    @FXML private TextField txtPacienteId;

    @FXML private TextField txtPacienteNombre;

    @FXML private TextField txtPacienteApellidos;

    @FXML private ComboBox<Dentista> cmbPacienteDentista;

    @FXML private DatePicker dpPacienteFechaNac;

    @FXML private ComboBox<Genero> cmbPacienteGenero;

    @FXML private TextField txtPacienteNumTel;

    @FXML private TextField txtPacienteCorreo;

    @FXML private TextArea txtPacienteDireccion;

    @FXML private Button btnAgregarPaciente;

    @FXML private Button btnEditarPaciente;

    @FXML private Button btnEliminarPaciente;

    @FXML private Button btnLimpiarPaciente;

    @FXML private Label lblTotal;

    public PacienteControllerFX() { }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::configurarInterfaz);
        configurarColumnas();
        mostrarTablaPacientes();
        configurarEventos();
    }

    private void configurarInterfaz() {
        txtPacienteId.setDisable(true);
        cmbPacienteGenero.setItems(FXCollections.observableArrayList(Genero.values()));

        cargarDentistasCombo();
        limpiar();
    }

    private void cargarDentistasCombo() {
        List<Dentista> dentistas = dentistaRepository.findAll();
        cmbPacienteDentista.setItems(FXCollections.observableArrayList(dentistas));
        cmbPacienteDentista.setCellFactory(param -> new ListCell<Dentista>() {
            @Override
            protected void updateItem(Dentista dentista, boolean empty) {
                super.updateItem(dentista, empty);
                if (empty || dentista == null) {
                    setText(null);
                } else {
                    setText(dentista.getNombre()); // Mostrar el nombre del dentista en el ComboBox
                }
            }
        });

        cmbPacienteDentista.setButtonCell(new ListCell<Dentista>() {
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
        colPacientesId.setCellValueFactory(new PropertyValueFactory<>("id")); // Se usa el nombre del atributo
        colPacientesNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPacientesApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));

        // Configurar la columna del dentista para mostrar solo el nombre
        colPacientesIdDentista.setCellValueFactory(new PropertyValueFactory<>("dentista"));
        colPacientesIdDentista.setCellFactory(column -> {
            return new TableCell<Paciente, Dentista>() {
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

        colPacientesFechaNac.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colPacientesGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colPacientesTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colPacientesEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPacientesDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
    }

    private void configurarEventos() {
        tablePacientes.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                cargarDatos(newValue);
            }
        });
    }

    private void cargarDatos(Paciente paciente) {
        this.fila = paciente;
        txtPacienteId.setText(String.valueOf(fila.getId()));
        txtPacienteNombre.setText(fila.getNombre());
        txtPacienteApellidos.setText(fila.getApellido());
        cmbPacienteDentista.setValue(fila.getDentista());
        dpPacienteFechaNac.setValue(fila.getFechaNacimiento());
        cmbPacienteGenero.setValue(fila.getGenero());
        txtPacienteNumTel.setText(fila.getTelefono());
        txtPacienteCorreo.setText(fila.getEmail());
        txtPacienteDireccion.setText(fila.getDireccion());

        btnAgregarPaciente.setVisible(false);
        btnLimpiarPaciente.setVisible(true);
        btnEditarPaciente.setVisible(true);
        btnEliminarPaciente.setVisible(true);
    }

    public void mostrarTablaPacientes() {
        tablePacientes.setItems(FXCollections.observableArrayList(pacienteRestClient.listarPacientes()));
        // Mostrar todos los registros
        lblTotal.setText(String.valueOf(tablePacientes.getItems().size()));
    }

    @FXML
    public void agregarPaciente() {
        if (validarCampos()) {
            mostrarConfirmacion(TITULO_AGREGAR, MENSAJE_AGREGAR, () -> {
                Paciente paciente = crearPaciente();
                pacienteRestClient.agregarPaciente(paciente);
                actualizarVista();
                mostrarMensajeExito("Éxito", "Paciente agregado correctamente.");
            });
        }
    }

    @FXML
    public void editarPaciente() {
        if (fila == null) {
            mostrarMensajeError("Error", "No se ha seleccionado un paciente para editar.");
            return;
        }

        if (validarCampos()) {
            mostrarConfirmacion(TITULO_EDITAR, MENSAJE_EDITAR, () -> {
                actualizarPacienteFormulario(fila);
                pacienteRestClient.actualizarPaciente(fila);
                actualizarVista();
                mostrarMensajeExito("Éxito", "Paciente editado correctamente.");
            });
        }
    }

    @FXML
    public void eliminarPaciente() {
        if (fila != null) {
            mostrarConfirmacion(TITULO_ELIMINAR, MENSAJE_ELIMINAR, () -> {
                pacienteRestClient.eliminarPaciente(fila.getId());
                actualizarVista();
                mostrarMensajeExito("Éxito", "Paciente eliminado correctamente.");
            });
        }
    }

    @FXML
    public void limpiar() {
        // Mejorar con un bucle for each
        for (TextField field : new TextField[]{
                txtPacienteId, txtPacienteNombre, txtPacienteApellidos,
                txtPacienteNumTel, txtPacienteCorreo
        }) {
            field.clear();
        }

        txtPacienteDireccion.clear();

        dpPacienteFechaNac.setValue(null);

        cmbPacienteGenero.setValue(null);
        cmbPacienteDentista.setValue(null);

        btnAgregarPaciente.setVisible(true);
        btnLimpiarPaciente.setVisible(false);
        btnEditarPaciente.setVisible(false);
        btnEliminarPaciente.setVisible(false);
    }

    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        // Verificar que los campos no sean null
        if (txtPacienteNombre == null || txtPacienteApellidos == null || cmbPacienteDentista == null || dpPacienteFechaNac == null || cmbPacienteGenero == null ||
                txtPacienteNumTel == null || txtPacienteCorreo == null || txtPacienteDireccion == null) {
            errores.append("- Error: Algunos campos del formulario no están inicializados correctamente.\n");
            mostrarMensajeError("Error de validación", errores.toString());
            return false;
        }

        // Validar nombre
        if (txtPacienteNombre.getText().trim().isEmpty()) {
            errores.append("- El nombre es obligatorio.\n");
        } else if (!Validador.validarTexto(txtPacienteNombre.getText().trim())) {
            errores.append("- El nombre debe contener al menos 3 letras y solo caracteres válidos.\n");
        }

        // Validar apellidos
        if (txtPacienteApellidos.getText().trim().isEmpty()) {
            errores.append("- Los apellidos son obligatorios.\n");
        } else if (!Validador.validarTexto(txtPacienteApellidos.getText().trim())) {
            errores.append("- Los apellidos deben contener al menos 3 letras y solo caracteres válidos.\n");
        }

        if (cmbPacienteDentista.getValue() == null) {
            errores.append("Debe seleccionar un dentista.\n");
        }

        // Validar fecha
        if (dpPacienteFechaNac.getValue() == null) {
            errores.append("- La fecha de nacimiento es obligatoria.\n");
        }

        if (cmbPacienteGenero.getValue() == null) {
            errores.append("- El genero es obligatorio.\n");
        }

        // Validar teléfono
        if (txtPacienteNumTel.getText().trim().isEmpty()) {
            errores.append("- El teléfono es obligatorio.\n");
        } else if (!Validador.validarTelefono(txtPacienteNumTel.getText().trim())) {
            errores.append("- El teléfono debe tener 9 dígitos.\n");
        }

        // Validar email (opcional)
        String emailText = txtPacienteCorreo.getText(); // Obtener el texto
        if (emailText != null && !emailText.trim().isEmpty()) {
            // Solo validar el email si no está vacío
            if (!Validador.validarEmail(emailText.trim())) {
                errores.append("- El email debe tener un formato válido (ejemplo: usuario@dominio.com).\n");
            }
        }

        // Validar email (opcional)
        String direccionText = txtPacienteDireccion.getText(); // Obtener el texto
        if (direccionText != null && !direccionText.trim().isEmpty()) {
            // Solo validar el email si no está vacío
            if (!Validador.validarTexto(direccionText.trim())) {
                errores.append("- La dirección debe contener al menos 3 letras y solo caracteres válidos.\n");
            }
        }

        // Mostrar errores si los hay
        if (!errores.isEmpty()) {
            mostrarMensajeError("Error de validación", errores.toString());
            return false;
        }
        return true;
    }

    private Paciente crearPaciente() {
        Paciente paciente = new Paciente();
        paciente.setNombre(txtPacienteNombre.getText());
        paciente.setApellido(txtPacienteApellidos.getText());

        paciente.setDentista(cmbPacienteDentista.getValue());

        paciente.setFechaNacimiento(dpPacienteFechaNac.getValue());

        paciente.setGenero(cmbPacienteGenero.getValue());

        paciente.setTelefono(txtPacienteNumTel.getText());

        // Manejar el caso en que el correo electrónico sea null o esté vacío
        String email = txtPacienteCorreo.getText(); // Obtener el texto
        paciente.setEmail(email == null || email.trim().isEmpty() ? null : email.trim());

        String direccion = txtPacienteDireccion.getText(); // Obtener el texto
        paciente.setDireccion(direccion == null || direccion.trim().isEmpty() ? null : direccion.trim());

        return paciente;
    }

    private void actualizarPacienteFormulario(Paciente paciente) {
        // Obtener el texto de cada campo, manejando el caso en que sea null
        String nombreText = txtPacienteNombre.getText();
        String apellidoText = txtPacienteApellidos.getText();
        String telefonoText = txtPacienteNumTel.getText();
        String emailText = txtPacienteCorreo.getText();
        String direccionText = txtPacienteDireccion.getText();

        // Obtener la fecha del DatePicker
        LocalDate fechaNacimiento = dpPacienteFechaNac.getValue();

        // Actualizar el objeto Paciente
        paciente.setNombre(nombreText == null || nombreText.trim().isEmpty() ? "" : nombreText.trim());
        paciente.setApellido(apellidoText == null || apellidoText.trim().isEmpty() ? "" : apellidoText.trim());
        paciente.setDentista(cmbPacienteDentista.getValue());
        paciente.setFechaNacimiento(fechaNacimiento); // Asignar la fecha de nacimiento
        paciente.setGenero(cmbPacienteGenero.getValue());
        paciente.setTelefono(telefonoText == null || telefonoText.trim().isEmpty() ? "" : telefonoText.trim());
        paciente.setEmail(emailText == null || emailText.trim().isEmpty() ? null : emailText.trim());
        paciente.setDireccion(direccionText == null || direccionText.trim().isEmpty() ? "" : direccionText.trim());
    }

    private void actualizarVista() {
        mostrarTablaPacientes();
        tablePacientes.refresh();
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
