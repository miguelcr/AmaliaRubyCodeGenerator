package structure.domain_model;

import java.util.ArrayList;
import java.util.List;

public class Entity {
	private String name;
	private EntityType type;
	private String id;
	private ArrayList<Attribute> attributes;
	private ArrayList<Method> methods;
	
	public Entity(String id, String name, EntityType type) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.attributes = new ArrayList<>();
		this.methods = new ArrayList<>();
	}
	
	public void addAttribute(Attribute attribute){
		attributes.add(attribute);
	}
	
	public void addMethod(Method method){
		methods.add(method);
	}	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public EntityType getType() {
		return type;
	}
	
	public void setType(EntityType type) {
		this.type = type;
	}
	
	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(ArrayList<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	public ArrayList<Method> getMethods() {
		return methods;
	}
	
	public void setMethods(ArrayList<Method> methods) {
		this.methods = methods;
	}
	
	/**
	 * Check if this entity has any reference type attribute
	 * @return True if has a reference type, false otherwise
	 */
	public boolean hasReferenceAttributes() {
		for(Attribute a : attributes)
			if(a.isReference())
				return true;
		return false;
	}	
	
	/**
	 * Get the references attributes
	 * @return List with references attributes
	 */
	public List<Attribute> referencedAttributes() {
		ArrayList<Attribute> attributes = new ArrayList<>();
		for(Attribute a : this.attributes)
			if(a.isReference())
				attributes.add(a);
		return attributes;
	}	
	
	public Attribute getAttributeByName(String name){
		for(Attribute attribute : attributes){
			if(attribute.getName().equals(name))
				return attribute;			
		}		
		return null;
	}
	
	public Attribute getAttributeByNameIgnoreCase(String name){
		for(Attribute attribute : attributes){
			if(attribute.getName().toLowerCase().equals(name.toLowerCase()))
				return attribute;			
		}		
		return null;
	}	
}