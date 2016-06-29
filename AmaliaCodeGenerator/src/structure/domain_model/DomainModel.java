package structure.domain_model;

import java.util.ArrayList;
import java.util.HashMap;

public class DomainModel {
	private HashMap<String,Entity> entities;
	private ArrayList<Connection> connections;
	private String name;
	private String fileName; // Name of the XML file containing the Domain Model
	
	public DomainModel(String name, String fileName){
		this.name = name;
		this.fileName = fileName;
		entities = new HashMap<>();
		connections = new ArrayList<>();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ArrayList<Connection> getConnections() {
		return connections;
	}
	
	
	public ArrayList<Connection> getSourceConnectionsFromEntity(Entity entity){
		ArrayList<Connection> connections = new ArrayList<>();
		for(Connection con : this.connections){
			if(con.getSource().equals(entity))
				connections.add(con);
		}
		return connections;
	}
	
	public ArrayList<Connection> getDestinationConnectionsToEntity(Entity entity){
		ArrayList<Connection> connections = new ArrayList<>();
		for(Connection con : this.connections){
			if(con.getDestination().equals(entity))
				connections.add(con);
		}
		return connections;
	}	

	public void setConnections(ArrayList<Connection> connections) {
		this.connections = connections;
	}

	public HashMap<String,Entity> getEntities() {
		return entities;
	}

	public void setEntities(HashMap<String,Entity> entities) {
		this.entities = entities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addEntity(Entity entity){
		this.entities.put(entity.getId(), entity);
	}
	
	public void addConnection(Connection connection){
		this.connections.add(connection);
	}
	
	public Entity getEntityById(String id){
		return entities.get(id);
	}
	
	public Entity getEntityByName(String name){
		for(Entity entity : entities.values()){
			if(entity.getName().equals(name))
				return entity;
		}
		//Optional<Entity> entity = entities.values().stream().filter(v -> v.getName().equals(name)).findFirst();
		return null;
	}
	
	/**
	 * Entidades onde a entidade passada está referênciada 
	 * @param entity
	 */
	public ArrayList<Entity> whereEntityIsReferenced(Entity entity){
		ArrayList<Entity> entitiesReferenced = new ArrayList<>();
		
		for(Entity e : entities.values()) {
			if(e.hasReferenceAttributes()){
				for(Attribute attrRef : e.referencedAttributes()){
					if(attrRef.getName().equalsIgnoreCase(entity.getName().toLowerCase())) {
						entitiesReferenced.add(e);
						break;
					}
				}
			}
		}
		
		return entitiesReferenced;
	}
	
	public String getInfo() {
		String projectInfo = "";
		projectInfo += ("Domain Model Name: " + getName());
		projectInfo += (" | File Name: " + getFileName());
		projectInfo += ("\n\tNumber of Entities: " + getEntities().size());

		for(Entity e : getEntities().values()){
			projectInfo +=("\n\tEntity ID: " + e.getId() +
					"\n\tName: " + e.getName() +
					"\n\tNumber of Attributes: " + e.getAttributes().size() + "\n");
			for(Attribute attribute : e.getAttributes())
				projectInfo +=("\t\t- " + attribute.getName() + " : " + attribute.getType() + "\n");
						
			projectInfo += "\tNumber of Methods: " + e.getMethods().size() + "\n";
			for(Method method : e.getMethods())
				projectInfo +=("\t\t- " + method.getName() + " : " + method.getOutput() + "\n");
		}
		
		projectInfo +=("\n\tNumber of Connections: " + getConnections().size());
		
		for (Connection connection : getConnections()){
			projectInfo +=("\n\tConnection ID: " + connection.getConnectionId() + "\n");
			projectInfo +=("\t\t" + connection.getType().toString().toLowerCase() + " [ " +
					connection.getSource().getName() + "(" +
					connection.getSourceCardinality() + ") - (" +
					connection.getDestinationCardinality() + ")" +
					connection.getDestination().getName() + " ]");
		}
		
		return projectInfo;
	}
}