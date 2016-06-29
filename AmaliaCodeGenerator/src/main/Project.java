package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exceptions.ParserException;
import structure.domain_model.DomainModel;
import structure.user_interface.InteractionSpace;

public class Project {
	private DomainModel domainModel;
	private HashMap<String, InteractionSpace> interactionSpaces;
	private InteractionSpace mainInteractionSpace;
		
	public Project() {		
		this.interactionSpaces = new HashMap<>();
	}
	
	public Project(DomainModel domainModel, HashMap<String, InteractionSpace> interactionSpaces) {
		this.domainModel = domainModel;
		this.interactionSpaces = interactionSpaces;
	}

	public DomainModel getDomainModel() {
		return domainModel;
	}

	public void setDomainModel(DomainModel domainModel) {
		this.domainModel = domainModel;
	}

	public Project(HashMap<String, InteractionSpace> interactionSpaces) {		
		this.interactionSpaces = interactionSpaces;
	}
	
	public void addInteractionSpace (InteractionSpace interactionSpace) throws ParserException{
		if(interactionSpaces.get(interactionSpace.getName()) == null)
			interactionSpaces.put(interactionSpace.getName(), interactionSpace);
		else
			throw new ParserException(interactionSpace.getFileName() + " and " + 
					interactionSpaces.get(interactionSpace.getName()).getFileName() + ": InteractionSpaces with same ID/Name (" + interactionSpace.getName() +
					")");
	}

	public HashMap<String, InteractionSpace> getInteractionSpaces() {
		return interactionSpaces;
	}

	public void setInteractionSpaces(HashMap<String, InteractionSpace> interactionSpaces) {
		this.interactionSpaces = interactionSpaces;
	}	
	
	public List<InteractionSpace> getInteractionSpacesWithParams(){
		ArrayList<InteractionSpace> is = new ArrayList<>();
		for(InteractionSpace i : interactionSpaces.values()){
			if(i.needParam()) is.add(i);
		}
			
		return is;				
	}
	
	public List<InteractionSpace> getInteractionSpacesWithoutParams(){
		ArrayList<InteractionSpace> is = new ArrayList<>();
		for(InteractionSpace i : interactionSpaces.values()){
			if(!i.needParam()) is.add(i);
		}
			
		return is;				
	}	
	
	/**
	 * Searches for an InteractionSpace with the specified name
	 * @param name Name of the InteractionSpace
	 * @return The InteractionSpace object, or null of not found
	 */
	public InteractionSpace getInteractionSpace(String name){
		return interactionSpaces.get(name);	
	}

	public InteractionSpace getMainInteractionSpace() {
		return mainInteractionSpace;
	}

	public void setMainInteractionSpace(InteractionSpace mainInteractionSpace) {
		this.mainInteractionSpace = mainInteractionSpace;
	}
}