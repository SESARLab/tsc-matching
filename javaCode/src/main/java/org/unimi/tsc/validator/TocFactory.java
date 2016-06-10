package org.unimi.tsc.validator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;


public class TocFactory {
	
	
	
	/**
	 * @param path file con lo schema dei ToC
	 * @return tutti gli elementi ToC presenti nel file path
	 */
	public static HashSet<ToC> getToCs(String path) {
		HashSet<ToC> tocs=new HashSet<ToC>();
		try {
			
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
		 
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList nList = doc.getElementsByTagName("ToC");
		 
			//System.out.println("----------------------------");
		 
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 
				Node nNode = nList.item(temp);
		 
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
					HashSet<String> events=new HashSet<String>();
					for(int i=0;i<eElement.getElementsByTagName("event").getLength();i++){
						events.add(eElement.getElementsByTagName("event").item(i).getTextContent());
					}
					ToC toc=new ToC(eElement.getAttribute("id"),eElement.getElementsByTagName("mechanism").item(0).getTextContent(),eElement.getElementsByTagName("service").item(0).getTextContent(),events);
					tocs.add(toc);
		 
				}
			}
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		return tocs;
	}
	
	static public String tocFromTemplate(String m){
		BaseXOntologyManager app = new BaseXOntologyManager(BasexFactory.getHost(), 1984, "admin", "admin", "mechanism");
		//BaseXOntologyManager app=BasexFactory.getBasex();
		ArrayList<String> result2=null;
		try {
			result2 = app.getSubClasses("/", m, false);
			Random randomG = new Random();
			return result2.get(randomG.nextInt(result2.size())); 
		} catch (Exception e) {
			System.out.println("ERRORE:"+e.getMessage());
			// TODO Auto-generated catch block
			return null;
		}
		
	}

	
	

	public static void writeToCfilefromTemplate(String tocT, String tocI,
			String graphT,
			ArrayList<ArrayList<Vertex>> paths, HashMap<String, String> nodeMapped) throws IOException {
		    ArrayList<ArrayList<Vertex>> classicPath=new GraphValidator(graphT,"n0").getGi().getGraphI(-1);
		    HashMap<Vertex, ArrayList<Edge>> vertexT = new GraphValidator(graphT,"n0").getGi().getGraphI();
			HashSet<ToC> tocs = TocFactory.getToCs(tocT);
			//HashMap<String,ToC> tocsI=new HashMap<String,ToC>();
			ArrayList<ToC> tocsI=new ArrayList<ToC>();
			int id=-1;
			for(ToC toc:tocs){
				id++;
				int k=0;
				boolean found=false;
				/*for( Entry<Vertex, ArrayList<Edge>> ve:vertexT.entrySet()){
					Vertex v=ve.getKey();
					if(v.getProperty("mechanism").toString().equalsIgnoreCase(toc.getMechanism())){
						
					}
						
					
				}*/
				for(ArrayList<Vertex> path:classicPath){
					int z=0;
					for(Vertex v:path){
						if(v.getProperty("mechanism").toString().equalsIgnoreCase(toc.getMechanism())){
							String mi=paths.get(k).get(z).getProperty("mechanism").toString();
							//if(tocsI.get(mi)==null){
							ToC newToC=new ToC(String.valueOf(id), mi,toc.getLayer(), toc.getEvents());
							//tocsI.put(mi, newToC);
							tocsI.add(newToC);
							found=true;
							break;
							/*}else{
								ToC newT=tocsI.get(mi);
								newT.addEvents(toc.getEvents());
								tocsI.remove(mi);
								tocsI.put(mi, newT);
								//System.out.println("ANOMALIES");
							}*/
						}
						z++;
					}
					k++;
					if(found==true)
						break;
				}
			}
			//System.out.println(tocsI);
			ArrayList<ToC> result=new ArrayList<ToC>();
			/*for(Entry<String, ToC> t:tocsI.entrySet()){
				result.add(t.getValue());
				
			}*/
			result=tocsI;
			try {
				TocFactory.writeToFile(result,tocI);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	static private void writeToFile(ArrayList<ToC> allTocs,String outFile) throws Exception{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();


		Element rootElement = doc.createElement("ToCs");
		doc.appendChild(rootElement);
		
		Random randomGenerator = new Random();
		for(ToC t:allTocs){
			
			//int nTCperPath=randomGenerator.nextInt(4);
			//System.out.println("NUMERO DI TESTCASE PER PATH "+pathC+"="+nTCperPath);
			
			// testcase elements
			Element toc = doc.createElement("ToC");
			Attr attrid = doc.createAttribute("id");

				//TODO path parameter
				attrid.setValue(String.valueOf(t.getId()));
				toc.setAttributeNode(attrid);
				Element mechanism=doc.createElement("mechanism");
				mechanism.appendChild(doc.createTextNode(t.getMechanism()));
				toc.appendChild(mechanism);
				Element service=doc.createElement("service");
				service.appendChild(doc.createTextNode(t.getLayer()));
				toc.appendChild(service);
				
				t.getLayer();
				//for each state
				HashSet<String> events=t.getEvents();
				for(String e:events){
					Element ev=doc.createElement("event");
					ev.appendChild(doc.createTextNode(e));
					toc.appendChild(ev);
				}

				rootElement.appendChild(toc);
			
		}



		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(outFile));

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		System.out.println("File saved!");
	}

	public static String randomMechanism() {
		BaseXOntologyManager app = new BaseXOntologyManager(BasexFactory.getHost(), 1984, "admin", "admin", "mechanism");
		//BaseXOntologyManager app=BasexFactory.getBasex();
		ArrayList<String> result2=null;
		try {
			result2 = app.getSubClasses("/", "M", false);
			Random randomG = new Random();
			int index;
			if(result2.size()==1)
				index=randomG.nextInt(result2.size());
			else
				index=randomG.nextInt(result2.size()-1);
			//System.out.println("TESTER"+index);
			return result2.get(index); 
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERRORE:"+e.getMessage());
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public static ToC createToc(String mec,String id){
		String service[]={"infrastructure","platform","software"};
		Random randomG = new Random();
		int nevent=randomG.nextInt(4);
		HashSet<String> event;
		event=new HashSet<String>();
		for(int i=0;i<nevent;i++){
			event.add("event"+id+"."+String.valueOf(i));
		}
		return new ToC(id, mec,service[randomG.nextInt(3)], event);
		//return null;
	}

	public static boolean writeToFileTocs(ToC[] ts, String dest) {
		ArrayList<ToC> t=new ArrayList<ToC>();
		for(ToC t1:ts){
			t.add(t1);
		}
		try {
			writeToFile(t,dest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}





