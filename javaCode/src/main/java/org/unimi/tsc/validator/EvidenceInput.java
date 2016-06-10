package org.unimi.tsc.validator;

public class EvidenceInput {
	private String domain,key,value;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public EvidenceInput(String domain,String key,String value){
		this.setDomain(domain);
		this.setKey(key);
		this.setValue(value);
	}

	public boolean compare(EvidenceInput ii) {
		if(!this.domain.equalsIgnoreCase(ii.getDomain()))
			return false;
		if(!this.key.equalsIgnoreCase(ii.getKey()))
			return false;
		return true;
		//check domain e value
	}
}
