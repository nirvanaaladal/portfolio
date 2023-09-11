package Phase2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *  @author razanabuzaid
 *   @date 20/04/2020
 * @version 10.0
 *
 */
public class App2 extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml")); 
		stage.setScene(new Scene(root));
		stage.setTitle("MainView.fxml");
		stage.show();
	}

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		launch(args);

	}//End of App. class

}
