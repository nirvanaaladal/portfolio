package Phase2;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import phase1.Visitor;
/**
 * @author ME
 * @date 20/04/2020
 * @version 10.0
 *
 */
public class AddTenantController {

    @FXML
    private TextField tenantIdTF,nameTF, phoneTF, addressTF, nationalityTF, passportNoTF;

    @FXML
    private DatePicker entryDateDP, visaExpiryDateDP;
    
    AddTenantInteraction interaction;
	public void setInteraction(AddTenantInteraction interaction) {
		this.interaction = interaction;
	}


    @FXML
    void handleCancel(ActionEvent event) {
    	Stage stage = (Stage) tenantIdTF.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void handleOk(ActionEvent event) {
    	Visitor visitor = new Visitor();
    	Visitor type = new Visitor(Integer.valueOf(tenantIdTF.getText()), nameTF.getText(), phoneTF.getText(), addressTF.getText(),
    			nationalityTF.getText(),Integer.valueOf(passportNoTF.getText()),entryDateDP.getValue(),visaExpiryDateDP.getValue());
    	
    
    	type.setTenantId(Integer.parseInt(tenantIdTF.getText()));
    	type.setName(nameTF.getText());
    	type.setPhone(phoneTF.getText());
    	type.setAddress(addressTF.getText());
    	type.setNationality(nationalityTF.getText());
    	type.setPassportNo(Integer.parseInt(passportNoTF.getText()));
    	type.setEntryDate(entryDateDP.getValue());
    	type.setVisaExpiryDate(visaExpiryDateDP.getValue());
    	interaction.saveTenant(type);
		System.out.println(type);
    	handleCancel(null);
    	
    }
    public void setFromValues(Visitor visitor) {
    	tenantIdTF.setText(String.format("%d", visitor.getTenantId()));
    	nameTF.setText(visitor.getName());
    	phoneTF.setText(visitor.getPhone());
    	addressTF.setText(visitor.getAddress());
		nationalityTF.setText(visitor.getNationality());
		passportNoTF.setText(String.format("%d",visitor.getPassportNo()));
		entryDateDP.setValue(visitor.getEntryDate());
		visaExpiryDateDP.setValue(visitor.getVisaExpiryDate());

	}

    
    public interface AddTenantInteraction{
    	
		public void saveTenant(Visitor visitor);
		
	}


}
