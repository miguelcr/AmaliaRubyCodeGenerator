package structure.user_interface;

import java.util.ArrayList;

public class Menu extends Layer{
	private ArrayList<MenuItem> menuItems;
	
	public Menu(String name, String text, Double x, Double y, Double width, Double height) {
		super(name, text, x, y, width, height);
		menuItems = new ArrayList<>();
	}

	public void addMenuItem(MenuItem menuItem){
		this.menuItems.add(menuItem);
	}
	
	public ArrayList<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(ArrayList<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}	
}