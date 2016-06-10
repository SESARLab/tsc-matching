package org.unimi.utilities;

import java.io.File;

public class Utilities {

	
	public static boolean createDir(String dir){
		File theDir = new File(dir);
		boolean result = false;
		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    System.out.println("creating directory: " + dir);
		    

		    try{
		        theDir.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        result=false;
		    }        
		    if(result) {    
		        System.out.println("DIR created");  
		    }
		}
		return result;
	}
}
