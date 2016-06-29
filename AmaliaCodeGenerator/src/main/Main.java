package main;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import exceptions.ParserException;
import structure.domain_model.DomainModel;
import structure.domain_model.Entity;
import xml.parser.DomainModelXMLParserJDOM;
import xml.parser.InteractionSpaceBuilder;
import xtend_templates.cakephp.Controller;
import xtend_templates.cakephp.DatabaseTemplate;
import xtend_templates.cakephp.EntityCake;
import xtend_templates.cakephp.Table;
import xtend_templates.cakephp.TemplateAdd;
import xtend_templates.cakephp.TemplateEdit;
import xtend_templates.cakephp.TemplateIndex;
import xtend_templates.cakephp.TemplateView;
import xtend_templates.rubyonrails.RubyScriptTemplate;

public class Main {
	private static ResourceBundle rb;
	public static void main(String[] args) {
		// Get the Resource Bundle file
		String language = "en"; // choose the language
		Locale locale = new Locale(language);
		rb = ResourceBundle.getBundle("bundles.Bundle", locale);		
		
		/*
		 * HOW TO CREATE A NEW PROJECT
		 * 1. Create an instance of "Project" class. This object will keep all the project data (Domain Model and User Interface Elements)
		 * 2. Use the "DomainModelXMLParserJDOM" to read the XML file from domain model and create the DomainModel object
		 * 3. Set the DomainModel value in the project with the previously output
		 * 4.  
		 */
		// HOW TO CREATE A NEW PROJECT
		Project project = new Project();
		try {
			DomainModelXMLParserJDOM DMparser = new DomainModelXMLParserJDOM(rb);
			DomainModel domainModel;
			domainModel = DMparser.readXML("./src/xml/samples/domain_model/rent_car_domain_model.xml");
			//domainModel = DMparser.readXML("./src/xml/samples/domain_model/cars.xml");
			project.setDomainModel(domainModel);
			//System.out.println(domainModel.getInfo());
			
			// Stub User Interface Models
			ArrayList<String> interactionSpaces = new ArrayList<>();
//			interactionSpaces.add("./src/xml/samples/user_interface/proj_exe_UC_ListarClientes_new.xml");
//			interactionSpaces.add("./src/xml/samples/user_interface/proj_exe_UC_ListarViaturas_new.xml");
//			interactionSpaces.add("./src/xml/samples/user_interface/proj_exe_UC_VerDetalhesViatura+ListarAlugueresViatura_new.xml");
//			interactionSpaces.add("./src/xml/samples/user_interface/proj_exe_UC_VerDetalhesViatura+ListarAlugueresViatura_new2.xml");
//			interactionSpaces.add("./src/xml/samples/user_interface/menus.xml");
//			interactionSpaces.add("./src/xml/samples/user_interface/Target_Test_ListAlugueres.xml");
//			interactionSpaces.add("./src/xml/samples/user_interface/Target_Test_ListViaturas.xml");
//			interactionSpaces.add("./src/xml/samples/user_interface/persons.xml");
//			interactionSpaces.add("./src/xml/samples/user_interface/persons_cars.xml");
//			interactionSpaces.add("./src/xml/samples/user_interface/person_view.xml");
			interactionSpaces.add("./src/xml/samples/user_interface/add_car.xml");
			interactionSpaces.add("./src/xml/samples/user_interface/add_client.xml");
			interactionSpaces.add("./src/xml/samples/user_interface/add_rental.xml");
			interactionSpaces.add("./src/xml/samples/user_interface/details_car.xml");
			interactionSpaces.add("./src/xml/samples/user_interface/details_client.xml");
			interactionSpaces.add("./src/xml/samples/user_interface/details_rental.xml");
			interactionSpaces.add("./src/xml/samples/user_interface/edit_car.xml");
			interactionSpaces.add("./src/xml/samples/user_interface/edit_client.xml");
			interactionSpaces.add("./src/xml/samples/user_interface/edit_rental.xml");
			interactionSpaces.add("./src/xml/samples/user_interface/list_cars.xml");
			interactionSpaces.add("./src/xml/samples/user_interface/list_clients.xml");
			interactionSpaces.add("./src/xml/samples/user_interface/list_rentals.xml");
			
			// METHOD 1 - No validation of null targets
	//		UserInterfaceXMLParserJDOM userInterfaceXMLParserJDOM = new UserInterfaceXMLParserJDOM();
	//		InteractionSpace interactionSpace;
	//		
	//		try {
	//			for(String uiModelFile : interactionSpaces){
	//				System.out.println("##################################### InteractionSpace #####################################");
	//				interactionSpace = userInterfaceXMLParserJDOM.readXML(uiModelFile, project);
	//				project.addInteractionSpace(interactionSpace);
	//				System.out.println(interactionSpace.getInfo());
	//			}
	//			
	//			// CHECK IF THE ELEMENTS WITH TARGETS ARE OK			
	//		} catch (ParserException e) {
	//			System.out.println(e.getMessage()); 
	//			e.printStackTrace();
	//		}
			
			// METHOD 2 - Validates if the target is null
			InteractionSpaceBuilder builder = new InteractionSpaceBuilder(rb);
			builder.build(project, interactionSpaces);
			project.setMainInteractionSpace(project.getInteractionSpacesWithoutParams().get(0));
//			for(InteractionSpace iSpace : project.getInteractionSpaces().values())
//				System.out.println(iSpace.getInfo());
			RubyScriptTemplate t = new RubyScriptTemplate();
			CharSequence result = t.generate(project);
			System.out.println("#### TEMPLATE ####\n"+ result);
		} catch (ParserException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		DatabaseTemplate db = new DatabaseTemplate(project);
		Controller appController = new Controller(project);
		EntityCake entityCake = new EntityCake(project);
		Table table = new Table(project);
		TemplateAdd templateAdd = new TemplateAdd(project);
		TemplateEdit templateEdit = new TemplateEdit(project);
		TemplateIndex templateIndex = new TemplateIndex(project);
		TemplateView templateView = new TemplateView(project);
		CharSequence result = db.generate();
		//CharSequence result = templateView.generate((Entity)project.getDomainModel().getEntities().values().toArray()[2]);
		//CharSequence result = templateIndex.generate((Entity)project.getDomainModel().getEntities().values().toArray()[1]);
		//CharSequence result = templateEdit.generate((Entity)project.getDomainModel().getEntities().values().toArray()[0]);
		//CharSequence result = templateAdd.generate((Entity)project.getDomainModel().getEntities().values().toArray()[0]);
		//CharSequence result = appController.generate((Entity)project.getDomainModel().getEntities().values().toArray()[1]);
		//CharSequence result = appController.generate((Entity)project.getDomainModel().getEntities().values().toArray()[0]);
		//CharSequence result = entityCake.generate((Entity)project.getDomainModel().getEntities().values().toArray()[2]);
		//CharSequence result = table.generate((Entity)project.getDomainModel().getEntities().values().toArray()[1]);
		//System.out.println(result);
		//System.out.println(result2);
		
	}
}
