package ui.properties;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import structure.user_interface.InteractionSpace;

/**
 *
 * @author Sérgio GOnçalves
 */
public class InteractionSpaceProperty {
    private StringProperty name;
    private StringProperty text;
    private InteractionSpace interactionSpace;
    private boolean dummy = false;
    
    public InteractionSpaceProperty(InteractionSpace interactionSpace){
        this.interactionSpace = interactionSpace;
        this.name = new SimpleStringProperty(interactionSpace.getName());
        this.text = new SimpleStringProperty(interactionSpace.getText());
    }
    
    // Dummy Object Constructor
    public InteractionSpaceProperty(String dummyName){
        dummy = true;
        this.name = new SimpleStringProperty(dummyName);
        this.text = new SimpleStringProperty(dummyName);
        interactionSpace = null;
    }     
    
    public StringProperty getNameProperty(){
        return name;
    }
    
    public StringProperty getTextProperty(){
        return text;
    }
    
    public InteractionSpace getInteractionSpace(){
        return interactionSpace;
    }
    
    public boolean isDummy() {
        return dummy;
    }
    
    @Override
    public String toString() {
        return name.getValue();
    }
}