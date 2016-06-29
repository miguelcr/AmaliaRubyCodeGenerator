package xml.parser;

//Class that will represent a system file name
import java.io.File;
//Triggered when an I/O error occurs
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

//Represents your XML document and contains useful methods
import org.jdom2.Document;
//Represents XML elements and contains useful methods
import org.jdom2.Element;
//Top level JDOM exception
import org.jdom2.JDOMException;
//Creates a JDOM document parsed using SAX Simple API for XML
import org.jdom2.input.SAXBuilder;

import exceptions.ParserException;
import main.Project;
import main.Utils;
import structure.domain_model.Attribute;
import structure.domain_model.Entity;
import structure.user_interface.ActionAIONavigation;
import structure.user_interface.ActionAIONavigationType;
import structure.user_interface.ActionAIOOperation;
import structure.user_interface.ActionAIOOperationType;
import structure.user_interface.ActionAIOUIOperation;
import structure.user_interface.ActionAIOUIOperationType;
import structure.user_interface.DataAIO;
import structure.user_interface.DataAIOType;
import structure.user_interface.InteractionBlock;
import structure.user_interface.InteractionBlockType;
import structure.user_interface.InteractionSpace;
import structure.user_interface.LayerTarget;
import structure.user_interface.Menu;
import structure.user_interface.MenuBar;
import structure.user_interface.MenuItem;

/**
 * This class has the power to read an User Interface Model file and create an
 * Interaction space based on the contents of the file (Layers/Elements)
 * 
 * @author Sérgio Gonçalves
 */
public class UserInterfaceXMLParserJDOM {
	private InteractionSpace interactionSpace;
	private Project project;
	private HashMap<LayerTarget, ArrayList<String>> missingTarget;
	private final String ROOT = "UIM";
	private ResourceBundle rb;

	/**
	 * Constructor
	 */
	public UserInterfaceXMLParserJDOM(ResourceBundle rb) {
		this.rb = rb;
	}

	/**
	 * Reads a User Interface Model file and return an Interaction Space. This
	 * method ignores missing targets
	 * 
	 * @param path
	 *            The path for the file
	 * @param project
	 *            The project where this Interaction Space belong
	 * @return The read file in form of an Interaction Space
	 * @throws ParserException
	 */
	public InteractionSpace readXMLIgnoreTargets(String path, Project project) throws ParserException {
		this.missingTarget = new HashMap<>();
		this.project = project;
		return read(new File(path));
	}

	/**
	 * Reads a UIModel file and return an Interaction Space
	 * 
	 * @param path
	 *            The path for the file
	 * @param missingTarget
	 *            A map that will contain the layers with missing targets
	 * @param project
	 *            The project where this InteractionSpace belong
	 * @return The read file in form of an Interaction Space
	 * @throws ParserException
	 */
	public InteractionSpace readXML(String path, Project project, HashMap<LayerTarget, ArrayList<String>> missingTarget)
			throws ParserException {
		this.missingTarget = missingTarget;
		this.project = project;
		return read(new File(path));
	}

	/**
	 * Reads a UIModel file and return an Interaction Space
	 * 
	 * @param fileUI
	 *            The User Interface file
	 * @param missingTarget
	 *            A map that will contain the layers with missing targets
	 * @param project
	 *            The project where this Interaction Space belong
	 * @return The read file in form of an Interaction Space
	 * @throws ParserException
	 */
	public InteractionSpace readXML(File fileUI, Project project, HashMap<LayerTarget, ArrayList<String>> missingTarget)
			throws ParserException {
		this.missingTarget = missingTarget;
		this.project = project;

		return read(fileUI);
	}

	private InteractionSpace read(File file) throws ParserException {
		interactionSpace = null;
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;

		if (file == null)
			throw new ParserException("The file '" + file + "' was not found.");

		try {
			doc = builder.build(file);
		} catch (JDOMException | IOException e) {
			throw new ParserException("The file '" + file + "' was not found.");
		}

		Element root = doc.getRootElement();

		// Check if the file root is valid
		if (!root.getName().equals(ROOT))
			throw new ParserException("Root of file '" + file.getName() + "' is not valid");

		Element isElement = root.getChild("InteractionSpace");

		// Check if the domain model file is the same as the one chosen in the
		// project
		if (!root.getAttributeValue("dm").equals(project.getDomainModel().getFileName()))
			throw new ParserException(file.getName() + " - The domain model file of the interaction space ("
					+ root.getAttributeValue("dm") + ") is not the same as the domain model chosen ("
					+ project.getDomainModel().getFileName() + ")");

		// Create a new interaction space instance
		// InteractionSpace(String name, String text, String modelName, String
		// domainModelFileName)
		interactionSpace = new InteractionSpace(isElement.getAttributeValue("id"), isElement.getAttributeValue("text"),
				root.getAttributeValue("nome"), root.getAttributeValue("dm"), file.getName(),
				project.getDomainModel().getEntityByName(isElement.getAttributeValue("classe")),
				Double.parseDouble(isElement.getAttributeValue("x")),
				Double.parseDouble(isElement.getAttributeValue("y")),
				Double.parseDouble(isElement.getAttributeValue("w")),
				Double.parseDouble(isElement.getAttributeValue("h")));

		// Get the elements and all data inside the interaction space node
		getLayers(isElement.getChildren());

		return interactionSpace;
	}

	/**
	 * Get all the element layer in the file
	 * 
	 * @param elements
	 *            List with all the elements in the XML file
	 * @throws ParserException
	 */
	private void getLayers(List<Element> elements) throws ParserException {
		// Check all the elements
		for (Element element : elements) {
			// Check if element is an InteractionBlock
			if (Utils.isValidEnum(InteractionBlockType.class, element.getName())) {
				// Get the entity related with that interactionBlock
				Entity entity = project.getDomainModel().getEntityByName(element.getAttributeValue("classe"));
				// Create a new InteractionBlock object
				InteractionBlock interactionBlock = new InteractionBlock(element.getAttributeValue("id"),
						element.getAttributeValue("text"), entity, InteractionBlockType.valueOf(element.getName()),
						Double.valueOf(element.getAttributeValue("x")), Double.valueOf(element.getAttributeValue("y")),
						Double.valueOf(element.getAttributeValue("w")), Double.valueOf(element.getAttributeValue("h")));
				// Add the interactionBlock to the interactionSpace
				interactionSpace.addInteractionBlock(interactionBlock);
				// Get all the elements inside the interactionBlock
				getElementsInInteractionBlock(interactionBlock, element.getChildren());
			} else if (element.getName().equalsIgnoreCase("menubar")) {
				// Create a 'menuBar' instance
				MenuBar menuBar = new MenuBar(element.getAttributeValue("id"), element.getAttributeValue("text"),
						Double.valueOf(element.getAttributeValue("x")), Double.valueOf(element.getAttributeValue("y")),
						Double.valueOf(element.getAttributeValue("w")), Double.valueOf(element.getAttributeValue("h")));
				// Get the menus inside the menuBar
				getMenus(element, menuBar);
				// Add the 'MenuBar' to the 'InteractionSpace'
				interactionSpace.setMenuBar(menuBar);
				// ActionAIO Navigation
			} else if (Utils.isValidEnum(ActionAIONavigationType.class, element.getName())) {
				interactionSpace.addActionAIO(getActionAIONavigation(element));
				// ActionAIO (Domain) Operation
			} else if (Utils.isValidEnum(ActionAIOOperationType.class, element.getName())) {
				interactionSpace.addActionAIO(getActionAIOOperation(element));
				// ActionAIO UI Operation
			} else if (Utils.isValidEnum(ActionAIOUIOperationType.class, element.getName())) {
				interactionSpace.addActionAIO(getActionAIOUIOperation(element));
			}
		}
	}

	private void getMenus(Element menuBarElement, MenuBar menuBar) throws ParserException {
		// A 'menuBar' MUST have at least one 'Menu'
		for (Element element : menuBarElement.getChildren()) {
			if (element.getName().equalsIgnoreCase("menu")) {
				Menu menu = new Menu(element.getAttributeValue("id"), element.getAttributeValue("text"),
						Double.valueOf(element.getAttributeValue("x")), Double.valueOf(element.getAttributeValue("y")),
						Double.valueOf(element.getAttributeValue("w")), Double.valueOf(element.getAttributeValue("h")));
				menuBar.addMenu(menu);
				getMenuItens(element, menu);
			}
		}
		// Verify is the 'MenuBar' has at least on 'Menu'
		if (menuBar.getMenus().size() == 0)
			throw new ParserException("The MenuBar must have at least one Menu");
	}

	/**
	 * Get the MenuItens child's of the menuElement. NOTE: The attribute target
	 * of the MenuItem may be null if the target was not found in the list of
	 * interactionSpaces in the project.
	 * 
	 * @param menuElement
	 *            The Menu element that contains the MenuItems
	 * @param menu
	 *            The Menu object
	 * @throws ParserException
	 */
	private void getMenuItens(Element menuElement, Menu menu) throws ParserException {
		// A 'Menu' must have at least one 'MenuItem'
		for (Element element : menuElement.getChildren()) {
			if (element.getName().equalsIgnoreCase("menuitem")) {
				if (element.getAttributeValue("target") == null || element.getAttributeValue("target").equals(""))
					throw new ParserException("Target empty in " + element.getAttributeValue("id"));

				MenuItem menuItem = new MenuItem(element.getAttributeValue("id"), element.getAttributeValue("text"),
						Double.valueOf(element.getAttributeValue("x")), Double.valueOf(element.getAttributeValue("y")),
						Double.valueOf(element.getAttributeValue("w")), Double.valueOf(element.getAttributeValue("h")));
				menuItem.setTarget(project.getInteractionSpace(element.getAttributeValue("target")));

				if (menuItem.getTarget() == null) {
					// Target undefined
					ArrayList<String> _missingTargetInfo = new ArrayList<>();
					_missingTargetInfo.add(element.getAttributeValue("target"));
					_missingTargetInfo.add(interactionSpace.getFileName());
					missingTarget.put(menuItem, _missingTargetInfo);
				}
				menu.addMenuItem(menuItem);
			}
		}
		// Verify is the menu has at least on 'MenuItem'
		if (menu.getMenuItems().size() == 0)
			throw new ParserException("The Menu must have at least one MenuItem");
	}

	/**
	 * Get the elements inside InteractionBlocks. Those elements can be
	 * ActionAIO and/or DataAIO
	 * 
	 * @param interactionBlock
	 *            The InteractionBlock who has the elements
	 * @param elements
	 *            List of the elements inside the InteractionBlock
	 * @throws ParserException
	 */
	private void getElementsInInteractionBlock(InteractionBlock interactionBlock, List<Element> elements)
			throws ParserException {
		// Inside InteractionBlocks must have: ActionAIO and/or DataAIO
		if (elements != null && elements.size() > 0) {
			for (Element element : elements) {
				// Check if the element is an DataAIO or an ActionAIO
				if (Utils.isValidEnum(DataAIOType.class, element.getName())) {
					// Get the entity related with the InteractionBlock
					Entity entity = interactionBlock.getEntity();
					// Check if the entity is the same, otherwise an exception
					// is thrown
					if (element.getAttributeValue("classe") != null
							&& entity.getName().equals(element.getAttributeValue("classe"))) {
						// Get the attribute in the entity and associates to the
						// new SataAIO object
						Attribute attribute = entity.getAttributeByName(element.getAttributeValue("atributo"));
						// Create the object
						DataAIO dataAIO = new DataAIO(element.getAttributeValue("id"),
								element.getAttributeValue("text"), attribute, DataAIOType.valueOf(element.getName()),
								Double.valueOf(element.getAttributeValue("x")),
								Double.valueOf(element.getAttributeValue("y")),
								Double.valueOf(element.getAttributeValue("w")),
								Double.valueOf(element.getAttributeValue("h")));
						// Add the object to the list of DataAIOs in the parent
						// InteractionBlock
						interactionBlock.addDataAIO(dataAIO);
					} else
						// Throw Exception
						throw new ParserException("File: " + interactionSpace.getName() + ". The 'classe' attribute in "
								+ element.getName() + " is not the same as the parent InteractionBlock");

					// ActionAIO Navigation
				} else if (Utils.isValidEnum(ActionAIONavigationType.class, element.getName())) {
					interactionBlock.addActionAIO(getActionAIONavigation(element));
					// ActionAIO (Domain) Operation
				} else if (Utils.isValidEnum(ActionAIOOperationType.class, element.getName())) {
					interactionBlock.addActionAIO(getActionAIOOperation(element));
					// ActionAIO UI Operation
				} else if (Utils.isValidEnum(ActionAIOUIOperationType.class, element.getName())) {
					interactionBlock.addActionAIO(getActionAIOUIOperation(element));
				}
			}
		}
	}

	private ActionAIOUIOperation getActionAIOUIOperation(Element element) {
		return new ActionAIOUIOperation(element.getAttributeValue("id"), element.getAttributeValue("text"),
				ActionAIOUIOperationType.valueOf(element.getName()), Double.valueOf(element.getAttributeValue("x")),
				Double.valueOf(element.getAttributeValue("y")), Double.valueOf(element.getAttributeValue("w")),
				Double.valueOf(element.getAttributeValue("h")));
	}

	private ActionAIOOperation getActionAIOOperation(Element element) {
		return new ActionAIOOperation(element.getAttributeValue("id"), element.getAttributeValue("text"),
				ActionAIOOperationType.valueOf(element.getName()), Double.valueOf(element.getAttributeValue("x")),
				Double.valueOf(element.getAttributeValue("y")), Double.valueOf(element.getAttributeValue("w")),
				Double.valueOf(element.getAttributeValue("h")));
	}

	/**
	 * Creates an ActionAIONavigation. NOTE: The attribute target of the
	 * ActionAIONavigation may be null if the target was not found in the list
	 * of interactionSpaces in the project.
	 * 
	 * @param element
	 *            The element form of the ActionAIONavigation
	 * @return The ActionAIONavigation object
	 * @throws ParserException
	 */
	private ActionAIONavigation getActionAIONavigation(Element element) throws ParserException {
		// TARGET: IT CAN BE, INITIALLY, NULL
		if (element.getAttributeValue("target") == null || element.getAttributeValue("target").equals(""))
			throw new ParserException(
					"File: " + interactionSpace.getFileName() + ": Target empty in " + element.getAttributeValue("id"));
		InteractionSpace target = project.getInteractionSpace(element.getAttributeValue("target"));

		ActionAIONavigation actionAIONavigation = new ActionAIONavigation(element.getAttributeValue("id"),
				element.getAttributeValue("text"), target, ActionAIONavigationType.valueOf(element.getName()),
				Double.valueOf(element.getAttributeValue("x")), Double.valueOf(element.getAttributeValue("y")),
				Double.valueOf(element.getAttributeValue("w")), Double.valueOf(element.getAttributeValue("h")));

		if (target == null) {
			// Target undefined
			ArrayList<String> _missingTargetInfo = new ArrayList<>();
			_missingTargetInfo.add(element.getAttributeValue("target"));
			_missingTargetInfo.add(interactionSpace.getFileName());
			missingTarget.put(actionAIONavigation, _missingTargetInfo);
		}

		return actionAIONavigation;
	}
}