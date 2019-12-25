package rs.ac.uns.ftn.bank.exception;


public class BadRequestException extends RuntimeException {

    public BadRequestException() {}

    public BadRequestException(String message) {
        super(message);
    }
}
