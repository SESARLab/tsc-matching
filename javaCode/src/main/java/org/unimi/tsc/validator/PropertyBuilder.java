package org.unimi.tsc.validator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class PropertyBuilder {

	
	public static void main( String[] args )
	{
		try {
			//PropertyBuilder.buildline();
			propertyFromTemplate("property-d8-b0-d7-b0");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static public void buildline() throws FileNotFoundException, IOException{
		String line;
		Set<String> key=new HashSet<String>();
		try (
		    InputStream fis = new FileInputStream("/Users/iridium/Downloads/property.txt");
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    BufferedReader br = new BufferedReader(isr);
		) {
		    while ((line = br.readLine()) != null) {
		        key.add(line.split(":")[0]);
		        
		    }
		    for(String a: key){
		    	System.out.println(a);
		    }
		}
	}

	static public String propertyFromTemplate(String p){
		BaseXOntologyManager app = new BaseXOntologyManager(BasexFactory.getHost(), 1984, "admin", "admin", "property");
		//BaseXOntologyManager app=BasexFactory.getBasex();
		ArrayList<String> result2=null;
		try {
			result2 = app.getSubClasses("/", p, false);
			Random randomG = new Random();
			String r=result2.get(randomG.nextInt(result2.size()));
			System.out.println("PROPERTY:"+r);
			return r;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR");
			return null;
		}
		
		
	}
	public static Property createProperty() {
		// TODO Auto-generated method stub
		return new Property();
	}
	

}


