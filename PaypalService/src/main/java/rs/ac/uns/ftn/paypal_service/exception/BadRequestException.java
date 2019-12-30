package rs.ac.uns.ftn.paypal_service.exception;


public class BadRequestException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2676274581452963186L;

	public BadRequestException() {}

    public BadRequestException(String message) {
        super(message);
    }
}
