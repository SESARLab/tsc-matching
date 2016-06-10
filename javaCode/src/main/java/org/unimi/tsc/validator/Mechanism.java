package org.unimi.tsc.validator;

import java.util.ArrayList;

public class Mechanism {

	public static boolean compareMechanism(String tMechanism, String iMechanism) {
		BaseXOntologyManager app = new BaseXOntologyManager(BasexFactory.getHost(), 1984, "admin", "admin", "mechanism");
		//BaseXOntologyManager app=BasexFactory.getBasex();
		ArrayList<String> result2;
		boolean t=true;
		//if(t)
			//return true;
		//System.out.println("COMPARING:"+tMechanism+" WITH "+iMechanism);
    	try {
			result2 = app.getSubClasses("/", tMechanism, false);
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("Template M:"+tMechanism+" not compatible with Instance M:"+iMechanism);
			// TODO Auto-generated catch block
			return false;
		}
    	boolean a;
    	for(String toprint:result2){
    		
    		a=toprint.equalsIgnoreCase(iMechanism);
    		//System.out.println("TO PRINT"+toprint);
    		if(toprint.equalsIgnoreCase(iMechanism))
    			return true;
    	}
    	//System.out.println("Template M:"+tMechanism+" not compatible with Instance M:"+iMechanism);
    	return false;
	}
	public static void main( String[] args )
    {
		System.out.println(compareMechanism("mechanism-d4-b0","mechanism-d4-b0"));
    }
}
