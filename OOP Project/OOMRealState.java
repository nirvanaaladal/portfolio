package phase1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ME
 * @author nirvanamohd
 * @author razanabuzaid
 * @author Kawther
 * @date 22/03/2020
 * @version 5.0
 *
 */
public class OOMRealState {

    //OOMRealState instance variables
    private static ArrayList<Tenant> tenants = new ArrayList<Tenant>();
    private static ArrayList<House> houses = new ArrayList<House>();
    private static ArrayList<HouseRental> houseRentals = new ArrayList<HouseRental>();
    private static ArrayList<Visitor> visitors = new ArrayList<Visitor>();


    //OOMRealState Constructor
    public OOMRealState(ArrayList<Tenant> tenants, ArrayList<House> houses, ArrayList<HouseRental> houseRentals) {
        this.tenants = tenants;
        OOMRealState.houses = houses;
        this.houseRentals = houseRentals;
    }//End of constructor

    //Empty constructor
    public OOMRealState() {
    }

    //Getters and Setters of the instance variables
    public static ArrayList<Tenant> getTenants() {
        loadTenant();
        return tenants;
    }

    public void setTenants(ArrayList<Tenant> tenants) {
        ArrayList<Tenant> tenant = new ArrayList<Tenant>();
        this.tenants = tenant;
    }


    public static List<Visitor> getVisitors() {

        List<Visitor> visitors = new ArrayList<Visitor>();
        ObjectInputStream in;
        System.out.println("Read from Object file");
        try {
            in = new ObjectInputStream(new FileInputStream("visitor.dat"));
            Object obj;

            visitors = Arrays.asList((Visitor[]) in.readObject());

            for (Visitor v : visitors)
                System.out.println(v);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return visitors;
    }

    public static void saveVisitors(Visitor[] visitors) {
        System.out.println("sava data method");

        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(new FileOutputStream("visitor.dat"));
            System.out.printf("%d houses loaded%n", visitors.length);
            for (Visitor v : visitors)
                System.out.println(v);
            out.writeObject(visitors);
            out.flush();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//    public static void saveHouse(House[] houses) {
//        System.out.println("sava data method house");
//        FileWriter outFile;
//        try {
//            outFile = new FileWriter("houseData.txt");
//            outFile.write("All Houses" + System.lineSeparator());
//            for (House t : houses) {
//                outFile.write(t + System.lineSeparator());
//            }
//            outFile.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public static void saveHouse(House[] houses) {
        System.out.println("sava data method");

        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(new FileOutputStream("houseData.dat"));
            System.out.printf("%d houses loaded%n", houses.length);
            for (House v : houses)
                System.out.println(v);
            out.writeObject(houses);
            out.flush();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//    public static void saveHouseRental(HouseRental[] houseRentals) {
//        // TODO Auto-generated method stub
//        System.out.println("sava data method house rental");
//        FileWriter outFile;
//        try {
//            outFile = new FileWriter("houseRentalData.txt");
//            outFile.write("All Houses" + System.lineSeparator());
//            for (HouseRental t : houseRentals) {
//                outFile.write(t + System.lineSeparator());
//            }
//            outFile.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public static void saveHouseRental(HouseRental[] houseRentals) {
        System.out.println("sava data method");

        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(new FileOutputStream("houseRentalData.dat"));
            System.out.printf("%d houses loaded%n", houseRentals.length);
            for (HouseRental v : houseRentals)
                System.out.println(v);
            out.writeObject(houseRentals);
            out.flush();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void saveVisitor(Visitor[] visitors) {
        // TODO Auto-generated method stub
        System.out.println("sava data method visitor");
        FileWriter outFile;
        try {
            outFile = new FileWriter("tenantData.txt");
            outFile.write("All Houses" + System.lineSeparator());
            System.out.println(visitors);
            for (Visitor v : visitors) {
                outFile.write(v + System.lineSeparator());
            }
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void setVisitors(ArrayList<Visitor> visitors) {
        this.visitors = visitors;
    }

    public static ArrayList<House> getHouses() {
        loadHouse();
        return houses;
    }

    public void setHouses(ArrayList<House> houses) {
        OOMRealState.houses = houses;
    }

    public static ArrayList<HouseRental> getHouseRentals() {
         loadHouseRentals();
        List<String> houseRental = new ArrayList<String>();
        for (HouseRental hR : houseRentals)
            if (!houseRental.contains(hR.getInvoice().getInvoiceNo()))
                houseRental.add(String.format("%d", hR.getInvoice().getInvoiceNo()));
        for (HouseRental hR : houseRentals)
            if (!houseRental.contains(hR.getInvoice().getInvoiceDate()))
                houseRental.add(String.format("%d", hR.getInvoice().getInvoiceDate()));
        return houseRentals;
    }

    public void setHouseRentals(ArrayList<HouseRental> houseRentals) {
        this.houseRentals = houseRentals;
    }//End of getters and setters

    //Add Tenant to Real State method
    public void addTenant(Tenant tenant) {
        tenants.add(tenant);
    }//End of Add Tenant Method

    //Find Tenant from ArrayList using tenant ID
    public Tenant findTenant(int tenantId) {

        //Validation (to make sure that the arrayList is not empty).
        if (tenants.size() == 0) {
            System.out.println("Error: Empty List");
        }

        for (Tenant t : tenants) {
            if (t.getTenantId() == tenantId) {
                return t;
            }
        }
        return null;

    }//End of Find Tenant method

    //Delete Tenant fromArrayList using tenant ID
    public void deleteTenant(int tenantId) {

        //Validation (to make sure that the arrayList is not empty).
        if (tenants.size() == 0) {
            System.out.println("Error: Empty List");
        }

        for (int i = 0; i < tenants.size(); i++) {
            if (tenants.get(i).getTenantId() == tenantId) {
                tenants.remove(tenants.get(i));
            }
        }
    }//End of Delete Tenant method

    //Add House method
    public void addHouse(House house) {
        houses.add(house);
    }//End of Add House method

    //Find House in an ArrayList using house number
    public House findHouse(int houseNo) {
        //Validation (to make sure that the arrayList is not empty).
        if (houses.size() == 0) {
            System.out.println("Error: Empty List");
        }

        for (House h : houses) {
            if (h.getHouseNo() == houseNo) {
                return h;
            }
        }
        return null;

    }//End of Find House method

    //Delete House from ArrayList using house number
    public void deleteHouse(int houseNo) {
        //Validation (to make sure that the arrayList is not empty).
        if (houses.size() == 0) {
            System.out.println("Error: Empty List");
        }

        for (int i = 0; i < houses.size(); i++) {
            if (houses.get(i).getHouseNo() == houseNo) {
                houses.remove(houses.get(i));
            }
        }
    }//End of Delete House method

    //Get available houses from ArrayList and returning it as a List
    public List<House> getHouseByAvailability(boolean available) {
        List<House> house = new ArrayList<House>();
        //Validation (to make sure that the arrayList is not empty).
        if (houses.size() == 0) {
            System.out.println("Error: Empty List");
        }

        for (House h : houses) {
            if (h.getAvailable() == available) {
                house.add(h);
            }
        }
        return house;

    }//End of Get available Houses method

    //Add House Rental method
    public void addHouseRental(HouseRental houseRental) {
        houseRentals.add(houseRental);
    }//End of Add House Rental method

    //Method to update the status of availability of House after a Tenant leaves it,
    public Invoice releaseHouse(int houseNo) {
        //Validation (to make sure that the arrayList is not empty).
        if (houseRentals.size() == 0) {
            System.out.println("Error: Empty List");
        }
        for (int i = 0; i < houseRentals.size(); i++) {
            if (houseRentals.get(i).getHouse().getHouseNo() == houseNo) {
                houseRentals.get(i).getHouse().setAvailable(true);
                return houseRentals.get(i).getInvoice();
            }
        }
        return null;

    }//End of Release House method

    //Method used to find House Rental belonging to a tenant using Tenant ID, and returning the results as a List
    public List<HouseRental> findHouseRentalByTenantId(int tenantId) {
        List<HouseRental> houseRenatal = new ArrayList<HouseRental>();
        //Validation (to make sure that the arrayList is not empty).
        if (houseRentals.size() == 0) {
            System.out.println("Error: Empty List");
        }
        for (HouseRental hr : houseRentals) {
            if (hr.getTenant().getTenantId() == tenantId) {
                houseRenatal.add(hr);
            }
        }
        return houseRenatal;

    }//End of Find House Rental by tenant ID method

    //Method to delete a House Rental using the tenant ID
    public void deleteHouseRental(int tenantId) {

        //Validation (to make sure that the arrayList is not empty).
        if (houseRentals.size() == 0) {
            System.out.println("Error: No Records!");
        }
        for (int i = 0; i < houseRentals.size(); i++) {
            if (houseRentals.get(i).getTenant().getTenantId() == tenantId) {
                houseRentals.remove(houseRentals.get(i));
            } else {
                System.out.println("Error: No Records For tenant #" + tenantId);
            }
        }
    }//End of Delete House Rental method


    //Method to get the available House by a specific date and return results as a List
    public List<House> getAvailableHousesByDate(Date date) {
        List<House> house = new ArrayList<House>();

        //Validation (to make sure that the arrayList is not empty).
        if (houseRentals.size() == 0) {
            System.out.println("Error: Empty List");
        }
        for (HouseRental hr : houseRentals) {
            if (hr.getRentalEndDate().getYear() <= date.getYear() && hr.getRentalEndDate().getMonth() <= date.getMonth() &&
                    hr.getRentalEndDate().getDay() <= date.getDay()) {
                house.add(hr.getHouse());
            }
        }
        return house;
    }//End of get Available Houses by date method

//    private static List<Tenant> tenantss;

    private static void loadTenant() {
        ObjectInputStream in;
        System.out.println("Read from Object file");
        try {
            in = new ObjectInputStream(new FileInputStream("visitor.dat"));
            tenants = new ArrayList<>( Arrays.asList((Tenant[]) in.readObject()));
            for (Tenant t : tenants)
                System.out.println(t);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//    private static List<House> housess;

    private static void loadHouse() {
        ObjectInputStream in;
        System.out.println("Read from Object file");
        try {
            in = new ObjectInputStream(new FileInputStream("houseData.dat"));
            houses = new ArrayList<>(Arrays.asList((House[]) in.readObject()));
            for (House h : houses)
                System.out.println(h);
        } catch (IOException e) {
            houses=new ArrayList<>();
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            houses=new ArrayList<>();
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//    private static List<HouseRental> houseRental;

    private static void loadHouseRentals() {
        ObjectInputStream in;
        System.out.println("Read from Object file");
        try {
            in = new ObjectInputStream(new FileInputStream("houseRentalData.dat"));
            houseRentals = new ArrayList<> (Arrays.asList((HouseRental[]) in.readObject()));
            for (HouseRental hR : houseRentals)
                System.out.println(hR);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static List<String> getTenant() {
         loadTenant();

        List<String> tenant = new ArrayList<String>();
        for (Tenant t : tenants)
            if (!tenant.contains(t.getName()))
                tenant.add(t.getName());
        return tenant;
    }

    @SuppressWarnings("unlikely-arg-type")
    public static List<String> getHouse() {
         loadHouse();
        List<String> house = new ArrayList<String>();
        for (House h : houses)
            if (!house.contains(h.getHouseNo()))
                house.add(String.format("%d", h.getHouseNo()));
        return house;
    }
	
	
	/*public static ArrayList<Integer> loadHouseNo(){
        ArrayList<Integer> houseNo =new ArrayList<Integer>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("file.txt"));
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split("        ");
                int houseNum = Integer.parseInt(data[0]);
                houseNo.add(houseNum);
                
            }

 

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return houseNo;

 

    }
    
    
    public static ArrayList<Integer> loadTanentId(){
        ArrayList<Integer> tenantId =new ArrayList<Integer>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("tenants.txt"));
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split("        ");
                int tenantID = Integer.parseInt(data[0]);
                tenantId.add(tenantID);
                
            }

 

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tenantId;

 

    }*/


    //saving data to the file
  /*  public void saveData(){
        FileWriter outFile;
        try {
            outFile = new FileWriter("file.txt");
            outFile.write("All Tenants"+System.lineSeparator());
           
            for(Tenant t: tenants) {
               
            outFile.write(t + System.lineSeparator());
            }
           
            outFile.write("All Houses"+System.lineSeparator());
           
            for(House t: houses) {
               
            	outFile.write(t + System.lineSeparator());
            }
           
            outFile.write("All RentalHouse"+System.lineSeparator());
           
            for(HouseRental hr: houseRentals) {
               
            	outFile.write(hr + System.lineSeparator());
            }
           
           
           
            outFile.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
  */  
    
	
	/*	private static List<OOMRealState> data;
	
	private static void loadData() {		
		System.out.println("load data method");

		ObjectInputStream in ;
		System.out.println("Read from Object file");
		try {
			in = new ObjectInputStream(new FileInputStream("data/countries.dat"));
			//List<Country> con =new ArrayList<Country>();
			data=  Arrays.asList((OOMRealState[])in.readObject());
			for (OOMRealState o:data)
				System.out.println(o);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/


}//End of OOMRealState Class