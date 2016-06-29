package xtend_templates.cakephp

import main.Project
import structure.domain_model.Entity
import exceptions.ParserException
import structure.domain_model.Attribute
import inflector.Inflector

class TemplateView {
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
			        «listItems»
			    </ul>
			</nav>
			<div class="«Inflector.plural(entity.name.toLowerCase)» view large-9 medium-8 columns content">
			    <h3><?= h($«entity.name.toLowerCase»->id) ?></h3>
			    <table class="vertical-table">
			        «tableBody»
			    </table>
			    «related»
			</div>
	'''

	def related() '''
		«IF project.domainModel.whereEntityIsReferenced(entity).size > 0»
			«FOR Entity e : project.domainModel.whereEntityIsReferenced(entity) »
				<div class="related">
				    <h4><?= __('Related «Inflector.plural(e.name.toLowerCase).toFirstUpper»') ?></h4>
				    <?php if (!empty($«entity.name.toLowerCase»->«Inflector.plural(e.name.toLowerCase)»)): ?>
				    <table cellpadding="0" cellspacing="0">
				        <tr>
				            «relatedHeaders(e)»
				        </tr>
				        <?php foreach ($«entity.name.toLowerCase»->«Inflector.plural(e.name.toLowerCase)» as $«Inflector.plural(e.name.toLowerCase)»): ?>
				        <tr>
				«relatedData(e)»
				</tr>
				<?php endforeach; ?>
				</table>
				<?php endif; ?>
				</div>
			«ENDFOR»
		«ELSE»
			«FOR Attribute attr : entity.referencedAttributes »
				«var Entity relatedEnt = project.domainModel.getEntityByName(attr.name)»
				<div class="related">
				   <h3>«relatedEnt.name.toLowerCase.toFirstUpper»</h3>
				   <table class="vertical-table">
				   		«FOR Attribute attrEnt : relatedEnt.attributes»
				   			<tr>
				   			  <th><?= __('«attrEnt.name.toFirstUpper»') ?></th>
				   			<td><?= h($«entity.name.toLowerCase»->«relatedEnt.name.toLowerCase»->«attrEnt.name.toLowerCase») ?></td>
				   			</tr>
				   		«ENDFOR»
				   </table>
				</div>
			«ENDFOR»
		«ENDIF»
	'''

	def relatedData(
		Entity related
	) '''
		<td><?= h($«Inflector.plural(related.name.toLowerCase)»->id) ?></td>
		«FOR Attribute attr : related.attributes»
			«IF attr.reference»
				<td><?= h($«Inflector.plural(related.name.toLowerCase)»->«attr.name.toLowerCase»_id) ?></td>
			«ELSE»
				<td><?= h($«Inflector.plural(related.name.toLowerCase)»->«attr.name.toLowerCase») ?></td>
			«ENDIF»			
		«ENDFOR»	
		<td class="actions">
			<?= $this->Html->link(__('View'), ['controller' => '«Inflector.plural(related.name.toLowerCase).toFirstUpper»', 'action' => 'view', $«Inflector.plural(related.name.toLowerCase)»->id]) ?>		
			<?= $this->Html->link(__('Edit'), ['controller' => '«Inflector.plural(related.name.toLowerCase).toFirstUpper»', 'action' => 'edit', $«Inflector.plural(related.name.toLowerCase)»->id]) ?>		
			<?= $this->Form->postLink(__('Delete'), ['controller' => '«Inflector.plural(related.name.toLowerCase).toFirstUpper»', 'action' => 'delete', $«Inflector.plural(related.name.toLowerCase)»->id], ['confirm' => __('Are you sure you want to delete # {0}?', $«Inflector.plural(related.name.toLowerCase)»->id)]) ?>
		</td>
	'''

	def relatedHeaders(
		Entity related
	) '''
		<th><?= __('Id') ?></th>
		«FOR Attribute attr : related.attributes»
			«IF attr.reference»
				<th><?= __('«attr.name.toLowerCase.toFirstUpper» Id') ?></th>
			«ELSE»
				<th><?= __('«attr.name.toLowerCase.toFirstUpper»') ?></th>
			«ENDIF»			
		«ENDFOR»		
		<th class="actions"><?= __('Actions') ?></th>
	'''

	def tableBody() '''
		«FOR Attribute attr : entity.attributes»
			<tr>
			  <th><?= __('«attr.name.toLowerCase.toFirstUpper»') ?></th>
			«IF attr.reference»
				<td><?= $«entity.name.toLowerCase»->has('«attr.name.toLowerCase»') ? $this->Html->link($«entity.name.toLowerCase»->«attr.name.toLowerCase»->id, ['controller' => '«Inflector.plural(attr.name.toLowerCase).toFirstUpper»', 'action' => 'view', $«entity.name.toLowerCase»->«attr.name.toLowerCase»->id]) : '' ?></td>
			«ELSE»
				<td><?= h($«entity.name.toLowerCase»->«attr.name.toLowerCase») ?></td>
			«ENDIF»
			</tr>
		«ENDFOR»
		      <tr>
		 <th><?= __('Id') ?></th>
		 <td><?= $this->Number->format($«entity.name.toLowerCase»->id) ?></td>
		      </tr>
	'''

	def listItems() '''
		<li class="heading"><?= __('Actions') ?></li>
		«««		CRUD
		<li><?= $this->Html->link(__('Edit «entity.name.toLowerCase.toFirstUpper»'), ['action' => 'edit', $«entity.name.toLowerCase»->id]) ?> </li>
		<li><?= $this->Form->postLink(__('Delete «entity.name.toLowerCase.toFirstUpper»'), ['action' => 'delete', $«entity.name.toLowerCase»->id], ['confirm' => __('Are you sure you want to delete # {0}?', $«entity.name.toLowerCase»->id)]) ?> </li>
		<li><?= $this->Html->link(__('List «Inflector.plural(entity.name.toLowerCase).toFirstUpper»'), ['action' => 'index']) ?> </li>
		<li><?= $this->Html->link(__('New «entity.name.toLowerCase.toFirstUpper»'), ['action' => 'add']) ?> </li>
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