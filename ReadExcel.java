package Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.PrintWriter;

/*
 * This program reads who sisters want to talk to on the preference round of rush and
 * transforms the data to export the potential new members (PNMs) who are requested.
 * Each PNM mentioned has a list of sisters who want to talk to them and also includes a number (#)
 * indicating where on the sister's list they fell.  The lower the number, the more the sister likes the PNM
 * and would like to be matched with her.
 * 
 * Written by Natalie Eichorn, PC17
 * Data Analysist 2019, Alpha Xi Delta Zeta Xi
*/


public class ReadExcel {
	private String inputFile;
	
	//map with PNMs as the key and a list of matched sisters as the value	
	public TreeMap<Integer, ArrayList<String>> match = new TreeMap<>();
	
	//list of sisters who want to talk to a PNM
	public ArrayList<String> rankedSis = new ArrayList<>();
	
	//list of all pnms who are wanted by sisters
	public ArrayList<PNM> pnms = new ArrayList<>();

	
    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

//------------------------------------------------------------------------------------------------------------
    //----reads in excel file---------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------
    
    public void read() throws IOException  {

        File inputWorkbook = new File(inputFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            
            //set up for reading sheet
            Sheet sheet = w.getSheet(0);
            
            //change to make minimum list length
            int row = 1200;
            
            //reads through excel sheet to pull data
            for (int r = 0; r < row; r++) {
            	//gets sister info
            	Cell cell1 = sheet.getCell(0, r);
            	Cell cell2 = sheet.getCell(1, r);
            	Cell cell3 = sheet.getCell(2, r);
            	

            	//full name of sister
            	String sister = cell2.getContents().toUpperCase() + " " + cell3.getContents().toUpperCase();

            	
            	//formatting for while loop
            	int p = 3;
            	int counter = 0;
            	int rank = 1;
            	
            	
            	//recognizes the end of the doc
            	if (cell1.getContents().equals(">>>")) {
        			System.out.println(printMap());
        			break;
        		} else {
        			
        			while (counter < 10) { //sets limit at 10 bc the form only has max 10 spots to fill out
            		
            		//checks for end of row
            		if(sheet.getCell(p,r).getContents().equals("")) {
            			break;
            		}
            		
            		//sets up PNM
            		int id = Integer.parseInt(sheet.getCell(p, r).getContents());
            		String first = sheet.getCell(p + 1, r).getContents().toUpperCase();
            		String last = sheet.getCell(p + 2, r).getContents().toUpperCase();
            		
            		PNM girl = new PNM(id, first, last, sister, 0);
            		girl.setPosition(rank);
            		
            		//System.out.println("pnm = " + girl.getFullName());
            		
            		pnms.add(girl);

            		//adds sisters with attached rank to PNM's list
            		String rSis = sister + " (" + rank + ")";
            		rankedSis.add(rSis);
            		
            		
            		//beginning of map build
            		if (!match.containsKey(girl.getID())) {
            			ArrayList<String> hmm = new ArrayList<>();
            			hmm.add(rSis);
            			match.put(girl.getID(), hmm);
            		}
            		else if(match.containsKey(girl.getID())) {
            			match.get(girl.getID()).add(rSis);
            			//System.out.println("updated list = " + Arrays.toString(match.get(girl.getID()).toArray()));
            		}

            		//sets up list for next data entry
            		rankedSis.clear();
            		p = p+4;
            		counter++;
            		rank++;
        			}
        		}
            }
                        
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

//--------------------------------------------------------------------------------------------------------------
    //----formats map to be printed/exported--------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------
   
    public String printMap() {
    	String export = "";
    	
    	for(Map.Entry<Integer, ArrayList<String>> entry : match.entrySet()) {
    		
    		int key = entry.getKey();
    		ArrayList<String> value = entry.getValue();
    		
    		export += "PNM " + key + ",--,";
    		
    		for (PNM p : pnms) {
    			if (p.getID() == key) {
    				export += p.getFullName() + ",";
    			}
    		}
    		
    		for (String name : value) {
    			export += name + ",";
    		}
    		export += "\n";
    		
    	}
    	return export;
    }
    
    public void exportMap() {
    	String fileName = "/Users/natalieeichorn/Desktop/axid/prefMatching/RUSH_CRUSH_LIST.txt";
    	try {
			PrintWriter outputStream = new PrintWriter(fileName);
			outputStream.println(printMap());
			outputStream.close();
			System.out.println("DONE :)");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

//------------------------------------------------------------------------------------------------------------
    //----main function---------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------
   
    public static void main(String[] args) throws IOException {
    	System.out.println("*****THIS SHOWS THE PNM MATCHED WITH THE SISTERS WHO WOULD LIKE TO PREF THEM*****");
    	System.out.println("**THE NUMBER IN () SHOW THE PRIORITY THAT THE SISTER RANKED THE PNM**");
    	System.out.println("----------------------------------------------------------------------------");
    	
        ReadExcel test = new ReadExcel();
        //ENTER FILE NAME WITH PATHWAY HERE!
        test.setInputFile("/Users/natalieeichorn/Desktop/axid/prefMatching/RC1.xls");
        test.read();  
        test.exportMap();
    }
}
