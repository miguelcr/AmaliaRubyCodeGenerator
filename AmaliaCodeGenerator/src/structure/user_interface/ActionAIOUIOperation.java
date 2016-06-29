package structure.user_interface;

import structure.domain_model.Entity;

public class ActionAIOUIOperation extends ActionAIO{
	private Entity entity; // needed??
	private ActionAIOUIOperationType actionAIOUIOperationType;
	
	public ActionAIOUIOperation(String name, String text, ActionAIOUIOperationType actionAIOUIOperationType, Double x, Double y, Double width, Double height) {
		super(name, text, x, y, width, height);		
		this.setActionAIOUIOperationType(actionAIOUIOperationType);
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public String getInfo() {
		String info = "\t\tActionAIO UI Operation (Operation :" + actionAIOUIOperationType + " | ";
		info += super.getInfo() + ")";
		return info;
	}

	public ActionAIOUIOperationType getActionAIOUIOperationType() {
		return actionAIOUIOperationType;
	}

	public void setActionAIOUIOperationType(ActionAIOUIOperationType actionAIOUIOperationType) {
		this.actionAIOUIOperationType = actionAIOUIOperationType;
	}
}