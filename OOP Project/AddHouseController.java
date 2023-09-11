package Phase2;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import phase1.House;
import phase1.HouseType;

/**
 *@author razanabuzaid
 *@date 20/04/2020
 * @version 10.0
 *
 */

public class AddHouseController {

	@FXML
	private TextField houseNoTF,addressTF,typeIdTF,priceTF;

	@FXML
	private RadioButton yesRB,noRB;

	@FXML
	private TextArea descriptionTA;

	@FXML
	private Button addButton,cancelButton;
	

		AddHouseInteraction interaction;
		public void setInteraction(AddHouseInteraction interaction) {
			this.interaction = interaction;
		}

		@FXML
		void handleCancel(ActionEvent event) {
			Stage stage = (Stage) houseNoTF.getScene().getWindow();
			stage.close();
		}

		@FXML
		void handleAdd(ActionEvent event) {
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
			interaction.saveHouse(house);
			System.out.println(house);
			handleCancel(null);
		}

		public void setFromValues(House house) {
			houseNoTF.setText(String.format("%d", house.getHouseNo()));
			addressTF.setText(house.getAddress());
			typeIdTF.setText(String.format("%d",house.getType().getTypeId()));
			//availability
		//	yesRB.setText(house.getAvailable());
		//	noRB.setText(house.getAvailable());
			priceTF.setText(String.format("%.2f",house.getPrice()));
			descriptionTA.setText(house.getDescription());
			
		}

		public interface AddHouseInteraction{
			public void saveHouse(House house);
		}
	


}