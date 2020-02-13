package rs.ac.uns.ftn.authentication_service.exceptions;

@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {

    public BadRequestException() {}

    public BadRequestException(String message) {
        super(message);
    }
}

