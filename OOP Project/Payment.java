package phase1;

import java.io.Serializable;
/**
 * @author ME
 * @date 29/02/2020
 * @version 5.0
 *
 */
public class Payment implements Serializable {
	//Payment Class instance variables
	private String paymentType;
	private int paymentId;
	private String description;
	private double price;
	private Date paymentDate;
	
	//Payment constructor
	public Payment(String paymentType, int paymentId, String description, double price, Date paymentDate) {
		super();
		this.paymentType = paymentType;
		this.paymentId = paymentId;
		this.description = description;
		this.price = price;
		this.paymentDate = paymentDate;
	}//End of constructor

	//Getters and setters of all variables
	public String getPaymentType() {
		return paymentType;
	}
	
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public int getPaymentId() {
		return paymentId;
	}
	
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getPaymentDate() {
		return paymentDate;
	}
	
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}//End of getters and setters

	@Override
	//toString method
	public String toString() {
		return  paymentType + "\nPayment Id: " + 
				paymentId + "\nDescription: " + description
				+ "\nPayment Date: " + paymentDate;
	}//End of toString method
	
}//End of Payment Class