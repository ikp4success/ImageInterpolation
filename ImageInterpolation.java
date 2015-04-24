package application;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.Scanner;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;


/**
 * Names/Authors: Immanuel I George , Graham Sullivan
 * Class: MTH311 (Numerical Analysis)
 * Final Project - Bi-Cubic Spline Image Interpolation
 * Objective - Generate Cubic Spline from one to two dimensions & Bi Cubic Interpolation algorithm
 * 
 * */


public class ImageInterpolation {
	public ImageInterpolation() {
		super();
	}
    
   public String ImageInterpolation(String imageLoc,int X, int Y) {
    	
    	File outFile =null;
	try {
//	    Scanner input = new Scanner(System.in);
//	    System.out.print("Please type a file path: ");
//	    String filePath = input.next();
//	    input.close();
		
	    BufferedImage source = ImageIO.read(new File(imageLoc));
	    
	    // variables that determine what size the new image will be
//	    int destWidth = 1440;
//	    int destHeight = 900;
	    int destWidth = X;
	    int destHeight = Y;
	    
	    // x and y proportions from original to new size
	    double xProportion = Integer.valueOf(destWidth).doubleValue()
		    / Integer.valueOf(source.getWidth()).doubleValue();
	    double yProportion = Integer.valueOf(destHeight).doubleValue()
		    / Integer.valueOf(source.getHeight()).doubleValue();
	    
	    GraphicsConfiguration graphicsConfig = GraphicsEnvironment
		    .getLocalGraphicsEnvironment().getDefaultScreenDevice()
		    .getDefaultConfiguration();
	    BufferedImage buffImage = graphicsConfig.createCompatibleImage(destWidth, destHeight,
		    source.getColorModel().getTransparency());
	    
	    // perform the interpolation
	    Graphics2D newImage = null;
	    newImage = buffImage.createGraphics();
	    newImage.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	    AffineTransform at = AffineTransform.getScaleInstance(xProportion, yProportion);
	    newImage.drawRenderedImage(source, at);
	    newImage.dispose();
	    
	    // Write the new image to file - need to change this to display in
	    // gui instead
	    Iterator iter = ImageIO.getImageWritersByFormatName("JPG");
	    if (iter.hasNext()) {
		ImageWriter writer = (ImageWriter) iter.next();
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwp.setCompressionQuality(0.95f);
		
		outFile = new File("output_1.jpg");
		FileImageOutputStream output = new FileImageOutputStream(outFile);
		writer.setOutput(output);
		IIOImage image = new IIOImage(buffImage, null, null);
		writer.write(null, image, iwp);
		output.close();
	    }
	} catch (Throwable e) {
	    e.printStackTrace();
	}
	return outFile.getAbsolutePath();
    }
    
}
