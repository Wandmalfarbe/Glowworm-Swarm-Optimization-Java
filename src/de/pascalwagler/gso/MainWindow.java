package de.pascalwagler.gso;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
		
		stage.getIcons().add(new Image("icons/icon_20x20.png"));
		stage.getIcons().add(new Image("icons/icon_20x20.png"));
		stage.getIcons().add(new Image("icons/icon_32x32.png"));
		stage.getIcons().add(new Image("icons/icon_40x40.png"));
		stage.getIcons().add(new Image("icons/icon_44x44.png"));
		stage.getIcons().add(new Image("icons/icon_64x64.png"));
		stage.getIcons().add(new Image("icons/icon_128x128.png"));
		stage.getIcons().add(new Image("icons/icon_256x256.png"));
		stage.getIcons().add(new Image("icons/icon_512x512.png"));
	    
		stage.setTitle("Glowworm Swarm Optimization");
		stage.setScene(scene);
		stage.show();
	}
	
	public static HostServices getStaticHostServices() {
        return hostServices ;
    }
}
