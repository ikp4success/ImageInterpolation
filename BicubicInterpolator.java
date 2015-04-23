package application;


public class BicubicInterpolator extends CubicInterpolator
{
	private double[] arr = new double[4];

	public double getValue (double[][] p, double x, double y) {
		System.out.println(p);
		arr[0] = getValue(p[0], y);
		arr[1] = getValue(p[1], y);
		arr[2] = getValue(p[2], y);
		arr[3] = getValue(p[3], y);
		return getValue(arr, x);
	}
	
	
//	public static BufferedImage toBufferedImage(Image img)
//	{
//	    if (img instanceof BufferedImage)
//	    {
//	        return (BufferedImage) img;
//	    }
//
//	    // Create a buffered image with transparency
//	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
//
//	    // Draw the image on to the buffered image
//	    Graphics2D bGr = bimage.createGraphics();
//	    bGr.drawImage(img, 0, 0, null);
//	    bGr.dispose();
//
//	    // Return the buffered image
//	    return bimage;
//	}
}
