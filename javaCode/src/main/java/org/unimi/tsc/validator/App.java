package org.unimi.tsc.validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.unimi.baseXClient.BaseXClient;






//import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	//, "/Users/iridium/Documents/workspace/validator/ToCt.xml", "/Users/iridium/Documents/workspace/validator/graphT2.xml", "/Users/iridium/Documents/workspace/validator/evidenceTemplate.xml",""
    	System.out.println("PROPERTY VALIDATION:"+Property.validatyChecherProperty("property-d8-b0-d7-b0-d6-b5-d5-b1","property-d8-b0-d7-b0-d6-b5-d5-b1"));
    	//Graph graph ;
    	//InputStream in;
    	//GraphMLReader.inputGraph(graph, in);
    	try {
    		ModelEvidenceValidator mev=new ModelEvidenceValidator(new GraphValidator("/Users/iridium/Documents/workspace/validator/nuovoGrafo.xml","n59"),new  EvidenceValidator("/Users/iridium/Documents/workspace/validator/nuovoEvidence.xml"));
    		System.out.println("RISULTATO MODEL+EVIDENCE:"+mev.validateEmp1("/Users/iridium/Documents/workspace/validator/graphT2.xml", "n0","/Users/iridium/Documents/workspace/validator/evidenceTemplate.xml"));
    		/*GraphValidator validator = new GraphValidator("/Users/iridium/Documents/workspace/validator/nuovoGrafo.xml","n69");
    		//GraphValidator validator2 = new GraphValidator("/Users/iridium/Documents/workspace/validator/graphT.xml","n0");
    		//boolean app=false;
    		boolean appg=validator.compareModel("/Users/iridium/Documents/workspace/validator/graphT2.xml", "n0");
			System.out.println("RISULTATO MODELLO:"+appg);
			
			
			EvidenceValidator ev= new  EvidenceValidator("/Users/iridium/Documents/workspace/validator/nuovoEvidence.xml");
			boolean appe=ev.CompareEvidences("/Users/iridium/Documents/workspace/validator/evidenceTemplate.xml");
			System.out.println("RISULTATO EVIDENZE:"+appe);*/
			 
			ToCValidator tc=new ToCValidator("/Users/iridium/Documents/workspace/validator/nuovoToc.xml");
			boolean appt= tc.compareTocs("/Users/iridium/Documents/workspace/validator/ToCt.xml");
			System.out.println("RISULTATO TOC:"+appt);
			
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
 

}





