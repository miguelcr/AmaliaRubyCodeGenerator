package xtend_templates.cakephp

import exceptions.ParserException
import main.Project
import structure.domain_model.Attribute
import structure.domain_model.Entity
import inflector.Inflector

class DatabaseTemplate {
	private var Project project;

	new(Project project) {
		this.project = project
	}

	def generate() {
		if (project != null && project.domainModel != null)
			return template
		else
			throw new ParserException("A domain model must be defined");
	}

	def template() '''
		-- Generated by Amalia Code Generator
		�FOR Entity entity : project.domainModel.entities.values�
			�createTable(entity)�
		�ENDFOR�
	'''

	def createTable(Entity entity) '''
		CREATE TABLE IF NOT EXISTS `�Inflector.plural(entity.name.toLowerCase)�` (
			`id` INT(10) NOT NULL AUTO_INCREMENT,
			�FOR Attribute attr : entity.attributes�
				�IF attr.reference�
					`�attr.name.toLowerCase�_id` INT(10) NOT NULL,
				�ELSE�
					`�attr.name.toLowerCase�` �attributeType(attr)� NOT NULL,
				�ENDIF�
			�ENDFOR�
			PRIMARY KEY (`id`)
		);
	'''

	def attributeType(Attribute attribute) {
		switch (attribute.type) {
			case 'text':
				return 'VARCHAR(255)'
			case 'string':
				return 'VARCHAR(255)'
			case 'date':
				return 'DATE'
			default:
				return 'VARCHAR(255)'
		}
	}

}