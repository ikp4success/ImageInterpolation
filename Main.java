package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

/**
 * Names/Authors: Immanuel I George , Graham Sullivan
 * Class: MTH311 (Numerical Analysis)
 * Final Project - Bi-Cubic Spline Image Interpolation
 * Objecttive - Generate Cubic Spline from one to two dimensions & Bi Cubic Interpolation algorithm
 * 
 * */


public class Main extends Application {
	// private Desktop desktop = Desktop.getDesktop();
	private ImageView imv = new ImageView();
	private Image image;
	
	String imageLoc;

	FileChooser fileChooser = new FileChooser();
	File selectedImage;

	Button Browse = new Button("Browse");

	Button Execute = new Button("Execute");
	
	TextField tf_X = new TextField();
	TextField tf_Y = new TextField();
	
	
	 ToggleGroup group = new ToggleGroup();
	RadioButton BICUBic = new RadioButton("ImageInterpolate");
	
	RadioButton CUBic = new RadioButton("Bi Cubic");
	

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Image Interpolation");
		try {
			
			// BorderPane root =
			// (BorderPane)FXMLLoader.load(getClass().getResource("img_interpolationframe.fxml"));
			//
			Pane pane = FXMLLoader.load(getClass().getResource(
					"img_interpolationframe.fxml"));
			BICUBic.setToggleGroup(group);
			BICUBic.setSelected(true);
			CUBic.setToggleGroup(group);

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
						imageLoc = selectedImage.getName();
						System.out.println(imageLoc);
						// image = new
						// Image(Main.class.getResourceAsStream(imageLoc));
						
						if (imageLoc.startsWith("file:")) {
							image = new Image(imageLoc);
							imv.setImage(image);
						} else {
							image = new Image(getClass().getResourceAsStream(
									imageLoc));
							imv.setImage(image);
						}

						

					}

					//
					// openFile(file);

				}
			});
			
			Execute.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					int Y = Integer.parseInt(tf_X.getText().toString());
					int X = Integer.parseInt(tf_Y.getText().toString());
					//if radio button selected , use BI-Cubical, cubic splines
					BufferedImage bimg = SwingFXUtils.fromFXImage(image, null);
					BicubicInterpolator bicubi = new BicubicInterpolator();
//					System.out.println(X +"" + Y);
					
					
					
					try {
						//convert2DTOImage(p, X, Y);
						if(CUBic.isSelected()){
							double p[][] = ConvertImage2darray(bimg,X,Y);
							double value_img = bicubi.getValue(p,X ,Y);
						imv.setImage(convert2DTOImage(p, X, Y));
//						System.out.println("IMAGE LOC-"+convert2DTOImage(p, X, Y));
						}else if(BICUBic.isSelected()){
							ImageInterpolation imgI = new ImageInterpolation();
							if (imageLoc.startsWith("file:")) {
								Image imgIII = new Image(imgI.ImageInterpolation(selectedImage.getAbsolutePath(),X,Y));
								//imv.setImage(imgIII);
							} else {
//								System.out.println("IMAGE LOC-"+imgI.ImageInterpolation(imageLoc,X,Y));
								
								 BufferedImage newSource = ImageIO.read(new File(imgI.ImageInterpolation(selectedImage.getAbsolutePath(),X,Y)));
								Image newSourceImage=SwingFXUtils.toFXImage(newSource, null);
								//imv.setImage(newSourceImage);
//								Image imgIII = new Image(getClass().getResourceAsStream(
//										imgI.ImageInterpolation(selectedImage.getAbsolutePath(),X,Y)));
								popupWindow(primaryStage,X,Y,newSourceImage);
								
							}
							
							
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static double[][] ConvertImage2darray(BufferedImage image,int X,int Y){
		 double[][] arr = new double[X][Y];
		 
		for(int i = 0; i < X; i++){
		    for(int j = 0; j < Y; j++){
		        arr[i][j] = image.getRGB(i, j);
		       
		    }
		}
		return arr;
	}
	
	public WritableImage convert2DTOImage(double[][] cImg,int X, int Y) throws IOException{
//		BufferedImage convimg = new BufferedImage(X, Y, 3);
		WritableImage fConimg = null;
//		 for(int y=0;y<cImg.length;y++){
//		  for(int x=0;x<cImg[y].length;x++){
//		       int Pixel=(int)cImg[x][y]<<16 | (int)cImg[x][y] << 8 | (int)cImg[x][y];
//		       convimg.setRGB(x, y,Pixel);
//		   }
//
//		 }
//		int xLenght = cImg.length;
//		int yLength = cImg[0].length;
//		BufferedImage convimg = new BufferedImage(X, Y, 3);
//
//		for(int x = 0; x < xLenght; x++) {
//		    for(int y = 0; yLength < Y; y++) {
//		        int rgb = (int)cImg[x][y]<<16 | (int)cImg[x][y] << 8 | (int)cImg[x][y];
//		        convimg.setRGB(x, y, rgb);
//		    }
//		}
//		ImageIO.write(convimg, "Doublearray", new File("Doublearray.png"));
		
		//String path = "res/world/PNGLevel_" + "NAME" + ".png";
	    BufferedImage convimg = new BufferedImage(X, Y, BufferedImage.TYPE_INT_RGB);
	    for (int x = 0; x < 200; x++) {
	        for (int y = 0; y < 200; y++) {
	        	convimg.setRGB(x, y, (int)cImg[x][y]);
	        }
	    }

	    File ImageFile = new File(imageLoc);
	    try {
	        ImageIO.write(convimg, "png", ImageFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		fConimg=SwingFXUtils.toFXImage(convimg, null);
		return fConimg;
	}
	
	public void popupWindow(Stage primaryStage,int X,int Y,Image newSourceImg){
		 final Stage dialog = new Stage();
		 ImageView imv = new ImageView();
		 imv.setLayoutX(X);
		 imv.setLayoutY(Y);
         dialog.initModality(Modality.APPLICATION_MODAL);
         dialog.initOwner(primaryStage);
         VBox dialogVbox = new VBox(20);
         dialogVbox.getChildren().add(imv);
         
         Scene dialogScene = new Scene(dialogVbox, X+10, Y+10);
         dialog.setScene(dialogScene);
         dialog.show();
         imv.setImage(newSourceImg);
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
