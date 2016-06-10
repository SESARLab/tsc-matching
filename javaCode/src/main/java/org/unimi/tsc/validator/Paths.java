package org.unimi.tsc.validator;

import com.tinkerpop.blueprints.Vertex;
import java.util.ArrayList;

public class Paths {

	private ArrayList<ArrayList<Vertex>> paths;
	
	public Paths(){
		this.paths=new ArrayList<ArrayList <Vertex>>();
		
	}
	public ArrayList<ArrayList<Vertex>> getAllPaths(){
		return this.paths;
	}
	public void addNewPath(ArrayList<Vertex> p){
			this.paths.add(p);
	}
	
	
	static Paths factoryPaths(Paths p,Vertex v){
		Paths newP=new Paths();
		//ArrayList<Vertex> vs=new ArrayList<Vertex>();
		ArrayList<ArrayList<Vertex>> allPaths=p.getAllPaths();
		for(ArrayList<Vertex> av:allPaths){
			av.add(v);
			//vs=av;
			newP.addNewPath(av);
		}
		return newP;
	}
	public void putOnTop(Paths p, Vertex v) {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<Vertex>> allPaths=p.getAllPaths();
		for(ArrayList<Vertex> av:allPaths){
			ArrayList<Vertex> vs=new ArrayList<Vertex>();
			for(Vertex vApp:av){
				vs.add(vApp);
			}
			vs.add(v);
			//vs=av;
			this.paths.add(vs);
		}
	}
	
	
}
