package rs.ac.uns.ftn.paypal_service.exception;

public class NotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -246363628768301824L;

	public NotFoundException() {}

    public NotFoundException(String message) {
        super(message);
    }
}
