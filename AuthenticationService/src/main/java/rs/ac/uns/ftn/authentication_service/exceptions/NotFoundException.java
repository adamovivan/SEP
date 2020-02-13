package rs.ac.uns.ftn.authentication_service.exceptions;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {

    public NotFoundException() {}

    public NotFoundException(String message) {
        super(message);
    }

}
