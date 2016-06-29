package ui.properties;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sérgio Gonçalves
 */
public class CodeTypeProperty {
    private StringProperty name;
	private StringProperty type;
    
    public CodeTypeProperty(String name, String type){
        this.name = new SimpleStringProperty(name);
        this.setType(new SimpleStringProperty(type));
    }
    
    public StringProperty getNameProperty(){
        return name;
    }
    
    @Override
    public String toString() {
        return name.getValue();
    }

	public StringProperty getType() {
		return type;
	}

	public void setType(StringProperty type) {
		this.type = type;
	}
}