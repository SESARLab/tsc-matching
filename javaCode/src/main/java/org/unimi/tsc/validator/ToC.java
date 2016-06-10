package org.unimi.tsc.validator;



import java.io.File;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class ToC {
	private String layer;
	private String id;
	private String mechanism;
	private HashSet<String> events;
	
	public String getId() {
		return id;
	}
	public String getMechanism() {
		return mechanism;
	}
	public String getLayer() {
		return layer;
	}
	public HashSet<String> getEvents() {
		return events;
	}
	public ToC(String id, String mechanism,String layer, HashSet<String> events) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.layer=layer;
		this.mechanism=mechanism;
		this.events=events;
	}
	public static void main(String argv[]) throws Exception {
		TocFactory.getToCs("/Users/iridium/Documents/workspace/validator/ToCt.xml");
	}
	
	/**
	 * Compare this ToC with ToC i; Comparison over mechanism, layer and events
	 * @param i
	 * @return
	 */
	public boolean compare(ToC i) {
		if(!Mechanism.compareMechanism(this.mechanism,i.getMechanism()))
			return false;
		if(!this.layer.equalsIgnoreCase(i.getLayer()))
			return false;
		for(String e:this.events){
			boolean matched=false;
			String toRemove=null;
			//TODO da controllare toRemove
			for(String ie:i.getEvents()){
				if(e.equalsIgnoreCase(ie)){
					matched=true;
					toRemove=ie;
					break;
				}
				
			}
			if(!matched)
				return false;
		}
		return true;
	}
	public void addEvents(HashSet<String> events2) {
		this.events.addAll(events2);
		
	}
	
}
