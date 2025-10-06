package es.cheste.ClinicaDental.controladores_fx;

import es.cheste.ClinicaDental.config.Validador;
import es.cheste.ClinicaDental.controladores_cliente.DentistaRestClient;
import es.cheste.ClinicaDental.entidades.Dentista;
import es.cheste.ClinicaDental.enums.Especialidad;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class DentistaControllerFX implements Initializable {
    private static final String TITULO_AGREGAR = "Agregar Dentista";
    private static final String MENSAJE_AGREGAR = "¿Está seguro de que desea agregar este dentista?";

    private static final String TITULO_EDITAR = "Actualizar Dentista";
    private static final String MENSAJE_EDITAR = "¿Está seguro de que desea actualizar este dentista?";

    private static final String TITULO_ELIMINAR = "Eliminar Dentista";
    private static final String MENSAJE_ELIMINAR = "¿Está seguro de que desea eliminar este dentista?";

    @Autowired
    private DentistaRestClient dentistaRestClient;
    private Dentista fila;

    @FXML private TableView<Dentista> tableDentistas;

    @FXML private TableColumn<Dentista, Long> colDentistasId;

    @FXML private TableColumn<Dentista, Integer> colDentistasNumCol;

    @FXML private TableColumn<Dentista, String> colDentistasNombre;

    @FXML private TableColumn<Dentista, String> colDentistasApellido;

    @FXML private TableColumn<Dentista, Especialidad> colDentistasEspecialidad;

    @FXML private TableColumn<Dentista, String> colDentistasTelefono;

    @FXML private TableColumn<Dentista, String> colDentistasEmail;

    @FXML private TextField txtDentistaId;

    @FXML private TextField txtDentistaNumCol;

    @FXML private TextField txtDentistaNombre;

    @FXML private TextField txtDentistaApellidos;

    @FXML private ComboBox<Especialidad> cmbDentistaEspecialidad;

    @FXML private TextField txtDentistaNumTel;

    @FXML private TextField txtDentistaCorreo;

    @FXML private Button btnAgregarDentista;

    @FXML private Button btnEditarDentista;

    @FXML private Button btnEliminarDentista;

    @FXML private Button btnLimpiarDentista;

    @FXML private Label lblTotal;

    public DentistaControllerFX() { }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::configurarInterfaz);
        configurarColumnas();
        mostrarTablaDentistas();
        configurarEventos();
    }

    private void configurarInterfaz() {
        txtDentistaId.setDisable(true);
        cmbDentistaEspecialidad.setItems(FXCollections.observableArrayList(Especialidad.values()));
        limpiar();
    }

    private void configurarColumnas() {
        colDentistasId.setCellValueFactory(new PropertyValueFactory<>("id")); // Se usa el nombre del atributo
        colDentistasNumCol.setCellValueFactory(new PropertyValueFactory<>("numColegiado"));
        colDentistasNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDentistasApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colDentistasEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        colDentistasTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDentistasEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private <T> void configurarColumnaCentrada(TableColumn<Dentista, T> columna) {
        columna.setCellFactory(column -> {
            TableCell<Dentista, T> cell = new TableCell<>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());
                        setAlignment(Pos.CENTER); // Centrar el texto
                    }
                }
            };
            return cell;
        });
    }

    private void configurarEventos() {
        tableDentistas.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                cargarDatos(newValue);
            }
        });
    }

    private void cargarDatos(Dentista dentista) {
        this.fila = dentista;
        txtDentistaId.setText(String.valueOf(fila.getId()));
        txtDentistaNumCol.setText(String.valueOf(fila.getNumColegiado()));
        txtDentistaNombre.setText(fila.getNombre());
        txtDentistaApellidos.setText(fila.getApellido());
        cmbDentistaEspecialidad.setValue(fila.getEspecialidad());
        txtDentistaNumTel.setText(fila.getTelefono());
        txtDentistaCorreo.setText(fila.getEmail());

        btnAgregarDentista.setVisible(false);
        btnLimpiarDentista.setVisible(true);
        btnEditarDentista.setVisible(true);
        btnEliminarDentista.setVisible(true);
    }

    public void mostrarTablaDentistas() {
        tableDentistas.setItems(FXCollections.observableArrayList(dentistaRestClient.listarDentistas()));
        // Mostrar todos los registros
        lblTotal.setText(String.valueOf(tableDentistas.getItems().size()));
    }

    @FXML
    public void agregarDentista() {
        if (validarCampos()) {
            mostrarConfirmacion(TITULO_AGREGAR, MENSAJE_AGREGAR, () -> {
                Dentista dentista = crearDentista();
                dentistaRestClient.agregarDentista(dentista);
                actualizarVista();
                mostrarMensajeExito("Éxito", "Dentista agregado correctamente.");
            });
        }
    }

    @FXML
    public void editarDentista() {
        if (fila == null) {
            mostrarMensajeError("Error", "No se ha seleccionado un dentista para editar.");
            return;
        }

        if (validarCampos()) {
            mostrarConfirmacion(TITULO_EDITAR, MENSAJE_EDITAR, () -> {
                actualizarDentistaFormulario(fila);
                dentistaRestClient.actualizarDentista(fila);
                actualizarVista();
                mostrarMensajeExito("Éxito", "Dentista editado correctamente.");
            });
        }
    }

    @FXML
    public void eliminarDentista() {
        if (fila != null) {
            mostrarConfirmacion(TITULO_ELIMINAR, MENSAJE_ELIMINAR, () -> {
                dentistaRestClient.eliminarDentista(fila.getId());
                actualizarVista();
                mostrarMensajeExito("Éxito", "Dentista eliminado correctamente.");
            });
        }
    }

    @FXML
    public void limpiar() {
        // Mejorar con un bucle for each
        for (TextField field : new TextField[]{
                txtDentistaId, txtDentistaNumCol, txtDentistaNombre, txtDentistaApellidos,
                txtDentistaNumTel, txtDentistaCorreo
        }) {
            field.clear();
        }
        cmbDentistaEspecialidad.setValue(null);

        btnAgregarDentista.setVisible(true);
        btnLimpiarDentista.setVisible(false);
        btnEditarDentista.setVisible(false);
        btnEliminarDentista.setVisible(false);
    }

    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        // Verificar que los campos no sean null
        if (txtDentistaNumCol == null || txtDentistaNombre == null || txtDentistaApellidos == null ||
                cmbDentistaEspecialidad == null || txtDentistaNumTel == null || txtDentistaCorreo == null) {
            errores.append("- Error: Algunos campos del formulario no están inicializados correctamente.\n");
            mostrarMensajeError("Error de validación", errores.toString());
            return false;
        }

        if (txtDentistaNumCol.getText().trim().isEmpty()) {
            errores.append("- El número de colegiado es obligatorio.\n");
        } else {
            try {
                Integer.parseInt(txtDentistaNumCol.getText().trim());
            } catch (NumberFormatException e) {
                errores.append("- El número de colegiado debe ser un número válido");
            }
        }

        // Validar nombre
        if (txtDentistaNombre.getText().trim().isEmpty()) {
            errores.append("- El nombre es obligatorio.\n");
        } else if (!Validador.validarTexto(txtDentistaNombre.getText().trim())) {
            errores.append("- El nombre debe contener al menos 3 letras y solo caracteres válidos.\n");
        }

        // Validar apellidos
        if (txtDentistaApellidos.getText().trim().isEmpty()) {
            errores.append("- Los apellidos son obligatorios.\n");
        } else if (!Validador.validarTexto(txtDentistaApellidos.getText().trim())) {
            errores.append("- Los apellidos deben contener al menos 3 letras y solo caracteres válidos.\n");
        }

        // Validar especialidad
        if (cmbDentistaEspecialidad.getValue() == null) {
            errores.append("- La especialidad es obligatoria.\n");
        }

        // Validar teléfono
        if (txtDentistaNumTel.getText().trim().isEmpty()) {
            errores.append("- El teléfono es obligatorio.\n");
        } else if (!Validador.validarTelefono(txtDentistaNumTel.getText().trim())) {
            errores.append("- El teléfono debe tener 9 dígitos.\n");
        }

        // Validar email (opcional)
        String emailText = txtDentistaCorreo.getText(); // Obtener el texto
        if (emailText != null && !emailText.trim().isEmpty()) {
            // Solo validar el email si no está vacío
            if (!Validador.validarEmail(emailText.trim())) {
                errores.append("- El email debe tener un formato válido (ejemplo: usuario@dominio.com).\n");
            }
        }

        // Mostrar errores si los hay
        if (!errores.isEmpty()) {
            mostrarMensajeError("Error de validación", errores.toString());
            return false;
        }
        return true;
    }

    private Dentista crearDentista() {
        Dentista dentista = new Dentista();
        dentista.setNumColegiado(Integer.parseInt(txtDentistaNumCol.getText()));
        dentista.setNombre(txtDentistaNombre.getText());
        dentista.setApellido(txtDentistaApellidos.getText());
        dentista.setEspecialidad(cmbDentistaEspecialidad.getValue());
        dentista.setTelefono(txtDentistaNumTel.getText());

        // Manejar el caso en que el correo electrónico sea null o esté vacío
        String email = txtDentistaCorreo.getText(); // Obtener el texto
        dentista.setEmail(email == null || email.trim().isEmpty() ? null : email.trim());

        return dentista;
    }

    private void actualizarDentistaFormulario(Dentista dentista) {
        // Obtener el texto de cada campo, manejando el caso en que sea null
        String numColText = txtDentistaNumCol.getText();
        String nombreText = txtDentistaNombre.getText();
        String apellidosText = txtDentistaApellidos.getText();
        String numTelText = txtDentistaNumTel.getText();
        String emailText = txtDentistaCorreo.getText();

        // Actualizar el objeto Dentista
        dentista.setNumColegiado(numColText == null || numColText.trim().isEmpty() ? 0 : Integer.parseInt(numColText.trim()));
        dentista.setNombre(nombreText == null || nombreText.trim().isEmpty() ? "" : nombreText.trim());
        dentista.setApellido(apellidosText == null || apellidosText.trim().isEmpty() ? "" : apellidosText.trim());
        dentista.setEspecialidad(cmbDentistaEspecialidad.getValue());
        dentista.setTelefono(numTelText == null || numTelText.trim().isEmpty() ? "" : numTelText.trim());
        dentista.setEmail(emailText == null || emailText.trim().isEmpty() ? null : emailText.trim());
    }

    private void actualizarVista() {
        mostrarTablaDentistas();
        tableDentistas.refresh();
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
