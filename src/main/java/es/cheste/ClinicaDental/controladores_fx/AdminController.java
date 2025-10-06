package es.cheste.ClinicaDental.controladores_fx;

import animatefx.animation.FadeOut;
import animatefx.animation.Pulse;
import animatefx.animation.Tada;
import es.cheste.ClinicaDental.config.AppContext;
import es.cheste.ClinicaDental.config.SpringFxmlLoader;
import es.cheste.ClinicaDental.controladores_cliente.DentistaRestClient;
import es.cheste.ClinicaDental.entidades.Dentista;
import es.cheste.ClinicaDental.enums.Especialidad;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

@Component
public class AdminController implements Initializable {

    @Autowired
    private SpringFxmlLoader springFxmlLoader;

    @FXML
    private AnchorPane mainForm; // Pane principal donde se cargarán las vistas

    @FXML
    private Circle btnClose; // Botón rojo (cerrar)

    @FXML
    private Circle btnMinimize; // Botón amarillo (ocultar)

    @FXML
    private Circle btnMaximize; // Botón verde (maximizar/restaurar)

    @FXML
    private Label lblDateTime;

    @FXML
    private Label lblBienvenida; // Label para mostrar el mensaje de bienvenida

    @FXML
    private Button btnVolverInicioSesion;

    @FXML
    private StackPane adminForm;

    @Autowired
    private DentistaRestClient dentistaRestClient;

    private boolean maximized = false;

    @FXML private TableView<Dentista> tableDashboard;

    @FXML private TableColumn<Dentista, Long> colDashDentistaId;

    @FXML private TableColumn<Dentista, Integer> colDashNumCol;

    @FXML private TableColumn<Dentista, String> colDashNombre;

    @FXML private TableColumn<Dentista, String> colDashApellido;

    @FXML private TableColumn<Dentista, Especialidad> colDashEspe;

    @FXML private TableColumn<Dentista, String> colDashTelefono;

    @FXML private TableColumn<Dentista, String> colDashEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        runTime(); // Inicia el reloj
        // Configurar las columnas usando el servicio
        /*dentistaService.configurarColumnas(tableDashboard, colDashDentistaId, colDashNumCol, colDashNombre,
                colDashApellido, colDashEspe, colDashTelefono, colDashEmail);

        // Cargar los datos de los dentistas
        tableDashboard.setItems(dentistaService.obtenerDentistas());*/
    }
    
    @FXML
    private void mostrarDashboard() {
        if (springFxmlLoader == null) { // Para que SpringFXMLLoader no sea Null
            springFxmlLoader = AppContext.getApplicationContext().getBean(SpringFxmlLoader.class);
        }
        cargarVista("/vista/dashboard-form.fxml");
    }

    @FXML
    private void mostrarDentistas() {
        if (springFxmlLoader == null) { // Para que SpringFXMLLoader no sea Null
            springFxmlLoader = AppContext.getApplicationContext().getBean(SpringFxmlLoader.class);
        }

        cargarVista("/vista/dentista-form.fxml");
    }

    @FXML
    private void mostrarPacientes() {
        if (springFxmlLoader == null) { // Para que SpringFXMLLoader no sea Null
            springFxmlLoader = AppContext.getApplicationContext().getBean(SpringFxmlLoader.class);
        }
        cargarVista("/vista/paciente-form.fxml");
    }

    @FXML
    private void mostrarSalas() {
        if (springFxmlLoader == null) { // Para que SpringFXMLLoader no sea Null
            springFxmlLoader = AppContext.getApplicationContext().getBean(SpringFxmlLoader.class);
        }
        cargarVista("/vista/consulta-form.fxml");
    }

    @FXML
    private void mostrarTratamientos() {
        if (springFxmlLoader == null) { // Para que SpringFXMLLoader no sea Null
            springFxmlLoader = AppContext.getApplicationContext().getBean(SpringFxmlLoader.class);
        }
        cargarVista("/vista/tratamiento-form.fxml");
    }

    @FXML
    private void mostrarCitas() {
        if (springFxmlLoader == null) { // Para que SpringFXMLLoader no sea Null
            springFxmlLoader = AppContext.getApplicationContext().getBean(SpringFxmlLoader.class);
        }
        cargarVista("/vista/cita-form.fxml");
    }

    @FXML
    private void mostrarFacturas() {
        if (springFxmlLoader == null) { // Para que SpringFXMLLoader no sea Null
            springFxmlLoader = AppContext.getApplicationContext().getBean(SpringFxmlLoader.class);
        }
        cargarVista("/vista/factura-form.fxml");
    }

    public void setNombreUsuario(String nombreUsuario) {
        lblBienvenida.setText(nombreUsuario + "!");
    }

    public void cargarVista(String fxml) {
        try {
            // Usa SpringFxmlLoader para cargar el FXML
            Parent nuevaVista = springFxmlLoader.load(fxml);

            // Limpia el contenido previo antes de agregar la nueva vista
            mainForm.getChildren().clear();
            mainForm.getChildren().add(nuevaVista);

            // Asegura que la nueva vista se expanda dentro del AnchorPane
            AnchorPane.setTopAnchor(nuevaVista, 0.0);
            AnchorPane.setBottomAnchor(nuevaVista, 0.0);
            AnchorPane.setLeftAnchor(nuevaVista, 0.0);
            AnchorPane.setRightAnchor(nuevaVista, 0.0);

            // Aplicar el CSS correspondiente
            nuevaVista.getStylesheets().add(getClass().getResource("/css/admin-panel.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista: " + fxml);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void runTime() {
        new Thread(() -> {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

                Platform.runLater(() -> {
                    lblDateTime.setText(format.format(new Date()));
                });
            }
        }).start();
    }

    @FXML
    private void handleButtons(MouseEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); // Obtener la ventana actual

        // Cerrar la aplicación (botón rojo)
        if (e.getSource() == btnClose) {
            FadeOut fadeOut = new FadeOut(adminForm);
            fadeOut.setOnFinished(actionEvent -> System.exit(0));
            fadeOut.play();
        }

        // Minimizar la ventana (botón amarillo)
        if (e.getSource() == btnMinimize) {
            Tada tada = new Tada(btnMinimize);
            tada.setOnFinished(actionEvent -> stage.setIconified(true));
            tada.play();
        }

        // Maximizar/restaurar ventana (botón verde)
        if (e.getSource() == btnMaximize) {
            Pulse pulse = new Pulse(btnMaximize);
            pulse.setOnFinished(actionEvent -> {
                stage.setMaximized(!maximized);
                maximized = !maximized;
            });
            pulse.play();
        }
    }

    @FXML
    private void volverAIniciarSesion() {
        try {
            if (springFxmlLoader == null) { // Para que SpringFXMLLoader no sea Null
                springFxmlLoader = AppContext.getApplicationContext().getBean(SpringFxmlLoader.class);
            }

            // Carga la vista de inicio de sesión
            Parent vistaInicioSesion = springFxmlLoader.load("/vista/login-view.fxml");

            // Obtiene la escena actual y cambia su contenido
            Scene escenaActual = btnVolverInicioSesion.getScene();
            escenaActual.setRoot(vistaInicioSesion);

            // Obtener el Stage (ventana) actual
            Stage stage = (Stage) escenaActual.getWindow();

            // Restablecer el tamaño de la ventana al tamaño preferido del nuevo FXML
            stage.sizeToScene(); // Ajusta el tamaño de la ventana al contenido

            // Centrar la ventana en la pantalla
            stage.centerOnScreen();

            // Opcional: Deshabilitar el redimensionamiento si no es necesario
            stage.setResizable(false); // Esto evita que el usuario cambie el tamaño de la ventana

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se ha podido cargar la interfaz");
        }
    }
}



