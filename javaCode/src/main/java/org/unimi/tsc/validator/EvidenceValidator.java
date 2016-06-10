package org.unimi.tsc.validator;

import java.util.ArrayList;

public class EvidenceValidator {
	
	
	
	//Evidence validator ha come costruttore un metodo che salva tutte le Evidence dell'instanza
	//Il metoro CompareEvidenceRichiede il path del file delle evidenze del Template che vengono trasformate in evsT che viene comparato con evsI
	//per ogn evidenza del template si scorre tutte le evidenze dell'istanza. [SBAGLIATO, bisogno vedere il mapping]
	

	ArrayList<Evidence> evsI;
	
	
	
	public static boolean compareTestInstance(Evidence t,Evidence i){
		ArrayList<EvidenceTI> tit = t.getEvidenceTI();
		ArrayList<EvidenceTI> tii = i.getEvidenceTI();
		if(tit.size()!=tii.size())
			return false;
		for(int k=0;k<tit.size();k++){
			EvidenceTI tocheckT = tit.get(k);
			EvidenceTI tocheckI = tii.get(k);
			if(!tocheckT.compare(tocheckI))
				return false;
		}
		return true;
	}
	
	
	
	public EvidenceValidator(String pathCM){
		this.evsI=EvidenceFactory.getEvidencesI(pathCM);
	}
	public boolean CompareEvidences(String pathTCM){
		ArrayList<Evidence> evsT=new ArrayList<Evidence>();
		if(pathTCM!=null){
			evsT=EvidenceFactory.getEvidencesI(pathTCM);
		}
			
		ArrayList<Evidence> evsTapp=(ArrayList<Evidence>) evsT.clone();
		//controllare che tutte le evidenze dell'istanza siano mappate su una evidenza del test case
		for(Evidence ei:evsI){
			ArrayList<Evidence> toRemove=new ArrayList<Evidence>();
			for(Evidence et:evsT){
				if(et.compare(ei)){
					//System.out.println("Evidence Template Mappata su Evidence Instanza");
					//System.out.println("\nTEMPLATE:"+et);
					//System.out.println("\nINSTANZA:"+ei);
					toRemove.add(ei);
				}
			}
			if(toRemove.size()==0){
				//System.out.println("Evidence in Instance not compatible with Template Test Cases");
				return false;
			}else{
				try{
				evsTapp.remove(toRemove);
				}catch(Exception e){
					
				}
			}
			
		}
		
		System.out.println("EVIDENCE IN Template NON MAPPATE");
		for(Evidence e:evsTapp)
			System.out.println(e);
		return true;
	}
	
	
	public static void main(String[] args) {
		EvidenceValidator app=new EvidenceValidator("/Users/iridium/Documents/workspace/validator/evidenceInstance.xml");
		System.out.println(app.CompareEvidences("/Users/iridium/Documents/workspace/validator/evidenceTemplate.xml"));
		
		/*
		 * "/Users/iridium/Documents/workspace/validator/l", "n0"); boolean app
		 * = validator.compareModel(
		 * "/Users/iridium/Documents/workspace/validator/graphT.xml", "n0");
		 * System.out.println("RISULTATO:" + app);
		 */

	}



	public boolean CompareEvidences(String evidenceTemplateF,
			ArrayList<Integer> pathsOrderInstance) {
		int i=0,find=0;
		ArrayList<Evidence> evsT=EvidenceFactory.getEvidencesI(evidenceTemplateF);
		ArrayList<Integer> tofind=new ArrayList<Integer>();
		ArrayList<Integer> found=new ArrayList<Integer>();
		
		for(i=0;i<pathsOrderInstance.size();i++){
			tofind.add(new Integer(0));
			found.add(new Integer(0));
		for(Evidence e:evsT){
			if(e.getPath()==i){
				tofind.set(i,new Integer(tofind.get(i).intValue()+1));
			}
		}
		}
		for(i=0;i<pathsOrderInstance.size();i++){
		for(Evidence e:evsT){
			if(e.getPath()==i){
				for(Evidence ei:this.evsI){
					if(ei.getPath()==pathsOrderInstance.get(i).intValue()){
						if(EvidenceValidator.compareTestInstance(e, ei)){
							found.set(i,new Integer(found.get(i).intValue()+1));
							break;
						}
					}
				}
			}
		}
		}
		for(i=0;i<tofind.size();i++){
			int nTemplate,nInstance;
			nTemplate=tofind.get(i).intValue();
			nInstance=found.get(i).intValue();
			if(nTemplate>nInstance)
				return false;
		}
		return true;
	}
}
