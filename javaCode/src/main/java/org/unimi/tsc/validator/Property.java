package org.unimi.tsc.validator;

import java.util.ArrayList;

public class Property {

	
	public static Boolean validatyChecherProperty(String pTemplate,String pInstance){
    	BaseXOntologyManager app = new BaseXOntologyManager(BasexFactory.getHost(), 1984, "admin", "admin", "property");
		//BaseXOntologyManager app=BasexFactory.getBasex();
		ArrayList<String> result2;
		try {
			result2 = app.getSubClasses("/", pTemplate, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
    	for(String toprint:result2){
    		if(toprint.equalsIgnoreCase(pInstance))
    			return true;
    	}
    	
    	return false;
    }
}
