package es.cheste.ClinicaDental;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    private ConfigurableApplicationContext springContext; // Contexto de Spring

    public static void main(String[] args) {
        System.out.println("Spring Boot Iniciado");
        launch(args); // Inicia la aplicación JavaFX
    }

    @Override
    public void init() {
        // Inicia el contexto de Spring en el método init
        springContext = SpringApplication.run(Main.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Carga el archivo FXML y usa Spring para manejar la creación del controlador
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/login-view.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean); // Usamos Spring para obtener el controlador

        // Carga la escena
        var scene = new Scene(fxmlLoader.load());

        // Configuración de la ventana
        stage.setTitle("Iniciar Sesión");
        stage.setScene(scene);

        // Hacer la ventana transparente y sin bordes
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        // Permitir que la ventana sea arrastrable
        scene.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        stage.show();
    }

    @Override
    public void stop() {
        // Cierra el contexto de Spring al detener la aplicación
        springContext.close();
    }
}
