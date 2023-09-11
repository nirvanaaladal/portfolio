package phase1;
/**
 * @author Kawther
 * @date 29/02/2020
 * @version 5.0
 *
 */
public class Resident extends Tenant{

	//Resident Class instance variables
	private int qatarId;
	private String bankName;
	private String bankAccountNo;

	//Resident constructor
	public Resident(int tenantId, String name, String phone, String address, String nationality, int qatarId,
			String bankName, String bankAccountNo) {
		super(tenantId, name, phone, address, nationality);
		this.qatarId = qatarId;
		this.bankName = bankName;
		this.bankAccountNo = bankAccountNo;
	}//End of constructor
	
	//Getters and setters of all variables
	public int getQatarId() {
		return qatarId;
	}
	
	public void setQatarId(int qatarId) {
		this.qatarId = qatarId;
	}
	
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getBankAccountNo() {
		return bankAccountNo;
	}
	
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}//End of getters and setters

	@Override
	//toString method
	public String toString() {
		return "Tenant info: " + super.toString()+ "\nQatar Id: " + qatarId 
				+ "\nBank Name: " + bankName + "\nBank Account Number: " 
				+ bankAccountNo;
	}//End of toString method
	
}//End of Resident Class