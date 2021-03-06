package xtend_templates.cakephp

import main.Project
import exceptions.ParserException
import structure.domain_model.Entity
import java.util.ArrayList
import structure.domain_model.Attribute
import inflector.Inflector

class Controller {
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
			throw new ParserException("A domain model must be defined")
	}

	def template() '''
		<?php
		// Generated by Amalia Code Generator	
		namespace App\Controller;
		
		use App\Controller\AppController;
		
		/**
		 * �entity.name.toLowerCase.toFirstUpper� Controller
		 *
		 * @property \App\Model\Table\�entity.name.toLowerCase.toFirstUpper�Table $�Inflector.plural(entity.name.toLowerCase).toFirstUpper�
		 */
		class �Inflector.plural(entity.name.toLowerCase).toFirstUpper�Controller extends AppController
		{
		
		    /**
		     * Index method
		     *
		     * @return void
		     */
		    public function index()
		    {
		    �IF entity.hasReferenceAttributes�
		    	$this->paginate = ['contain' => [�getContainReferenced�]];
		    �ENDIF�		    
		    $this->set('�Inflector.plural(entity.name.toLowerCase)�', $this->paginate($this->�Inflector.plural(entity.name.toLowerCase).toFirstUpper�));
		    $this->set('_serialize', ['�Inflector.plural(entity.name.toLowerCase)�']);
		    }
				
				/**
				 * View method
				 *
				 * @param string|null $id �entity.name.toLowerCase.toFirstUpper� id.
				 * @return void
				 * @throws \Cake\Network\Exception\NotFoundException When record not found.
				 */
				public function view($id = null)
				{

				$�entity.name.toLowerCase� = $this->�Inflector.plural(entity.name.toLowerCase).toFirstUpper�->get($id, [
				    'contain' => [�contain�]
				]);

				$this->set('�entity.name.toLowerCase�', $�entity.name.toLowerCase�);
				$this->set('_serialize', ['�entity.name.toLowerCase�']);
				}
		
		    /**
		     * Add method
		     *
		     * @return void Redirects on successful add, renders view otherwise.
		     */
		    public function add()
		    {
		        $�entity.name.toLowerCase� = $this->�Inflector.plural(entity.name.toLowerCase).toFirstUpper�->newEntity();
		        if ($this->request->is('post')) {
		            $�entity.name.toLowerCase� = $this->�Inflector.plural(entity.name.toLowerCase).toFirstUpper�->patchEntity($�entity.name.toLowerCase�, $this->request->data);
		            if ($this->�Inflector.plural(entity.name.toLowerCase).toFirstUpper�->save($�entity.name.toLowerCase�)) {
		                $this->Flash->success(__('The �entity.name.toLowerCase� has been saved.'));
		                return $this->redirect(['action' => 'index']);
		            } else {
		                $this->Flash->error(__('The �entity.name.toLowerCase� could not be saved. Please, try again.'));
		            }
		        }
		        �addMethodRelation�
		        $this->set('_serialize', ['�entity.name.toLowerCase�']);
		    }
		
		    /**
		     * Edit method
		     *
		     * @param string|null $id �entity.name.toLowerCase.toFirstUpper� id.
		     * @return void Redirects on successful edit, renders view otherwise.
		     * @throws \Cake\Network\Exception\NotFoundException When record not found.
		     */
		    public function edit($id = null)
		    {
		        $�entity.name.toLowerCase� = $this->�Inflector.plural(entity.name.toLowerCase).toFirstUpper�->get($id, [
		            'contain' => []
		        ]);
		        if ($this->request->is(['patch', 'post', 'put'])) {
		            $�entity.name.toLowerCase� = $this->�Inflector.plural(entity.name.toLowerCase).toFirstUpper�->patchEntity($�entity.name.toLowerCase�, $this->request->data);
		            if ($this->�Inflector.plural(entity.name.toLowerCase).toFirstUpper�->save($�entity.name.toLowerCase�)) {
		                $this->Flash->success(__('The �entity.name.toLowerCase� has been saved.'));
		                return $this->redirect(['action' => 'index']);
		            } else {
		                $this->Flash->error(__('The �entity.name.toLowerCase� could not be saved. Please, try again.'));
		            }
		        }
		        �addMethodRelation�
		        $this->set('_serialize', ['�entity.name.toLowerCase�']);
		    }
		
		    /**
		     * Delete method
		     *
		     * @param string|null $id �entity.name.toLowerCase.toFirstUpper� id.
		     * @return \Cake\Network\Response|null Redirects to index.
		     * @throws \Cake\Network\Exception\NotFoundException When record not found.
		     */
		    public function delete($id = null)
		    {
		        $this->request->allowMethod(['post', 'delete']);
		        $�entity.name.toLowerCase� = $this->�Inflector.plural(entity.name.toLowerCase).toFirstUpper�->get($id);
		        if ($this->�Inflector.plural(entity.name.toLowerCase).toFirstUpper�->delete($�entity.name.toLowerCase�)) {
		            $this->Flash->success(__('The �entity.name.toLowerCase� has been deleted.'));
		        } else {
		            $this->Flash->error(__('The �entity.name.toLowerCase� could not be deleted. Please, try again.'));
		        }
		        return $this->redirect(['action' => 'index']);
		    }
		}
	'''

	def addMethodRelation() '''
		�IF !entity.hasReferenceAttributes�
			$this->set(compact('�entity.name.toLowerCase�'));
		�ELSE�
			�FOR Attribute attr : entity.referencedAttributes�
				$�Inflector.plural(attr.name.toLowerCase)� = $this->�Inflector.plural(entity.name.toLowerCase).toFirstUpper�->�Inflector.plural(attr.name.toLowerCase).toFirstUpper�->find('list', ['limit' => 200]);
			�ENDFOR�
			$this->set(compact('�entity.name.toLowerCase�', �getReferencesPlural�));
		�ENDIF�
		
	'''

	def getReferencesPlural() {
		var String output = ""
		var boolean first = true;
		for (Attribute attr : entity.referencedAttributes) {
			if (first) {
				first = false;
				output = "'" + attr.name.toLowerCase + "s'"
			} else
				output += ", '" + attr.name.toLowerCase + "s'"
		}

		return output
	}

	def getContainWhereIsReferences() {
		// Expected output: 'Carros', 'Clientes'
		/*
		 * Colocar as entidades onde esta est� referenciada e as entidades que esta referencia
		 */
		var String output = ""
		var first = true

		var ArrayList<String> entitiesAdded = new ArrayList;

		// Entidades onde � referenciada
		for (Entity e : project.domainModel.whereEntityIsReferenced(entity)) {
			if (e != entity) {
				entitiesAdded.add(e.name.toLowerCase);
				if (first) {
					output = "'" + e.name.toLowerCase.toFirstUpper + "s'"
					first = false;
				} else
					output += ", '" + e.name.toLowerCase.toFirstUpper + "s'"
			}
		}

		return output
	}

	def getContain() {
		// Expected output: 'Carros', 'Clientes'
		/*
		 * Colocar as entidades onde esta est� referenciada e as entidades que esta referencia
		 */
		var String output = ""
		var first = true

		var ArrayList<String> entitiesAdded = new ArrayList;

		// Entidades onde � referenciada
		for (Entity e : project.domainModel.whereEntityIsReferenced(entity)) {
			if (e != entity) {
				entitiesAdded.add(e.name.toLowerCase);
				if (first) {
					output = "'" + e.name.toLowerCase.toFirstUpper + "s'"
					first = false;
				} else
					output += ", '" + e.name.toLowerCase.toFirstUpper + "s'"
			}
		}
		// Entidades que referencia
		for (Attribute attr : entity.referencedAttributes) {
			if (!entitiesAdded.contains(attr.name.toLowerCase)) {
				if (first) {
					output = "'" + attr.name.toLowerCase.toFirstUpper + "s'"
					first = false;
				} else
					output += ", '" + attr.name.toLowerCase.toFirstUpper + "s'"
			}
		}

		return output
	}
	
	def getContainReferenced() {
		// Expected output: 'Carros', 'Clientes'
		/*
		 * Colocar as entidades onde esta est� referenciada e as entidades que esta referencia
		 */
		var String output = ""
		var first = true

		var ArrayList<String> entitiesAdded = new ArrayList;

		// Entidades onde � referenciada
//		for (Entity e : project.domainModel.whereEntityIsReferenced(entity)) {
//			if (e != entity) {
//				entitiesAdded.add(e.name.toLowerCase);
//				if (first) {
//					output = "'" + e.name.toLowerCase.toFirstUpper + "s'"
//					first = false;
//				} else
//					output += ", '" + e.name.toLowerCase.toFirstUpper + "s'"
//			}
//		}
		// Entidades que referencia
		for (Attribute attr : entity.referencedAttributes) {
			if (!entitiesAdded.contains(attr.name.toLowerCase)) {
				if (first) {
					output = "'" + attr.name.toLowerCase.toFirstUpper + "s'"
					first = false;
				} else
					output += ", '" + attr.name.toLowerCase.toFirstUpper + "s'"
			}
		}

		return output
	}	
}