package Phase2;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import phase1.Date;
import phase1.House;
import phase1.HouseRental;
import phase1.Invoice;
import phase1.OOMRealState;
import phase1.Payment;
import phase1.Tenant;
/**
 * @author Kawther
 * @date 20/04/2020
 * @version 10.0
 *
 */
public class RentalViewController {

    @FXML
    private ComboBox<String> tenantComboBox, houseComboBox;

    @FXML
    private DatePicker startDatePicker, endDatePicker;

    @FXML
    private TableView<HouseRental> tableView;

    @FXML
    private TableColumn<HouseRental, LocalDate> rentalStartDateCol, rentalEndDateCol;

    @FXML
    private TableColumn<HouseRental, Double> depositeCol;

    @FXML
    private TableColumn<HouseRental, Invoice> invoiceNumberCol;

    @FXML
    private TableColumn<Invoice, Date> invoiceDateCol;

    @FXML
    private TableColumn<Payment, Double> totalCol;
    
    
    ObservableList<HouseRental> rentals = FXCollections.observableArrayList(OOMRealState.getHouseRentals());

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
    public void initialize() {
    	ObservableList<String> tenants = FXCollections.observableArrayList(OOMRealState.getTenant());
        ObservableList<String> houses = FXCollections.observableArrayList(OOMRealState.getHouse());
//        System.out.println(tenants);
//        System.out.println(houses);
    	tenantComboBox.setItems(tenants);
    	houseComboBox.setItems(houses);
    	rentalStartDateCol.setCellValueFactory(new PropertyValueFactory("startDate"));
    	rentalEndDateCol.setCellValueFactory(new PropertyValueFactory("endDate"));
    	depositeCol.setCellValueFactory(new PropertyValueFactory("deposite"));
    	invoiceNumberCol.setCellValueFactory(new PropertyValueFactory("invoiceNumber"));
    	invoiceDateCol.setCellValueFactory(new PropertyValueFactory("invoiceDate"));
    	totalCol.setCellValueFactory(new PropertyValueFactory("total"));
        System.out.println("Rentals :" +rentals);
    	tableView.setItems(rentals);
    }
    
    @FXML
    void handleRent(ActionEvent event) {
        HouseRental houseRental = new HouseRental();
        //TODO - get the house, get the rental and all the other stuff to fix the rent method
//        houseRental.setHouse(houseComboBox.getSelectionModel().getSelectedItem());
    	HouseRental [] rented = rentals.toArray(new HouseRental[rentals.size()]);
    	OOMRealState.saveHouseRental(rented);
    }
}
