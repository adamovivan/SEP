package rs.ac.uns.ftn.paypal_api.model;

public class Order {

	private double price;
	private String intent;
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getIntent() {
		return intent;
	}
	public void setIntent(String intent) {
		this.intent = intent;
	}

}
