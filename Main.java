package application;
/**
* NAMES: Immmanuel George, Graham Sullivan
* CLASS: MTH 323
* Final Project- Image Interpolation
*/
import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Main extends Application {
	// private Desktop desktop = Desktop.getDesktop();
	private ImageView imv = new ImageView();
	private Image image;

	FileChooser fileChooser = new FileChooser();
	File selectedImage;

	Button Browse = new Button("Browse");

	Button Execute = new Button("Execute");
	
	TextField tf_X = new TextField();
	TextField tf_Y = new TextField();
	
	RadioButton BICUBic = new RadioButton("Cubic Spline");
	RadioButton CUBic = new RadioButton("Bi Cubic ");

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Image Interpolation");
		try {
			// BorderPane root =
			// (BorderPane)FXMLLoader.load(getClass().getResource("img_interpolationframe.fxml"));
			//
			Pane pane = FXMLLoader.load(getClass().getResource(
					"img_interpolationframe.fxml"));

			Browse.setLayoutX(5);
			Browse.setLayoutY(2);
			
			Execute.setLayoutX(495);
			Execute.setLayoutY(353);
			
			imv.setLayoutX(38);
			imv.setLayoutY(34);
			imv.setFitHeight(266);
			imv.setFitWidth(526);
			imv.setPickOnBounds(true);
			imv.setPreserveRatio(true);
			
			tf_X.setLayoutX(314);
			tf_X.setLayoutY(353);
			tf_Y.setLayoutX(83);
			tf_Y.setLayoutY(353);
			
			BICUBic.setLayoutX(52);
			BICUBic.setLayoutY(323);
			BICUBic.setMnemonicParsing(false);
			CUBic.setLayoutX(300);
			CUBic.setLayoutY(323);
			CUBic.setMnemonicParsing(false);

			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().addAll(
			// new ExtensionFilter("Text Files", "*.txt"),
					new ExtensionFilter("Image Files", "*.png", "*.jpg",
							"*.gif"),
					// new ExtensionFilter("Audio Files", "*.wav", "*.mp3",
					// "*.aac"),
					new ExtensionFilter("All Files", "*.*"));

			pane.getChildren().addAll(Browse, Execute, imv,tf_X,tf_Y,BICUBic,CUBic);
			Scene scene = new Scene(pane, 600, 400);

			scene.getStylesheets().add(
					getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

			Browse.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					selectedImage = fileChooser.showOpenDialog(primaryStage);
					if (selectedImage != null) {
						String imageLoc = selectedImage.getName();
						System.out.println(imageLoc);
						// image = new
						// Image(Main.class.getResourceAsStream(imageLoc));
						
						if (imageLoc.startsWith("file:")) {
							image = new Image(imageLoc);
						} else {
							image = new Image(getClass().getResourceAsStream(
									imageLoc));
						}

						imv.setImage(image);

					}

					//
					// openFile(file);

				}
			});
			
			Execute.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					//if redia button selected , use BI Cubical, cubic splines
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	// private String openFile(File file, Stage primaryStage) {
	// String imageLoc ="";
	// try {
	// //desktop.open(file);
	// if (selectedImage != null) {
	// primaryStage.display(selectedImage);
	// }
	// imageLoc=file.getAbsolutePath();
	// } catch (IOException ex) {
	// Logger.getLogger(FileChooser.class.getName()).log(Level.SEVERE,
	// null, ex);
	// }
	// return imageLoc;
	// }

}
