package org.unimi.tsc.validator;

import java.io.IOException;
import java.util.ArrayList;

import com.tinkerpop.blueprints.Vertex;

public class ModelEvidenceValidator {
	private GraphValidator gv;
	private EvidenceValidator ev;

	public ModelEvidenceValidator(GraphValidator gv, EvidenceValidator ev) {
		// super();
		this.gv = gv;
		this.ev = ev;
	}

	public boolean validate(String modelTemplateF, String root,
			String evidenceTemplateF) {
		String timeC = "INIT MODEL:" + String.valueOf(System.nanoTime() / 1000)
				+ "\n";

		ArrayList<Integer> modelindex = gv.compareModel2(modelTemplateF, root);
		timeC += "END MODEL:" + String.valueOf(System.nanoTime() / 1000) + "\n";
		System.out.println(timeC);
		graphSTS gt = null;
		try {
			gt = new graphSTS(modelTemplateF, root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<ArrayList<Vertex>> toPrint = gv.getGi().getGraphI(-1);
		for (ArrayList<Vertex> vv : toPrint) {
			for (Vertex v : vv)
				System.out.print(" " + v.getId().toString());
			System.out.println(" ");
		}
		toPrint = gt.getGraphI(-1);
		for (ArrayList<Vertex> vv : toPrint) {
			for (Vertex v : vv)
				System.out.print(" " + v.getId().toString());
			System.out.println(" ");
		}

		System.out.println("RISULTATO model:" + modelindex.size() + " ->"
				+ modelindex.get(0));

		if (modelindex.size() == 0)
			return false;
		for (Integer modelI : modelindex) {

			toPrint = gv.getGi().getGraphI(modelI.intValue());
			for (ArrayList<Vertex> vv : toPrint) {
				for (Vertex v : vv)
					System.out.print(" " + v.getId().toString());
				System.out.println(" ");
			}

			ArrayList<Integer> pathsOrderInstance = gv.getGi().getOrder(
					modelI.intValue());

			timeC = "INIT EVIDENCE:" + String.valueOf(System.nanoTime() / 1000)
					+ "\n";
			boolean evid = this.ev.CompareEvidences(evidenceTemplateF,
					pathsOrderInstance);
			timeC += "END EVIDENCE:" + String.valueOf(System.nanoTime() / 1000)
					+ "\n";
			System.out.println(timeC);
			System.out.println("RISULTATO evidence:" + evid);
			if (evid)
				return true;
		}
		// if(!evid)
		// return false;

		return false;
	}

	public boolean validateEmp1(String modelTemplateF, String root,
			String evidenceTemplateF) {
		String timeC = "INIT MODEL:" + String.valueOf(System.nanoTime() / 1000)
				+ "\n";

		ArrayList<Integer> modelindex = gv.compareModelEmp1(modelTemplateF,
				root);
		timeC += "END MODEL:" + String.valueOf(System.nanoTime() / 1000) + "\n";
		graphSTS gt = null;
		try {
			gt = new graphSTS(modelTemplateF, root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * ArrayList<ArrayList<Vertex>> toPrint = gv.getGi().getGraphI(-1);
		 * for(ArrayList<Vertex>vv:toPrint){ for(Vertex v:vv)
		 * System.out.print(" "+v.getId().toString()); System.out.println(" ");
		 * } toPrint = gt.getGraphI(-1); for(ArrayList<Vertex>vv:toPrint){
		 * for(Vertex v:vv) System.out.print(" "+v.getId().toString());
		 * System.out.println(" "); }
		 */

		for (Integer l : modelindex) {
			if (l.intValue() == -1) {
				System.out.println("rmodel:false");
				System.out.println("revidence:false");
				return false;
			}
		}
		System.out.println("rmodel:true");
		System.out.println(modelindex);
		// if(modelindex.size()==0)
		// return false;
		// for(Integer modelI:modelindex){

		// toPrint =gv.getGi().getAllPahsbyIndexes(modelindex);
		// for(ArrayList<Vertex>vv:toPrint){
		// for(Vertex v:vv) System.out.print(" "+v.getId().toString());
		// System.out.println(" ");
		// }

		ArrayList<Integer> pathsOrderInstance = modelindex;
		// gv.getGi().getOrder(modelI.intValue());

		timeC += "INIT EVIDENCE:" + String.valueOf(System.nanoTime() / 1000)
				+ "\n";
		boolean evid = this.ev.CompareEvidences(evidenceTemplateF,
				pathsOrderInstance);
		timeC += "END EVIDENCE:" + String.valueOf(System.nanoTime() / 1000)
				+ "\n";
		System.out.println(timeC);
		System.out.println("revidence:" + evid);
		if (evid)
			return true;

		// if(!evid)
		// return false;

		return false;
	}

	public boolean validateEmp2(String modelTemplateF, String root,
			String evidenceTemplateF) {
		String timeC = "INIT MODEL:" + String.valueOf(System.nanoTime() / 1000)
				+ "\n";
		// ArrayList<Integer> model1=gv.compareModelEmp1(modelTemplateF, root);
		// System.out.println("RISULTATO model:"+model1);
		ArrayList<Integer> modelindex = gv.compareModelEmp2(modelTemplateF,
				root);
		timeC += "END MODEL:" + String.valueOf(System.nanoTime() / 1000) + "\n";
		graphSTS gt = null;
		try {
			gt = new graphSTS(modelTemplateF, root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * ArrayList<ArrayList<Vertex>> toPrint = gv.getGi().getGraphI(-1);
		 * for(ArrayList<Vertex>vv:toPrint){ for(Vertex v:vv)
		 * System.out.print(" "+v.getId().toString()); System.out.println(" ");
		 * } toPrint = gt.getGraphI(-1); for(ArrayList<Vertex>vv:toPrint){
		 * for(Vertex v:vv) System.out.print(" "+v.getId().toString());
		 * System.out.println(" "); }
		 */

		for (Integer l : modelindex) {
			if (l.intValue() == -1) {
				System.out.println("rmodel:false");
				System.out.println("revidence:false");
				return false;
			}
		}
		System.out.println("rmodel:true");
		System.out.println(modelindex);
		// if(modelindex.size()==0)
		// return false;
		// for(Integer modelI:modelindex){

		// toPrint =gv.getGi().getAllPahsbyIndexes(modelindex);
		// for(ArrayList<Vertex>vv:toPrint){
		// for(Vertex v:vv) System.out.print(" "+v.getId().toString());
		// System.out.println(" ");
		// }

		ArrayList<Integer> pathsOrderInstance = modelindex;
		// gv.getGi().getOrder(modelI.intValue());

		timeC += "INIT EVIDENCE:" + String.valueOf(System.nanoTime() / 1000)
				+ "\n";
		boolean evid = this.ev.CompareEvidences(evidenceTemplateF,
				pathsOrderInstance);
		timeC += "END EVIDENCE:" + String.valueOf(System.nanoTime() / 1000)
				+ "\n";
		System.out.println(timeC);
		System.out.println("revidence:" + evid);
		if (evid)
			return true;

		// if(!evid)
		// return false;

		return false;
	}

	public boolean binder(String modelTemplateF, String root) {
		ArrayList<Integer> modelindex = gv.compareModelBinder(modelTemplateF,
				root);
		// graphSTS gt=null;
		int max = modelindex.get(0).intValue();
		int somma = modelindex.get(0).intValue();
		System.out.println("PATH:" + 0 + " ha bindng:" + max);
		for (int i = 1; i < modelindex.size(); i++) {
			int value = modelindex.get(i).intValue();
			System.out.println("PATH:" + i + " ha bindng:" + value);
			somma += value;
			if (max < value)
				max = value;
		}
		System.out.println(somma + "/" + modelindex.size());
		float avg = (float) somma / modelindex.size();
		System.out.println("MAX:" + max);
		System.out.println("AVG:" + avg);
		// TODO Auto-generated method stub
		return true;
	}

	public boolean validateEmp3(String modelTemplateF, String root,
			String evidenceTemplateF) {
		boolean falser = true;
		String timeC = "INIT MODEL:" + String.valueOf(System.nanoTime() / 1000)
				+ "\n";
		// ArrayList<Integer> model1=gv.compareModelEmp1(modelTemplateF, root);
		// System.out.println("RISULTATO model:"+model1);
		ArrayList<ArrayList<Integer>> modelindex = gv.compareModelEmp3Imb(
				modelTemplateF, root);
		timeC += "END MODEL:" + String.valueOf(System.nanoTime() / 1000) + "\n";
		graphSTS gt = null;
		try {
			gt = new graphSTS(modelTemplateF, root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * ArrayList<ArrayList<Vertex>> toPrint = gv.getGi().getGraphI(-1);
		 * for(ArrayList<Vertex>vv:toPrint){ for(Vertex v:vv)
		 * System.out.print(" "+v.getId().toString()); System.out.println(" ");
		 * } toPrint = gt.getGraphI(-1); for(ArrayList<Vertex>vv:toPrint){
		 * for(Vertex v:vv) System.out.print(" "+v.getId().toString());
		 * System.out.println(" "); }
		 */

		if (modelindex.size() == 0) {
			System.out.println("GRAPH BINDING:0");
			System.out.println("rmodel:false");
			System.out.println("revidence:false");
			return false;
		}
		// boolean atleast=false;
		ArrayList<ArrayList<Integer>> modelindexf = new ArrayList<ArrayList<Integer>>();
		for (ArrayList<Integer> l : modelindex) {
			if (l.size() == gt.getGraphI(-1).size()) {
				modelindexf.add(l);
				// atleast=true;
			}
		}
		if (modelindexf.size() == 0) {
			System.out.println("rmodel:false");
			System.out.println("revidence:false");
			return false;
		}

		System.out.println("rmodel:true");
		System.out.println(modelindexf);
		System.out.println("GRAPH BINDING:"+modelindex.size());
		// if(modelindex.size()==0)
		// return false;
		// for(Integer modelI:modelindex){

		// toPrint =gv.getGi().getAllPahsbyIndexes(modelindex);
		// for(ArrayList<Vertex>vv:toPrint){
		// for(Vertex v:vv) System.out.print(" "+v.getId().toString());
		// System.out.println(" ");
		// }
		timeC += "INIT EVIDENCE:"
				+ String.valueOf(System.nanoTime() / 1000) + "\n";
		int evidenceChecker=0;
		for (ArrayList<Integer> m : modelindexf) {
			ArrayList<Integer> pathsOrderInstance = m;
			// gv.getGi().getOrder(modelI.intValue());

			
			boolean evid = this.ev.CompareEvidences(evidenceTemplateF,
					pathsOrderInstance);

			if (evid) {
				timeC += "END EVIDENCE:"
						+ String.valueOf(System.nanoTime() / 1000) + "\n";
				System.out.println(timeC);
				System.out.println("revidence:" + evid);
				falser = false;
				// return true;
			}

			// if(!evid)
			// return false;
			evidenceChecker++;
		}
		System.out.println("CHECKED EVIDENCE:"+evidenceChecker);
		if (falser) {
			timeC += "END EVIDENCE:" + String.valueOf(System.nanoTime() / 1000)
					+ "\n";
			System.out.println(timeC);
			System.out.println("revidence:false");
			return false;
		} else {
			return true;
		}
	}

}
