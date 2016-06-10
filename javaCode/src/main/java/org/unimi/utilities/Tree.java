package org.unimi.utilities;

import java.util.ArrayList;

	public class Tree {
	    ArrayList<Tree> node;
	    String value;
	    public Tree(String value){
	    	this.value=value;
	    	this.node=new ArrayList<Tree>();
	    }
	    public void addChild(Tree t){
	    	this.node.add(t);
	    }
	    public boolean hasSon(String v){
	        boolean found=false;
	    	for(Tree t:node){
	    		if(t.getValue().equalsIgnoreCase(v))
	    			return true;
	    		else{
	    			found=t.hasSon(v);
	    			if(found==true)
	    				return true;
	    		}
	    	
	    	}
	    	return false;
	    }
		public ArrayList<Tree> getNode() {
			return node;
		}
		public void setNode(ArrayList<Tree> node) {
			this.node = node;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		
	    
	}

