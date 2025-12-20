import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);
		
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
	    for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                // קריאת שלושת מרכיבי הצבע לפי הסדר
                int r = in.readInt();
                int g = in.readInt();
                int b = in.readInt();             
                // יצירת אובייקט צבע והכנסתו למקום המתאים במערך
                image[i][j] = new Color(r, g, b);
            }
        }
        return image;
    }
    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
	        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                print(image[i][j]);
            }
			System.out.println();
    }
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		int w = image[0].length;
		int h = image.length;
		Color[][] flippedimg = new Color[h][w];
		for(int i=0; i<flippedimg.length; i++){
			for(int j=0; j<flippedimg[0].length; j++){
				flippedimg[i][j] = image[i][w-1-j];
		}
	}
		return flippedimg;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		int w = image[0].length;
		int h = image.length;
		Color[][] flippedVimg = new Color[h][w];
		for(int i=0; i<flippedVimg.length; i++){
			for(int j=0; j<flippedVimg[0].length; j++){
				flippedVimg[i][j] = image[h-1-i][j];
		}
	}
		return flippedVimg;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();
        int lum = (int) (0.299 * r + 0.587 * g + 0.114 * b);
        return new Color(lum, lum, lum);
    }
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		int w = image[0].length;
		int h = image.length;
		Color[][] grayScaled = new Color[h][w];
		for(int i=0; i<grayScaled.length; i++){
			for(int j=0; j<grayScaled[0].length; j++){
				grayScaled[i][j] = luminance(image[i][j]);
		}
	}
		return grayScaled;
	}
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		int h0= image.length;
		int w0= image[0].length;
		double propH = h0/height;
		double propW = w0/width;
		Color[][] scaled = new Color[height][width];
		for(int i=0; i<scaled.length; i++){
			for(int j=0; j<scaled[0].length; j++){
				int srcI = (int) (i * propH);
                int srcJ = (int) (j * propW);  
                // שמירה על גבולות המערך (למקרה של אי דיוקים זעירים)
                srcI = Math.min(srcI, h0 - 1);
                srcJ = Math.min(srcJ, w0 - 1);
                scaled[i][j] = image[srcI][srcJ]; 
	}
}
return scaled;
	}
	/**
     * Computes and returns a blended color which is a linear combination of the two given
     * colors. Each r, g, b, value v in the returned color is calculated using the formula 
     * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
     * values in the two input color.
     */
    public static Color blend(Color c1, Color c2, double alpha) {
        // חישוב הרכיב האדום
        double r = alpha * c1.getRed() + (1 - alpha) * c2.getRed();
        // חישוב הרכיב הירוק
        double g = alpha * c1.getGreen() + (1 - alpha) * c2.getGreen();
        // חישוב הרכיב הכחול
        double b = alpha * c1.getBlue() + (1 - alpha) * c2.getBlue();

        // יצירת צבע חדש (יש להמיר את הערכים ל-int)
        return new Color((int) r, (int) g, (int) b);
    }

    /**
     * Constructs and returns an image which is the blending of the two given images.
     * The blended image is the linear combination of (alpha) part of the first image
     * and (1 - alpha) part the second image.
     * The two images must have the same dimensions.
     */
    public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
        int rows = image1.length;
        int cols = image1[0].length;
        Color[][] newImage = new Color[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // שימוש בפונקציית העזר blend שהגדרנו למעלה עבור כל פיקסל
                newImage[i][j] = blend(image1[i][j], image2[i][j], alpha);
            }
        }
        return newImage;
    }

    /**
     * Morphs the source image into the target image, gradually, in n steps.
     * Animates the morphing process by displaying the morphed image in each step.
     * Before starting the process, scales the target image to the dimensions
     * of the source image.
     */
    public static void morph(Color[][] source, Color[][] target, int n) {
        // שלב 1: התאמת גודל תמונת המטרה לגודל תמונת המקור
        // הערה: מניח שפונקציית scaled קיימת במחלקה (חלק מהתרגיל)
        // אם היא לא קיימת, יש להוסיף אותה או לוודא שהתמונות מגיעות בגודל זהה
        if (source.length != target.length || source[0].length != target[0].length) {
            target = scaled(target, source[0].length, source.length);
        }

        // שלב 2: לולאת האנימציה
        for (int i = 0; i <= n; i++) {
            // חישוב ה-alpha: מתחיל ב-1 (מקור) ויורד ל-0 (מטרה)
            double alpha = (double) (n - i) / n;
            
            // יצירת התמונה המעורבבת לשלב הנוכחי
            Color[][] morphedImage = blend(source, target, alpha);
            
            // הצגת התמונה
            display(morphedImage);
            
            // השהייה של 500 מילישניות (חצי שנייה) כדי שניתן יהיה לראות את האנימציה
            StdDraw.pause(500); 
        }
    }
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

