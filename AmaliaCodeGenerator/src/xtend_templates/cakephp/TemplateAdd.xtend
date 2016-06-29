package xtend_templates.cakephp

import main.Project
import structure.domain_model.Entity
import exceptions.ParserException
import inflector.Inflector
import structure.domain_model.Attribute

class TemplateAdd {
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
		<nav class="large-3 medium-4 columns" id="actions-sidebar">
			    <ul class="side-nav">
			        <li class="heading"><?= __('Actions') ?></li>
			        <li><?= $this->Html->link(__('List «Inflector.plural(entity.name.toLowerCase).toFirstUpper»'), ['action' => 'index']) ?></li>
			        «otherListItems»
			    </ul>
		</nav>
		<div class="«Inflector.plural(entity.name.toLowerCase)» form large-9 medium-8 columns content">
		    <?= $this->Form->create($«entity.name.toLowerCase») ?>
		    <fieldset>
		        <legend><?= __('Add «entity.name.toLowerCase.toFirstUpper»') ?></legend>
		        <?php
		        	«options»
		        ?>
		    </fieldset>
		    <?= $this->Form->button(__('Submit')) ?>
		    <?= $this->Form->end() ?>
		</div>
	'''

	def options() '''
		«FOR Attribute attr : entity.attributes»
			«IF attr.reference»
				echo $this->Form->input('«attr.name.toLowerCase»_id', ['options' => $«Inflector.plural(attr.name.toLowerCase)»]);
			«ELSE»
				echo $this->Form->input('«attr.name.toLowerCase»');
			«ENDIF»
		«ENDFOR»
	'''

	def otherListItems() '''
		«IF entity.hasReferenceAttributes»
			«FOR Attribute attr : entity.referencedAttributes»
				<li><?= $this->Html->link(__('List «Inflector.plural(attr.name.toLowerCase).toFirstUpper»'), ['controller' => '«Inflector.plural(attr.name.toLowerCase).toFirstUpper»', 'action' => 'index']) ?></li>
				<li><?= $this->Html->link(__('New «attr.name.toLowerCase.toFirstUpper»'), ['controller' => '«Inflector.plural(attr.name.toLowerCase).toFirstUpper»', 'action' => 'add']) ?></li>
			«ENDFOR»
		«ENDIF»
		«IF project.domainModel.whereEntityIsReferenced(entity).size() > 0»
			«FOR Entity e : project.domainModel.whereEntityIsReferenced(entity)»
				<li><?= $this->Html->link(__('List «Inflector.plural(e.name.toLowerCase).toFirstUpper»'), ['controller' => '«Inflector.plural(e.name.toLowerCase).toFirstUpper»', 'action' => 'index']) ?></li>
				<li><?= $this->Html->link(__('New «e.name.toLowerCase.toFirstUpper»'), ['controller' => '«Inflector.plural(e.name.toLowerCase).toFirstUpper»', 'action' => 'add']) ?></li>
			«ENDFOR»
		«ENDIF»
	'''
}