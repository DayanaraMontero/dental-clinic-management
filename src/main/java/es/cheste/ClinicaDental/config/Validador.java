package es.cheste.ClinicaDental.config;

public class Validador {

    // Validación de email
    public static boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]{3,}@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }

    // Validación de teléfono (solo números, 9 dígitos)
    public static boolean validarTelefono(String telefono) {
        String regex = "^[0-9]{9}$";
        return telefono.matches(regex);
    }

    // Validación de nombres, apellidos, ubicación, dirección, observaciones
    public static boolean validarTexto(String texto) {
        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]{3,}$"; // Acepta letras, espacios y caracteres acentuados
        return texto.matches(regex);
    }

    // Validación de contraseña (mínimo 8 caracteres, permite letras, números y caracteres especiales)
    public static boolean validarContrasenya(String contrasenya) {
        String regex = "^[A-Za-z0-9@\\-_.]{8,}$";
        return contrasenya.matches(regex);
    }

    // Validación de BigDecimal (solo números y un punto decimal opcional)
    public static boolean validarBigDecimal(String valor) {
        String regex = "^[0-9]+(\\.[0-9]{1,2})?$"; // Acepta números con un punto decimal opcional
        return valor.matches(regex);
    }
}

