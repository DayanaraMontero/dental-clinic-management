package es.cheste.ClinicaDental.config;

public class ValidacionUtil {

    public static boolean validarCampo(String valor, String regex) {
        return valor != null && valor.matches(regex);
    }

    public static boolean validarNombre(String nombre) {
        return validarCampo(nombre, "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,}$");
    }

    public static boolean validarNumCol(String numCol) {
        return validarCampo(numCol, "^\\d+$");
    }

    public static boolean validarTelefono(String telefono) {
        return validarCampo(telefono, "^\\d{3}[- ]?\\d{3}[- ]?\\d{3}$");
    }

    public static boolean validarEmail(String email) {
        return validarCampo(email, "^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
    }
}

