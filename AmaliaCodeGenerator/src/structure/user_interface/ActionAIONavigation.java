package structure.user_interface;

public class ActionAIONavigation extends ActionAIO implements LayerTarget{
	// This attribute can be null temporarily until all the UI XML files in queue to be read are read.
	private InteractionSpace target;
	private ActionAIONavigationType navigationType;
	
	public ActionAIONavigation(String name, String text, InteractionSpace target, ActionAIONavigationType navigationType, Double x, Double y, Double width, Double height) {
		super(name, text, x, y, width, height);
		this.target = target;
		this.navigationType = navigationType;
	}

	@Override
	public InteractionSpace getTarget() {
		return target;
	}

	@Override
	public void setTarget(InteractionSpace target) {
		this.target = target;
	}
	
	@Override
	public String getInfo() {
		String info = "\t\tActionAIO Navigation (Navigation Type: " + navigationType + " | Target IS : ";
		if(target != null)
			info += target.getName();
		else info += "ERRO";
		info += " | ";
		info += super.getInfo() + ")";
		return info;
	}	

	public ActionAIONavigationType getNavigationType() {
		return navigationType;
	}

	public void setNavigationType(ActionAIONavigationType navigationType) {
		this.navigationType = navigationType;
	}
}
