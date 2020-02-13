package rs.ac.uns.ftn.authentication_service.exceptions;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {}

    public ForbiddenException(String message) {
        super(message);
    }

}
