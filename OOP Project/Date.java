package phase1;
/**
 * @author ME
 * @author nirvanamohd
 * @author razanabuzaid
 * @author Kawther
 * @date 29/02/2020
 * @version 5.0
 *
 */
public class Date {
	//Date instance variables
	private int day;
	private int month;
	private int year;
	
	//Empty constructor
	public Date() {}
	
	//Date constructor
	public Date(int day, int month, int year) {
		super();
		this.day = day;
		this.month = month;
		this.year = year;
	}//End of constructor

	//Getters and setters of instance variables
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}//End of Getters and setters 

	@Override
	//toString method
	public String toString() {
		return String.format("%02d/%02d/%d", day, month, year);
	}//End of toString
	
}//End of Date Class