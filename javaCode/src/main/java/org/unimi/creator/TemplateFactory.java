package org.unimi.creator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

import org.unimi.tsc.validator.EvidenceFactory;
import org.unimi.tsc.validator.InstanceFactory;
import org.unimi.tsc.validator.Property;
import org.unimi.tsc.validator.PropertyBuilder;
import org.unimi.tsc.validator.ToC;
import org.unimi.tsc.validator.TocFactory;
import org.unimi.tsc.validator.graphSTS;
import org.unimi.utilities.Utilities;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLWriter;

public class TemplateFactory {

	public static void createTemplate(int path,int mindeep,int maxdeep, String dest){

		Property p=PropertyBuilder.createProperty();
		ArrayList<Vertex> vs=createModel(path,mindeep,maxdeep,dest);
		createToCs(vs,dest);
		for(int i=0;i<10;i++)
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

	}
	private static void createToCs(ArrayList<Vertex> vs,String dest){
		
		Random randomG = new Random();
		HashSet<String> mec=new HashSet<String>();
		for(Vertex v:vs){
			//System.out.println(v.getId().toString());
			mec.add(v.getProperty("mechanism").toString());
		}
		int id=1;
		ToC[] ts=new ToC[mec.size()];
		for(String m:mec){
			//System.out.println(m);
			ts[id-1]=TocFactory.createToc(m,String.valueOf(id));
			System.out.println(ts[id-1].getId()+" - "+ts[id-1].getMechanism()+" - "+ts[id-1].getLayer()+" - "+ts[id-1].getEvents().size());
			id++;
		}
		TocFactory.writeToFileTocs(ts,dest+"/TemplateToC.xml");
	}

	private static ArrayList<Vertex> createModel(int path,int mindeep,int maxdeep,String dest){
		Random randomG = new Random();
		Graph graph = new TinkerGraph();
		GraphMLWriter writer = new GraphMLWriter(graph);
		String node="n";
		int nnode=0;
		ArrayList<Vertex> vs=new ArrayList<Vertex>();
		Vertex root = graph.addVertex(node+String.valueOf(nnode));
		nnode++;
		root.setProperty("mechanism",TocFactory.randomMechanism());
		vs.add(root);
		System.out.println("CREATA RADICE:"+root.getId().toString());
		for(int i=0;i<path;i++){
			//int deep=randomG.nextInt((maxdeep - mindeep) + 1) + mindeep;
			int deep=maxdeep;
			System.out.println("path:"+i+" deep:"+deep);
			Vertex prev=root;
			for(int k=1;k<deep;k++){
				System.out.println("aggiunto vertice: "+node+String.valueOf(nnode));
				Vertex v=graph.addVertex(node+String.valueOf(nnode));
				v.setProperty("mechanism",TocFactory.randomMechanism());
				vs.add(v);
				nnode++;
				graph.addEdge(null, prev, v, UUID.randomUUID().toString());
				prev=v;
			}
		}
		

		String outG=dest+"/TemplateModel.xml";
		OutputStream os=null;
		try {
			os = new FileOutputStream(outG);
			writer.outputGraph(graph,os);
			os.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			graphSTS gt=new graphSTS(outG,root.getId().toString());
			ArrayList<ArrayList<Vertex>> paths = gt.getGraphI(-1);
			
			EvidenceFactory.writeEvidenceT(paths, dest+"/TemplateEvidence.xml",50/path);
			//createEvidence(paths);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return vs;
	}

	/*private static void createEvidence(ArrayList<ArrayList<Vertex>> paths) {
		for(int id=0;id<paths.size();id++){
			EvidenceFactory.
		}
		
	}*/
	public static void main( String[] args ) throws IOException, InterruptedException
    {
		String basedir="/Users/iridium/Downloads/datasetPerformance";
		String dir=basedir+"/CMT";
		Utilities.createDir(dir);
		TemplateFactory.createTemplate(4, 3, 5,dir);
		InstanceFactory.createInstance("", dir+"/TemplateToC.xml", dir+"/TemplateModel.xml", dir+"/TemplateEvidence.xml", null,dir,String.valueOf(3));
		//System.out.println(TocFactory.randomMechanism());
		//createTemplate(5,3,6);
    }
}

