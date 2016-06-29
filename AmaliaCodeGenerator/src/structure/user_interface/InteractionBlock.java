package structure.user_interface;

import java.util.ArrayList;

import structure.domain_model.Entity;

public class InteractionBlock extends Layer{
	private ArrayList<DataAIO> dataAIOs;
	private ArrayList<ActionAIO> actionAIOs;
	private Entity entity;
	private InteractionBlockType interactionBlockType;
	
	public InteractionBlock(String name, String text, Entity entity, InteractionBlockType interactionBlockType, Double x, Double y, Double width, Double height) {
		super(name, text, x, y, width, height);
		this.dataAIOs = new ArrayList<>();
		this.actionAIOs = new ArrayList<>();
		this.entity = entity;
		this.interactionBlockType = interactionBlockType;
	}

	public ArrayList<DataAIO> getDataAIOs() {
		return dataAIOs;
	}

	public void setDataAIOs(ArrayList<DataAIO> dataAIOs) {
		this.dataAIOs = dataAIOs;
	}

	public ArrayList<ActionAIO> getActionAIOs() {
		return actionAIOs;
	}

	public void setActionAIOs(ArrayList<ActionAIO> actionAIOs) {
		this.actionAIOs = actionAIOs;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public void addDataAIO(DataAIO dataAIO){
		dataAIOs.add(dataAIO);
	}
	
	public void addActionAIO(ActionAIO actionAIO){
		actionAIOs.add(actionAIO);
	}	
	
	public InteractionBlockType getInteractionBlockType() {
		return interactionBlockType;
	}

	public void setInteractionBlockType(InteractionBlockType interactionBlockType) {
		this.interactionBlockType = interactionBlockType;
	}
	
	@Override
	public String getInfo(){
		String info = "";
		info = "InteractionBlock (type: " + interactionBlockType + " | Entity : " + entity.getName();
		info += (")\n\tNumber of DataAIOs : " + dataAIOs.size() + "\n");
		for(DataAIO dataAIO : dataAIOs)
			info += dataAIO.getInfo() + "\n";
		info += ("\n\tNumber of ActionAIOs : " + actionAIOs.size() + "\n");
		for (ActionAIO actionAIO : actionAIOs)
			info += actionAIO.getInfo() + "\n";
		return info;
	}
}
