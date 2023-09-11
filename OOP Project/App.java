package phase1;

import java.util.ArrayList;

import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Kawther
 * @author razanabuzaid
 * @date 22/03/2020
 * @version 5.0
 *
 */

public class App  {
	public static void main(String[] args) {

		OOMRealState state= new OOMRealState();

		// Add Tenants.
		Tenant tenant1= new Resident(1,"Mariam","12212340","Al-Waab","Egyptian",2342342,"AlRayyan","324234");
		state.addTenant(tenant1);
		Tenant tenant2= new Resident(2,"Razan","12212345","Al-Azyzia","Sudanese",5675675,"QNB","234324");
		state.addTenant(tenant2);
		Tenant tenant3= new Resident(3,"Nirvana","12212350","Al-Sad","Syrian",8918918,"QIB","657567");
		state.addTenant(tenant3);
		Tenant tenant4= new Resident(4,"Kawther","12212355","Duhail","Canadian",3453453,"QIIB","567657");
		state.addTenant(tenant4); //4 Tenants been added to the Real State.

		// Find Tenant and Display results
		System.out.println("=========================");
		System.out.println("   Finding tenant 1	");
		System.out.println("=========================\n");
		System.out.println(state.findTenant(1).toString());
		// End of finding the tenant

		// Delete Tenant 
		System.out.println("\n=========================");
		System.out.println("   Deleting tenant 1	");
		System.out.println("=========================");
		state.deleteTenant(1);
		if(state.findTenant(1)==null) {
			System.out.println("Succesfully Deleted!");
		}// End of deleting tenant

		// Add House 	
		House house1= new House(1,"Al-waab",true, new HouseType(1,"1 Bedroom",3000.0));	
		House house2= new House(2,"Duhail",false, new HouseType(2,"2 Bedroom",5000.0));	
		House house3= new House(3,"Al-Saad",true, new HouseType(3,"3 Bedroom",8000.0));
		state.addHouse(house1);
		state.addHouse(house2);
		state.addHouse(house3);
		// End of adding houses

		// Find House 
		System.out.println("\n=========================");
		System.out.println("   Finding House 1	");
		System.out.println("=========================\n");
		System.out.println(state.findHouse(1).toString());


		// Delete House
		System.out.println("\n=========================");
		System.out.println("   Deleting House 1	");
		System.out.println("=========================");
		state.deleteHouse(1);
		System.out.println("Succesfully Deleted!");
		// End of deleting house

		// Get available Houses
		List<House> houses= state.getHouseByAvailability(true);
		for(House h: houses) {
			System.out.println("\n============================");
			System.out.println(" Getting House availability	");
			System.out.println("============================\n");
			System.out.println(h.toString());
		}

		// Create Payment object 
		Payment payment1= new Payment("Pre-payment",1,"Cash",5000.0,new Date(21,03,2020));

		// ArrayList of payments
		ArrayList<Payment> payments= new ArrayList<Payment>();

		// Add the payments object to the arrayList
		payments.add(payment1);

		// Create an Invoice 
		Invoice invoice= new Invoice(1,new Date(21,03,2020),payments);

		// Create House Rental
		HouseRental houseRental1= new HouseRental(tenant2,house2,new Date(21,03,2020),new Date(21,03,2022),5000.0,invoice);

		// Add house rental
		state.addHouseRental(houseRental1);

		// Finding house rental using tenant Id
		List<HouseRental> rentals= state.findHouseRentalByTenantId(2);
		for(HouseRental h: rentals) {
			System.out.println("\n=== Razan's information: ===\n"+h.toString());
		}

		// Releasing house
		System.out.println("\n=== Release information: ===\n");
		System.out.println(state.releaseHouse(2).toString());

		// state.deleteHouseRental(2);
		List<House> houses1= state.getAvailableHousesByDate(new Date(21,04,2022));
		for(House h: houses1) {
			System.out.println("\n====================================");
			System.out.println(" Getting Available Houses by Date");
			System.out.println("====================================\n");
			System.out.println("Houses: "+h.toString());
		}


	}



}//End of App. class