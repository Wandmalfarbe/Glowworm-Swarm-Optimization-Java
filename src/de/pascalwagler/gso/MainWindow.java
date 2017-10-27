package de.pascalwagler.gso;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application {

	private static HostServices hostServices ;
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainWindow.fxml"));
		
		hostServices = getHostServices();
		Scene scene = new Scene(root);
	    
		stage.setTitle("Glowworm Swarm Optimization");
		stage.setScene(scene);
		stage.show();
	}
	
	public static HostServices getStaticHostServices() {
        return hostServices ;
    }
}
