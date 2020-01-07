package rs.ac.uns.ftn.paypal_service.exception;

public class PayPalException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2412029048252037678L;

	public PayPalException() {}

    public PayPalException(String message) {
        super(message);
    }
}
