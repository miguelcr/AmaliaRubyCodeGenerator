package structure.user_interface;

import java.util.ArrayList;

import structure.domain_model.Entity;

public class InteractionSpace extends Layer{
	private ArrayList<InteractionBlock> interactionBlocks;
	private ArrayList<ActionAIO> actionAIOs;
	private MenuBar menuBar;
	private String modelName; // Value of the attribute "nome" in <UIM> node of XML
	private String domainModelFileName; // Value of the attribute "dm" in <UIM> node of XML
	private String fileName; // Name of the User Interface Model containing this Interaction Space
	private Entity entity; // Entity related in this Interaction Space
	
	public InteractionSpace(String name, String text, ArrayList<InteractionBlock> interactionBlocks,
			ArrayList<ActionAIO> actionAIOs, MenuBar menuBar, Entity entity, Double x, Double y, Double width, Double height) {
		super(name, text, x, y, width, height);
		this.interactionBlocks = interactionBlocks;
		this.actionAIOs = actionAIOs;
		this.menuBar = menuBar;
		this.entity = entity;
	}
	
	public InteractionSpace(String name, String text, String modelName, String domainModelFileName, 
			String fileName, Entity entity, Double x, Double y, Double width, Double height){
		super(name, text, x, y, width, height);
		this.setFileName(fileName);
		this.modelName = modelName;
		this.entity = entity;
		this.domainModelFileName = domainModelFileName;
		interactionBlocks = new ArrayList<>();
		actionAIOs = new ArrayList<>();
		menuBar = null;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getDomainModelFileName() {
		return domainModelFileName;
	}

	public void setDomainModelFileName(String domainModelFileName) {
		this.domainModelFileName = domainModelFileName;
	}

	public ArrayList<InteractionBlock> getInteractionBlocks() {
		return interactionBlocks;
	}
	
	public void setInteractionBlocks(ArrayList<InteractionBlock> interactionBlocks) {
		this.interactionBlocks = interactionBlocks;
	}
	
	public ArrayList<ActionAIO> getActionAIOs() {
		return actionAIOs;
	}
	
	public void setActionAIOs(ArrayList<ActionAIO> actionAIOs) {
		this.actionAIOs = actionAIOs;
	}
	
	public MenuBar getMenuBar() {
		return menuBar;
	}
	
	public void setMenuBar(MenuBar menuBar) {
		this.menuBar = menuBar;
	}
	
	public void addInteractionBlock(InteractionBlock interactionBlock){
		interactionBlocks.add(interactionBlock);
	}
	
	public void addActionAIO(ActionAIO actionAIO){
		actionAIOs.add(actionAIO);
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public String getInfo(){
		String info = "";
		info = ("InteractionSpace (Name: " + getName());
		info += (" | Model Name: " + getModelName());
		info += (" | DM File Name: " + getDomainModelFileName());
		info += (")\n\t" + super.getInfo());
		info += ("\n\tNumber of InteractionBlocks: " + interactionBlocks.size() + "\n");
		
		for(InteractionBlock ib : interactionBlocks)
			info += ib.getInfo();
	
		info += "# ACTION AIO'S # SIZE : " + actionAIOs.size() + "\n";
		for(ActionAIO actionAIO : actionAIOs){
			info += actionAIO.getInfo() + "\n";
		}
		if(menuBar != null){
			info += "# MEMU #\n";
			info += menuBar.getInfo();
		}
					
		return info;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public boolean needParam(){
		boolean need = false;
		for(InteractionBlock ib : interactionBlocks){
			if(ib.getInteractionBlockType() == InteractionBlockType.ViewRelatedList || 
					ib.getInteractionBlockType() == InteractionBlockType.ViewRelatedEntity)
				need = true;
			else if(ib.getInteractionBlockType() == InteractionBlockType.ViewEntity){
				if(ib.getActionAIOs().size() > 0){
					for(ActionAIO aio : ib.getActionAIOs()){
						if(aio instanceof ActionAIOOperation){
							ActionAIOOperation aioOp = (ActionAIOOperation) aio;
							if(aioOp.getActionAIOOperationType() == ActionAIOOperationType.CallDeleteOp || 
									aioOp.getActionAIOOperationType() == ActionAIOOperationType.CallUpdateOp)
								need = true;
						}
					}
				}
			}
		}
		return need;
	}
}
