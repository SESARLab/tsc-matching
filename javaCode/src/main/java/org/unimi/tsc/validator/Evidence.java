package org.unimi.tsc.validator;

import java.io.IOException;
import java.util.ArrayList;

public class Evidence {
	private ArrayList<EvidenceTI> evs;
	private String path;
	
	public ArrayList<EvidenceTI> getEvidenceTI() {
		return evs;
	}
	public Evidence(String path){
		this.evs=new ArrayList<EvidenceTI>();
		this.path=path;
	}
	public void add(EvidenceTI evti) {
		this.evs.add(evti);

	}
	public String toString(){
		String output="";
		//int i=0;
		for (EvidenceTI e:evs){
			output+="---------------------------------------------------";
			//i++;
			output+=e.toString();
		}
		return output;
	}
	public boolean compare(Evidence e) {
		ArrayList<EvidenceTI> eivs=e.getTI();
		if(eivs.size()!=this.evs.size())
			return false;
		for(int i=0;i<this.evs.size();i++){
			EvidenceTI tit=this.evs.get(i);
			EvidenceTI tii=eivs.get(i);
			if(!EvidenceTI.compareInput(tit,tii))
				return false;
			if(!EvidenceTI.compareOutput(tit,tii))
				return false;
			if(!EvidenceTI.comparePostCond(tit,tii))
				return false;
			if(!EvidenceTI.comparePreCond(tit,tii))
				return false;
		}
		return true;
	}

	private ArrayList<EvidenceTI> getTI() {
		return (ArrayList<EvidenceTI>) this.evs.clone();
	}

	public static void main(String[] args) throws IOException {
//		GraphValidator instance = new GraphValidator("/Users/iridium/Documents/workspace/validator/graphI2.xml","n0");	
//		GraphValidator template = new GraphValidator("/Users/iridium/Documents/workspace/validator/graphT2.xml","n0");
//		instance.getGi().getGraphI(-1);
//		template.getGi().getGraphI(-1);
		ArrayList<Evidence> evT=EvidenceFactory.getEvidencesI("/Users/iridium/Documents/workspace/validator/evidenceTemplate.xml");
		ArrayList<Evidence> evI=EvidenceFactory.getEvidencesI("/Users/iridium/Documents/workspace/validator/evidenceInstance.xml");
		int[] t={0,1,2,3};
		int[] i={0,1,2,3};
		boolean r=validation(t,i,evT,evI);
		System.out.println("RISULTATO:"+r);

	}
	private static boolean validation(int[] t, int[] i,
			ArrayList<Evidence> evT, ArrayList<Evidence> evI) {
		ArrayList<Evidence> evPathT;
		ArrayList<Evidence> evPathI;
		for(int k=0;k<t.length;k++){
			System.out.println("controllando path:"+k);
			evPathT=new ArrayList<Evidence>();
			evPathI=new ArrayList<Evidence>();
			for(Evidence evi:evI){
				if(evi.getPath()==i[k])
					evPathI.add(evi);
			}
			for(Evidence evt:evT){
				if(evt.getPath()==t[k])
					evPathT.add(evt);
			}


			/*attached part*/
			ArrayList<Evidence> evsTapp=(ArrayList<Evidence>) evPathT.clone();
			for(Evidence ei:evPathI){
				ArrayList<Evidence> toRemove=new ArrayList<Evidence>();
				for(Evidence et:evPathT){
					if(et.compare(ei)){
						//System.out.println("Evidence Template Mappata su Evidence Instanza");
						//System.out.println("\nTEMPLATE:"+et);
						//System.out.println("\nINSTANZA:"+ei);
						toRemove.add(ei);
					}
				}
				if(toRemove.size()==0){
					System.out.println("Evidence in Instance not compatible with Template Test Cases");
					return false;
				}else{
					try{
						evsTapp.remove(toRemove);
					}catch(Exception e){

					}
				}

			}
			if(evsTapp.size()>0){
			System.out.println("EVIDENCE IN Template NON MAPPATE ->");
			for(Evidence e:evsTapp)
				System.out.println(e);
			return false;
				
			}







		}
		return true;
	}
	public int getPath() {
		return Integer.parseInt(this.path);
	}
}
