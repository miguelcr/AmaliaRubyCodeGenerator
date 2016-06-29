package structure.user_interface;

import structure.domain_model.Attribute;

public class DataAIO extends Layer{
	private Attribute attribute;
	private DataAIOType type;
	
	public DataAIO(String name, String text, Attribute attribute, DataAIOType dataAIOType, Double x, Double y, Double width, Double height) {
		super(name, text, x, y, width, height);
		this.attribute = attribute;
	}

	public Attribute getAttribute() {
		return attribute;
	}
	
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	
	@Override
	public String getInfo(){
		return "\t\t" + super.getInfo() + " | Affects attribute: " + attribute.getName();
	}

	public DataAIOType getType() {
		return type;
	}

	public void setType(DataAIOType type) {
		this.type = type;
	}
}
