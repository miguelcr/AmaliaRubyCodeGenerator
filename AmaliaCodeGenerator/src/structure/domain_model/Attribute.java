package structure.domain_model;

public class Attribute {
	private String name;
	private String type;
	private Boolean referenceType;
	
	public Attribute(String name, String type, Boolean referenceType) {
		this.name = name;
		this.type = type;
		this.referenceType = referenceType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/** 
	 * Check if the attribute is a reference to other entity
	 */	
	public Boolean isReference() {
		return referenceType;
	}

	public void setReference(Boolean referenceType) {
		this.referenceType = referenceType;
	}	
}