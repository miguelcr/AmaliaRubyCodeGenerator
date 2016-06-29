package structure.domain_model;

public class Method {
	private String name;
	private String output;		
	
	public Method(String name, String output) {
		this.name = name;
		this.output = output;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOutput() {
		return output;
	}
	
	public void setOutput(String output) {
		this.output = output;
	}
		
}
