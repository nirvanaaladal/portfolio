package Phase2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.time.LocalDate;

import Phase2.AddTenantController.AddTenantInteraction;
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
import phase1.OOMRealState;
import phase1.Visitor;
/**
 * @author ME
 * @date 20/04/2020
 * @version 10.0
 *
 */
public class MainTenantController implements AddTenantInteraction{

	@FXML
	private Button addButton,updateButton,deleteButton,saveButton;

	 @FXML
	    private TableView<Visitor> tenantTable;

	    @FXML
	    private TableColumn<Visitor, Integer> idColumn;

	    @FXML
	    private TableColumn<Visitor, String> nameColumn, phoneColumn, addressColumn, nationalityColumn;

	    @FXML
	    private TableColumn<Visitor, Integer> passportNoColumn;

	    @FXML
	    private TableColumn<Visitor, LocalDate> entryDateColumn, expireDateColumn;
	
	    ObservableList<Visitor> visitors = FXCollections.observableArrayList(OOMRealState.getVisitors());


	@FXML 
	void initialize() {
		idColumn.setCellValueFactory(new PropertyValueFactory("tenantId"));
    	nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
    	phoneColumn.setCellValueFactory(new PropertyValueFactory("phone"));
    	 addressColumn.setCellValueFactory(new PropertyValueFactory("address"));
    	 nationalityColumn.setCellValueFactory(new PropertyValueFactory("nationality"));
    	 passportNoColumn.setCellValueFactory(new PropertyValueFactory("passportNo"));
    	 entryDateColumn.setCellValueFactory(new PropertyValueFactory("entryDate"));
    	 expireDateColumn.setCellValueFactory(new PropertyValueFactory("visaExpiryDate"));
    	 
		tenantTable.setItems(visitors);
	}
	@FXML
	void handleAdd(ActionEvent event) {
		Stage stage = new Stage();
		Parent root;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AddTenant.fxml"));
		try {
			root = loader.load();
			AddTenantController controller = loader.getController();
			controller.setInteraction(this);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

    @FXML
    void handleUpdate(ActionEvent event) {
  		int index = tenantTable.getSelectionModel().getSelectedIndex();

		Stage stage = new Stage();
		Parent root;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AddTenant.fxml"));
		try {
			root = loader.load();
			AddTenantController controller = loader.getController();
			controller.setInteraction(this);
		    controller.setFromValues(visitors.get(index));//
		    visitors.remove(index);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	 @FXML
	    void handleDelete(ActionEvent event) {
	    	int index = tenantTable.getSelectionModel().getSelectedIndex();
			visitors.remove(index);}
	 @FXML
	    void handleSave(ActionEvent event) {
	    	Visitor[] visitorArray = visitors.toArray( new Visitor[visitors.size()]);
			OOMRealState.saveVisitors(visitorArray);
	    }
	 @Override
		public void saveTenant(Visitor visitor) {
			// TODO Auto-generated method stub
			visitors.add(visitor);
		 System.out.println(visitors);
		} 	
	 
	
}
