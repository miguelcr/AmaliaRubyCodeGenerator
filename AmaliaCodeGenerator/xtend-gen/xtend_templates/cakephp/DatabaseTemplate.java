package xtend_templates.cakephp;

import com.google.common.base.Objects;
import exceptions.ParserException;
import inflector.Inflector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import main.Project;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import structure.domain_model.Attribute;
import structure.domain_model.DomainModel;
import structure.domain_model.Entity;

@SuppressWarnings("all")
public class DatabaseTemplate {
  private Project project;
  
  public DatabaseTemplate(final Project project) {
    this.project = project;
  }
  
  public CharSequence generate() {
    try {
      boolean _and = false;
      boolean _notEquals = (!Objects.equal(this.project, null));
      if (!_notEquals) {
        _and = false;
      } else {
        DomainModel _domainModel = this.project.getDomainModel();
        boolean _notEquals_1 = (!Objects.equal(_domainModel, null));
        _and = _notEquals_1;
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
    _builder.append("-- Generated by Amalia Code Generator");
    _builder.newLine();
    {
      DomainModel _domainModel = this.project.getDomainModel();
      HashMap<String, Entity> _entities = _domainModel.getEntities();
      Collection<Entity> _values = _entities.values();
      for(final Entity entity : _values) {
        CharSequence _createTable = this.createTable(entity);
        _builder.append(_createTable, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence createTable(final Entity entity) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CREATE TABLE IF NOT EXISTS `");
    String _name = entity.getName();
    String _lowerCase = _name.toLowerCase();
    String _plural = Inflector.plural(_lowerCase);
    _builder.append(_plural, "");
    _builder.append("` (");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("`id` INT(10) NOT NULL AUTO_INCREMENT,");
    _builder.newLine();
    {
      ArrayList<Attribute> _attributes = entity.getAttributes();
      for(final Attribute attr : _attributes) {
        {
          Boolean _isReference = attr.isReference();
          if ((_isReference).booleanValue()) {
            _builder.append("\t");
            _builder.append("`");
            String _name_1 = attr.getName();
            String _lowerCase_1 = _name_1.toLowerCase();
            _builder.append(_lowerCase_1, "\t");
            _builder.append("_id` INT(10) NOT NULL,");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("\t");
            _builder.append("`");
            String _name_2 = attr.getName();
            String _lowerCase_2 = _name_2.toLowerCase();
            _builder.append(_lowerCase_2, "\t");
            _builder.append("` ");
            String _attributeType = this.attributeType(attr);
            _builder.append(_attributeType, "\t");
            _builder.append(" NOT NULL,");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("PRIMARY KEY (`id`)");
    _builder.newLine();
    _builder.append(");");
    _builder.newLine();
    return _builder;
  }
  
  public String attributeType(final Attribute attribute) {
    String _type = attribute.getType();
    switch (_type) {
      case "text":
        return "VARCHAR(255)";
      case "string":
        return "VARCHAR(255)";
      case "date":
        return "DATE";
      default:
        return "VARCHAR(255)";
    }
  }
}
