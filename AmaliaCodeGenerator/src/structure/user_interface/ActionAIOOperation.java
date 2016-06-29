package structure.user_interface;

import structure.domain_model.Entity;

public class ActionAIOOperation extends ActionAIO{
	private Entity entity;
	private ActionAIOOperationType actionAIOOperationType;
	
	public ActionAIOOperation(String name, String text, ActionAIOOperationType actionAIOOperationType, Double x, Double y, Double width, Double height) {
		super(name, text, x, y, width, height);
		this.setActionAIOOperationType(actionAIOOperationType);
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public String getInfo() {
		String info = "\t\tActionAIO Operation (Operation :" + actionAIOOperationType + " | ";
		info += super.getInfo() + ")";
		return info;
	}

	public ActionAIOOperationType getActionAIOOperationType() {
		return actionAIOOperationType;
	}

	public void setActionAIOOperationType(ActionAIOOperationType actionAIOOperationType) {
		this.actionAIOOperationType = actionAIOOperationType;
	}
}