package org.unimi.tsc.validator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tinkerpop.blueprints.Vertex;

public class EvidenceFactory {

	public static ArrayList<Evidence> getEvidencesI(String fileXML) {
		ArrayList<Evidence> evidences = new ArrayList<Evidence>();
		try {
			//System.out.println("LOADING EVIDENCE FROM:"+fileXML);
			File fXmlFile = new File(fileXML);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :"
			//		+ doc.getDocumentElement().getNodeName());
			//ArrayList<Evidence> result=new ArrayList<Evidence>();
			NodeList nList = doc.getElementsByTagName("evidences");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					//System.out.println("\nCurrent Element :"
					//+ nNode.getNodeName());
					
					
					Element mainElement = (Element) nNode;
					//System.out.println("PATH:"+mainElement.getAttribute("path"));
					Evidence ev = new Evidence(mainElement.getAttribute("path"));
					NodeList nodes = mainElement
							.getElementsByTagName("evidence");
				
					for (int evtemp = 0; evtemp < nodes.getLength(); evtemp++) {
						Node node = nodes.item(evtemp);

						if (node.getNodeType() == Node.ELEMENT_NODE) {

							Element eElement = (Element) node;
							
							HashSet<EvidenceInput> inputs = new HashSet<EvidenceInput>();
							HashSet<EvidenceOutput> outputs = new HashSet<EvidenceOutput>();
							//System.out.println("------- INPUT ----");
							NodeList inputnodes = eElement
									.getElementsByTagName("input");
							for (int i = 0; i < inputnodes.getLength(); i++) {
								Node nodei = inputnodes.item(i);
								if (nodei.getNodeType() == Node.ELEMENT_NODE) {
									Element iElement = (Element) nodei;
									inputs.add(new EvidenceInput(iElement
											.getElementsByTagName("domain").item(0)
											.getTextContent(), iElement
											.getElementsByTagName("key").item(0)
											.getTextContent(), iElement
											.getElementsByTagName("value").item(0)
											.getTextContent()));
								}
								/*
								inputs.add(new EvidenceInput(eElement
										.getElementsByTagName("domain").item(i)
										.getTextContent(), eElement
										.getElementsByTagName("key").item(i)
										.getTextContent(), eElement
										.getElementsByTagName("value").item(i)
										.getTextContent()));
								/*	System.out.println("key:"
										+ eElement.getElementsByTagName("key")
												.item(i).getTextContent());
								System.out.println("value:"
										+ eElement
												.getElementsByTagName("value")
												.item(i).getTextContent());
								if (eElement.getElementsByTagName("domain")
										.getLength() > i)
									System.out.println("Domain:"
											+ eElement
													.getElementsByTagName(
															"domain").item(i)
													.getTextContent());*/
								// events.add(eElement.getElementsByTagName("").item(i).getTextContent());
							}
							//System.out.println("-------EXPECTED OUTPUT----");
							NodeList outputnodes = eElement
									.getElementsByTagName("output");
							for (int i = 0; i < outputnodes.getLength(); i++) {
								Node nodei = outputnodes.item(i);
								if (nodei.getNodeType() == Node.ELEMENT_NODE) {
									Element iElement = (Element) nodei;
									outputs.add(new EvidenceOutput(iElement
											.getElementsByTagName("domain").item(0)
											.getTextContent(), iElement
											.getElementsByTagName("key").item(0)
											.getTextContent(), iElement
											.getElementsByTagName("value").item(0)
											.getTextContent()));
								}
							
							
							}
							/*for (int i = 0; i < eElement.getElementsByTagName("output").getLength(); i++) {
								outputs.add(new EvidenceOutput(eElement
										.getElementsByTagName("output").item(i).get
										.getTextContent(), eElement.getElementsByTagName("key").item(i).getTextContent(), eElement
										.getElementsByTagName("value").item(i)
										.getTextContent()));
								
								System.out.println("key:"
										+ eElement.getElementsByTagName("key")
												.item(i).getTextContent());
								System.out.println("value:"
										+ eElement
												.getElementsByTagName("value")
												.item(i).getTextContent());
								if (eElement.getElementsByTagName("domain")
										.getLength() > i)
									System.out.println("Domain:"
											+ eElement
													.getElementsByTagName(
															"domain").item(i)
													.getTextContent());
								// events.add(eElement.getElementsByTagName("").item(i).getTextContent());
							}*/


							EvidenceTI evti = new EvidenceTI(eElement
									.getElementsByTagName("state").item(0)
									.getTextContent(), eElement
									.getElementsByTagName("mechanism").item(0)
									.getTextContent(), inputs, outputs);
							//System.out.println(evti);
							ev.add(evti);
						}
					}
					evidences.add(ev);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return evidences;

	}

	
	
	public static void writeEvidenceFromTemplate(ArrayList<Evidence> allEvidence,String file){
		try{
			BaseXOntologyManager basex = new BaseXOntologyManager(BasexFactory.getHost(), 1984, "admin", "admin", "mechanism");
			//BaseXOntologyManager basex=BasexFactory.getBasex();
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();


			Element rootElement = doc.createElement("allEvidences");
			doc.appendChild(rootElement);
			
			Random randomGenerator = new Random();
			for(Evidence e:allEvidence){
				
				//int nTCperPath=randomGenerator.nextInt(4);
				//System.out.println("NUMERO DI TESTCASE PER PATH "+pathC+"="+nTCperPath);
				
				// testcase elements
				Element testcase = doc.createElement("evidences");
				Attr attrPath = doc.createAttribute("path");

					//TODO path parameter
					attrPath.setValue(String.valueOf(e.getPath()));
					testcase.setAttributeNode(attrPath);
					

					//for each state
					ArrayList<EvidenceTI> tis=e.getEvidenceTI();
					for(EvidenceTI ti:tis){
						//Vertex v=path.get(z);
						Element evidence=doc.createElement("evidence");



						Element mechanism=doc.createElement("mechanism");
						//TODO meccanismo da grafo originale
						
						mechanism.appendChild(doc.createTextNode(" "));
						evidence.appendChild(mechanism);

						Element state=doc.createElement("state");
						//TODO stato da grafo originale
						state.appendChild(doc.createTextNode(""));
						evidence.appendChild(state);

						Element inputs=doc.createElement("inputs");
						evidence.appendChild(inputs);


						Element input=doc.createElement("input");

						String domainN=basex.getDomain(ti.getMechanism());
						String domainI=String.valueOf(randomGenerator.nextInt(Integer.parseInt(domainN)));
						String domainO=String.valueOf(randomGenerator.nextInt(Integer.parseInt(domainN)));
						Element key=doc.createElement("key");
						//TODO key parameter
						key.appendChild(doc.createTextNode("key"));
						input.appendChild(key);

						Element value=doc.createElement("value");
						//TODO value parameter
						value.appendChild(doc.createTextNode(UUID.randomUUID().toString()));
						input.appendChild(value);

						Element domain=doc.createElement("domain");
						//TODO value parameter
						domain.appendChild(doc.createTextNode("d"+domainI));
						input.appendChild(domain);
						inputs.appendChild(input);



						Element outputs=doc.createElement("expectedOutput");
						
						//for each output
						Element output=doc.createElement("output");


						Element keyo=doc.createElement("key");
						//TODO key parameter
						keyo.appendChild(doc.createTextNode("key"));
						output.appendChild(keyo);

						Element valueo=doc.createElement("value");
						//TODO value parameter
						valueo.appendChild(doc.createTextNode(UUID.randomUUID().toString()));
						output.appendChild(valueo);

						Element domaino=doc.createElement("domain");
						//TODO value parameter
						domaino.appendChild(doc.createTextNode("d"+domainO));
						output.appendChild(domaino);
						outputs.appendChild(output);
						evidence.appendChild(outputs);	


						testcase.appendChild(evidence);

					}

					rootElement.appendChild(testcase);
				
			}



			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("/Users/iridium/Documents/workspace/validator/"+file+".xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	public static void writeEvidence(ArrayList<ArrayList<Vertex>> allpaths,String file,int nTC){
		try {
			BaseXOntologyManager basex = new BaseXOntologyManager(BasexFactory.getHost(), 1984, "admin", "admin", "mechanism");
			//BaseXOntologyManager basex=BasexFactory.getBasex();
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();


			Element rootElement = doc.createElement("allEvidences");
			doc.appendChild(rootElement);
			int pathC=-1;
			Random randomGenerator = new Random();
			for(int j=allpaths.size()-1;j>=0;j--){
				ArrayList<Vertex> path=allpaths.get(j);
				pathC++;
				int nTCperPath;
				if(nTC==0){
				nTCperPath=randomGenerator.nextInt(4);
				}else{
					nTCperPath=nTC;
				}
				//System.out.println("NUMERO DI TESTCASE PER PATH "+pathC+"="+nTCperPath);
				
				for(int k=0;k<nTCperPath;k++){
					// testcase elements
					Element testcase = doc.createElement("evidences");
					Attr attrPath = doc.createAttribute("path");

					//TODO path parameter
					attrPath.setValue(String.valueOf(pathC));
					testcase.setAttributeNode(attrPath);
					

					//for each state
					int l=path.size()-1;
					for(int z=l;z>=0;z--){
						Vertex v=path.get(z);
						Element evidence=doc.createElement("evidence");



						Element mechanism=doc.createElement("mechanism");
						//TODO meccanismo parameter
						mechanism.appendChild(doc.createTextNode(v.getProperty("mechanism").toString()));
						evidence.appendChild(mechanism);

						Element state=doc.createElement("state");
						//TODO stato parameter
						state.appendChild(doc.createTextNode(v.getId().toString()));
						evidence.appendChild(state);

						Element inputs=doc.createElement("inputs");
						evidence.appendChild(inputs);


						Element input=doc.createElement("input");

						String domainN=basex.getDomain(v.getProperty("mechanism").toString());
						//System.out.println("DOMINIO di"+v.getProperty("mechanism").toString()+" -->"+domainN);
						String domainI=String.valueOf(randomGenerator.nextInt(Integer.parseInt(domainN)));
						String domainO=String.valueOf(randomGenerator.nextInt(Integer.parseInt(domainN)));
						Element key=doc.createElement("key");
						//TODO key parameter
						key.appendChild(doc.createTextNode("key"));
						input.appendChild(key);

						Element value=doc.createElement("value");
						//TODO value parameter
						value.appendChild(doc.createTextNode(UUID.randomUUID().toString()));
						input.appendChild(value);

						Element domain=doc.createElement("domain");
						//TODO value parameter
						domain.appendChild(doc.createTextNode("d"+domainI));
						input.appendChild(domain);
						inputs.appendChild(input);



						Element outputs=doc.createElement("expectedOutput");
						
						//for each output
						Element output=doc.createElement("output");


						Element keyo=doc.createElement("key");
						//TODO key parameter
						keyo.appendChild(doc.createTextNode("key"));
						output.appendChild(keyo);

						Element valueo=doc.createElement("value");
						//TODO value parameter
						valueo.appendChild(doc.createTextNode(UUID.randomUUID().toString()));
						output.appendChild(valueo);

						Element domaino=doc.createElement("domain");
						//TODO value parameter
						domaino.appendChild(doc.createTextNode("d"+domainO));
						output.appendChild(domaino);
						outputs.appendChild(output);
						evidence.appendChild(outputs);	


						testcase.appendChild(evidence);

					}

					rootElement.appendChild(testcase);
				}
			}



			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(file));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	
	
	
	
	public static void writeEvidenceT(ArrayList<ArrayList<Vertex>> allpaths,String file,int nTC){
		try {
			BaseXOntologyManager basex = new BaseXOntologyManager(BasexFactory.getHost(), 1984, "admin", "admin", "mechanism");
			//BaseXOntologyManager basex=BasexFactory.getBasex();
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();


			Element rootElement = doc.createElement("allEvidences");
			doc.appendChild(rootElement);
			int pathC=-1;
			Random randomGenerator = new Random();
			for(int j=0;j<allpaths.size();j++){
				ArrayList<Vertex> path=allpaths.get(j);
				pathC++;
				int nTCperPath;
				if(nTC==0){
				nTCperPath=randomGenerator.nextInt(4);
				}else{
					nTCperPath=nTC;
				}
				//System.out.println("NUMERO DI TESTCASE PER PATH "+pathC+"="+nTCperPath);
				
				for(int k=0;k<nTCperPath;k++){
					// testcase elements
					Element testcase = doc.createElement("evidences");
					Attr attrPath = doc.createAttribute("path");

					//TODO path parameter
					attrPath.setValue(String.valueOf(pathC));
					testcase.setAttributeNode(attrPath);
					

					//for each state
					int l=path.size()-1;
					for(int z=l;z>=0;z--){
						Vertex v=path.get(z);
						Element evidence=doc.createElement("evidence");



						Element mechanism=doc.createElement("mechanism");
						//TODO meccanismo parameter
						mechanism.appendChild(doc.createTextNode(v.getProperty("mechanism").toString()));
						evidence.appendChild(mechanism);

						Element state=doc.createElement("state");
						//TODO stato parameter
						state.appendChild(doc.createTextNode(v.getId().toString()));
						evidence.appendChild(state);

						Element inputs=doc.createElement("inputs");
						evidence.appendChild(inputs);


						Element input=doc.createElement("input");

						String domainN=basex.getDomain(v.getProperty("mechanism").toString());
						//System.out.println("DOMINIO di"+v.getProperty("mechanism").toString()+" -->"+domainN);
						String domainI=String.valueOf(randomGenerator.nextInt(Integer.parseInt(domainN)));
						String domainO=String.valueOf(randomGenerator.nextInt(Integer.parseInt(domainN)));
						Element key=doc.createElement("key");
						//TODO key parameter
						key.appendChild(doc.createTextNode("key"));
						input.appendChild(key);

						Element value=doc.createElement("value");
						//TODO value parameter
						value.appendChild(doc.createTextNode(UUID.randomUUID().toString()));
						input.appendChild(value);

						Element domain=doc.createElement("domain");
						//TODO value parameter
						domain.appendChild(doc.createTextNode("d"+domainI));
						input.appendChild(domain);
						inputs.appendChild(input);



						Element outputs=doc.createElement("expectedOutput");
						
						//for each output
						Element output=doc.createElement("output");


						Element keyo=doc.createElement("key");
						//TODO key parameter
						keyo.appendChild(doc.createTextNode("key"));
						output.appendChild(keyo);

						Element valueo=doc.createElement("value");
						//TODO value parameter
						valueo.appendChild(doc.createTextNode(UUID.randomUUID().toString()));
						output.appendChild(valueo);

						Element domaino=doc.createElement("domain");
						//TODO value parameter
						domaino.appendChild(doc.createTextNode("d"+domainO));
						output.appendChild(domaino);
						outputs.appendChild(output);
						evidence.appendChild(outputs);	


						testcase.appendChild(evidence);

					}

					rootElement.appendChild(testcase);
				}
			}



			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(file));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	
	
	
	
	public static void main(String[] args) throws IOException {
		/*ArrayList<Evidence> evs=EvidenceFactory
			.getEvidencesI("/Users/iridium/Documents/workspace/validator/evidenceT.xml");
	for(Evidence e:evs){
		System.out.println(e);
	}
	/*
		 * "/Users/iridium/Documents/workspace/validator/l", "n0"); boolean app
		 * = validator.compareModel(
		 * "/Users/iridium/Documents/workspace/validator/graphT.xml", "n0");
		 * System.out.println("RISULTATO:" + app);
		 */
		
		
		
		//CREATE EVIDENCE
		GraphValidator validator = new GraphValidator("/Users/iridium/Documents/workspace/validator/graphT2.xml","n0");
		
		writeEvidence(validator.getGi().getGraphI(-1),"/Users/iridium/Documents/workspace/validator/evidenceTemplateMain.xml",0);
		
		
		
		//summuryEvidence("/Users/iridium/Documents/workspace/validator/evidenceInstance.xml");
	}
	//"/Users/iridium/Documents/workspace/validator/evidenceInstance.xml"
	public static void summuryEvidence(String file){
		ArrayList<Evidence> evI=EvidenceFactory.getEvidencesI(file);
		for(Evidence ev:evI){
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			System.out.println("EVIDENCE FOR PATH:"+ev.getPath());
			System.out.println(ev);
		}
		
	}



	public static ArrayList<Evidence> writeEvidenceFromTemplate(
			ArrayList<Evidence> templateEvidences, ArrayList<Vertex> v, int pathIDi,int pathIDt ) {
		ArrayList<Evidence> iEvidences=new ArrayList<Evidence>();
		for(Evidence et:templateEvidences){
			if(et.getPath()==pathIDt){
				Evidence ei=new Evidence(String.valueOf(pathIDi));
				ArrayList<EvidenceTI> tits = et.getEvidenceTI();
				//ArrayList<EvidenceTI> tiis= new ArrayList<EvidenceTI>();
				//FILIPPO CONTROLLARE
				int k=0;
				for(int i=v.size()-1;i>=0;i--){
					EvidenceTI tit=tits.get(k);
					k++;
					EvidenceTI tii=new EvidenceTI(v.get(i).getId().toString(), v.get(i).getProperty("mechanism").toString(),tit.geteIn(), tit.geteEo());
					ei.add(tii);
				}
				
				iEvidences.add(ei);
				}
			}
		
		
		
		// TODO Auto-generated method stub
		return iEvidences;
	}
	
	public static void writeOnFile(ArrayList<Evidence> allEvidence,String outFile) throws Exception{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();


		Element rootElement = doc.createElement("allEvidences");
		doc.appendChild(rootElement);
		
		Random randomGenerator = new Random();
		for(Evidence e:allEvidence){
			
			//int nTCperPath=randomGenerator.nextInt(4);
			//System.out.println("NUMERO DI TESTCASE PER PATH "+pathC+"="+nTCperPath);
			
			// testcase elements
			Element testcase = doc.createElement("evidences");
			Attr attrPath = doc.createAttribute("path");

				//TODO path parameter
				attrPath.setValue(String.valueOf(e.getPath()));
				testcase.setAttributeNode(attrPath);
				

				//for each state
				ArrayList<EvidenceTI> tis=e.getEvidenceTI();
				for(EvidenceTI ti:tis){
					//Vertex v=path.get(z);
					Element evidence=doc.createElement("evidence");



					Element mechanism=doc.createElement("mechanism");
					//TODO meccanismo da grafo originale
					
					mechanism.appendChild(doc.createTextNode(ti.getMechanism()));
					evidence.appendChild(mechanism);

					Element state=doc.createElement("state");
					//TODO stato da grafo originale
					state.appendChild(doc.createTextNode(ti.getState()));
					evidence.appendChild(state);

					Element inputs=doc.createElement("inputs");
					evidence.appendChild(inputs);


					Element input=doc.createElement("input");

					
					//for input
					HashSet<EvidenceInput> inputE=ti.geteIn();
					for(EvidenceInput in:inputE){
					Element key=doc.createElement("key");
					//TODO key parameter
					key.appendChild(doc.createTextNode(in.getKey()));
					input.appendChild(key);

					Element value=doc.createElement("value");
					//TODO value parameter
					value.appendChild(doc.createTextNode(in.getValue()));
					input.appendChild(value);

					Element domain=doc.createElement("domain");
					//TODO value parameter
					domain.appendChild(doc.createTextNode(in.getDomain()));
					input.appendChild(domain);
					inputs.appendChild(input);
					}


					Element outputs=doc.createElement("expectedOutput");
					HashSet<EvidenceOutput> outputE=ti.geteEo();
					for(EvidenceOutput eo:outputE){
					//for each output
					Element output=doc.createElement("output");


					Element keyo=doc.createElement("key");
					//TODO key parameter
					keyo.appendChild(doc.createTextNode(eo.getKey()));
					output.appendChild(keyo);

					Element valueo=doc.createElement("value");
					//TODO value parameter
					valueo.appendChild(doc.createTextNode(eo.getValue()));
					output.appendChild(valueo);
					
					Element domaino=doc.createElement("domain");
					//TODO value parameter
					domaino.appendChild(doc.createTextNode(eo.getDomain()));
					output.appendChild(domaino);
					outputs.appendChild(output);
					}
					evidence.appendChild(outputs);	


					testcase.appendChild(evidence);

				}

				rootElement.appendChild(testcase);
			
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
}
