package phase1;

import java.beans.Transient;
import java.io.Serializable;
/**
 * @author razanabuzaid
 * @date 29/02/2020
 * @version 5.0
 *
 */
public class House implements Serializable {
	//House instance variables
	private int houseNo;
	private String address;
	private boolean available;
	private HouseType type;

	//Empty constructor
	public House() {}

	//House constructor
	public House(int houseNo, String address, boolean isAvailable, HouseType type) {
		this.houseNo = houseNo;
		this.address = address;
		this.available = isAvailable;
		this.type = type;
	}//End of constructor.

	//Getters and setters of the instance variables
	public int getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(int houseNo) {
		this.houseNo = houseNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean isAvailable) {
		this.available = isAvailable;
	}

	public HouseType getType() {
		return type;
	}

	public void setType(HouseType type) {
		this.type = type;
	}//End of getters and setters
	
	
	@Override
	//toString method
	public String toString() {
		return "House Number: "+ houseNo + "\nHouse Address: "+ 
				address+ "\nAvailable: " + available + "\nHouse Type: "
				+ type;
		
	}//End of toString
	
	@Transient
	public double getPrice() {
		return type.getHousePrice();
	}
	@Transient
	public String getDescription() {
		return type.getDescription();
	}
}//End of House Class