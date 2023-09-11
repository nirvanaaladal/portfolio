package phase1;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author nirvanamohd
 * @date 29/02/2020
 * @version 5.0
 *
 */
public class Visitor extends Tenant implements Serializable {
	
	
	//Visitor instance variables
	private int passportNo;
	private LocalDate entryDate;
	private LocalDate visaExpiryDate;

	
	
	//Visitor constructor
	public Visitor(int tenantId, String name, String phone, String address, String nationality, int passportNo,
			LocalDate entryDate, LocalDate visaExpiryDate) {
		super(tenantId, name, phone, address, nationality);
		this.passportNo = passportNo;
		this.entryDate = entryDate;
		this.visaExpiryDate = visaExpiryDate;
	}//End of constructor

	
	public Visitor() {
		// TODO Auto-generated constructor stub
	}


	//Getters and setters of all variables
	public int getPassportNo() {
		return passportNo;
	}
	
	public void setPassportNo(int passportNo) {
		this.passportNo = passportNo;
	}
	
	public LocalDate getEntryDate() {
		return entryDate;
	}
	
	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}
	
	public LocalDate getVisaExpiryDate() {
		return visaExpiryDate;
	}
	
	public void setVisaExpiryDate(LocalDate visaExpiryDate) {
		this.visaExpiryDate = visaExpiryDate;
	}//End of getters and setters

	@Override
	//toString method
	public String toString() {
		return "Visitor: " + passportNo + 
				"\nEntry Date: " + entryDate + "\nVisa Expiry Date: "
				+ visaExpiryDate;
	}//End of toString method
	
}//End of Visitor Class