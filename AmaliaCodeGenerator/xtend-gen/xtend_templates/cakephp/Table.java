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
public class Table {
  private Project project;
  
  private Entity entity;
  
  public Table(final Project project) {
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
    _builder.append("/* Generated by Amalia Code Generator */");
    _builder.newLine();
    _builder.append("namespace App\\Model\\Table;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("use App\\Model\\Entity\\");
    String _name = this.entity.getName();
    String _lowerCase = _name.toLowerCase();
    String _firstUpper = StringExtensions.toFirstUpper(_lowerCase);
    _builder.append(_firstUpper, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("use Cake\\ORM\\Query;");
    _builder.newLine();
    _builder.append("use Cake\\ORM\\RulesChecker;");
    _builder.newLine();
    _builder.append("use Cake\\ORM\\Table;");
    _builder.newLine();
    _builder.append("use Cake\\Validation\\Validator;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* ");
    String _name_1 = this.entity.getName();
    String _lowerCase_1 = _name_1.toLowerCase();
    String _plural = Inflector.plural(_lowerCase_1);
    String _firstUpper_1 = StringExtensions.toFirstUpper(_plural);
    _builder.append(_firstUpper_1, " ");
    _builder.append(" Model");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("class ");
    String _name_2 = this.entity.getName();
    String _lowerCase_2 = _name_2.toLowerCase();
    String _plural_1 = Inflector.plural(_lowerCase_2);
    String _firstUpper_2 = StringExtensions.toFirstUpper(_plural_1);
    _builder.append(_firstUpper_2, "");
    _builder.append("Table extends Table");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("\t     ");
    _builder.append("* Initialize method");
    _builder.newLine();
    _builder.append("\t     ");
    _builder.append("*");
    _builder.newLine();
    _builder.append("\t     ");
    _builder.append("* @param array $config The configuration for the Table.");
    _builder.newLine();
    _builder.append("\t     ");
    _builder.append("* @return void");
    _builder.newLine();
    _builder.append("\t     ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("public function initialize(array $config)");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t        ");
    _builder.append("parent::initialize($config);");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t        ");
    _builder.append("$this->table(\'");
    String _name_3 = this.entity.getName();
    String _lowerCase_3 = _name_3.toLowerCase();
    String _plural_2 = Inflector.plural(_lowerCase_3);
    _builder.append(_plural_2, "\t        ");
    _builder.append("\');");
    _builder.newLineIfNotEmpty();
    _builder.append("\t        ");
    _builder.append("$this->displayField(\'id\');");
    _builder.newLine();
    _builder.append("\t        ");
    _builder.append("$this->primaryKey(\'id\');");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t        ");
    CharSequence _variables = this.variables();
    _builder.append(_variables, "\t        ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("\t     ");
    _builder.append("* Default validation rules.");
    _builder.newLine();
    _builder.append("\t     ");
    _builder.append("*");
    _builder.newLine();
    _builder.append("\t     ");
    _builder.append("* @param \\Cake\\Validation\\Validator $validator Validator instance.");
    _builder.newLine();
    _builder.append("\t     ");
    _builder.append("* @return \\Cake\\Validation\\Validator");
    _builder.newLine();
    _builder.append("\t     ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("public function validationDefault(Validator $validator)");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t        ");
    _builder.append("$validator");
    _builder.newLine();
    _builder.append("\t            ");
    _builder.append("->add(\'id\', \'valid\', [\'rule\' => \'numeric\'])");
    _builder.newLine();
    _builder.append("\t            ");
    _builder.append("->allowEmpty(\'id\', \'create\');");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    CharSequence _requirePresence = this.requirePresence();
    _builder.append(_requirePresence, "\t\t\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t     ");
    _builder.append("return $validator;");
    _builder.newLine();
    _builder.append("\t\t\t\t ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _buildRules = this.buildRules();
    _builder.append(_buildRules, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence buildRules() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasReferenceAttributes = this.entity.hasReferenceAttributes();
      if (_hasReferenceAttributes) {
        _builder.append("/**");
        _builder.newLine();
        _builder.append("* Returns a rules checker object that will be used for validating");
        _builder.newLine();
        _builder.append("* application integrity.");
        _builder.newLine();
        _builder.append("*");
        _builder.newLine();
        _builder.append("* @param \\Cake\\ORM\\RulesChecker $rules The rules object to be modified.");
        _builder.newLine();
        _builder.append("* @return \\Cake\\ORM\\RulesChecker");
        _builder.newLine();
        _builder.append("*/");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("public function buildRules(RulesChecker $rules)");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("{");
        _builder.newLine();
        {
          List<Attribute> _referencedAttributes = this.entity.referencedAttributes();
          for(final Attribute attr : _referencedAttributes) {
            _builder.append("  \t");
            _builder.append("$rules->add($rules->existsIn([\'");
            String _name = attr.getName();
            String _lowerCase = _name.toLowerCase();
            _builder.append(_lowerCase, "  \t");
            _builder.append("_id\'], \'");
            String _name_1 = attr.getName();
            String _lowerCase_1 = _name_1.toLowerCase();
            String _plural = Inflector.plural(_lowerCase_1);
            String _firstUpper = StringExtensions.toFirstUpper(_plural);
            _builder.append(_firstUpper, "  \t");
            _builder.append("\'));");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("  \t");
        _builder.append("return $rules;");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("}");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  public CharSequence variables() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasReferenceAttributes = this.entity.hasReferenceAttributes();
      if (_hasReferenceAttributes) {
        {
          List<Attribute> _referencedAttributes = this.entity.referencedAttributes();
          for(final Attribute attr : _referencedAttributes) {
            _builder.append("$this->belongsTo(\'");
            String _name = attr.getName();
            String _lowerCase = _name.toLowerCase();
            String _plural = Inflector.plural(_lowerCase);
            String _firstUpper = StringExtensions.toFirstUpper(_plural);
            _builder.append(_firstUpper, "");
            _builder.append("\', [");
            _builder.newLineIfNotEmpty();
            _builder.append("    ");
            _builder.append("\'foreignKey\' => \'");
            String _name_1 = attr.getName();
            String _lowerCase_1 = _name_1.toLowerCase();
            _builder.append(_lowerCase_1, "    ");
            _builder.append("_id\',");
            _builder.newLineIfNotEmpty();
            _builder.append("    ");
            _builder.append("\'joinType\' => \'INNER\'");
            _builder.newLine();
            _builder.append("]);");
            _builder.newLine();
          }
        }
      }
    }
    {
      DomainModel _domainModel = this.project.getDomainModel();
      ArrayList<Entity> _whereEntityIsReferenced = _domainModel.whereEntityIsReferenced(this.entity);
      int _size = _whereEntityIsReferenced.size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        {
          DomainModel _domainModel_1 = this.project.getDomainModel();
          ArrayList<Entity> _whereEntityIsReferenced_1 = _domainModel_1.whereEntityIsReferenced(this.entity);
          for(final Entity e : _whereEntityIsReferenced_1) {
            _builder.append("$this->hasMany(\'");
            String _name_2 = e.getName();
            String _lowerCase_2 = _name_2.toLowerCase();
            String _plural_1 = Inflector.plural(_lowerCase_2);
            String _firstUpper_1 = StringExtensions.toFirstUpper(_plural_1);
            _builder.append(_firstUpper_1, "");
            _builder.append("\', [");
            _builder.newLineIfNotEmpty();
            _builder.append("  ");
            _builder.append("\'foreignKey\' => \'");
            String _name_3 = this.entity.getName();
            String _lowerCase_3 = _name_3.toLowerCase();
            _builder.append(_lowerCase_3, "  ");
            _builder.append("_id\'");
            _builder.newLineIfNotEmpty();
            _builder.append("]);");
            _builder.newLine();
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence requirePresence() {
    StringConcatenation _builder = new StringConcatenation();
    {
      ArrayList<Attribute> _attributes = this.entity.getAttributes();
      for(final Attribute attr : _attributes) {
        {
          Boolean _isReference = attr.isReference();
          boolean _not = (!(_isReference).booleanValue());
          if (_not) {
            _builder.append("$validator");
            _builder.newLine();
            {
              String _type = attr.getType();
              boolean _equalsIgnoreCase = _type.equalsIgnoreCase("date");
              if (_equalsIgnoreCase) {
                _builder.append("->add(\'");
                String _name = attr.getName();
                String _lowerCase = _name.toLowerCase();
                _builder.append(_lowerCase, "");
                _builder.append("\', \'valid\', [\'rule\' => \'date\'])");
                _builder.newLineIfNotEmpty();
              }
            }
            _builder.append("->requirePresence(\'");
            String _name_1 = attr.getName();
            String _lowerCase_1 = _name_1.toLowerCase();
            _builder.append(_lowerCase_1, "");
            _builder.append("\', \'create\')");
            _builder.newLineIfNotEmpty();
            _builder.append("->notEmpty(\'");
            String _name_2 = attr.getName();
            String _lowerCase_2 = _name_2.toLowerCase();
            String _firstUpper = StringExtensions.toFirstUpper(_lowerCase_2);
            _builder.append(_firstUpper, "");
            _builder.append("\');");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
}
