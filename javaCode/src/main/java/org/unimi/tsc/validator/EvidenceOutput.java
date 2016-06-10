package org.unimi.tsc.validator;

public class EvidenceOutput {
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
	public EvidenceOutput(String domain,String key,String value){
		this.setDomain(domain);
		this.setKey(key);
		this.setValue(value);
	}
}
