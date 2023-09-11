package Phase2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import phase1.Visitor;
/**
 *@author Kawther
 *@date 20/04/2020
 * @version 10.0
 *
 */
public class UpdateTenantController {

    @FXML
    private TextField tenantIdText, nameText,phoneText, addressText, nationalityText, passportNoText;

    @FXML
    private DatePicker entryDatePicker, visaExpiryDatePicker;
    
    @FXML
    public void initialize() {
    	tenantIdText.getText();
    	nameText.getText();
    	phoneText.getText();
    	addressText.getText();
    	nationalityText.getText();
    	passportNoText.getText();
    	entryDatePicker.getValue();
    	visaExpiryDatePicker.getValue();
    }

	@FXML
    void handleCancel(ActionEvent event) {
    	Stage stage = (Stage) tenantIdText.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void handleOk(ActionEvent event) {
    	Visitor visitor = new Visitor();
		visitor.setTenantId(Integer.parseInt((tenantIdText.getText())));
		visitor.setName(nameText.getText());
		visitor.setPhone(phoneText.getText());
		visitor.setAddress(addressText.getText());
		visitor.setNationality(nationalityText.getText());
		visitor.setPassportNo(Integer.parseInt((passportNoText.getText())));
		visitor.setEntryDate((entryDatePicker.getValue()));
		visitor.setVisaExpiryDate((visaExpiryDatePicker.getValue()));
		
		System.out.println(visitor);
		handleCancel(null);
    }

}