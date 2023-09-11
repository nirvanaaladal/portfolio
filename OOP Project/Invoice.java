package phase1;

import java.io.Serializable;
import java.util.ArrayList;
/**
 *@author ME
 *@author nirvanamohd
 *@date 21/03/2020
 * @version 5.0
 */
public class Invoice implements Serializable {
	//Invoice instance variables.
	private int invoiceNo;
	private Date invoiceDate;
	ArrayList<Payment> payments = new ArrayList<Payment>();
	
	//Invoice constructor.
	public Invoice(int invoiceNo, Date invoiceDate, ArrayList<Payment> payments) {
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.payments = payments;
		
	}//End of constructor.
	
	//Getters and setters of the instance variables.
	public int getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public ArrayList<Payment> getPayments() {
		return payments;
	}

	public void setPayments(ArrayList<Payment> payments) {
		this.payments = payments;
	}//End of getters and setters
	
	//Method to modify the Payment list using the Payment ID.
	public void modifyPayment(Payment payment) {
		
		//Validation (to make sure that the arrayList is not empty).
		if(payments.size()==0) {
			System.out.println("Error: Empty List");
		}
		for (int i=0; i<payments.size();i++) {
			if(payments.get(i).getPaymentId()==payment.getPaymentId()) {

				payments.get(i).setDescription(payment.getDescription());
				payments.get(i).setPaymentDate(payment.getPaymentDate());
				payments.get(i).setPaymentType(payment.getPaymentType());
				payments.get(i).setPrice(payment.getPrice());
			}
		}
	}//End of Modify payment method.
	
	//Method to delete a Payment from the PaymentList using Payment ID.
	public String deletePayment(int paymentId) {
		
		//Validation (to make sure that the arrayList is not empty).
		if(payments.size()==0) {
			return "Error: Empty List";
		}
		for (int i=0; i<payments.size();i++) {
			if(payments.get(i).getPaymentId()==paymentId) {
				payments.remove(i);
				return "Payment record has been deleted";
			}
		}
		//Error message that will appears when payment ID is not found.
		return "Error: Id cannot be found!";
		
	}//End of Delete Payment method.

	//Method to add new payment.
	public void addPayment(Payment payment) {
		payments.add(payment);
		
	}//End of Add Payment method

	
	//Method to get the PaymentList.
	public Payment getPayment(int paymentId) {
		
		// Validation (to make sure that the arrayList is not empty)
		if(payments.size()==0) {
			System.out.println("Error: Empty List");
		}
		for(Payment i: payments) {
			if(i.getPaymentId()==paymentId) {
				return i;
			}
		}
		return null;
		
	}//End of Get PaymentList method.

	//Method to calculate the total price in the PaymentList
	public double calculateTotalPayment() {
		double total=0;
		for(Payment i:payments) {
			total+=i.getPrice();
		}
		return total;	
		
	}//End of Calculate total payment method

	@Override
	//toString method
	public String toString() {
		return "Invoice: " + invoiceNo + "\nInvoice Date: " + 
				invoiceDate + "\nPayments: " + payments;
		
	}//End of toString
	
}//End of Invoice Class