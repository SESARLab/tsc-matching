package org.unimi.checker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.unimi.tsc.validator.EvidenceValidator;
import org.unimi.tsc.validator.GraphValidator;
import org.unimi.tsc.validator.ModelEvidenceValidator;
import org.unimi.tsc.validator.ToCValidator;

public class BinderCalculator {

	public static void main(String[] args)  throws Exception{
		BinderCalculator.calculator();
	}
	
	public static void calculator() throws Exception{
		String dd;
		dd="/Volumes/ramdisk/quality/deep-";
		int[] array={5};

		
		for(int j=0;j<array.length;j++){
				//array.lengt	h;j++){
			String d=dd+String.valueOf(array[j])+"/";
	for(int z=9;z<=9	;z++){	
		String nfat=String.valueOf(z);
		System.out.println("\n\n\n\n\n N="+nfat+"\n\n\n");
		for(int i=0;i<160;i++){
		
		 FileReader reader = new FileReader(d+"CMm1-"+nfat+"/root-"+String.valueOf(i)+".pt");
         BufferedReader bufferedReader = new BufferedReader(reader);
         String line,root="";
         while ((line = bufferedReader.readLine()) != null){
        	root=line;
         };
         reader.close();
		 System.out.println("ANALISYS:"+d+"CMm1-"+nfat+"/IstanceGraph-"+String.valueOf(i)+".xml"	);
		 
		 ModelEvidenceValidator mev=new ModelEvidenceValidator(new GraphValidator(d+"CMm1-"+nfat+"/IstanceGraph-"+String.valueOf(i)+".xml",root),new  EvidenceValidator(d+"CMm1-"+nfat+"/IstanceEvidence-"+String.valueOf(i)+".xml"));
			System.out.println("RISULTATO MODEL+EVIDENCE:"+mev.binder(d+"CMm1-"+nfat+"/TemplateModel.xml", "n0"));

			for(int k=0;k<10;k++)
				System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			
			//Thread.sleep(100);
			
		}
	}
	}
		
	}
}
