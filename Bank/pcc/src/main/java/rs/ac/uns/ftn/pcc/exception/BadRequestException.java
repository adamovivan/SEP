package rs.ac.uns.ftn.pcc.exception;


public class BadRequestException extends RuntimeException {

    public BadRequestException() {}

    public BadRequestException(String message) {
        super(message);
    }
}
