package Phase2;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * @author nirvanamohd
 * @date 20/04/2020
 * @version 10.0
 *
 */
public class MainViewController {

    @FXML
    private Pane rootPane;

    @FXML
    private Button tenants;

    @FXML
    private Button rentals;

    @FXML
    private Button houses;

    @FXML
    void displayHouses(ActionEvent event) {
    	
    	Stage stage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("MainHouseWindow.fxml"));
			stage.setScene(new Scene(root));
			stage.setTitle("MainHouseWindow.fxml");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


    } 

    @FXML
    void displayRentals(ActionEvent event) {
    	Stage stage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("RentalView.fxml"));
			stage.setScene(new Scene(root));
			stage.setTitle("RentalView.fxml");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

    }

    @FXML
    void displayTenants(ActionEvent event) {
    	
    	Stage stage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("MainTenant.fxml"));
			stage.setScene(new Scene(root));
			stage.setTitle("MainTenant.fxml");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

    }
    

    

}
