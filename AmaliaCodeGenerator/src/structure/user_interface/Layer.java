package structure.user_interface;

public class Layer {
	private String name; // AKA id
	private String text; // Required
	private Double x, y, width, height;
	
	
	public Layer(String name, String text) {
		this.name = name;
		this.text = text;
	}
	
	public Layer(String name, String text, Double x, Double y, Double width, Double height) {
		this.name = name;
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getInfo(){
		String info = "Layer name/id: " + getName() + " | text: " + getText() + " | x:" + getX() + 
				" | y:" + getY() + " | width:" + getWidth() + " | height:" + getHeight();
		return info;
	}
}
