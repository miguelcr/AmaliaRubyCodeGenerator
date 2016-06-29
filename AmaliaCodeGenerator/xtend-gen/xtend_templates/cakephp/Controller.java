package xtend_templates.cakephp;

import com.google.common.base.Objects;
import exceptions.ParserException;
import inflector.Inflector;
import java.util.ArrayList;
import java.util.List;
import main.Project;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import structure.domain_model.Attribute;
import structure.domain_model.DomainModel;
import structure.domain_model.Entity;

@SuppressWarnings("all")
public class Controller {
  private Project project;
  
  private Entity entity;
  
  public Controller(final Project project) {
    this.project = project;
  }
  
  public CharSequence generate(final Entity entity) {
    try {
      this.entity = entity;
      boolean _and = false;
      boolean _and_1 = false;
      boolean _notEquals = (!Objects.equal(this.project, null));
      if (!_notEquals) {
        _and_1 = false;
      } else {
        DomainModel _domainModel = this.project.getDomainModel();
        boolean _notEquals_1 = (!Objects.equal(_domainModel, null));
        _and_1 = _notEquals_1;
      }
      if (!_and_1) {
        _and = false;
      } else {
        boolean _notEquals_2 = (!Objects.equal(entity, null));
        _and = _notEquals_2;
      }
      if (_and) {
        return this.template();
      } else {
        throw new ParserException("A domain model must be defined");
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public CharSequence template() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?php");
    _builder.newLine();
    _builder.append("// Generated by Amalia Code Generator\t");
    _builder.newLine();
    _builder.append("namespace App\\Controller;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("use App\\Controller\\AppController;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* ");
    String _name = this.entity.getName();
    String _lowerCase = _name.toLowerCase();
    String _firstUpper = StringExtensions.toFirstUpper(_lowerCase);
    _builder.append(_firstUpper, " ");
    _builder.append(" Controller");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* @property \\App\\Model\\Table\\");
    String _name_1 = this.entity.getName();
    String _lowerCase_1 = _name_1.toLowerCase();
    String _firstUpper_1 = StringExtensions.toFirstUpper(_lowerCase_1);
    _builder.append(_firstUpper_1, " ");
    _builder.append("Table $");
    String _name_2 = this.entity.getName();
    String _lowerCase_2 = _name_2.toLowerCase();
    String _plural = Inflector.plural(_lowerCase_2);
    String _firstUpper_2 = StringExtensions.toFirstUpper(_plural);
    _builder.append(_firstUpper_2, " ");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("class ");
    String _name_3 = this.entity.getName();
    String _lowerCase_3 = _name_3.toLowerCase();
    String _plural_1 = Inflector.plural(_lowerCase_3);
    String _firstUpper_3 = StringExtensions.toFirstUpper(_plural_1);
    _builder.append(_firstUpper_3, "");
    _builder.append("Controller extends AppController");
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* Index method");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("*");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* @return void");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public function index()");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    {
      boolean _hasReferenceAttributes = this.entity.hasReferenceAttributes();
      if (_hasReferenceAttributes) {
        _builder.append("    ");
        _builder.append("$this->paginate = [\'contain\' => [");
        String _containReferenced = this.getContainReferenced();
        _builder.append(_containReferenced, "    ");
        _builder.append("]];");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("    ");
    _builder.append("$this->set(\'");
    String _name_4 = this.entity.getName();
    String _lowerCase_4 = _name_4.toLowerCase();
    String _plural_2 = Inflector.plural(_lowerCase_4);
    _builder.append(_plural_2, "    ");
    _builder.append("\', $this->paginate($this->");
    String _name_5 = this.entity.getName();
    String _lowerCase_5 = _name_5.toLowerCase();
    String _plural_3 = Inflector.plural(_lowerCase_5);
    String _firstUpper_4 = StringExtensions.toFirstUpper(_plural_3);
    _builder.append(_firstUpper_4, "    ");
    _builder.append("));");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("$this->set(\'_serialize\', [\'");
    String _name_6 = this.entity.getName();
    String _lowerCase_6 = _name_6.toLowerCase();
    String _plural_4 = Inflector.plural(_lowerCase_6);
    _builder.append(_plural_4, "    ");
    _builder.append("\']);");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("\t\t ");
    _builder.append("* View method");
    _builder.newLine();
    _builder.append("\t\t ");
    _builder.append("*");
    _builder.newLine();
    _builder.append("\t\t ");
    _builder.append("* @param string|null $id ");
    String _name_7 = this.entity.getName();
    String _lowerCase_7 = _name_7.toLowerCase();
    String _firstUpper_5 = StringExtensions.toFirstUpper(_lowerCase_7);
    _builder.append(_firstUpper_5, "\t\t ");
    _builder.append(" id.");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t ");
    _builder.append("* @return void");
    _builder.newLine();
    _builder.append("\t\t ");
    _builder.append("* @throws \\Cake\\Network\\Exception\\NotFoundException When record not found.");
    _builder.newLine();
    _builder.append("\t\t ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("public function view($id = null)");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("$");
    String _name_8 = this.entity.getName();
    String _lowerCase_8 = _name_8.toLowerCase();
    _builder.append(_lowerCase_8, "\t\t");
    _builder.append(" = $this->");
    String _name_9 = this.entity.getName();
    String _lowerCase_9 = _name_9.toLowerCase();
    String _plural_5 = Inflector.plural(_lowerCase_9);
    String _firstUpper_6 = StringExtensions.toFirstUpper(_plural_5);
    _builder.append(_firstUpper_6, "\t\t");
    _builder.append("->get($id, [");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t    ");
    _builder.append("\'contain\' => [");
    String _contain = this.getContain();
    _builder.append(_contain, "\t\t    ");
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("]);");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("$this->set(\'");
    String _name_10 = this.entity.getName();
    String _lowerCase_10 = _name_10.toLowerCase();
    _builder.append(_lowerCase_10, "\t\t");
    _builder.append("\', $");
    String _name_11 = this.entity.getName();
    String _lowerCase_11 = _name_11.toLowerCase();
    _builder.append(_lowerCase_11, "\t\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("$this->set(\'_serialize\', [\'");
    String _name_12 = this.entity.getName();
    String _lowerCase_12 = _name_12.toLowerCase();
    _builder.append(_lowerCase_12, "\t\t");
    _builder.append("\']);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* Add method");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("*");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* @return void Redirects on successful add, renders view otherwise.");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public function add()");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("$");
    String _name_13 = this.entity.getName();
    String _lowerCase_13 = _name_13.toLowerCase();
    _builder.append(_lowerCase_13, "        ");
    _builder.append(" = $this->");
    String _name_14 = this.entity.getName();
    String _lowerCase_14 = _name_14.toLowerCase();
    String _plural_6 = Inflector.plural(_lowerCase_14);
    String _firstUpper_7 = StringExtensions.toFirstUpper(_plural_6);
    _builder.append(_firstUpper_7, "        ");
    _builder.append("->newEntity();");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("if ($this->request->is(\'post\')) {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("$");
    String _name_15 = this.entity.getName();
    String _lowerCase_15 = _name_15.toLowerCase();
    _builder.append(_lowerCase_15, "            ");
    _builder.append(" = $this->");
    String _name_16 = this.entity.getName();
    String _lowerCase_16 = _name_16.toLowerCase();
    String _plural_7 = Inflector.plural(_lowerCase_16);
    String _firstUpper_8 = StringExtensions.toFirstUpper(_plural_7);
    _builder.append(_firstUpper_8, "            ");
    _builder.append("->patchEntity($");
    String _name_17 = this.entity.getName();
    String _lowerCase_17 = _name_17.toLowerCase();
    _builder.append(_lowerCase_17, "            ");
    _builder.append(", $this->request->data);");
    _builder.newLineIfNotEmpty();
    _builder.append("            ");
    _builder.append("if ($this->");
    String _name_18 = this.entity.getName();
    String _lowerCase_18 = _name_18.toLowerCase();
    String _plural_8 = Inflector.plural(_lowerCase_18);
    String _firstUpper_9 = StringExtensions.toFirstUpper(_plural_8);
    _builder.append(_firstUpper_9, "            ");
    _builder.append("->save($");
    String _name_19 = this.entity.getName();
    String _lowerCase_19 = _name_19.toLowerCase();
    _builder.append(_lowerCase_19, "            ");
    _builder.append(")) {");
    _builder.newLineIfNotEmpty();
    _builder.append("                ");
    _builder.append("$this->Flash->success(__(\'The ");
    String _name_20 = this.entity.getName();
    String _lowerCase_20 = _name_20.toLowerCase();
    _builder.append(_lowerCase_20, "                ");
    _builder.append(" has been saved.\'));");
    _builder.newLineIfNotEmpty();
    _builder.append("                ");
    _builder.append("return $this->redirect([\'action\' => \'index\']);");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("} else {");
    _builder.newLine();
    _builder.append("                ");
    _builder.append("$this->Flash->error(__(\'The ");
    String _name_21 = this.entity.getName();
    String _lowerCase_21 = _name_21.toLowerCase();
    _builder.append(_lowerCase_21, "                ");
    _builder.append(" could not be saved. Please, try again.\'));");
    _builder.newLineIfNotEmpty();
    _builder.append("            ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    CharSequence _addMethodRelation = this.addMethodRelation();
    _builder.append(_addMethodRelation, "        ");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("$this->set(\'_serialize\', [\'");
    String _name_22 = this.entity.getName();
    String _lowerCase_22 = _name_22.toLowerCase();
    _builder.append(_lowerCase_22, "        ");
    _builder.append("\']);");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* Edit method");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("*");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* @param string|null $id ");
    String _name_23 = this.entity.getName();
    String _lowerCase_23 = _name_23.toLowerCase();
    String _firstUpper_10 = StringExtensions.toFirstUpper(_lowerCase_23);
    _builder.append(_firstUpper_10, "     ");
    _builder.append(" id.");
    _builder.newLineIfNotEmpty();
    _builder.append("     ");
    _builder.append("* @return void Redirects on successful edit, renders view otherwise.");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* @throws \\Cake\\Network\\Exception\\NotFoundException When record not found.");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public function edit($id = null)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("$");
    String _name_24 = this.entity.getName();
    String _lowerCase_24 = _name_24.toLowerCase();
    _builder.append(_lowerCase_24, "        ");
    _builder.append(" = $this->");
    String _name_25 = this.entity.getName();
    String _lowerCase_25 = _name_25.toLowerCase();
    String _plural_9 = Inflector.plural(_lowerCase_25);
    String _firstUpper_11 = StringExtensions.toFirstUpper(_plural_9);
    _builder.append(_firstUpper_11, "        ");
    _builder.append("->get($id, [");
    _builder.newLineIfNotEmpty();
    _builder.append("            ");
    _builder.append("\'contain\' => []");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("]);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("if ($this->request->is([\'patch\', \'post\', \'put\'])) {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("$");
    String _name_26 = this.entity.getName();
    String _lowerCase_26 = _name_26.toLowerCase();
    _builder.append(_lowerCase_26, "            ");
    _builder.append(" = $this->");
    String _name_27 = this.entity.getName();
    String _lowerCase_27 = _name_27.toLowerCase();
    String _plural_10 = Inflector.plural(_lowerCase_27);
    String _firstUpper_12 = StringExtensions.toFirstUpper(_plural_10);
    _builder.append(_firstUpper_12, "            ");
    _builder.append("->patchEntity($");
    String _name_28 = this.entity.getName();
    String _lowerCase_28 = _name_28.toLowerCase();
    _builder.append(_lowerCase_28, "            ");
    _builder.append(", $this->request->data);");
    _builder.newLineIfNotEmpty();
    _builder.append("            ");
    _builder.append("if ($this->");
    String _name_29 = this.entity.getName();
    String _lowerCase_29 = _name_29.toLowerCase();
    String _plural_11 = Inflector.plural(_lowerCase_29);
    String _firstUpper_13 = StringExtensions.toFirstUpper(_plural_11);
    _builder.append(_firstUpper_13, "            ");
    _builder.append("->save($");
    String _name_30 = this.entity.getName();
    String _lowerCase_30 = _name_30.toLowerCase();
    _builder.append(_lowerCase_30, "            ");
    _builder.append(")) {");
    _builder.newLineIfNotEmpty();
    _builder.append("                ");
    _builder.append("$this->Flash->success(__(\'The ");
    String _name_31 = this.entity.getName();
    String _lowerCase_31 = _name_31.toLowerCase();
    _builder.append(_lowerCase_31, "                ");
    _builder.append(" has been saved.\'));");
    _builder.newLineIfNotEmpty();
    _builder.append("                ");
    _builder.append("return $this->redirect([\'action\' => \'index\']);");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("} else {");
    _builder.newLine();
    _builder.append("                ");
    _builder.append("$this->Flash->error(__(\'The ");
    String _name_32 = this.entity.getName();
    String _lowerCase_32 = _name_32.toLowerCase();
    _builder.append(_lowerCase_32, "                ");
    _builder.append(" could not be saved. Please, try again.\'));");
    _builder.newLineIfNotEmpty();
    _builder.append("            ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    CharSequence _addMethodRelation_1 = this.addMethodRelation();
    _builder.append(_addMethodRelation_1, "        ");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("$this->set(\'_serialize\', [\'");
    String _name_33 = this.entity.getName();
    String _lowerCase_33 = _name_33.toLowerCase();
    _builder.append(_lowerCase_33, "        ");
    _builder.append("\']);");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* Delete method");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("*");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* @param string|null $id ");
    String _name_34 = this.entity.getName();
    String _lowerCase_34 = _name_34.toLowerCase();
    String _firstUpper_14 = StringExtensions.toFirstUpper(_lowerCase_34);
    _builder.append(_firstUpper_14, "     ");
    _builder.append(" id.");
    _builder.newLineIfNotEmpty();
    _builder.append("     ");
    _builder.append("* @return \\Cake\\Network\\Response|null Redirects to index.");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* @throws \\Cake\\Network\\Exception\\NotFoundException When record not found.");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public function delete($id = null)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("$this->request->allowMethod([\'post\', \'delete\']);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("$");
    String _name_35 = this.entity.getName();
    String _lowerCase_35 = _name_35.toLowerCase();
    _builder.append(_lowerCase_35, "        ");
    _builder.append(" = $this->");
    String _name_36 = this.entity.getName();
    String _lowerCase_36 = _name_36.toLowerCase();
    String _plural_12 = Inflector.plural(_lowerCase_36);
    String _firstUpper_15 = StringExtensions.toFirstUpper(_plural_12);
    _builder.append(_firstUpper_15, "        ");
    _builder.append("->get($id);");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("if ($this->");
    String _name_37 = this.entity.getName();
    String _lowerCase_37 = _name_37.toLowerCase();
    String _plural_13 = Inflector.plural(_lowerCase_37);
    String _firstUpper_16 = StringExtensions.toFirstUpper(_plural_13);
    _builder.append(_firstUpper_16, "        ");
    _builder.append("->delete($");
    String _name_38 = this.entity.getName();
    String _lowerCase_38 = _name_38.toLowerCase();
    _builder.append(_lowerCase_38, "        ");
    _builder.append(")) {");
    _builder.newLineIfNotEmpty();
    _builder.append("            ");
    _builder.append("$this->Flash->success(__(\'The ");
    String _name_39 = this.entity.getName();
    String _lowerCase_39 = _name_39.toLowerCase();
    _builder.append(_lowerCase_39, "            ");
    _builder.append(" has been deleted.\'));");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("} else {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("$this->Flash->error(__(\'The ");
    String _name_40 = this.entity.getName();
    String _lowerCase_40 = _name_40.toLowerCase();
    _builder.append(_lowerCase_40, "            ");
    _builder.append(" could not be deleted. Please, try again.\'));");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("return $this->redirect([\'action\' => \'index\']);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence addMethodRelation() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasReferenceAttributes = this.entity.hasReferenceAttributes();
      boolean _not = (!_hasReferenceAttributes);
      if (_not) {
        _builder.append("$this->set(compact(\'");
        String _name = this.entity.getName();
        String _lowerCase = _name.toLowerCase();
        _builder.append(_lowerCase, "");
        _builder.append("\'));");
        _builder.newLineIfNotEmpty();
      } else {
        {
          List<Attribute> _referencedAttributes = this.entity.referencedAttributes();
          for(final Attribute attr : _referencedAttributes) {
            _builder.append("$");
            String _name_1 = attr.getName();
            String _lowerCase_1 = _name_1.toLowerCase();
            String _plural = Inflector.plural(_lowerCase_1);
            _builder.append(_plural, "");
            _builder.append(" = $this->");
            String _name_2 = this.entity.getName();
            String _lowerCase_2 = _name_2.toLowerCase();
            String _plural_1 = Inflector.plural(_lowerCase_2);
            String _firstUpper = StringExtensions.toFirstUpper(_plural_1);
            _builder.append(_firstUpper, "");
            _builder.append("->");
            String _name_3 = attr.getName();
            String _lowerCase_3 = _name_3.toLowerCase();
            String _plural_2 = Inflector.plural(_lowerCase_3);
            String _firstUpper_1 = StringExtensions.toFirstUpper(_plural_2);
            _builder.append(_firstUpper_1, "");
            _builder.append("->find(\'list\', [\'limit\' => 200]);");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("$this->set(compact(\'");
        String _name_4 = this.entity.getName();
        String _lowerCase_4 = _name_4.toLowerCase();
        _builder.append(_lowerCase_4, "");
        _builder.append("\', ");
        String _referencesPlural = this.getReferencesPlural();
        _builder.append(_referencesPlural, "");
        _builder.append("));");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    return _builder;
  }
  
  public String getReferencesPlural() {
    String output = "";
    boolean first = true;
    List<Attribute> _referencedAttributes = this.entity.referencedAttributes();
    for (final Attribute attr : _referencedAttributes) {
      if (first) {
        first = false;
        String _name = attr.getName();
        String _lowerCase = _name.toLowerCase();
        String _plus = ("\'" + _lowerCase);
        String _plus_1 = (_plus + "s\'");
        output = _plus_1;
      } else {
        String _output = output;
        String _name_1 = attr.getName();
        String _lowerCase_1 = _name_1.toLowerCase();
        String _plus_2 = (", \'" + _lowerCase_1);
        String _plus_3 = (_plus_2 + "s\'");
        output = (_output + _plus_3);
      }
    }
    return output;
  }
  
  public String getContainWhereIsReferences() {
    String output = "";
    boolean first = true;
    ArrayList<String> entitiesAdded = new ArrayList<String>();
    DomainModel _domainModel = this.project.getDomainModel();
    ArrayList<Entity> _whereEntityIsReferenced = _domainModel.whereEntityIsReferenced(this.entity);
    for (final Entity e : _whereEntityIsReferenced) {
      boolean _notEquals = (!Objects.equal(e, this.entity));
      if (_notEquals) {
        String _name = e.getName();
        String _lowerCase = _name.toLowerCase();
        entitiesAdded.add(_lowerCase);
        if (first) {
          String _name_1 = e.getName();
          String _lowerCase_1 = _name_1.toLowerCase();
          String _firstUpper = StringExtensions.toFirstUpper(_lowerCase_1);
          String _plus = ("\'" + _firstUpper);
          String _plus_1 = (_plus + "s\'");
          output = _plus_1;
          first = false;
        } else {
          String _output = output;
          String _name_2 = e.getName();
          String _lowerCase_2 = _name_2.toLowerCase();
          String _firstUpper_1 = StringExtensions.toFirstUpper(_lowerCase_2);
          String _plus_2 = (", \'" + _firstUpper_1);
          String _plus_3 = (_plus_2 + "s\'");
          output = (_output + _plus_3);
        }
      }
    }
    return output;
  }
  
  public String getContain() {
    String output = "";
    boolean first = true;
    ArrayList<String> entitiesAdded = new ArrayList<String>();
    DomainModel _domainModel = this.project.getDomainModel();
    ArrayList<Entity> _whereEntityIsReferenced = _domainModel.whereEntityIsReferenced(this.entity);
    for (final Entity e : _whereEntityIsReferenced) {
      boolean _notEquals = (!Objects.equal(e, this.entity));
      if (_notEquals) {
        String _name = e.getName();
        String _lowerCase = _name.toLowerCase();
        entitiesAdded.add(_lowerCase);
        if (first) {
          String _name_1 = e.getName();
          String _lowerCase_1 = _name_1.toLowerCase();
          String _firstUpper = StringExtensions.toFirstUpper(_lowerCase_1);
          String _plus = ("\'" + _firstUpper);
          String _plus_1 = (_plus + "s\'");
          output = _plus_1;
          first = false;
        } else {
          String _output = output;
          String _name_2 = e.getName();
          String _lowerCase_2 = _name_2.toLowerCase();
          String _firstUpper_1 = StringExtensions.toFirstUpper(_lowerCase_2);
          String _plus_2 = (", \'" + _firstUpper_1);
          String _plus_3 = (_plus_2 + "s\'");
          output = (_output + _plus_3);
        }
      }
    }
    List<Attribute> _referencedAttributes = this.entity.referencedAttributes();
    for (final Attribute attr : _referencedAttributes) {
      String _name_3 = attr.getName();
      String _lowerCase_3 = _name_3.toLowerCase();
      boolean _contains = entitiesAdded.contains(_lowerCase_3);
      boolean _not = (!_contains);
      if (_not) {
        if (first) {
          String _name_4 = attr.getName();
          String _lowerCase_4 = _name_4.toLowerCase();
          String _firstUpper_2 = StringExtensions.toFirstUpper(_lowerCase_4);
          String _plus_4 = ("\'" + _firstUpper_2);
          String _plus_5 = (_plus_4 + "s\'");
          output = _plus_5;
          first = false;
        } else {
          String _output_1 = output;
          String _name_5 = attr.getName();
          String _lowerCase_5 = _name_5.toLowerCase();
          String _firstUpper_3 = StringExtensions.toFirstUpper(_lowerCase_5);
          String _plus_6 = (", \'" + _firstUpper_3);
          String _plus_7 = (_plus_6 + "s\'");
          output = (_output_1 + _plus_7);
        }
      }
    }
    return output;
  }
  
  public String getContainReferenced() {
    String output = "";
    boolean first = true;
    ArrayList<String> entitiesAdded = new ArrayList<String>();
    List<Attribute> _referencedAttributes = this.entity.referencedAttributes();
    for (final Attribute attr : _referencedAttributes) {
      String _name = attr.getName();
      String _lowerCase = _name.toLowerCase();
      boolean _contains = entitiesAdded.contains(_lowerCase);
      boolean _not = (!_contains);
      if (_not) {
        if (first) {
          String _name_1 = attr.getName();
          String _lowerCase_1 = _name_1.toLowerCase();
          String _firstUpper = StringExtensions.toFirstUpper(_lowerCase_1);
          String _plus = ("\'" + _firstUpper);
          String _plus_1 = (_plus + "s\'");
          output = _plus_1;
          first = false;
        } else {
          String _output = output;
          String _name_2 = attr.getName();
          String _lowerCase_2 = _name_2.toLowerCase();
          String _firstUpper_1 = StringExtensions.toFirstUpper(_lowerCase_2);
          String _plus_2 = (", \'" + _firstUpper_1);
          String _plus_3 = (_plus_2 + "s\'");
          output = (_output + _plus_3);
        }
      }
    }
    return output;
  }
}
