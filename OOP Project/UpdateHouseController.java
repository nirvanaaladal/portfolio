package Phase2;

import java.io.IOException;

import Phase2.AddHouseController.AddHouseInteraction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import phase1.House;
import phase1.HouseType;
/**
 * @author razanabuzaid
 * @date 20/04/2020
 * @version 10.0
 *
 */
public class UpdateHouseController {

    @FXML
    private TextField houseNoTF;

    @FXML
    private RadioButton yesRB;

    @FXML
    private RadioButton noRB;

    @FXML
    private TextField addressTF;

    @FXML
    private TextField typeIdTF;

    @FXML
    private TextArea descriptionTA;

    @FXML
    private TextField priceTF;

    @FXML
    private Button updateButton;

    @FXML
    private Button cancelButton;
    
    @FXML
    public void initialize() {
    	houseNoTF.getText();
    	yesRB.getText();
    	noRB.getText();
    	addressTF.getText();
    	typeIdTF.getText();
    	descriptionTA.getText();
    	priceTF.getText();
    	
    }

    @FXML
    void handleUpdate(ActionEvent event) {
		House house = new House();
		HouseType type = new HouseType(Integer.valueOf(typeIdTF.getText()),descriptionTA.getText(),Double.valueOf(priceTF.getText()));
		house.setHouseNo(Integer.parseInt(houseNoTF.getText()));
		house.setAddress(addressTF.getText());
		house.setType(type);
		
		if(yesRB.isSelected()){
			house.setAvailable(true);;
		}
		else {
			house.setAvailable(false);
		}
		//interaction.saveHouse(house);
		System.out.println(house);
		handleCancel(null);
    }

    @FXML
    void handleCancel(ActionEvent event) {
    	Stage stage = (Stage) houseNoTF.getScene().getWindow();
    	stage.close();
    }

    

}
