package es.cheste.ClinicaDental.controladores_fx;

import es.cheste.ClinicaDental.config.AppContext;
import es.cheste.ClinicaDental.config.SpringFxmlLoader;
import es.cheste.ClinicaDental.config.Validador;
import es.cheste.ClinicaDental.controladores_cliente.UsuarioRestClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class RegistroController implements Initializable {

    @Autowired
    private UsuarioRestClient usuariosRestClient;

    @Autowired
    private SpringFxmlLoader springFxmlLoader; // Para cargar la vista de inicio de sesión

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtContrasenya;

    @FXML
    private PasswordField txtConfirmarContrasenya;

    @FXML
    private ImageView flechaVolver;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asegurar que las dependencias estén inyectadas
        if (usuariosRestClient == null) {
            usuariosRestClient = AppContext.getApplicationContext().getBean(UsuarioRestClient.class);
        }
        if (springFxmlLoader == null) {
            springFxmlLoader = AppContext.getApplicationContext().getBean(SpringFxmlLoader.class);
        }
    }

    @FXML
    private void registrarUsuario() {
        if (usuariosRestClient == null) {
            System.err.println("Error: usuariosRestClient es null");
            return;
        }

        String nombre = txtNombre.getText().trim();
        String email = txtCorreo.getText().trim();
        String contrasenya = txtContrasenya.getText().trim();
        String confirmarContrasenya = txtConfirmarContrasenya.getText().trim();

        // Validar que todos los campos estén completos
        if (nombre.isEmpty() || email.isEmpty() || contrasenya.isEmpty() || confirmarContrasenya.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Por favor, completa todos los campos.");
            return;
        }

        // Validar el nombre
        if (!Validador.validarTexto(nombre)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "El nombre no es válido. Debe contener al menos 3 caracteres y solo letras.");
            return;
        }

        // Validar el correo electrónico
        if (!Validador.validarEmail(email)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "El email debe tener un formato válido (ejemplo: usuario@dominio.com)");
            return;
        }

        // Validar la contraseña
        if (!Validador.validarContrasenya(contrasenya)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "La contraseña no es válida. Debe tener al menos 8 caracteres y puede contener letras, números y caracteres especiales.");
            return;
        }

        // Validar que las contraseñas coincidan
        if (!contrasenya.equals(confirmarContrasenya)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Las contraseñas no coinciden.");
            return;
        }

        try {
            // Registrar el usuario (sin TipoUsuario)
            usuariosRestClient.registrarUsuario(nombre, email, contrasenya);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario registrado con éxito.");
            volverAIniciarSesion();
        } catch (Exception e) {
            // Verificar si el error es debido a un correo electrónico duplicado
            if (e.getMessage().contains("clave única") || e.getMessage().contains("duplicate key")) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "El correo electrónico ya está registrado. Por favor, introduce otro correo electrónico.");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al registrar el usuario: " + e.getMessage());
            }
        }
    }

    @FXML
    private void volverAIniciarSesion() {
        try {
            if (springFxmlLoader == null) { // Para que SpringFXMLLoader no sea null
                springFxmlLoader = AppContext.getApplicationContext().getBean(SpringFxmlLoader.class);
            }

            // Cargar la vista de inicio de sesión
            Parent vistaInicioSesion = springFxmlLoader.load("/vista/login-view.fxml");

            // Obtener la escena actual y cambiar su contenido
            Scene escenaActual = flechaVolver.getScene();
            escenaActual.setRoot(vistaInicioSesion);

            // Obtener el Stage (ventana) actual
            Stage stage = (Stage) escenaActual.getWindow();
            stage.sizeToScene(); // Ajustar el tamaño de la ventana al contenido

            // Variables para el arrastre de la ventana
            final double[] xOffset = new double[1];
            final double[] yOffset = new double[1];

            vistaInicioSesion.setOnMousePressed(event -> {
                xOffset[0] = event.getSceneX();
                yOffset[0] = event.getSceneY();
            });

            vistaInicioSesion.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset[0]);
                stage.setY(event.getScreenY() - yOffset[0]);
            });

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista de inicio de sesión.");
        }
    }


    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}