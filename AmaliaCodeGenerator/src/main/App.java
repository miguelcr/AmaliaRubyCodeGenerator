package main;

import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ui.controllers.MainStageController;

public class App extends Application {
	private ResourceBundle rb;
	private Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;

		// Get the Resource Bundle file
		String language = "en"; // choose the language
		Locale locale = new Locale(language);
		rb = ResourceBundle.getBundle("bundles.Bundle", locale);

		gotoMainScene();
	}

	/**
	 * Opens the main Scene
	 */
	public void gotoMainScene() {
		try {
			stage = new Stage();
			stage.setTitle(rb.getString("application.title"));
			stage.centerOnScreen();
			// stage.setResizable(false);
			MainStageController mainStage = (MainStageController) replaceSceneContent("../ui/fxml/MainStage.fxml",
					stage);			
			mainStage.setUp(stage);
			stage.show();
			stage.getIcons().add(new Image("/main/icon_medium.png"));
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Replace the content of the stage with another scene
	 * 
	 * @param fxml The FXML file location
	 * @param stage Affected stage
	 * @return Return an Node that can be casted into the respective controller
	 * @throws Exception
	 */
	public Node replaceSceneContent(String fxml, Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		InputStream in = App.class.getResourceAsStream(fxml);
		loader.setBuilderFactory(new JavaFXBuilderFactory());
		loader.setLocation(App.class.getResource(fxml));
		loader.setResources(rb);

		Parent page;
		try {
			page = (Parent) loader.load(in);
		} finally {
			in.close();
		}

		// puts this FXML file in the selected stage
		stage.setScene(new Scene(page));

		return (Node) loader.getController();
	}

}