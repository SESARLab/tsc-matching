package org.unimi.checker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;

import org.unimi.tsc.validator.EvidenceValidator;
import org.unimi.tsc.validator.GraphValidator;
import org.unimi.tsc.validator.ModelEvidenceValidator;
import org.unimi.tsc.validator.ToCValidator;

public class Validator {

	public static void main(String[] args) throws Exception {
		//int[] maxN={19,13,11,10,9};
		int[] maxN={3,3,3,4,5,6,7,8,9,10};
		String dd;	
		dd = "/Volumes/ramdisk/negativerecall/deep-";
		// dd="/Users/iridium/Downloads/TEST_PAPER_esaustiva/deep-";
		for(int space=1;space<=3;space++){
			GraphValidator.ALGOSPACE=space;
			//int[] array = { 3, 5, 10, 12, 15 };
			int[] array = { 5 };
			System.out.println("init space="+space);
			for (int y = 0; y < 1; y++) {
				//foreach element of array, all flow deep
				for (int j = 0; //j < 2; j++) {
						j<array.length;j++){
					String d = dd + String.valueOf(array[j]) + "/";
					//for each deep family get all breadthes CM-5 breadth=5 CM-15 breadth=15
					for (int z = 3	; z <= maxN[Math.abs(space-1)]; z++) {
						
						String nfat = String.valueOf(z);
						System.out.println("\n\n\n\n\n N=" + nfat + "\n\n\n");
						for (int i = 0; i < 160; i++) {
							long init = System.nanoTime();
							FileReader reader = new FileReader(d + "CM-" + nfat
									+ "/root-" + String.valueOf(i) + ".pt");
							BufferedReader bufferedReader = new BufferedReader(
									reader);

							String line, root = "";

							while ((line = bufferedReader.readLine()) != null) {
								root = line;
							}
							;
							reader.close();
							//System.out.println(init);
							System.out
							.println("ANALISYS:" + d + "CM-" + nfat
									+ "/IstanceGraph-" + String.valueOf(i)
									+ ".xml");

							ModelEvidenceValidator mev = new ModelEvidenceValidator(
									new GraphValidator(d + "CM-" + nfat
											+ "/IstanceGraph-" + String.valueOf(i)
											+ ".xml", root), new EvidenceValidator(
													d + "CM-" + nfat + "/IstanceEvidence-"
															+ String.valueOf(i) + ".xml"));
							boolean moev = mev.validateEmp3(d + "CM-" + nfat
									+ "/TemplateModel.xml", "n0", d + "CM-" + nfat
									+ "/TemplateEvidence.xml");
							//System.out.println("RISULTATO MODEL+EVIDENCE:" + moev);

							String timeC = "INIT TOC:"
									+ String.valueOf(System.nanoTime() / 1000)
									+ "\n";
							ToCValidator tc = new ToCValidator(d + "CM-" + nfat
									+ "/IstanceToC-" + String.valueOf(i) + ".xml");
							boolean appt = tc.compareTocs(d + "CM-" + nfat
									+ "/TemplateToC.xml");
							timeC += "END TOC:"
									+ String.valueOf(System.nanoTime() / 1000)
									+ "\n";
							//System.out.println("RISULTATO TOC:" + appt);
							//System.out.println("TIMETOC:" + timeC);
							long end = System.nanoTime();
							//System.out.println(end); 
							System.out.println("TIMEALL:"
									+ String.valueOf(end - init));
							if (appt && moev) {
								System.out.println("TSC MATCHING");
							} else
								System.out.println("TSC NOT MATCHING");
							
							FileWriter rootwriter = new FileWriter(d + "CM-" + nfat
									+ "/qualityImb"+space+"-" +y+ ".txt", true);
							rootwriter.write(String.valueOf((end - init) / 1000)
									+ "\n");
							rootwriter.close();
							
							// for(int k=0;k<10;k++)
							System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

							//Thread.sleep(100);

						}
					}
				}
			}
		}
	}
}
