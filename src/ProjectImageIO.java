import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.Queue;

import javax.imageio.ImageIO;

public class ProjectImageIO {
	
	public String imagePath, imageName;
	public int imageWidth, imageHeight;
	public BufferedImage image;

	public ProjectImageIO(String imagePath) {
		this.imagePath = imagePath;
		this.imageName = imagePath.substring(8, imagePath.length() - 4);
		openImage();
	}
	
	public void openImage() {
		try {
			long timeStart = System.nanoTime();
		    image = ImageIO.read(new File(this.imagePath));
		    
		    this.imageWidth = image.getWidth();
		    this.imageHeight = image.getHeight();
		    
		    System.out.println("width, height: " + this.imageWidth + ", " + this.imageHeight);
		    
		    double difference = (System.nanoTime() - timeStart) / 1000000000;
		    System.out.println("Time to load Image: " + difference);
		}catch(IOException e) {
			String workingDir = System.getProperty("user.dir");
		    System.out.println("Current working directory : " + workingDir);
		    e.printStackTrace();
		}
	}
	
	public int[] getImagePixelArray() {
		int[] pixelArray = new int[this.imageWidth * this.imageHeight];
	    
	    for(int row = 0; row < this.imageWidth; row++) {
	    	for(int col = 0; col < this.imageHeight; col++) {
	    		int pixel = image.getRGB(col, row);
	    		if(pixel < -1) {
//	    			System.out.print("1 ");
	    			pixelArray[(row * this.imageWidth) + col] = 0;
	    		}else {
//	    			System.out.print("0 ");
	    			pixelArray[(row * this.imageHeight) + col] = 255;
	    		}
	    		
	    	}
//	    	System.out.println("");
	    }
	    
	    
	    return pixelArray;
	}

	public int getImageWidth() {
		return this.imageWidth;
	}
	
	public int getImageHeight() {
		return this.imageHeight;
	}
	
	public void saveImage(Queue<Point> path) {
		int a = 255; 
        int r = 255; 
        int g = 0; 
        int b = 0; 
  
        //set the pixel value 
        int pValue = (a<<24) | (r<<16) | (g<<8) | b; 
        
        while(!path.isEmpty()) {
        	Point cur = path.remove();
        	Point next = path.peek();
        	
        	if(next == null) {
        		image.setRGB(cur.col, cur.row, pValue);
        		break;
        	}
        	
        	int hDif = next.col - cur.col;
        	int vDif = next.row - cur.row;
        	
        	if(hDif != 0) {
        		if(hDif < 0) {
        			while(hDif != 0) {
        				image.setRGB(cur.col + hDif + 1, cur.row, pValue);
        				hDif++;
        			}
        		}else if(hDif > 0) {
        			while(hDif != 0) {
        				image.setRGB(cur.col + hDif - 1, cur.row, pValue);
        				hDif--;
        			}
        		}
        	}else if(vDif != 0) {
        		if(vDif < 0) {
        			while(vDif != 0) {
        				image.setRGB(cur.col, cur.row + vDif + 1, pValue);
        				vDif++;
        			}
        		}else if(vDif > 0) {
        			while(vDif != 0) {
        				image.setRGB(cur.col, cur.row  + vDif - 1, pValue);
        				vDif--;
        			}
        		}
        	} 
        }
        
        
        System.out.println("Saving image");
        try {
        	File file = new File("./Results/" + imageName + "_Result.png");
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}
}
