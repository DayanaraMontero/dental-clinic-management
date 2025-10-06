package es.cheste.ClinicaDental.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class SpringFxmlLoader {

    private final ApplicationContext context;

    @Autowired
    public SpringFxmlLoader(ApplicationContext context) {
        this.context = context;
    }

    public Parent load(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean); // Usar Spring para crear controladores
        loader.setLocation(Objects.requireNonNull(getClass().getResource(fxmlPath))); // Verificar que la ruta no sea nula
        System.out.println(fxmlPath);
        return loader.load();
    }
}
