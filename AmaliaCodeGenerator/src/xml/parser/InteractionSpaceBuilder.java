package xml.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import exceptions.ParserException;
import main.Project;
import structure.user_interface.InteractionSpace;
import structure.user_interface.LayerTarget;

/**
 * 
 * @author Sérgio Gonçalves | Francisco Gonçalves
 */
public class InteractionSpaceBuilder {
	// Map of layers with targets to other InteractionSpaces, that cannot be
	// resolved during the reading
	// of the file. This may occur if the file with the InteractionSpace was not
	// read or doesn't exist.
	// After all the files are read the missing targets will checked again and
	// if not resolved
	// an exception will be thrown.
	private HashMap<LayerTarget, ArrayList<String>> missingTargetLayers;
	private Project project;
	private ResourceBundle rb;

	/**
	 * Constructor
	 */
	public InteractionSpaceBuilder(ResourceBundle rb) {
		this.rb = rb;
	}

	public void build(Project project, List<String> uiModelFiles) throws ParserException {
		this.project = project;
		missingTargetLayers = new HashMap<>();

		UserInterfaceXMLParserJDOM parser = new UserInterfaceXMLParserJDOM(rb);

		for (String uiModel : uiModelFiles)
			project.addInteractionSpace(parser.readXML(uiModel, project, missingTargetLayers));

		if (missingTargetLayers.size() > 0)
			resolveTargets();
	}

	public void buildFile(Project project, List<File> uiModelFiles) throws ParserException {
		this.project = project;
		missingTargetLayers = new HashMap<>();

		UserInterfaceXMLParserJDOM parser = new UserInterfaceXMLParserJDOM(rb);

		for (File uiModel : uiModelFiles)
			project.addInteractionSpace(parser.readXML(uiModel, project, missingTargetLayers));

		if (missingTargetLayers.size() > 0)
			resolveTargets();
	}

	private void resolveTargets() throws ParserException {
		for (LayerTarget layer : missingTargetLayers.keySet()) {
			InteractionSpace target = project.getInteractionSpace(missingTargetLayers.get(layer).get(0));
			if (target == null) {
				throw new ParserException(rb.getString("general.file") + ": " + missingTargetLayers.get(layer).get(1)
						+ ". " + rb.getString("parser.ui.missing.target1") + " '"
						+ missingTargetLayers.get(layer).get(0) + "' " + rb.getString("parser.ui.missing.target2")
						+ " '" + layer.getName() + "'");
			} else
				layer.setTarget(target);
		}
	}
}
