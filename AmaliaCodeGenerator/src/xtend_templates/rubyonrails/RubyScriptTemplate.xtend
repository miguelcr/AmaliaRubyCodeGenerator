package xtend_templates.rubyonrails

import java.util.Collection
import java.util.List
import main.Project
import structure.domain_model.Attribute
import structure.domain_model.Entity
import structure.user_interface.ActionAIO
import structure.user_interface.ActionAIONavigation
import structure.user_interface.ActionAIOOperation
import structure.user_interface.ActionAIOOperationType
import structure.user_interface.DataAIO
import structure.user_interface.DataAIOType
import structure.user_interface.InteractionBlock
import structure.user_interface.InteractionBlockType
import structure.user_interface.InteractionSpace
import structure.user_interface.Menu
import structure.user_interface.MenuItem
import inflector.Inflector

class RubyScriptTemplate {
	private val controllerName = "pages";
	private var Project project;
	
	def generate (Project project){
		this.project = project
		return template
	}
	
	def template () '''
	# Generated by Amalia Code Generator
	# Here we are removing the Gemfile and starting over
	remove_file "Gemfile"
	run "touch Gemfile"
	# be sure to add source at the top of the file
	add_source 'https://rubygems.org'
	
	gem 'rails', '4.2.4'
	gem 'sqlite3'
	gem 'sass-rails', '~> 5.0'
	gem 'uglifier', '>= 1.3.0'
	gem 'coffee-rails', '~> 4.1.0'
	
	gem 'jquery-rails'
	gem 'turbolinks'
	gem 'jbuilder', '~> 2.0'
	gem 'sdoc', '~> 0.4.0'
	gem_group :development, :test do
	  gem 'byebug'
	end
	
	gem_group :development do
	  gem 'web-console', '~> 2.0'
	  gem 'spring'
	end
	
	gem 'bootstrap-sass'
	
	gem 'simple_form'
	gem 'rails_layout'
	
	# run 'spring stop'
	run 'bundle install'
	
	# instala um layout generico do bootstrap
	generate 'layout:install bootstrap3 --force'
	# instala o simple_form usando o bootstrap
	generate 'simple_form:install --bootstrap --force'
	
	# ativa as validacoes do html5 nas paginas
	comment_lines 'config/initializers/simple_form.rb', /config.browser_validations = false/
	insert_into_file 'config/initializers/simple_form.rb', :after => /# config.browser_validations = false/ do <<-TEXT
	 
	config.browser_validations = true
	TEXT
	end	
	
	# Scaffold all controllers models views
	
	# Pluralized names variables
	�FOR Entity e : project.domainModel.entities.values�
	�e.name.toLowerCase� = "�e.name.toLowerCase�".pluralize
	�ENDFOR�

	# Model and Scaffold
	�FOR Entity e : project.domainModel.entities.values�
		�IF !e.hasReferenceAttributes�
		generate(:model, "�e.name.toLowerCase� �
		FOR Attribute attr : e.attributes� �attr.name.toLowerCase�:�getDataType(attr.type.toLowerCase)��ENDFOR�")
		�ENDIF�
		generate(:scaffold, "�e.name.toLowerCase� �
		FOR Attribute attr : e.attributes� �attr.name.toLowerCase�:�getDataType(attr.type.toLowerCase)��ENDFOR�")
	�ENDFOR�
	
	�updateApplicationFile()�
	�createMenu()�
	
	#rotas
	remove_file 'config/routes.rb'
	
	create_file 'config/routes.rb' do <<-TEXT
	Rails.application.routes.draw do
	�FOR Entity e : project.domainModel.entities.values�
		resources :#{�e.name.toLowerCase�}
	�ENDFOR�
���	Escolhe a p�gina inicial da aplica��o
	�IF project.mainInteractionSpace != null �
		root '�controllerName�#�project.mainInteractionSpace.name.toLowerCase�'
	�ELSE�
		root '#{�project.domainModel.entities.values.get(0).name.toLowerCase�}#index'
	�ENDIF�
	end
	TEXT
	end
	
	# Cria o controlador e p�gina IMPORTANTE
	generate(:controller, "�controllerName.toFirstUpper��getPagesNames(project.interactionSpaces.values)�")
	remove_file 'app/controllers/�controllerName�_controller.rb'
	create_file 'app/controllers/�controllerName�_controller.rb' do <<-TEXT
	class �controllerName.toFirstUpper�Controller < ApplicationController
	��� Itera por todos os InteractionSpaces 
	�FOR InteractionSpace is : project.interactionSpaces.values�
��� Declara o conte�do necess�rio para cada InteractionSpace
		def �is.name.toLowerCase�
���			Adiciona o t�tulo � p�gina
			@title = "�is.text.toLowerCase.toFirstUpper�"
��� Itera os InteractionBlocks e adiciona o seu conte�do 
		�FOR InteractionBlock ib : is.interactionBlocks�			
			�IF ib.interactionBlockType == InteractionBlockType.ViewList�
��� Cont�m todos os elementos e seus atributos da entidade na ViewList
				@#{�ib.entity.name.toLowerCase�} = �ib.entity.name.toLowerCase.toFirstUpper�.all
			�ELSEIF ib.interactionBlockType == InteractionBlockType.ViewEntity�
				@new_�ib.entity.name.toLowerCase� = �is.entity.name.toFirstUpper�.new
				�IF ib.actionAIOs.size > 0 && hasUpdateOrCreateOp(ib) != null�
					�var ActionAIOOperationType aioOp = hasUpdateOrCreateOp(ib)�
					�IF aioOp == ActionAIOOperationType.CallUpdateOp�
��� 					Inst�ncia da entidiade cujo id � identificado por par�metro
						@instance_�ib.entity.name.toLowerCase� = �ib.entity.name.toFirstUpper�.find(params[:param])
					�ENDIF�
				�ELSE�
��� 				Inst�ncia da entidiade cujo id � identificado por par�metro
					@instance_�ib.entity.name.toLowerCase� = �ib.entity.name.toFirstUpper�.find(params[:param])
				�ENDIF�
			�ELSEIF ib.interactionBlockType == InteractionBlockType.ViewRelatedList�
���				 Valida se a entidade do IB tem uma rela��o com a entidade principal no IS
���				 Exemplos: Veiculo 1 --- * Aluguer
���				 CASO1 - Os alugueres de um veiculo / CASO2 - Os veiculos de um aluguer
���				 CASO1: Se a entidade principal � o ve�culo, cada aluguer tem o id do ve�culo alugado, logo
���				 		para identificar os alugueres do ve�culo basta pesquisar pelo campo referenciado
���				 CASO2: Se a entdade principal � o Aluguer, cada aluguer s� tem um ve�culo e para listar esse
���				 		ve�culo � necess�rio saber primeiro qual o ID do ve�culo que est� associado ao aluguer
			 	�IF ib.entity.getAttributeByNameIgnoreCase(is.entity.name.toLowerCase) != null �
			 		��� Aplicando o CASO1
			 		@list_�ib.entity.name.toLowerCase� = �ib.entity.name.toLowerCase.toFirstUpper�.where(�is.entity.name.toLowerCase�_id:params[:param])
			 	�ELSE�
			 		��� Aplicando o CASO2
			 		@list_�ib.entity.name.toLowerCase� = �ib.entity.name.toLowerCase.toFirstUpper�.where(id:@instance_�is.entity.name.toLowerCase�.�ib.entity.name.toLowerCase�_id)
			 	�ENDIF�					 	
			�ELSEIF ib.interactionBlockType == InteractionBlockType.ViewRelatedEntity�
���				 Valida se a entidade do IB tem uma rela��o com a entidade principal no IS
���				 Exemplos: Veiculo 1 --- * Aluguer
���				 CASO1 - Os alugueres de um veiculo / CASO2 - Os veiculos de um aluguer
���				 CASO1: Se a entidade principal � o ve�culo, cada aluguer tem o id do ve�culo alugado, logo
���				 		para identificar os alugueres do ve�culo basta pesquisar pelo campo referenciado
���				 CASO2: Se a entdade principal � o Aluguer, cada aluguer s� tem um ve�culo e para listar esse
���				 		ve�culo � necess�rio saber primeiro qual o ID do ve�culo que est� associado ao aluguer
			 	�IF ib.entity.getAttributeByNameIgnoreCase(is.entity.name.toLowerCase) != null �
��� 			Aplicando o CASO1
			 		@list_�ib.entity.name.toLowerCase� = �ib.entity.name.toLowerCase.toFirstUpper�.where(�is.entity.name.toLowerCase�_id:params[:param])
			 	�ELSE�
��� 			Aplicando o CASO2
			 		@list_�ib.entity.name.toLowerCase� = �ib.entity.name.toLowerCase.toFirstUpper�.where(id:@instance_�is.entity.name.toLowerCase�.�ib.entity.name.toLowerCase�_id)
			 	�ENDIF�
			�ENDIF�
		�ENDFOR�
		end
	�ENDFOR�
	end
	TEXT
	end
	
	��� Percorre todos os InteractionSapaces de modo a invocar um m�todo que coloca o conte�do 
	��� dos InteractionBlocks no template
	�FOR InteractionSpace is : project.interactionSpaces.values�
			�toText(is.interactionBlocks, is)�
	�ENDFOR�
	
	�changeOriginalForm�
	�changeOriginalIndex�
	�changeOriginalShow�
	
	rake "db:migrate"
	'''
	
	def getDataType(String type){
		var output = "string";
		switch(type) {
			case 'references':
				output = 'references'			
			case 'int':
				output = 'integer'
			case 'number':
				output = 'integer'
			case 'numeric':
				output = 'integer'
			case 'integer':
				output = 'integer'				
			case 'date':
				output = 'date'
			default:
				output = 'string'
		}
		
		return output;
	}
	
	def changeOriginalShow()'''
	�FOR Entity e : project.domainModel.entities.values�
		�IF e.referencedAttributes.size > 0�
			remove_file 'app/views/�Inflector.plural(e.name.toLowerCase)�/show.html.erb'
			create_file 'app/views/�Inflector.plural(e.name.toLowerCase)�/show.html.erb' do <<-TEXT		
				<p id="notice"><%= notice %></p>
		      	�FOR Attribute attr : e.attributes�
		      		�IF attr.reference�
						�var Entity associatedEntity = project.domainModel.getEntityByName(attr.name)�
						�var List<Attribute> entityAttributes = associatedEntity.attributes�
						<p>
						  	<strong>�attr.name.toFirstUpper�:</strong>
						  	<%= @�e.name.toLowerCase�.�attr.name.toLowerCase�.�entityAttributes.get(0).name.toLowerCase� %>
						</p>
					�ELSE�
						<p>
							<strong>�attr.name.toFirstUpper�:</strong>
						  	<%= @�e.name.toLowerCase�.�attr.name.toLowerCase� %>
						</p>
					�ENDIF�		      		
		      	�ENDFOR�
				<%= link_to 'Edit', edit_�e.name.toLowerCase�_path(@�e.name.toLowerCase�) %> |
				<%= link_to 'Back', #{�e.name.toLowerCase�}_path %>
			TEXT
			end
		�ENDIF�
	�ENDFOR�
	'''	
	
	def changeOriginalIndex()'''
	�FOR Entity e : project.domainModel.entities.values�
		�IF e.referencedAttributes.size > 0�
			remove_file 'app/views/�Inflector.plural(e.name.toLowerCase)�/index.html.erb'
			create_file 'app/views/�Inflector.plural(e.name.toLowerCase)�/index.html.erb' do <<-TEXT
			<p id="notice"><%= notice %></p>
			
			<h1>Listing #{�e.name.toLowerCase�}</h1>
			
			<table>
			  <thead>
			    <tr>
			   �FOR Attribute attr : e.attributes�
			   <th>�attr.name.toFirstUpper�</th>
			   �ENDFOR�
			      <th colspan="3"></th>
			    </tr>
			  </thead>
			
			  <tbody>
			    <% @#{�e.name.toLowerCase�}.each do |�e.name.toLowerCase�| %>
			      <tr>
		      	�FOR Attribute attr : e.attributes�
		      		�IF attr.reference�
						�var Entity associatedEntity = project.domainModel.getEntityByName(attr.name)�
						�var List<Attribute> entityAttributes = associatedEntity.attributes�
		      			<td><%= �e.name.toLowerCase�.�attr.name.toLowerCase�.�entityAttributes.get(0).name.toLowerCase� %></td>
		      		�ELSE�
		      			<td><%= �e.name.toLowerCase�.�attr.name.toLowerCase� %></td>
		      		�ENDIF�
			   �ENDFOR�
			        <td><%= link_to 'Show', �e.name.toLowerCase� %></td>
			        <td><%= link_to 'Edit', edit_�e.name.toLowerCase�_path(�e.name.toLowerCase�) %></td>
			        <td><%= link_to 'Destroy', �e.name.toLowerCase�, method: :delete, data: { confirm: 'Are you sure?' } %></td>
			      </tr>
			    <% end %>
			  </tbody>
			</table>
			
			<br>
			
			<%= link_to 'New �e.name.toFirstUpper�', new_�e.name.toLowerCase�_path %>
			TEXT
			end
		�ENDIF�
	�ENDFOR�
	'''
	
	def changeOriginalForm()'''
	�FOR Entity e : project.domainModel.entities.values�
		�IF e.referencedAttributes.size > 0�
			remove_file 'app/views/�Inflector.plural(e.name.toLowerCase)�/_form.html.erb'
			create_file 'app/views/�Inflector.plural(e.name.toLowerCase)�/_form.html.erb' do <<-TEXT
			<%= simple_form_for(@�e.name.toLowerCase�) do |f| %>
			  <%= f.error_notification %>
			
			  <div class="form-inputs">
			  �FOR Attribute attr : e.attributes�
			  	�IF attr.reference�
				�var Entity associatedEntity = project.domainModel.getEntityByName(attr.name)�
				�var List<Attribute> entityAttributes = associatedEntity.attributes�
				<%= f.association :�attr.name.toLowerCase�,label_method: :�entityAttributes.get(0).name.toLowerCase�, required:true %>
			  	�ELSE�
			  		<%= f.input :�attr.name.toLowerCase� %>
			  	�ENDIF�
			  �ENDFOR�			
			  </div>
			
			  <div class="form-actions">
			    <%= f.button :submit %>
			  </div>
			<% end %>
			TEXT
			end
		�ENDIF�
	�ENDFOR�
	'''	
	
	def toText(List<InteractionBlock> interactionBlocks, InteractionSpace parent)'''
	# INTERACTION SPACE: �parent.name.toLowerCase�
��� Removemos a controlador original
	remove_file 'app/views/�controllerName�/�parent.name.toLowerCase�.html.erb'
��� Criamos um novo controlador total gerado no template
	create_file 'app/views/�controllerName�/�parent.name.toLowerCase�.html.erb' do <<-TEXT
��� Exibe mensagens de erro
	<p id="notice">
		<%= notice %>
	</p>
	�FOR InteractionBlock ib : interactionBlocks�
		�switch ib.interactionBlockType {
			case ViewEntity: { viewEntity(ib, parent) }
			case ViewList: { viewList(ib, parent) }
			case ViewListS: { viewList(ib, parent) }
			case ViewRelatedEntity: { viewRelatedEntity(ib, parent) }
			case ViewRelatedList: { viewRelatedList(ib, parent) }
			case ViewRelatedListS: { viewRelatedList(ib, parent) }
		}�
	�ENDFOR�
	TEXT
	end	
	'''

	def viewRelatedEntity(InteractionBlock ib, InteractionSpace parent)'''
	<h3>�ib.text�</h3>
	
	<table style="width:100%; border:1px solid;">
		<thead>
	    	<tr>
	    	�FOR DataAIO d : ib.dataAIOs�
	    		<th>�d.text.toFirstUpper�</th>
	    	�ENDFOR�	      
	      		<th colspan="�ib.actionAIOs.size�"></th>
	    	</tr>
		</thead>	
	  	<tbody>
	    <% @list_�ib.entity.name.toLowerCase�.each do |�ib.entity.name.toLowerCase�| %>
	      <tr>
			�FOR DataAIO d : ib.dataAIOs�
			<td><%= �ib.entity.name.toLowerCase�.�d.attribute.name� %></td>
	    	�ENDFOR�
	    	�FOR ActionAIO aio : ib.actionAIOs�
	    		�IF aio instanceof ActionAIONavigation�
					�var aio_ = aio as ActionAIONavigation�
					<td><a href="/�controllerName�/�aio_.target.name.toLowerCase�?param=<%= �ib.entity.name.toLowerCase�.id %>">�aio_.text�</a></td>
	    		�ENDIF�	    	
	    	�ENDFOR�
	      </tr>
	    <% end %>
	  </tbody>
	</table>
	
	<br>
	'''

	def viewRelatedList(InteractionBlock ib, InteractionSpace parent)'''
	<h3>�ib.text�</h3>
	
	<table style="width:100%; border:1px solid;">
	  <thead>
	    <tr>
	    	�FOR DataAIO d : ib.dataAIOs�
	    	<th>�d.text.toFirstUpper�</th>
	    	�ENDFOR�	      
	      <th colspan="�ib.actionAIOs.size�"></th>
	    </tr>
	  </thead>
	
	  <tbody>
	    <% @list_�ib.entity.name.toLowerCase�.each do |�ib.entity.name.toLowerCase�| %>
	      <tr>
			�FOR DataAIO d : ib.dataAIOs�
			<td><%= �ib.entity.name.toLowerCase�.�d.attribute.name� %></td>
	    	�ENDFOR�
	    	�FOR ActionAIO aio : ib.actionAIOs�
	    		�IF aio instanceof ActionAIONavigation�
					�var aio_ = aio as ActionAIONavigation�
					<td><a href="/�controllerName�/�aio_.target.name.toLowerCase�?param=<%= �ib.entity.name.toLowerCase�.id %>">�aio_.text�</a></td>
	    		�ENDIF�	    	
	    	�ENDFOR�
	      </tr>
	    <% end %>
	  </tbody>
	</table>
	
	<br>
	'''

	def viewEntity(InteractionBlock ib, InteractionSpace parent)'''
		<h1>�ib.text�</h1>
		�IF ib.actionAIOs.size == 0 || hasUpdateOrCreateOp(ib) == null �
���			 Se n�o tiver nenhum Action AIO, assume que � s� para listar
			�FOR DataAIO data : ib.dataAIOs�	
				<p>	
				  <strong>�data.text.toFirstUpper�</strong>
				  <%= @instance_�ib.entity.name.toLowerCase�.�data.attribute.name� %>
				</p>
			�ENDFOR�
		�ELSE�
			�var ActionAIOOperationType aioOp = hasUpdateOrCreateOp(ib)�
			�IF aioOp == ActionAIOOperationType.CallCreateOp�
				<%= simple_form_for(@new_�ib.entity.name.toLowerCase�) do |f| %>
			�ELSE�
				<%= simple_form_for(@instance_�ib.entity.name.toLowerCase�) do |f| %>
			�ENDIF�
			<%= f.error_notification %>
			<div class="form-inputs">
		  		�FOR DataAIO data : ib.dataAIOs�
					�IF data.type == DataAIOType.OutputOnly && aioOp == ActionAIOOperationType.CallUpdateOp�
						<%= @instance_�ib.entity.name.toLowerCase�.�data.attribute.name� %>
					�ELSE�
						<%= f.input :�data.attribute.name�, required:true %>
					�ENDIF�
			  	�ENDFOR�
				�FOR Attribute att : ib.entity.referencedAttributes�
					�var Entity associatedEntity = project.domainModel.getEntityByName(att.name)�
					�var List<Attribute> entityAttributes = associatedEntity.attributes�
					�IF entityAttributes.size > 2�
					<%= f.association :�att.name.toLowerCase�,:label_method => lambda { |t| h(t.�entityAttributes.get(0).name.toLowerCase� + " " + t.�entityAttributes.get(1).name.toLowerCase�) }, required:true %>
					�ELSE�
					<%= f.association :�att.name.toLowerCase�,label_method: :�entityAttributes.get(0).name.toLowerCase�, required:true %>
					�ENDIF�
				�ENDFOR�
			</div>
			<div class="form-actions">
				<%= f.button :submit %>
			</div>
			<% end %>
		�ENDIF�
	'''

	def hasUpdateOrCreateOp(InteractionBlock interactionBlock){
		for(ActionAIO aio : interactionBlock.actionAIOs){
			if(aio instanceof ActionAIOOperation){				
				var ActionAIOOperation aioOp = aio as ActionAIOOperation;
				if(aioOp.actionAIOOperationType == ActionAIOOperationType.CallCreateOp){
					return ActionAIOOperationType.CallCreateOp;
				}else if(aioOp.actionAIOOperationType == ActionAIOOperationType.CallUpdateOp)
					return ActionAIOOperationType.CallUpdateOp;
			}			
		}
		return null;
	}	

	def viewList(InteractionBlock ib, InteractionSpace parent)'''
	<h1>�ib.text�</h1>
	
	<table style="width:100%; border:1px solid;">
	  <thead>
	    <tr>
	    	�FOR DataAIO d : ib.dataAIOs�
	    	<th>�d.text�</th>
	    	�ENDFOR�	      
	      <th colspan="�ib.actionAIOs.size�"></th>
	    </tr>
	  </thead>
	
	  <tbody>
	    <% @#{�ib.entity.name.toLowerCase�}.each do |�ib.entity.name.toLowerCase�| %>
	      <tr>
			�FOR DataAIO d : ib.dataAIOs�
			<td><%= �ib.entity.name.toLowerCase�.�d.attribute.name� %></td>
	    	�ENDFOR�
	    	�FOR ActionAIO aio : ib.actionAIOs�
	    		�IF aio instanceof ActionAIONavigation�
					�var aio_ = aio as ActionAIONavigation�
					<td><a href="/�controllerName�/�aio_.target.name.toLowerCase�?param=<%= �ib.entity.name.toLowerCase�.id %>">�aio_.text�</a></td>
	    		�ENDIF�	    	
	    	�ENDFOR�
	      </tr>
	    <% end %>
	  </tbody>
	</table>
	
	<br>
���	<%= link_to 'New �ib.entity.name�', new_�ib.entity.name.toLowerCase�_path %>
	'''
	
	/*
	 * Creates a menu for the application
	 */
	def createMenu()'''
	#Menu de navega��o
	remove_file 'app/views/layouts/_navigation_links.html.erb'
	
	create_file 'app/views/layouts/_navigation_links.html.erb' do <<-TEXT
���	Verifica se o utilizador definiu um menu na p�gina inicial
	�IF project.mainInteractionSpace.menuBar == null�
		�FOR Entity e : project.domainModel.entities.values�
		 <%= link_to '�e.name.toUpperCase�', #{�e.name.toLowerCase�}_path, class: 'navbar-brand' %>
		�ENDFOR�
		<li class="dropdown">
		   	<a href="#" class="dropdown-toggle" data-toggle="dropdown">�controllerName.toUpperCase� <b class="caret"></b></a>
		   	<ul class="dropdown-menu">
		   		�FOR InteractionSpace is : project.interactionSpacesWithoutParams�
		   		<li><a href="/�controllerName�/�is.name.toLowerCase�">�is.text�</a></li>
		   		�ENDFOR�
		 	</ul>
		</li>
	�ELSE�		
		�FOR Menu menu : project.mainInteractionSpace.menuBar.menus�			
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">�menu.text.toUpperCase� <b class="caret"></b></a>
				<ul class="dropdown-menu">
		   		�FOR MenuItem menuItem : menu.menuItems�
					<li><a href="/�controllerName�/�menuItem.target.name.toLowerCase�">�menuItem.target.text�</a></li>
		   		�ENDFOR�
				</ul>
			</li>
		�ENDFOR�
	�ENDIF�
	TEXT
	end

	
	#Menu de navega��o
	remove_file 'app/views/layouts/_navigation.html.erb'
	
	create_file 'app/views/layouts/_navigation.html.erb' do <<-TEXT
	<%# navigation styled for Bootstrap 3.0 %>
	<nav class="navbar navbar-inverse navbar-fixed-top">
	  <div class="container">
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	    </div>
	    <div class="collapse navbar-collapse">
	      <ul class="nav navbar-nav">
	        <%= render 'layouts/navigation_links' %>
	      </ul>
	    </div>
	  </div>
	</nav>
	TEXT
	end
	'''
	
	/**
	 * Updates the application page
	 */
	def updateApplicationFile()'''
	# Modifica o esquema da p�gina para permitir o uso de um t�tulo personalizado por p�gina
	remove_file 'app/views/layouts/application.html.erb'

	create_file 'app/views/layouts/application.html.erb' do <<-TEXT
	<!DOCTYPE html>
	<html>
		<head>
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
���		Allow us to use a diferent title in pages, using the variable @title as the title
			<title><%= @title || "Default Page Title" %></title>
			<meta name="description" content="<%= content_for?(:description) ? yield(:description) : "Default Page Description" %>">
			<%= stylesheet_link_tag 'application', media: 'all', 'data-turbolinks-track' => true %>
			<%= javascript_include_tag 'application', 'data-turbolinks-track' => true %>
			<%= csrf_meta_tags %>
		</head>
	 	<body>
			<header>
	  			<%= render 'layouts/navigation' %>
			</header>
			<main role="main">
	   			<%= render 'layouts/messages' %>
	   			<%= yield %>
			</main>
		</body>
	</html>

	TEXT
	end
	'''
	
	/**
	 * Returns the name of all pages (InteractionSpaces) to be generated
	 * Devolve o nome de todas as p�ginas (InteractionSpaces) que v�o ser gerados
	 */
	def getPagesNames(Collection<InteractionSpace> is){
		var names = "";
		for(InteractionSpace i : is)
			names += " " + i.name.toLowerCase
		return names;
	}	
}