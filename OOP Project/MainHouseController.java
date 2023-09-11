package Phase2;

import java.io.IOException;

import Phase2.AddHouseController.AddHouseInteraction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import phase1.House;
import phase1.HouseType;
import phase1.OOMRealState;
/**
 * @author nirvanamohd
 * @date 20/04/2020
 * @version 10.0
 *
 */
public class MainHouseController implements AddHouseInteraction {

    	@FXML
    	private Button addButton,updateButton,deleteButton,saveButton;

    	@FXML
		private TableView<House> houseTable;

    	@FXML
    	private TableColumn<House, Integer> houseCol;

    	@FXML
    	private TableColumn<House, String> addressCol;

    	@FXML
    	private TableColumn<House, String> descriptionCol;

    	@FXML
    	private TableColumn<House, Double> priceCol;

    	@FXML
    	private TableColumn<House, Boolean> availabilityCol;

        ObservableList<House> houses = FXCollections.observableArrayList(OOMRealState.getHouses());

    	@FXML 
    	void initialize() {
    		houseCol.setCellValueFactory(new PropertyValueFactory("houseNo"));
    		addressCol.setCellValueFactory(new PropertyValueFactory("address"));
    		descriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
    		priceCol.setCellValueFactory(new PropertyValueFactory("price"));
    		availabilityCol.setCellValueFactory(new PropertyValueFactory("available"));
    		
    		houseTable.setItems(houses);
    	}
    	@FXML
    	void handleAdd(ActionEvent event) {
    		Stage stage = new Stage();
    		Parent root;
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("AddHouse.fxml"));
    		try {
    			root = loader.load();
    			AddHouseController controller = loader.getController();
    			controller.setInteraction(this);
    			stage.setScene(new Scene(root));
    			stage.show();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}

    	@FXML
    	void handleDelete(ActionEvent event) {
    		int index = houseTable.getSelectionModel().getSelectedIndex();
    		houses.remove(index);
    	}

    	@FXML
    	void handleSave(ActionEvent event) {
    		House[] housesArray = houses.toArray( new House[houses.size()]);
    		OOMRealState.saveHouse(housesArray);
    	}

    	@FXML
    	void handleUpdate(ActionEvent event) {
    		int index = houseTable.getSelectionModel().getSelectedIndex();

    		Stage stage = new Stage();
    		Parent root;
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("AddHouse.fxml"));
    		try {
    			root = loader.load();
    			AddHouseController controller = loader.getController();
    			controller.setInteraction(this);
    		    controller.setFromValues(houses.get(index));//
    		    houses.remove(index);
    			stage.setScene(new Scene(root));
    			stage.show();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

    		
    	}
    	


		@Override
		public void saveHouse(House house) {
			// TODO Auto-generated method stub
    		houses.add(house);


		}
    


}