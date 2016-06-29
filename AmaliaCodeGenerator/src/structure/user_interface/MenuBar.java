package structure.user_interface;

import java.util.ArrayList;

public class MenuBar extends Layer{
	private ArrayList<Menu> menus;

	public MenuBar(String name, String text, Double x, Double y, Double width, Double height) {
		super(name, text, x, y, width, height);
		menus = new ArrayList<>();
	}

	public void addMenu(Menu menu){
		menus.add(menu);
	}	
	
	public ArrayList<Menu> getMenus() {
		return menus;
	}

	public void setMenus(ArrayList<Menu> menus) {
		this.menus = menus;
	}
	
	@Override
	public String getInfo(){
		String info = "\tMenuBar (" + super.getInfo() + ")\n";
		info += "\tNumber of Menus: " + menus.size() + "\n";
		for(Menu menu : menus)
			info += "\t\t"+menu.getInfo() + "\n";
		return info;
	}
}