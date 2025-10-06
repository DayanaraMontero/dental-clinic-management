package es.cheste.ClinicaDental.config;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppContext {

    private static ApplicationContext context;

    public AppContext(ApplicationContext context) {
        AppContext.context = context;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }
}
