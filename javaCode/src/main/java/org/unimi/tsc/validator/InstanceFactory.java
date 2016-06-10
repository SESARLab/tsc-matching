package org.unimi.tsc.validator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Random;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class InstanceFactory {

	public static boolean createInstance(String propertyT,String tocT,String graphT,String evidenceT,String lifeCycleT, String dir,String id) throws IOException, InterruptedException{
		String property;
		String rootNodeI;
		String graphI=dir+"/IstanceGraph-"+id+".xml";
		String evidenceFI=dir+"/IstanceEvidence-"+id+".xml";
		String tocI=dir+"/IstanceToC-"+id+".xml";
		PrintWriter writer = new PrintWriter(graphI);
		writer.print("");
		writer.close();
		writer = new PrintWriter(evidenceFI);
		writer.print("");
		writer.close();
		writer = new PrintWriter(tocI);
		writer.print("");
		writer.close();

		Random randomG = new Random();
		//restituisce una proprietà di certificazione a partire dalla proprietà (stringa) del template
		property=PropertyBuilder.propertyFromTemplate(propertyT);

		//restituisce il mapping fra nodi template e nodi grafo i e crea il file graphI con tutto il grafo (vertici+nodi)
		HashMap<String, String> nodeMapped = graphSTS.graphFromTemplate(graphT,"n0",graphI);
		rootNodeI=nodeMapped.get("n0");
		GraphValidator gv=new GraphValidator(graphI,rootNodeI);

		//associazione tra tutti i path del template e tutti i path di gv(grago instanza) - Restituisce un array di path del template posizionati nello stesso ordine dei path dell'istanza
		ArrayList<ArrayList<Vertex>> paths=gv.bind(graphT,"n0");
		Thread.sleep(10);
		if(!graphSTS.validateValidation(nodeMapped,paths,new graphSTS(graphT,"n0").getGraphI(-1))){
			System.out.println("error in validation");
			return false;
		}

		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\nCreated Instance from template");
		System.out.println("Property OLD:"+propertyT+" NEW:"+property);
		ArrayList<Evidence> templateEvidences = EvidenceFactory.getEvidencesI(evidenceT);
		ArrayList<Evidence> instanceEvidences = new ArrayList<Evidence>();
		System.out.println("PATHS");
		//GraphValidator gt=new GraphValidator(graphT,"n0");
		ArrayList<ArrayList<Vertex>> classicPath=gv.getGi().getGraphI(-1);

		if(paths==null)
			System.out.println("NO PATHS");
		else{
			System.out.println("PASSIAMO ALLE EVIDENZE");
			int nclassic=0;
			for(ArrayList<Vertex> v:paths){
				for(int i=0;i<classicPath.size();i++){
					ArrayList<Vertex> c=classicPath.get(i);
					if(c.equals(v)){
						System.out.println("matching trovato per path singolo");
						instanceEvidences.addAll(EvidenceFactory.writeEvidenceFromTemplate(templateEvidences, v,i,nclassic));
						//EvidenceFactory.writeEvidenceFromTemplate(templateEvidences, i);
					}
				}
				nclassic++; 		
			}
			try {
				EvidenceFactory.writeOnFile(instanceEvidences,evidenceFI);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			TocFactory.writeToCfilefromTemplate(tocT,tocI,graphT,paths,nodeMapped);
			// tocT, String tocI, classicPath,  paths,  nodeMappe



			HashMap<Vertex, ArrayList<Edge>> verticiInstanza = gv.getGi().getGraphI();
			graphSTS gt = new GraphValidator(graphT,"n0").getGi();
			ArrayList<ArrayList<Vertex>> templatePath = gt.getGraphI(-1);
			HashMap<Vertex, ArrayList<Edge>> verticiTemplate =gt.getGraphI();
			System.out.println("\n\n\n\n\n*******************************************************************************");
			System.out.println("NODO RADICE ISTANCE:"+rootNodeI);
			//File froot=new File(dir+"/root-"+id+".pt");
			FileWriter rootwriter = new FileWriter(dir+"/root-"+id+".pt",false);
			rootwriter.write(rootNodeI);
			rootwriter.close();
			for(Entry<String, String> n:nodeMapped.entrySet()){
				String nodoI,nodoT;
				String meccanismoI="";
				String meccanismoT="";
				nodoT=n.getKey();
				nodoI=n.getValue();
				for(Entry<Vertex,ArrayList<Edge>> appV:verticiTemplate.entrySet()){
					if(appV.getKey().toString().equalsIgnoreCase("v["+nodoT+"]"))
						meccanismoT=appV.getKey().getProperty("mechanism").toString();
				}
				for(Entry<Vertex,ArrayList<Edge>> appV:verticiInstanza.entrySet()){
					if(appV.getKey().toString().equalsIgnoreCase("v["+nodoI+"]"))
						meccanismoI=appV.getKey().getProperty("mechanism").toString();
				}
				System.out.println("VECCHIO NODO:"+nodoT+"\tm:"+meccanismoT+ "\t NUOVO NODO:"+nodoI+"\tm:"+meccanismoI);
			}
			System.out.println("\n\n\n***********");
			int numP=0;
			/*for(ArrayList<Vertex> path:classicPath){
			System.out.print("Percorso" +numP+":");
			System.out.println(path);
			numP++;
		}*/
			numP=0;
			ArrayList<Integer> order=gv.getGi().getOrder();
			for(ArrayList<Vertex> path:paths){
				System.out.print("T Percorso" +numP+":");
				System.out.println(templatePath.get(numP));	
				System.out.print("I Percorso" +order.get(numP)+":");
				System.out.println(path+"\n");

				numP++;
			}

		}
		for(int i=0;i<10;i++)
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		return true;
	}


	public static void main(String[] args) throws Exception {
		InstanceFactory.createInstance("property-d8-b0-d7-b0-d6-b5-d5-b1", "/Users/iridium/Documents/workspace/validator/ToCt.xml", "/Users/iridium/Documents/workspace/validator/graphT2.xml", "/Users/iridium/Documents/workspace/validator/evidenceTemplate.xml",null,"/Users/iridium/Documents/workspace/validator","1");
	}




	public static boolean createInstanceClean(String propertyT,String tocT,String graphT,String evidenceT,String lifeCycleT, String dir,String id) throws IOException, InterruptedException{
		String property;
		String rootNodeI;
		String graphI=dir+"/IstanceGraph-"+id+".xml";
		String evidenceFI=dir+"/IstanceEvidence-"+id+".xml";
		String tocI=dir+"/IstanceToC-"+id+".xml";
		PrintWriter writer = new PrintWriter(graphI);
		writer.print("");
		writer.close();
		writer = new PrintWriter(evidenceFI);
		writer.print("");
		writer.close();
		writer = new PrintWriter(tocI);
		writer.print("");
		writer.close();

		Random randomG = new Random();
		//restituisce una proprietà di certificazione a partire dalla proprietà (stringa) del template
		//property=PropertyBuilder.propertyFromTemplate(propertyT);

		//restituisce il mapping fra nodi template e nodi grafo i e crea il file graphI con tutto il grafo (vertici+nodi)
		HashMap<String, String> nodeMapped = graphSTS.graphFromTemplate(graphT,"n0",graphI);
		rootNodeI=nodeMapped.get("n0");
		GraphValidator gv=new GraphValidator(graphI,rootNodeI);
		ArrayList<ArrayList<Vertex>> paths=null;
		//associazione tra tutti i path del template e tutti i path di gv(grago instanza) - Restituisce un array di path del template posizionati nello stesso ordine dei path dell'istanza
		//ArrayList<ArrayList<Vertex>> paths=gv.bind(graphT,"n0");
		ArrayList<Integer> permutations = gv.compareModel2(graphT, "n0");
		Thread.sleep(10);
		boolean found=false;
		for(int i=0;i<permutations.size();i++){
			paths=gv.getGi().getGraphI(permutations.get(i).intValue());

			if(graphSTS.validateValidation(nodeMapped,paths,new graphSTS(graphT,"n0").getGraphI(-1))){
				System.out.println("Valditation at "+i+" mapping");
				found=true;
				break;
				//return false;
			}
		}
		if(!found)
			return false;
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\nCreated Instance from template");
		//System.out.println("Property OLD:"+propertyT+" NEW:"+property);
		ArrayList<Evidence> templateEvidences = EvidenceFactory.getEvidencesI(evidenceT);
		ArrayList<Evidence> instanceEvidences = new ArrayList<Evidence>();
		System.out.println("PATHS");
		//GraphValidator gt=new GraphValidator(graphT,"n0");
		ArrayList<ArrayList<Vertex>> classicPath=gv.getGi().getGraphI(-1);

		if(paths==null){
			System.out.println("NO PATHS");
			return false;
		}else{
			System.out.println("PASSIAMO ALLE EVIDENZE");
			int nclassic=0;
			for(ArrayList<Vertex> v:paths){
				for(int i=0;i<classicPath.size();i++){
					ArrayList<Vertex> c=classicPath.get(i);
					if(c.equals(v)){
						System.out.println("matching trovato per path singolo");
						instanceEvidences.addAll(EvidenceFactory.writeEvidenceFromTemplate(templateEvidences, v,i,nclassic));
						//EvidenceFactory.writeEvidenceFromTemplate(templateEvidences, i);
					}
				}
				nclassic++; 		
			}
			try {
				EvidenceFactory.writeOnFile(instanceEvidences,evidenceFI);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			TocFactory.writeToCfilefromTemplate(tocT,tocI,graphT,paths,nodeMapped);
			// tocT, String tocI, classicPath,  paths,  nodeMappe



			HashMap<Vertex, ArrayList<Edge>> verticiInstanza = gv.getGi().getGraphI();
			graphSTS gt = new GraphValidator(graphT,"n0").getGi();
			ArrayList<ArrayList<Vertex>> templatePath = gt.getGraphI(-1);
			HashMap<Vertex, ArrayList<Edge>> verticiTemplate =gt.getGraphI();
			System.out.println("\n\n\n\n\n*******************************************************************************");
			System.out.println("NODO RADICE ISTANCE:"+rootNodeI);
			//File froot=new File(dir+"/root-"+id+".pt");
			FileWriter rootwriter = new FileWriter(dir+"/root-"+id+".pt",false);
			rootwriter.write(rootNodeI);
			rootwriter.close();
			for(Entry<String, String> n:nodeMapped.entrySet()){
				String nodoI,nodoT;
				String meccanismoI="";
				String meccanismoT="";
				nodoT=n.getKey();
				nodoI=n.getValue();
				for(Entry<Vertex,ArrayList<Edge>> appV:verticiTemplate.entrySet()){
					if(appV.getKey().toString().equalsIgnoreCase("v["+nodoT+"]"))
						meccanismoT=appV.getKey().getProperty("mechanism").toString();
				}
				for(Entry<Vertex,ArrayList<Edge>> appV:verticiInstanza.entrySet()){
					if(appV.getKey().toString().equalsIgnoreCase("v["+nodoI+"]"))
						meccanismoI=appV.getKey().getProperty("mechanism").toString();
				}
				System.out.println("VECCHIO NODEO:"+nodoT+"\tm:"+meccanismoT+ "\t NUOVO NODO:"+nodoI+"\tm:"+meccanismoI);
			}
			System.out.println("\n\n\n***********");
			int numP=0;
			/*for(ArrayList<Vertex> path:classicPath){
			System.out.print("Percorso" +numP+":");
			System.out.println(path);
			numP++;
		}*/
			numP=0;
			ArrayList<Integer> order=gv.getGi().getOrder();
			for(ArrayList<Vertex> path:paths){
				System.out.print("T Percorso" +numP+":");
				System.out.println(templatePath.get(numP));	
				System.out.print("I Percorso" +order.get(numP)+":");
				System.out.println(path+"\n");

				numP++;
			}

		}
		for(int i=0;i<10;i++)
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		return true;
	}




	public static boolean createInstanceSpeed(String propertyT,String tocT,String graphT,String evidenceT,String lifeCycleT, String dir,String id) throws IOException, InterruptedException{
		String property;
		String rootNodeI;
		String graphI=dir+"/IstanceGraph-"+id+".xml";
		String evidenceFI=dir+"/IstanceEvidence-"+id+".xml";
		String tocI=dir+"/IstanceToC-"+id+".xml";
		PrintWriter writer = new PrintWriter(graphI);
		writer.print("");
		writer.close();
		writer = new PrintWriter(evidenceFI);
		writer.print("");
		writer.close();
		writer = new PrintWriter(tocI);
		writer.print("");
		writer.close();

		Random randomG = new Random();
		//restituisce una proprietà di certificazione a partire dalla proprietà (stringa) del template
		//property=PropertyBuilder.propertyFromTemplate(propertyT);

		//restituisce il mapping fra nodi template e nodi grafo i e crea il file graphI con tutto il grafo (vertici+nodi)
		HashMap<String, String> nodeMapped = graphSTS.graphFromTemplate(graphT,"n0",graphI);
		rootNodeI=nodeMapped.get("n0");
		GraphValidator gv=new GraphValidator(graphI,rootNodeI);
		ArrayList<ArrayList<Vertex>> paths=null;
		//associazione tra tutti i path del template e tutti i path di gv(grago instanza) - Restituisce un array di path del template posizionati nello stesso ordine dei path dell'istanza
		//ArrayList<ArrayList<Vertex>> paths=gv.bind(graphT,"n0");
		System.out.println("STARTING COMPARE");
		
		
		
		
		//PERFORMANCE
		ArrayList<ArrayList<Integer>> permutations = gv.compareModelEmp3(graphT, "n0");
		
		
		
		
		System.out.println("STOPPING COMPARE");
		//Thread.sleep(10);
		boolean found=false;
		
		for(int i=0;i<permutations.size();i++){
			paths=gv.getGi().getPathbyPerm(permutations.get(i));
			//paths=gv.getGi().getGraphI(permutations.get(i).intValue());

			if(graphSTS.validateValidation(nodeMapped,paths,new graphSTS(graphT,"n0").getGraphI(-1))){
				System.out.println("Valditation at "+i+" mapping");
				found=true;
				break;
				//return false;
			}
		}
		
		//PERFORMANCE
		//if(!found)
			//return false;
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\nCreated Instance from template");
		//System.out.println("Property OLD:"+propertyT+" NEW:"+property);
		ArrayList<Evidence> templateEvidences = EvidenceFactory.getEvidencesI(evidenceT);
		ArrayList<Evidence> instanceEvidences = new ArrayList<Evidence>();
		System.out.println("PATHS");
		//GraphValidator gt=new GraphValidator(graphT,"n0");
		ArrayList<ArrayList<Vertex>> classicPath=gv.getGi().getGraphI(-1);

		if(paths==null){
			System.out.println("NO PATHS");
			return false;
		}else{
			System.out.println("PASSIAMO ALLE EVIDENZE");
			int nclassic=0;
			for(ArrayList<Vertex> v:paths){
				for(int i=0;i<classicPath.size();i++){
					ArrayList<Vertex> c=classicPath.get(i);
					if(c.equals(v)){
						System.out.println("matching trovato per path singolo");
						instanceEvidences.addAll(EvidenceFactory.writeEvidenceFromTemplate(templateEvidences, v,i,nclassic));
						//EvidenceFactory.writeEvidenceFromTemplate(templateEvidences, i);
					}
				}
				nclassic++; 		
			}
			try {
				EvidenceFactory.writeOnFile(instanceEvidences,evidenceFI);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			TocFactory.writeToCfilefromTemplate(tocT,tocI,graphT,paths,nodeMapped);
			// tocT, String tocI, classicPath,  paths,  nodeMappe



			HashMap<Vertex, ArrayList<Edge>> verticiInstanza = gv.getGi().getGraphI();
			graphSTS gt = new GraphValidator(graphT,"n0").getGi();
			ArrayList<ArrayList<Vertex>> templatePath = gt.getGraphI(-1);
			HashMap<Vertex, ArrayList<Edge>> verticiTemplate =gt.getGraphI();
			System.out.println("\n\n\n\n\n*******************************************************************************");
			System.out.println("NODO RADICE ISTANCE:"+rootNodeI);
			//File froot=new File(dir+"/root-"+id+".pt");
			FileWriter rootwriter = new FileWriter(dir+"/root-"+id+".pt",false);
			rootwriter.write(rootNodeI);
			rootwriter.close();
			for(Entry<String, String> n:nodeMapped.entrySet()){
				String nodoI,nodoT;
				String meccanismoI="";
				String meccanismoT="";
				nodoT=n.getKey();
				nodoI=n.getValue();
				for(Entry<Vertex,ArrayList<Edge>> appV:verticiTemplate.entrySet()){
					if(appV.getKey().toString().equalsIgnoreCase("v["+nodoT+"]"))
						meccanismoT=appV.getKey().getProperty("mechanism").toString();
				}
				for(Entry<Vertex,ArrayList<Edge>> appV:verticiInstanza.entrySet()){
					if(appV.getKey().toString().equalsIgnoreCase("v["+nodoI+"]"))
						meccanismoI=appV.getKey().getProperty("mechanism").toString();
				}
				System.out.println("VECCHIO NODEO:"+nodoT+"\tm:"+meccanismoT+ "\t NUOVO NODO:"+nodoI+"\tm:"+meccanismoI);
			}
			System.out.println("\n\n\n***********");
			int numP=0;
			/*for(ArrayList<Vertex> path:classicPath){
			System.out.print("Percorso" +numP+":");
			System.out.println(path);
			numP++;
		}*/
			numP=0;
			/*ArrayList<Integer> order=gv.getGi().getOrder();
			for(ArrayList<Vertex> path:paths){
				System.out.print("T Percorso" +numP+":");
				System.out.println(templatePath.get(numP));	
				System.out.print("I Percorso" +order.get(numP)+":");
				System.out.println(path+"\n");

				numP++;
			}*/

		}
		for(int i=0;i<10;i++)
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		return true;
	}
	
	//no controllo di validità
	public static boolean createInstanceSpeedAllgood(String propertyT,String tocT,String graphT,String evidenceT,String lifeCycleT, String dir,String id) throws IOException, InterruptedException{
		String property;
		String rootNodeI;
		String graphI=dir+"/IstanceGraph-"+id+".xml";
		String evidenceFI=dir+"/IstanceEvidence-"+id+".xml";
		String tocI=dir+"/IstanceToC-"+id+".xml";
		PrintWriter writer = new PrintWriter(graphI);
		writer.print("");
		writer.close();
		writer = new PrintWriter(evidenceFI);
		writer.print("");
		writer.close();
		writer = new PrintWriter(tocI);
		writer.print("");
		writer.close();

		Random randomG = new Random();
		//restituisce una proprietà di certificazione a partire dalla proprietà (stringa) del template
		//property=PropertyBuilder.propertyFromTemplate(propertyT);

		//restituisce il mapping fra nodi template e nodi grafo i e crea il file graphI con tutto il grafo (vertici+nodi)
		HashMap<String, String> nodeMapped = graphSTS.graphFromTemplate(graphT,"n0",graphI);
		rootNodeI=nodeMapped.get("n0");
		GraphValidator gv=new GraphValidator(graphI,rootNodeI);
		ArrayList<ArrayList<Vertex>> paths=null;
		//associazione tra tutti i path del template e tutti i path di gv(grago instanza) - Restituisce un array di path del template posizionati nello stesso ordine dei path dell'istanza
		//ArrayList<ArrayList<Vertex>> paths=gv.bind(graphT,"n0");
		System.out.println("STARTING COMPARE");
		ArrayList<ArrayList<Integer>> permutations = gv.compareModelEmp3(graphT, "n0");
		System.out.println("STOPPING COMPARE");
		//Thread.sleep(10);
		boolean found=false;

		for(int i=0;i<permutations.size();i++){
			paths=gv.getGi().getPathbyPerm(permutations.get(i));
			//paths=gv.getGi().getGraphI(permutations.get(i).intValue());

			if(graphSTS.validateValidation(nodeMapped,paths,new graphSTS(graphT,"n0").getGraphI(-1))){
				System.out.println("Valditation at "+i+" mapping");
				found=true;
				break;
				//return false;
			}
		}
		if(!found)
			return false;
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\nCreated Instance from template");
		//System.out.println("Property OLD:"+propertyT+" NEW:"+property);
		ArrayList<Evidence> templateEvidences = EvidenceFactory.getEvidencesI(evidenceT);
		ArrayList<Evidence> instanceEvidences = new ArrayList<Evidence>();
		System.out.println("PATHS");
		//GraphValidator gt=new GraphValidator(graphT,"n0");
		ArrayList<ArrayList<Vertex>> classicPath=gv.getGi().getGraphI(-1);

		if(paths==null){
			System.out.println("NO PATHS");
			return false;
		}else{
			System.out.println("PASSIAMO ALLE EVIDENZE");
			int nclassic=0;
			for(ArrayList<Vertex> v:paths){
				for(int i=0;i<classicPath.size();i++){
					ArrayList<Vertex> c=classicPath.get(i);
					if(c.equals(v)){
						System.out.println("matching trovato per path singolo");
						instanceEvidences.addAll(EvidenceFactory.writeEvidenceFromTemplate(templateEvidences, v,i,nclassic));
						//EvidenceFactory.writeEvidenceFromTemplate(templateEvidences, i);
					}
				}
				nclassic++; 		
			}
			try {
				EvidenceFactory.writeOnFile(instanceEvidences,evidenceFI);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			TocFactory.writeToCfilefromTemplate(tocT,tocI,graphT,paths,nodeMapped);
			// tocT, String tocI, classicPath,  paths,  nodeMappe



			HashMap<Vertex, ArrayList<Edge>> verticiInstanza = gv.getGi().getGraphI();
			graphSTS gt = new GraphValidator(graphT,"n0").getGi();
			ArrayList<ArrayList<Vertex>> templatePath = gt.getGraphI(-1);
			HashMap<Vertex, ArrayList<Edge>> verticiTemplate =gt.getGraphI();
			System.out.println("\n\n\n\n\n*******************************************************************************");
			System.out.println("NODO RADICE ISTANCE:"+rootNodeI);
			//File froot=new File(dir+"/root-"+id+".pt");
			FileWriter rootwriter = new FileWriter(dir+"/root-"+id+".pt",false);
			rootwriter.write(rootNodeI);
			rootwriter.close();
			for(Entry<String, String> n:nodeMapped.entrySet()){
				String nodoI,nodoT;
				String meccanismoI="";
				String meccanismoT="";
				nodoT=n.getKey();
				nodoI=n.getValue();
				for(Entry<Vertex,ArrayList<Edge>> appV:verticiTemplate.entrySet()){
					if(appV.getKey().toString().equalsIgnoreCase("v["+nodoT+"]"))
						meccanismoT=appV.getKey().getProperty("mechanism").toString();
				}
				for(Entry<Vertex,ArrayList<Edge>> appV:verticiInstanza.entrySet()){
					if(appV.getKey().toString().equalsIgnoreCase("v["+nodoI+"]"))
						meccanismoI=appV.getKey().getProperty("mechanism").toString();
				}
				System.out.println("VECCHIO NODEO:"+nodoT+"\tm:"+meccanismoT+ "\t NUOVO NODO:"+nodoI+"\tm:"+meccanismoI);
			}
			System.out.println("\n\n\n***********");
			int numP=0;
			/*for(ArrayList<Vertex> path:classicPath){
			System.out.print("Percorso" +numP+":");
			System.out.println(path);
			numP++;
		}*/
			numP=0;
			/*ArrayList<Integer> order=gv.getGi().getOrder();
			for(ArrayList<Vertex> path:paths){
				System.out.print("T Percorso" +numP+":");
				System.out.println(templatePath.get(numP));	
				System.out.print("I Percorso" +order.get(numP)+":");
				System.out.println(path+"\n");

				numP++;
			}*/

		}
		for(int i=0;i<10;i++)
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		return true;
	}

}
