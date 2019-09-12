package Reader;

/*
 * this class creates Potential New Member objects
 * */


public class PNM extends GIRL{
	
	int position;
	String sisterAssociated;
	
	public PNM(int i, String f, String l, String sis, int p) {
		super(i,f,l);
		position = p;
		sisterAssociated = sis;
	}
	
	public String getFullName() {
		return first + " " + last;
	}

	public String getFullInfo() {
		return first + " " + last + " (" + super.ID + ") -- " + sisterAssociated + " ranked her = " + position;
	}
	public void setPosition(int p) {
		position = p;
	}
	
}