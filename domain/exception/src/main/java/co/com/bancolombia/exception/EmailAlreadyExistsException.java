package co.com.bancolombia.exception;


public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("El correo ya está registrado: " + email);
    }
}
