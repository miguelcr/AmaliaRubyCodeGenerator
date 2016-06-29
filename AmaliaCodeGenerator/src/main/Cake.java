package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import inflector.Inflector;
import structure.domain_model.Entity;
import xtend_templates.cakephp.Controller;
import xtend_templates.cakephp.DatabaseTemplate;
import xtend_templates.cakephp.EntityCake;
import xtend_templates.cakephp.Routes;
import xtend_templates.cakephp.Table;
import xtend_templates.cakephp.TemplateAdd;
import xtend_templates.cakephp.TemplateEdit;
import xtend_templates.cakephp.TemplateIndex;
import xtend_templates.cakephp.TemplateView;

public class Cake {
	private HashMap<String, CharSequence> controllers;
	private HashMap<String, CharSequence> entities;
	private HashMap<String, CharSequence> tables;
	private HashMap<String, HashMap<String, CharSequence>> templates;
	private CharSequence database;
	private CharSequence routes;
	private Project project;
	

	public Cake(Project project){
		this.project = project;
		controllers = new HashMap<>();
		entities = new HashMap<>();
		tables = new HashMap<>();
		templates = new HashMap<>();
	}
	
	private String addSomeControllers(){
		String output = "";
		CharSequence code;
		for(Entity e : project.getDomainModel().getEntities().values()){
			code = new Controller(project).generate(e);
			output += code + "\n";
			controllers.put(Utils.toFirstUpper(Inflector.plural(e.getName().toLowerCase())) + "Controller.php", code);
		}
		
		return output;
	}
	
	private String addDatabase(){
		database = new DatabaseTemplate(project).generate();
		return database.toString();
	}
	
	private String addTables(){
		String output = "";
		CharSequence code;
		for(Entity e : project.getDomainModel().getEntities().values()){
			code = new Table(project).generate(e);
			output += code + "\n";
			tables.put(Utils.toFirstUpper(Inflector.plural(e.getName().toLowerCase())) + "Table.php", code);
		}
		return output;
	}
	
	private String addEntities(){
		String output = "";
		CharSequence code;		
		for(Entity e : project.getDomainModel().getEntities().values()){
			code = new EntityCake(project).generate(e);
			output += code + "\n";
			entities.put(Utils.toFirstUpper(Inflector.plural(e.getName().toLowerCase())) + ".php", code);
		}
		return output;
	}
	
	private String addRoutes(){
		routes = new Routes(project).generate((Entity)project.getDomainModel().getEntities().values().toArray()[0]);
		return routes.toString();
	}
	
	private String addTemplates(){
		String output = "";
		CharSequence code;		
		HashMap<String, CharSequence> temp;
		for(Entity e : project.getDomainModel().getEntities().values()){
			temp = new HashMap<>();
			temp.put("add.ctp", new TemplateAdd(project).generate(e));
			temp.put("edit.ctp", new TemplateEdit(project).generate(e));
			temp.put("index.ctp", new TemplateIndex(project).generate(e));
			temp.put("view.ctp", new TemplateView(project).generate(e));
			templates.put(Utils.toFirstUpper(Inflector.plural(e.getName().toLowerCase())), temp);
		}
		
		return output;
	}
	
	public String generate(){
		String output = "";
		output += addTemplates();
		output += addEntities();
		output += addSomeControllers();
		output += addTables();
		output += addDatabase();
		output += addRoutes();
		return output;
	}
	
	public HashMap<String, CharSequence> getControllers() {
		return controllers;
	}

	public void setControllers(HashMap<String, CharSequence> controllers) {
		this.controllers = controllers;
	}

	public HashMap<String, CharSequence> getEntities() {
		return entities;
	}

	public void setEntities(HashMap<String, CharSequence> entities) {
		this.entities = entities;
	}

	public HashMap<String, CharSequence> getTables() {
		return tables;
	}

	public void setTables(HashMap<String, CharSequence> tables) {
		this.tables = tables;
	}

	public HashMap<String, HashMap<String, CharSequence>> getTemplates() {
		return templates;
	}

	public void setTemplates(HashMap<String, HashMap<String, CharSequence>> templates) {
		this.templates = templates;
	}

	public CharSequence getDatabase() {
		return database;
	}

	public void setDatabase(CharSequence database) {
		this.database = database;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	private void addEntryToZip(HashMap<String, CharSequence> map, String location, ZipOutputStream out) throws IOException{
		// Add Controller Files
		for(String name : map.keySet()){
			// name the file inside the zip file
			out.putNextEntry(new ZipEntry(location + name));
			String txt = map.get(name).toString();
			byte[] buffer = txt.getBytes();
			out.write(buffer, 0, buffer.length);
			out.closeEntry();
		}		
	}

	public void bakeCake(File file) throws IOException{
		// output file
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
		
		out.putNextEntry(new ZipEntry("database.sql"));
		String txt = database.toString();
		byte[] buffer = txt.getBytes();
		out.write(buffer, 0, buffer.length);
		out.closeEntry();
		
		out.putNextEntry(new ZipEntry("config/routes.php"));
		txt = routes.toString();
		buffer = txt.getBytes();
		out.write(buffer, 0, buffer.length);
		out.closeEntry();		
		
		addEntryToZip(controllers, "src/Controller/", out);
		addEntryToZip(entities, "src/Model/Entity/", out);
		addEntryToZip(tables, "src/Model/Table/", out);
		
		for(String map : templates.keySet())
				addEntryToZip(templates.get(map), "src/Template/" + map + "/", out);

		out.close();
	}
}
