package org.unimi.tsc.validator;

import java.util.HashSet;

public class ToCValidator {
	HashSet<ToC> instance;
	
	public ToCValidator(String tocFile){
		instance=TocFactory.getToCs(tocFile);
	}
	
	public boolean compareTocs(String templateToCFile){
		HashSet<ToC> template;
		HashSet<ToC> instanceApp = instance;
		template=TocFactory.getToCs(templateToCFile);
		for(ToC t:template){
			boolean found=false;
			for(ToC i:instance){
				
				if(t.compare(i)){
					found=true;
					break;
				}
				
			}
			if(!found)
				return false;
			
				
		}
		/*if(instanceApp.size()==0)
		return true;
		else
		return false;*/
		return true;
		
	}
	public static void main(String argv[]) throws Exception {
		ToCValidator tc = new ToCValidator("/Users/iridium/Documents/workspace/validator/ToCi.xml");
		System.out.println(tc.compareTocs("/Users/iridium/Documents/workspace/validator/ToCt.xml"));
	}
}
