package org.unimi.utilities;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;

public class TreeFactory {

  public static Tree CreateTree(String init) {

	  	/*Tree t=new Tree(init);
	  	Tree d3_b0= new Tree(init+"d3-b0");
	  	Tree d3_b1= new Tree(init+"d3-b1");
	  	Tree d3_b2= new Tree(init+"d3-b2");
	  	Tree d3_b3= new Tree(init+"d3-b3");
	  	t.addChild(d3_b3);
	  	t.addChild(d3_b2);
	  	t.addChild(d3_b1);
	  	t.addChild(d3_b0);
	  	//d3_b0.addChild(new Tree(init+"d3-b0"));*/
	  Tree t=null;
	  try 
	    {
	        File file = new File(init);
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        Document doc = db.parse(file);
	        doc.getDocumentElement().normalize();
	        System.out.println("Root element " + doc.getDocumentElement().getNodeName());
	        NodeList nodeLst = doc.getChildNodes();
	        System.out.println("Information of all activities");

	        for (int s = 0; s < nodeLst.getLength(); s++) {


	            Node fstNode = nodeLst.item(s);

	          if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
	        	 System.out.println(fstNode.toString());
	        	 t=new Tree(fstNode.getNodeName());
	             getChilds((Element)fstNode,0,t);
	          }

	       }
	       
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	   return t;
	  //System.out.println(t.isSon("mechanism-d4-b0-d3-b1-d2-b5"));
  }
  private static void getChilds(Element fstElmnt,int i,Tree t) {
	  //Element fstElmnt = (Element) fstNode;
      NodeList fstNmElmntLst = fstElmnt.getChildNodes();
      int sz=fstNmElmntLst.getLength();
  	  i=i+1;

      for(int nodes=0;nodes<sz;nodes++)
      {
          Node tempNode=fstNmElmntLst.item(nodes);
          if (tempNode.getNodeType() == Node.ELEMENT_NODE)
          {
        	  	System.out.println(String.valueOf(i)+" -- "+tempNode);
        	  	
        	  	 Tree ts=new Tree(tempNode.getNodeName());
        	  	 t.addChild(ts);
  	             getChilds((Element)tempNode,i,ts);
  	          }

  	       }
              //if(tmpElmnt.getNodeName().equals("activity"))System.out.println("a: "+tmpElmnt.getAttribute("android:name"));
          }


public static void main(String[] args)  throws Exception{
	 CreateTree("/Users/iridium/Documents/workspace/validator/mechanism.xml");
  }

}