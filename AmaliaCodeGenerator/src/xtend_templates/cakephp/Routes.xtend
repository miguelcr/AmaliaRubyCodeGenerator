package xtend_templates.cakephp

import main.Project
import structure.domain_model.Entity
import exceptions.ParserException
import inflector.Inflector

class Routes {
	private var Project project;
	private var Entity entity;

	new(Project project) {
		this.project = project
	}

	def generate(Entity entity) {
		this.entity = entity
		if (project != null && project.domainModel != null && entity != null)
			return template
		else
			throw new ParserException("A domain model must be defined");
	}

	def template() '''
		<?php
		use Cake\Core\Plugin;
		use Cake\Routing\Router;
		
		Router::defaultRouteClass('DashedRoute');
		
		Router::scope('/', function ($routes) {
			Router::connect('/', array('controller' => '«Inflector.plural(entity.name.toLowerCase)»', 'action' => 'index'));
			$routes->fallbacks('DashedRoute');
		});
		
		Plugin::routes();
	'''
}