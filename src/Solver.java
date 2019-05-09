import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.omg.CORBA.Current;

public class Solver {
	
	public static Point mazeStart = null;
	public static Point mazeEnd = null;
	
	public static int imageWidth;
	public static int imageHeight;

	public static ArrayList<Point> createdPoints;
	public static void main(String[] args) {
		ProjectImageIO ing = new ProjectImageIO("./Mazes/10k.png");
		int[] pixels = ing.getImagePixelArray();
		
		if(pixels == null) {
			System.out.println("Image wasn't loaded properly. Exiting...");
			System.exit(0);
		}
		
		createdPoints = new ArrayList<>();
		imageWidth = ing.getImageWidth();
		imageHeight = ing.getImageHeight();
		
		System.out.println(imageWidth + ", " + imageHeight);
		
		long timeStart = System.nanoTime();
		mazeCreation(pixels);
		double difference = (System.nanoTime() - timeStart) / 1000000000;
		
		System.out.println("Time to create maze: " + difference);
		System.out.println("# of Created points: " + createdPoints.size());
		
		
		
		Queue<Point> path = breadthFirstSearch();
		
		
		
//		while(!path.isEmpty()) {
//			System.out.println(path.remove());
//		}
		
		ing.saveImage(path);
		
	}

	private static Queue<Point> breadthFirstSearch() {
		long timeStart = System.nanoTime();
		
		Queue<Point> queue = new LinkedList<>();
		queue.add(mazeStart);
		
		Point[] prev = new Point[imageWidth * imageHeight];
		boolean[] visited = new boolean[imageHeight * imageWidth];
		visited[mazeStart.col] = true;
		
		while(!queue.isEmpty()) {
			Point current = queue.remove();
			if(current.isFinish) {
				break;
			}
			
			for(int i = 0; i < current.neighbors.length; i++) {
				Point n = current.neighbors[i];
				if(n != null) {
					int npos = n.row * imageWidth + n.col;
					
					if(visited[npos] == false) {
						queue.add(n);
						visited[npos] = true;
						prev[npos] = current;
						
					}
				}
			}
		}
		
		Queue<Point> path = new LinkedList<>();
		Point current = mazeEnd;
		
		while(current != null) {
			path.add(current);
			current = prev[current.row * imageWidth + current.col];
		}
		
		double difference = (System.nanoTime() - timeStart) / 1000000000;
		System.out.println("Breadth First Search: " + difference);
		
		return path;
		
	}

	private static void mazeCreation(int[] pixels) {
		
		Point lastLeft = null;
		
		Point[] lastAboveList = new Point[imageWidth];
		
		for(int i = 0; i < imageWidth; i++) {
			if(pixels[i] == 255) {
				Point p = new Point(0, i);
				p.isStart = true;
				mazeStart = p;
				lastAboveList[i] = p;
				createdPoints.add(p);
			}
		}
		
		for(int i = imageWidth; i < pixels.length; i++) {
			int row = i / imageHeight;
			int col = i % imageWidth;
			
			if(pixels[i] == 0) {
				lastAboveList[col] = null;
				lastLeft = null;
				
			}else if(pixels[i] != 0) {
				
				int leftPixel = pixels[i - 1];
				int rightPixel = pixels[i + 1];
				
				int upPixel = 0;
				int downPixel = 0;
				
				if(i > imageWidth) {
					upPixel = pixels[i - imageWidth];
				}
				if(i < (imageWidth * imageHeight) - imageWidth) {
					downPixel = pixels[i + imageWidth];
				}
				
				int hPassage = (leftPixel + rightPixel) / 255;
				int vPassage = (upPixel + downPixel) / 255;
				
				if(hPassage == 1 || vPassage == 1 || (hPassage + vPassage) == 4) {
					Point newPoint = new Point(row, col);
					
					if(lastLeft != null) {
						lastLeft.neighbors[1] = newPoint;
						newPoint.neighbors[0] = lastLeft;
					}
					
					if(lastAboveList[col] != null) {
						newPoint.neighbors[2] = lastAboveList[col];
						lastAboveList[col].neighbors[3] = newPoint;
					}
					
					if(row == imageHeight-1) {
						newPoint.isFinish = true;
						mazeEnd = newPoint;
					}
					
					lastLeft = newPoint;
					lastAboveList[col] = newPoint;
					createdPoints.add(newPoint);
				}
			}
			
			
		}
		
	}
	
	
	
	
}
