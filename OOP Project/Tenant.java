package phase1;

import java.io.Serializable;
/**
 * @author nirvanamohd
 * @date 29/02/2020
 * @version 5.0
 * 
 *
 */
public class Tenant implements Serializable {
	//Tenant Class instance variables
	private int tenantId;
	private String name;
	private String phone;
	private String address;
	private String nationality;
	//Tenant constructor
	public Tenant(int tenantId, String name, String phone, String address, String nationality) {
		super();
		this.tenantId = tenantId;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.nationality = nationality;
	}//End of constructor
	
	public Tenant() {}

	//Getters and setters of all variables
	public int getTenantId() {
		return tenantId;
	}
	
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getNationality() {
		return nationality;
	}
	
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}//End getters and setters 
	
	@Override
	//toString method
	public String toString() {
		return "Tenant: " + tenantId + "\nTenant Name: " + name + 
				"\nPhone: " + phone + "\nAddress: " + address
				+ "\nNationality: " + nationality;
	}//End of toString method
}//End of Tenant Class
