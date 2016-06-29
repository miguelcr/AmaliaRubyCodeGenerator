package xtend_templates.cakephp;

import com.google.common.base.Objects;
import exceptions.ParserException;
import main.Project;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import structure.domain_model.DomainModel;
import structure.domain_model.Entity;

@SuppressWarnings("all")
public class EntityCake {
  private Project project;
  
  private Entity entity;
  
  public EntityCake(final Project project) {
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
    _builder.append("namespace App\\Model\\Entity;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("use Cake\\ORM\\Entity;");
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
    _builder.append(" Entity.");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* @property int $");
    String _name_1 = this.entity.getName();
    String _lowerCase_1 = _name_1.toLowerCase();
    _builder.append(_lowerCase_1, " ");
    _builder.append("_id");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("class ");
    String _name_2 = this.entity.getName();
    String _lowerCase_2 = _name_2.toLowerCase();
    String _firstUpper_1 = StringExtensions.toFirstUpper(_lowerCase_2);
    _builder.append(_firstUpper_1, "");
    _builder.append(" extends Entity");
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* Fields that can be mass assigned using newEntity() or patchEntity().");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("*");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* Note that when \'*\' is set to true, this allows all unspecified fields to");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* be mass assigned. For security purposes, it is advised to set \'*\' to false");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* (or remove it), and explicitly make individual fields accessible as needed.");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("*");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* @var array");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("protected $_accessible = [");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\'*\' => true,");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\'id\' => false,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("];");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}