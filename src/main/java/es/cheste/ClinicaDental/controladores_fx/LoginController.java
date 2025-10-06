package es.cheste.ClinicaDental.controladores_fx;

import animatefx.animation.FadeOut;
import es.cheste.ClinicaDental.config.Validador;
import es.cheste.ClinicaDental.controladores_cliente.UsuarioRestClient;
import es.cheste.ClinicaDental.entidades.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class LoginController implements Initializable {

    @Autowired
    private UsuarioRestClient usuariosRestClient;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtContrasenya;

    @FXML
    private TextField txtContrasenyaVisible;

    @FXML
    private CheckBox mostrarContrasenya;

    @FXML
    private AnchorPane loginForm;

    @FXML
    private Circle btnClose; // Botón rojo (cerrar)

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar el CheckBox para mostrar/ocultar la contraseña
        mostrarContrasenya.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // Mostrar la contraseña en texto plano
                txtContrasenyaVisible.setText(txtContrasenya.getText());
                txtContrasenyaVisible.setVisible(true);
                txtContrasenya.setVisible(false);
            } else {
                // Ocultar la contraseña en texto plano
                txtContrasenya.setText(txtContrasenyaVisible.getText());
                txtContrasenya.setVisible(true);
                txtContrasenyaVisible.setVisible(false);
            }
        });
    }

    @FXML
    public void handleLogin() {
        String email = txtCorreo.getText().trim();
        String contrasenya = txtContrasenya.getText().trim();

        // Validar que todos los campos estén completos
        if (email.isEmpty() || contrasenya.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Por favor, completa todos los campos.");
            return;
        }

        // Validar el correo electrónico
        if (!Validador.validarEmail(email)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "El email debe tener un formato válido (ejemplo: usuario@dominio.com)");
            return;
        }

        try {
            // Realizar el login a través del cliente REST
            Usuario usuario = usuariosRestClient.login(email, contrasenya);

            // Verificar que el usuario sea válido
            if (usuario != null) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Login exitoso: " + usuario.getNombre());
                // Redirigir a la pantalla principal del administrador
                mostrarPanelAdmin(usuario.getNombre());
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "El usuario no existe. ¿Desea registrarse?");
                abrirRegistro();
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "El usuario no existe. ¿Desea registrarse?");
            abrirRegistro();
        }
    }

    @FXML
    private void abrirRegistro() {
        try {
            // Cargar la vista de registro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/registro-view.fxml"));
            Parent root = loader.load();

            // Obtener la escena actual y cambiarla por la nueva escena
            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            Scene scene = new Scene(root);

            // Variables para el arrastre de la ventana
            final double[] xOffset = new double[1];
            final double[] yOffset = new double[1];

            root.setOnMousePressed(event -> {
                xOffset[0] = event.getSceneX();
                yOffset[0] = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset[0]);
                stage.setY(event.getScreenY() - yOffset[0]);
            });

            // Asignar la nueva escena
            stage.setScene(scene);

            // Centrar la ventana en la pantalla
            stage.centerOnScreen();

            // Cambiar el título
            stage.setTitle("Registro");

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la interfaz de registro.");
        }
    }


    private void mostrarPanelAdmin(String nombreUsuario) {
        try {
            // Cargar la vista del administrador
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/admin-view.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la nueva ventana
            AdminController adminController = loader.getController();
            adminController.setNombreUsuario(nombreUsuario);

            // Crear un nuevo Stage
            Stage stage = new Stage();

            // Aplicar estilo antes de hacer visible la ventana
            stage.initStyle(StageStyle.TRANSPARENT); // Sin bordes

            // Crear la nueva escena
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/admin-panel.css").toExternalForm());
            scene.setFill(Color.TRANSPARENT);

            // Variables para el arrastre de la ventana
            final double[] xOffset = new double[1];
            final double[] yOffset = new double[1];

            scene.setOnMousePressed(event -> {
                xOffset[0] = event.getSceneX();
                yOffset[0] = event.getSceneY();
            });

            scene.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset[0]);
                stage.setY(event.getScreenY() - yOffset[0]);
            });

            // Asignar la escena al Stage y mostrar
            stage.setScene(scene);

            stage.setMaximized(true); // Ventana maximizada por defecto

            stage.show();

            // Cerrar la ventana de login
            ((Stage) txtCorreo.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo cargar la interfaz de administrador.");
        }
    }

    @FXML
    private void handleButton(MouseEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); // Obtener la ventana actual

        // Cerrar la aplicación (botón rojo)
        if (e.getSource() == btnClose) {
            FadeOut fadeOut = new FadeOut(loginForm);
            fadeOut.setOnFinished(actionEvent -> System.exit(0));
            fadeOut.play();
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
