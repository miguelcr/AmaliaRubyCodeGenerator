package xml.parser;

//Class that will represent a system file name
import java.io.File;
//Triggered when an I/O error occurs
import java.io.IOException;
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
import structure.domain_model.Attribute;
import structure.domain_model.Connection;
import structure.domain_model.ConnectionType;
import structure.domain_model.DomainModel;
import structure.domain_model.Entity;
import structure.domain_model.EntityType;
import structure.domain_model.Method;

public class DomainModelXMLParserJDOM {
	private DomainModel dm;
	private final String ROOT = "project";
	private ResourceBundle rb;
	
	public DomainModelXMLParserJDOM(ResourceBundle rb) {
		this.rb = rb;
		dm = null;
	}
	
	public DomainModel readXML(String path) throws ParserException{
		SAXBuilder builder = new SAXBuilder();
		File file = null;
		Document doc = null;
		try {
			file = new File(path);
			doc = builder.build(file);
		} catch (JDOMException | IOException e) {
			System.err.println("ERROR: " + e.getMessage());
			throw new ParserException(e.getMessage());
		}

		Element root = doc.getRootElement();

		// Check if the file root is valid
		if(!root.getName().equals(ROOT))		
			throw new ParserException(rb.getString("parser.dm.invalid.root"));
		
		// Create a new project
		dm = new DomainModel(root.getChild("name").getValue(), file.getName());
		
		// Get the entities and all data (methods and attributes) associated
		retrieveEntities(root.getChild("domain_model").getChild("elements").getChildren());		
		
		// Get the connections
		retrieveConnections(root.getChild("domain_model").getChild("connections").getChildren());
		
		return dm;
	}
	
	private void retrieveConnections(List<Element> elements){
		for(Element element : elements){
			String connectionId = element.getAttributeValue("id");
			Entity source = dm.getEntityById(element.getChild("source_id").getValue());
			Entity destination = dm.getEntityById(element.getChild("destination_id").getValue());
			String sourceCardinality = element.getChild("cardinality_source").getValue();
			String destinationCardinality = element.getChild("cardinality_destination").getValue();
			ConnectionType connectionType = ConnectionType.valueOf(element.getChild("type").getValue().toUpperCase());
			
			// Create the connection
			Connection connection = new Connection(connectionId, source, destination, connectionType, sourceCardinality, destinationCardinality);
			
			// If a connection is association and the cardinality is (1-*) or (*-1)
			if(connectionType == ConnectionType.ASSOCIATION){
				if((sourceCardinality.equals("*") && destinationCardinality.equals("1")))
					source.addAttribute(new Attribute(destination.getName(), "references", true));
				else if(sourceCardinality.equals("1") && destinationCardinality.equals("*"))
					destination.addAttribute(new Attribute(source.getName(), "references", true));
			}
			
			// Add the connection to the list
			dm.addConnection(connection);
		}
	}
	
	private void retrieveEntities(List<Element> elements){
		for(Element element : elements){
			Entity entity = new Entity(element.getAttributeValue("id"), 
					element.getChild("name").getValue(), 
					(!element.getChild("type").equals("class") ? EntityType.INTERFACE : EntityType.CLASS));
			
			if(element.getChild("attributes") != null){
				for(Element attributeData : element.getChild("attributes").getChildren()){

					String dataType = attributeData.getAttributeValue("type");					
					Attribute attribute = new Attribute(attributeData.getValue(), dataType, false);
					entity.addAttribute(attribute);
				} // end for attributes
			}
			
			if(element.getChild("methods") != null){
				for(Element attributeData : element.getChild("methods").getChildren()){
					Method method = new Method(attributeData.getValue(), "NULL");
					entity.addMethod(method);
				} // end for methods
			}			
			
			dm.addEntity(entity);
		}		
	}
}