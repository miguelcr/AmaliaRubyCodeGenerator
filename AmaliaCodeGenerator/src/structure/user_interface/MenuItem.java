package structure.user_interface;

public class MenuItem extends Layer implements LayerTarget {
	private InteractionSpace target; // Required
	
	public MenuItem (String name, String text, Double x, Double y, Double width, Double height){
		super(name, text, x, y, width, height);	
	}
	
	public MenuItem(String name, String text, InteractionSpace target, Double x, Double y, Double width, Double height) {
		super(name, text, x, y, width, height);
		this.target = target;
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
	public String getInfo(){
		String info = super.getInfo();
		return info;
	}
}