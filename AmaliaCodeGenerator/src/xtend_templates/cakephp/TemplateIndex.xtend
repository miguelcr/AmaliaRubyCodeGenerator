package xtend_templates.cakephp

import main.Project
import structure.domain_model.Entity
import exceptions.ParserException
import structure.domain_model.Attribute
import inflector.Inflector

class TemplateIndex {
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
			        <li><?= $this->Html->link(__('New «entity.name.toLowerCase.toFirstUpper»'), ['action' => 'add']) ?></li>
					«otherListItems»
					  </ul>
			</nav>
			<div class="«Inflector.plural(entity.name.toLowerCase)» index large-9 medium-8 columns content">
			    <h3><?= __('«Inflector.plural(entity.name.toLowerCase).toFirstUpper»') ?></h3>
			    <table cellpadding="0" cellspacing="0">
			        <thead>
			            <tr>
			                «tableHeaders»
			            </tr>
			        </thead>
			        <tbody>
			            <?php foreach ($«Inflector.plural(entity.name.toLowerCase)» as $«entity.name.toLowerCase»): ?>
			            <tr>
			                «tableData»
			            </tr>
			            <?php endforeach; ?>
			        </tbody>
			    </table>
			    <div class="paginator">
			        <ul class="pagination">
			            <?= $this->Paginator->prev('< ' . __('previous')) ?>
			            <?= $this->Paginator->numbers() ?>
			            <?= $this->Paginator->next(__('next') . ' >') ?>
			        </ul>
			        <p><?= $this->Paginator->counter() ?></p>
			    </div>
			</div>
	'''

	def tableData() '''
		«FOR Attribute attr : entity.attributes»
			«IF attr.reference»
				<td><?= $«entity.name.toLowerCase»->has('«attr.name.toLowerCase»') ? $this->Html->link($«entity.name.toLowerCase»->«attr.name.toLowerCase»->id, ['controller' => '«Inflector.plural(attr.name.toLowerCase).toFirstUpper»', 'action' => 'view', $«entity.name.toLowerCase»->«attr.name.toLowerCase»->id]) : '' ?></td>
			«ELSE»
				<td><?= h($«entity.name.toLowerCase»->«attr.name.toLowerCase») ?></td>
			«ENDIF»
		«ENDFOR»
		<td><?= $this->Number->format($«entity.name.toLowerCase»->id) ?></td>
		      <td class="actions">
		          <?= $this->Html->link(__('View'), ['action' => 'view', $«entity.name.toLowerCase»->id]) ?>
		          <?= $this->Html->link(__('Edit'), ['action' => 'edit', $«entity.name.toLowerCase»->id]) ?>
		          <?= $this->Form->postLink(__('Delete'), ['action' => 'delete', $«entity.name.toLowerCase»->id], ['confirm' => __('Are you sure you want to delete # {0}?', $«entity.name.toLowerCase»->id)]) ?>
		      </td>
	'''

	def tableHeaders() '''
		«FOR Attribute attr : entity.attributes»
			«IF attr.reference»
				<th><?= $this->Paginator->sort('«attr.name.toLowerCase»_id') ?></th>
			«ELSE»
				<th><?= $this->Paginator->sort('«attr.name.toLowerCase»') ?></th>
			«ENDIF»
		«ENDFOR»
		<th><?= $this->Paginator->sort('id') ?></th>
		<th class="actions"><?= __('Actions') ?></th>
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