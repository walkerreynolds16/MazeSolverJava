import java.util.Arrays;

public class Point {

	public int row, col;
	public boolean isStart, isFinish;
	public Point[] neighbors;
	
	public Point(int row, int col) {
		this.row = row;
		this.col = col;
		
		this.isStart = false;
		this.isFinish = false;
		
		this.neighbors = new Point[] {null, null, null, null};
	}

	@Override
	public String toString() {
		String res = "[" + row + ", " + col + "] ->";
		
		if(this.neighbors[0] != null) {
			res += " Left: " + this.neighbors[0].getPointLocationString() + ",";
		}else {
			res += " Left: " + "Null,";
		}
		
		if(this.neighbors[1] != null) {
			res += " Right: " + this.neighbors[1].getPointLocationString() + ",";
		}else {
			res += " Right: " + "Null,";
		}
		
		if(this.neighbors[2] != null) {
			res += " Up: " + this.neighbors[2].getPointLocationString() + ",";
		}else {
			res += " Up: " + "Null,";
		}
		
		if(this.neighbors[3] != null) {
			res += " Down: " + this.neighbors[3].getPointLocationString() + ",";
		}else {
			res += " Down: " + "Null,";
		}
		
		
		return res;
	}
	
	public String getPointLocationString() {
		return "[" + row + ", " + col + "]";
	}
	
	
}
