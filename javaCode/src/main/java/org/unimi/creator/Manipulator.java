package org.unimi.creator;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.unimi.tsc.validator.Evidence;
import org.unimi.tsc.validator.EvidenceFactory;
import org.unimi.tsc.validator.ToC;
import org.unimi.tsc.validator.TocFactory;
import org.unimi.tsc.validator.graphSTS;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class Manipulator {


	static public void manipulate(String oldModel,String oldP,String oldEvidence,String oldToc,int type,String newModel,String newP,String newEvidence,String newToc) throws Exception{
		 File source=new File(oldP);
		 File dest=new File(newP);
		 FileReader reader = new FileReader(oldP);
         BufferedReader bufferedReader = new BufferedReader(reader);

         String line,root="";

         while ((line = bufferedReader.readLine()) != null){
        	root=line;
         };
         //FileUtils.copyFile(srcFile, destFile);
		 FileUtils.copyFile(source, dest);
		reader.close();
		
		
		if(type==0){

			graphSTS.manipulationFromGraph(oldModel, root, newModel);
			source=new File(oldEvidence);
			dest=new File(newEvidence);
			FileUtils.copyFile(source, dest);
			source=new File(oldToc);
			dest=new File(newToc);
			 FileUtils.copyFile(source, dest);
			return;
		}

		if(type==1){
			String outFile=newEvidence;
			ArrayList<Evidence> evs = EvidenceFactory.getEvidencesI(oldEvidence);
			ArrayList<Evidence> nevs=new ArrayList<Evidence>();
			ArrayList<Integer> app=new ArrayList<Integer>();
			graphSTS g=new graphSTS(oldModel, root);
			for(int i=0;i<g.getGraphI(-1).size();i++){

				app.add(new Integer(0));	
				for(Evidence e:evs){
					if(e.getPath()==i){
						Integer nvalue=new Integer(app.get(i).intValue()+1);
						app.set(i, nvalue);
					}

				}	
			}
			int maxp=0;
			int max=app.get(0).intValue();
			for(int i=1;i<g.getGraphI(-1).size();i++){
				if(max<app.get(i).intValue()){
					maxp=i;
					max=app.get(i).intValue();
				}
			}
			for(int i=0;i<evs.size();){
				Evidence e=evs.get(i);
				if((e.getPath()==maxp)&&(max>1)){
					evs.remove(maxp);
					max--;
				}else{
						i++;
				}
					
				}
			EvidenceFactory.writeOnFile(evs, outFile);
			source=new File(oldModel);
			dest=new File(newModel);
			FileUtils.copyFile(source, dest);
			source=new File(oldToc);
			dest=new File(newToc);
			 FileUtils.copyFile(source, dest);


			return;
		}
		if(type==2){
			String destfile=newToc;
			HashSet<ToC> tocis = TocFactory.getToCs(oldToc);
			Random randomG = new Random();
			int todelete=randomG.nextInt(tocis.size());
			int i=0;
			ArrayList<ToC> tocs=new ArrayList<ToC>();
			for(ToC t:tocis){
				tocs.add(t);
			}
			for(int k=0;k<tocs.size();){
				if(k!=todelete){
					tocs.remove(k);
				}else
					k++;
			}
			ToC[] ntocs=new ToC[tocs.size()];
			i=0;
			for(ToC t:tocs){
				ntocs[i]=t;
				i++;
			}
				TocFactory.writeToFileTocs(ntocs, destfile);
			
				source=new File(oldEvidence);
				dest=new File(newEvidence);
				FileUtils.copyFile(source, dest);
				source=new File(oldModel);
				dest=new File(newModel);
				 FileUtils.copyFile(source, dest);
			return;
		}


	}



	public static void main(String argv[]) throws Exception {
			String[] array={""};
		    String sourceDir="/Volumes/ramdisk/quality/";
		    String destinationDir="/Volumes/ramdisk/negativerecall/";
		    String sourceG,sourceE,sourceT,sourceP;
		    String destG,destE,destT,destP;
		    for(int i=0;i<array.length;i++){
		    	for(int k=1;k<=3;k++){
		    		for(int z=0;z<160;z++){
		    			sourceG=sourceDir+array[i]+"/deep-5/CM-"+k+"/IstanceGraph-"+z+".xml";
		    			sourceE=sourceDir+array[i]+"/deep-5/CM-"+k+"/IstanceEvidence-"+z+".xml";
		    			sourceT=sourceDir+array[i]+"/deep-5/CM-"+k+"/IstanceToC-"+z+".xml";
		    			sourceP=sourceDir+array[i]+"/deep-5/CM-"+k+"/root-"+z+".pt";
		    			destG=destinationDir+array[i]+"/deep-5/CM-"+k+"/IstanceGraph-"+z+".xml";
		    			destE=destinationDir+array[i]+"/deep-5/CM-"+k+"/IstanceEvidence-"+z+".xml";
		    			destT=destinationDir+array[i]+"/deep-5/CM-"+k+"/IstanceToC-"+z+".xml";
		    			destP=destinationDir+array[i]+"/deep-5/CM-"+k+"/root-"+z+".pt";
		    			int type=z%3;
		    			Manipulator.manipulate(sourceG, sourceP, sourceE,sourceT, type,destG,destP,destE,destT);
		    		}
		    	}
		    }
	}
}


