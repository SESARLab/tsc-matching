package org.unimi.tsc.validator;

import java.util.ArrayList;
import java.util.HashSet;

public class EvidenceTI {
	public final static String[] DOMAIN = { "Dr", "Dw", "Dr_malformed",
			"Dw_malformed" };

	public EvidenceTI(String state, String mechanism,
			HashSet<EvidenceInput> inputs, HashSet<EvidenceOutput> outputs) {
		this.state = state;
		this.mechanism = mechanism;
		this.eIn = inputs;
		this.eEo = outputs;
	}

	public String toString() {
		String output = "\nmechanism:  "
				+ this.mechanism + "\nstate:  "+this.state;
		output+="\nINPUTS";
		for (EvidenceInput i : eIn) {
			//output += "\n\tkey:" + i.getKey() + " value:" + i.getValue()
			output+=		 "	domain:" + i.getDomain();
		}
		output+="\nOUTPUTS";
		for (EvidenceOutput i : eEo) {
			//output += "\n\tkey:" + i.getKey() + " value:" + i.getValue()
			output+= 		"	domain:" + i.getDomain()+"\n";
		}
		return output;
	}

	private String state;
	private String mechanism;
	private HashSet<EvidenceInput> eIn;
	private HashSet<EvidenceOutput> eEo;

	public boolean compare(EvidenceTI ei) {
		//if (!this.state.equalsIgnoreCase(ei.getState()))
		//	return false;
		if(!Mechanism.compareMechanism(this.mechanism, ei.getMechanism()))
			return false;
		HashSet<EvidenceInput> inputsI = ei.geteIn();
		for (EvidenceInput i : this.eIn) {
			boolean found=false;
			for (EvidenceInput ii : inputsI) {
				if (i.compare(ii)) {
					found=true;
					break;
				}
			}
			if(!found) return false;
		}

		return true;
	}

	public String getMechanism() {
		return mechanism;
	}

	public void setMechanism(String mechanism) {
		this.mechanism = mechanism;
	}

	public String getState() {
		return state;
	}

	public HashSet<EvidenceInput> geteIn() {
		return (HashSet<EvidenceInput>) eIn.clone();
	}

	public HashSet<EvidenceOutput> geteEo() {
		return (HashSet<EvidenceOutput>) eEo.clone();
	}
	public static boolean comparePreCond(EvidenceTI tit, EvidenceTI tii) {
		// TODO Auto-generated method stub
		return true;
	}
	public static boolean comparePostCond(EvidenceTI tit, EvidenceTI tii) {
		
		// TODO Auto-generated method stub
		return true;
	}
	public static boolean compareOutput(EvidenceTI tit, EvidenceTI tii) {
		HashSet<EvidenceOutput> inputT = tit.geteEo();
		HashSet<EvidenceOutput> inputI = tii.geteEo();
		HashSet<EvidenceOutput> inputIapp=new HashSet<EvidenceOutput>();
		for(EvidenceOutput h:inputI)
			inputIapp.add(new EvidenceOutput(h.getDomain(),h.getKey(),h.getValue()));
		for(EvidenceOutput it:inputT){
			boolean found=false;
			for(EvidenceOutput ii:inputIapp){
				if(it.getDomain().equalsIgnoreCase(ii.getDomain())){
					inputIapp.remove(ii);
					found=true;
					break;
				}
				
			}
			if(!found)
				return false;
		}
		if (inputIapp.size()==0)
			return true;
		return false;
	}
	public static boolean compareInput(EvidenceTI tit, EvidenceTI tii) {
		HashSet<EvidenceInput> inputT = tit.geteIn();
		HashSet<EvidenceInput> inputI = tii.geteIn();
		HashSet<EvidenceInput> inputIapp=new HashSet<EvidenceInput>();
		for(EvidenceInput h:inputI)
			inputIapp.add(new EvidenceInput(h.getDomain(),h.getKey(),h.getValue()));
		for(EvidenceInput it:inputT){
			boolean found=false;
			for(EvidenceInput ii:inputIapp){
				if(it.getDomain().equalsIgnoreCase(ii.getDomain())){
					inputIapp.remove(ii);
					found=true;
					break;
				}
				
			}
			if(!found)
				return false;
		}
		if (inputIapp.size()==0)
			return true;
		return false;
	}

}
