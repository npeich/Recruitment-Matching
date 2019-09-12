package Reader;

/*
 * this class creates sister objects
 * */


public class SIS extends GIRL{
	
	int pnmAssociated;
	int position;
	
	public SIS(int i, String f, String l, int pnm, int position) {
		super(i,f,l);
		pnmAssociated = pnm;
		this.position = position;
	}
	
	public int getPNM() {
		return pnmAssociated;
	}

	public int getRank() {
		return position;
	}
	
	public String returnAll() {
		return "";
	}
}