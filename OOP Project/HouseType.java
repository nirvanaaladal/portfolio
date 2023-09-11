package phase1;

import java.io.Serializable;
/**
 *@author razanabuzaid
 *@date 29/02/2020
 * @version 5.0
 *
 */
public class HouseType implements Serializable {
	//HouseType instance variables.
	private int typeId;
	private String description;
	private double housePrice;
	
	//HouseType Constructor.
	public HouseType(int typeId, String description, double housePrice) {
		this.typeId = typeId;
		this.description = description;
		this.housePrice = housePrice;
	}//End of constructor
	
	public HouseType() {
		// TODO Auto-generated constructor stub
	}

	//Setters and getters of the instance variables.
	public int getTypeId() {
		return typeId;
	}
	
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getHousePrice() {
		return housePrice;
	}
	
	public void setHousePrice(double housePrice) {
		this.housePrice = housePrice;
		
	}//End of setters and getters.
	
	@Override
	//toString method
	public String toString() {
		return "HouseType: " + typeId + "\nDescription: " +
				description + "\nHousePrice: " + housePrice;
	}//End of toString 
	
}//End of HouseType Class