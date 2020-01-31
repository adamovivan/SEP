package rs.ac.uns.ftn.bank.exception;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException() {}

    public InvalidDataException(String message) {
        super(message);
    }
}
