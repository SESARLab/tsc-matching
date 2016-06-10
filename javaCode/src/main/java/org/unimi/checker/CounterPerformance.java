package org.unimi.checker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CounterPerformance {
	//static final int deepController=5;
	//static final String file="/Users/iridium/Downloads/TEST_PAPER_esaustiva/deep-5/eu1.txt";
	public static void main(String[] args) throws Exception {
		//long a=new Long("1448787981744527").longValue();
		
		//String file="/Users/iridium/Downloads/TEST_PAPER_esaustiva/deep-3/eu1.txt";
		int[] array={3,5,10,12,15};
		for(int k=0;k<array.length;k++){
		int deepController=array[k];
		for(int j=0;j<=0;j++){
		String dir="/Users/iridium/Downloads/TEST_PAPER_esaustiva/deep-"+deepController+"/";
		String file="/Users/iridium/Downloads/TEST_PAPER_esaustiva/cm5-9esaustiva.txt";
    	String buffer[]=null;
    	long init=0;
    	long minit=0;
    	long mend=0;
    	long tinit=0;
    	long tend=0;
    	long einit=0;
    	long eend=0;
    	ArrayList<String> cm5 = new ArrayList<String>() ;
    	ArrayList<String> cm6 = new ArrayList<String>() ;
    	ArrayList<String> cm7 = new ArrayList<String>() ;
    	ArrayList<String> cm8 = new ArrayList<String>() ;
    	ArrayList<String> cm9 = new ArrayList<String>() ;
    	ArrayList<String> cm10 = new ArrayList<String>() ;
    	ArrayList<String> app=null;
    	
    	int deep=-1;
    	int cm=-1;
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	
		    	
		    	if(line.startsWith("ANALISYS:")){
		    		buffer=line.split("/");
		    		init=1;
		    		
		    	}
		    	  
		    	if ( ( buffer != null ) && ( init == 1 ) ) {
		    		deep=new Integer(buffer[5].split("-")[1]).intValue();
		    		cm=new Integer(buffer[6].split("-")[1]).intValue();
		    		//app.add(deep+" "+cm);
		    		if(deep==deepController){
		    			if(cm==5)
		    				app=cm5;
		    			if(cm==6)
		    				app=cm6;
		    			if(cm==7)
		    				app=cm7;
		    			if(cm==8)
		    				app=cm8;
		    			if(cm==9)
		    				app=cm9;
		    			if(cm==10)
		    				app=cm10;
		    			
		    			
		    			init=2;
		    		}
		    	}
		    	if(init==2){
//		    		INIT MODEL:1448787966075491
//		    		END MODEL:1448787966138343
//		    		INIT EVIDENCE:1448787966141151
//		    		END EVIDENCE:1448787966603085
		    		if(line.startsWith("INIT MODEL:")){
		    			minit=new Long(line.split(":")[1]).longValue();
		    		}
		    		if(line.startsWith("END MODEL:")){
		    			mend=new Long(line.split(":")[1]).longValue();
		    			app.add("cm-"+cm+" m:"+(mend-minit));
		    		}
		    		if(line.startsWith("INIT EVIDENCE:")){
		    			einit=new Long(line.split(":")[1]).longValue();
		    		}
		    		if(line.startsWith("END EVIDENCE:")){
		    			eend=new Long(line.split(":")[1]).longValue();
		    			app.add("cm-"+cm+" e:"+(eend-einit));
		    		}
//		    		TIMETOC:INIT TOC:1448787966603213
//		    		END TOC:1448787966630877
		    		if(line.startsWith("TIMETOC:INIT TOC:")){
		    			tinit=new Long(line.split(":")[2]).longValue();
		    			
		    		}
		    		
		    		if(line.startsWith("END TOC:")){
		    			tend=new Long(line.split(":")[1]).longValue();
		    			app.add("cm-"+cm+" t:"+(tend-tinit));
		    			
		    		}
		    		
		    	}
		    	
		    }
		    // line is not visible here.
		}
		System.out.println(file);
		printArrayList(cm5,5,dir,j);
		printArrayList(cm6,6,dir,j);
		printArrayList(cm7,7,dir,j);
		printArrayList(cm8,8,dir,j);
		printArrayList(cm9,9,dir,j);
		printArrayList(cm10,10,dir,j);
		}
		}	
	}
	private static void printArrayList(ArrayList<String> app, int i,String dir,int type) throws Exception {
		System.out.println("\n\n CM-"+i);
		FileWriter rootwriter = new FileWriter(dir+"esaustiva"+type+"detailed-cm"+i+".txt",false);
        
		for(String toPrint: app){
			rootwriter.write(toPrint+"\n");
	        
		}
		rootwriter.close();
		
	}

}
