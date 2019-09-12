package Reader;

/*
 * this class creates the frame work for everyone on the lists
 * also sets up compare
 * */

public class GIRL implements Comparable<GIRL>{
	protected String first, last;
	protected int ID;
	public GIRL(int i, String f, String l) {
		ID = i;
		first = f;
		last = l;
	}
	public int getID() {
		return ID;
	}
	public String getFirst() {
		return first;
	}
	public String getLast() {
		return last;
	}
	public String getFull() {
		return first+last;
	}
	
	public String getAllInfo() {
		return "" + ID + " " + first + last;
	}
	
	public int compareTo(GIRL g) {
	      return getAllInfo().compareTo(g.getAllInfo());
	}
	
	public String stitch(PNM p, SIS s, int rank) {
		return "";
	}
}