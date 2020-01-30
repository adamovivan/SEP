package rs.ac.uns.ftn.pcc.exception;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException() {}

    public InvalidDataException(String message) {
        super(message);
    }
}
