package rs.ac.uns.ftn.scientific_center.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {}

    public NotFoundException(String message) {
        super(message);
    }
}
