package phase1;

import java.io.Serializable;
/**
 * @author nirvanamohd
 * @date 29/02/2020
 * @version 5.0
 *
 */
public class HouseRental implements Serializable {
	//HouseRental instance variables
	private Tenant tenant;

	public HouseRental() {
	}

	private House house;
	private Date rentalStartDate;
	private Date rentalEndDate;
	private double deposit;
	private Invoice invoice;

	//HouseRental constructor
	public HouseRental(Tenant tenant, House house, Date rentalStartDate, Date rentalEndDate, double deposit,
			Invoice invoice) {
		this.tenant = tenant;
		this.house = house;
		this.rentalStartDate = rentalStartDate;
		this.rentalEndDate = rentalEndDate;
		this.deposit = deposit;
		this.invoice = invoice;
	}//End of constructor

	//Getters and setters of all variables
	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public Date getRentalStartDate() {
		return rentalStartDate;
	}

	public void setRentalStartDate(Date rentalStartDate) {
		this.rentalStartDate = rentalStartDate;
	}

	public Date getRentalEndDate() {
		return rentalEndDate;
	}

	public void setRentalEndDate(Date rentalEndDate) {
		this.rentalEndDate = rentalEndDate;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}//End of getters and setters

	@Override
	//toString method
	public String toString() {
		return "House Tenant: " + tenant + "\nHouse: " + house +
				"\nStarting Rental Date: " + rentalStartDate
				+ "\nEnding Rental Date: " + rentalEndDate + 
				"\nDeposit: " + deposit + "\nInvoice: " + invoice.toString();
	}//End of toString
	
}//End of HouseRental Class