package org.unimi.tsc.validator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TocSaxParser extends DefaultHandler {

	
    public void startDocument() throws SAXException {
    }

    public void endDocument() throws SAXException {
    }
    
    /**
    * Recognizes an XML element to index
    * @param namespaceURI String namespace URI or an empty String
    * @param localName String name of element (with no namespace prefix)
    * @param rawName String XML 1.0 of element name: [namespace prefix]:[localName]
    * @param atts Attributes list for this element
    * @throws SAXException when things go wrong
    */
    public void startElement (String namespaceURI, String localName, 
		String rawName, Attributes atts) throws SAXException {

    }

    /**
    * Buffers management and end of record
    * @param namespaceURI String URI of namespace this element is associated with
    * @param localName String name of element without prefix
    * @param rawName String name of element in XML 1.0 form
    * @throws SAXException when things go wrong
    */
	public void endElement (String namespaceURI, String localName, 
		String rawName) throws SAXException {
    }
    
    /**
    * @param ch char[] character array with character data
    * @param start int index in array where data starts.
    * @param length int length of data in array.
    * @throws SAXException when things go wrong
    */
    public void characters(char ch[], int start, int length)
    throws SAXException {
    }

    public void ignorableWhitespace(char ch[], int start, int length)
    throws SAXException {
    }

}    		