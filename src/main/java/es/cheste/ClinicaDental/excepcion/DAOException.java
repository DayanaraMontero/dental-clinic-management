package es.cheste.ClinicaDental.excepcion;

public class DAOException extends Exception {
    public DAOException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

