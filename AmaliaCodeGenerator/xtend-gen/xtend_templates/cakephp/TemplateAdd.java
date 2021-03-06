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
public class TemplateAdd {
  private Project project;
  
  private Entity entity;
  
  public TemplateAdd(final Project project) {
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
    _builder.append("<nav class=\"large-3 medium-4 columns\" id=\"actions-sidebar\">");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("<ul class=\"side-nav\">");
    _builder.newLine();
    _builder.append("\t        ");
    _builder.append("<li class=\"heading\"><?= __(\'Actions\') ?></li>");
    _builder.newLine();
    _builder.append("\t        ");
    _builder.append("<li><?= $this->Html->link(__(\'List ");
    String _name = this.entity.getName();
    String _lowerCase = _name.toLowerCase();
    String _plural = Inflector.plural(_lowerCase);
    String _firstUpper = StringExtensions.toFirstUpper(_plural);
    _builder.append(_firstUpper, "\t        ");
    _builder.append("\'), [\'action\' => \'index\']) ?></li>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t        ");
    CharSequence _otherListItems = this.otherListItems();
    _builder.append(_otherListItems, "\t        ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t    ");
    _builder.append("</ul>");
    _builder.newLine();
    _builder.append("</nav>");
    _builder.newLine();
    _builder.append("<div class=\"");
    String _name_1 = this.entity.getName();
    String _lowerCase_1 = _name_1.toLowerCase();
    String _plural_1 = Inflector.plural(_lowerCase_1);
    _builder.append(_plural_1, "");
    _builder.append(" form large-9 medium-8 columns content\">");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("<?= $this->Form->create($");
    String _name_2 = this.entity.getName();
    String _lowerCase_2 = _name_2.toLowerCase();
    _builder.append(_lowerCase_2, "    ");
    _builder.append(") ?>");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("<fieldset>");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("<legend><?= __(\'Add ");
    String _name_3 = this.entity.getName();
    String _lowerCase_3 = _name_3.toLowerCase();
    String _firstUpper_1 = StringExtensions.toFirstUpper(_lowerCase_3);
    _builder.append(_firstUpper_1, "        ");
    _builder.append("\') ?></legend>");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("<?php");
    _builder.newLine();
    _builder.append("        \t");
    CharSequence _options = this.options();
    _builder.append(_options, "        \t");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("?>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("</fieldset>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<?= $this->Form->button(__(\'Submit\')) ?>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<?= $this->Form->end() ?>");
    _builder.newLine();
    _builder.append("</div>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence options() {
    StringConcatenation _builder = new StringConcatenation();
    {
      ArrayList<Attribute> _attributes = this.entity.getAttributes();
      for(final Attribute attr : _attributes) {
        {
          Boolean _isReference = attr.isReference();
          if ((_isReference).booleanValue()) {
            _builder.append("echo $this->Form->input(\'");
            String _name = attr.getName();
            String _lowerCase = _name.toLowerCase();
            _builder.append(_lowerCase, "");
            _builder.append("_id\', [\'options\' => $");
            String _name_1 = attr.getName();
            String _lowerCase_1 = _name_1.toLowerCase();
            String _plural = Inflector.plural(_lowerCase_1);
            _builder.append(_plural, "");
            _builder.append("]);");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("echo $this->Form->input(\'");
            String _name_2 = attr.getName();
            String _lowerCase_2 = _name_2.toLowerCase();
            _builder.append(_lowerCase_2, "");
            _builder.append("\');");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence otherListItems() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _hasReferenceAttributes = this.entity.hasReferenceAttributes();
      if (_hasReferenceAttributes) {
        {
          List<Attribute> _referencedAttributes = this.entity.referencedAttributes();
          for(final Attribute attr : _referencedAttributes) {
            _builder.append("<li><?= $this->Html->link(__(\'List ");
            String _name = attr.getName();
            String _lowerCase = _name.toLowerCase();
            String _plural = Inflector.plural(_lowerCase);
            String _firstUpper = StringExtensions.toFirstUpper(_plural);
            _builder.append(_firstUpper, "");
            _builder.append("\'), [\'controller\' => \'");
            String _name_1 = attr.getName();
            String _lowerCase_1 = _name_1.toLowerCase();
            String _plural_1 = Inflector.plural(_lowerCase_1);
            String _firstUpper_1 = StringExtensions.toFirstUpper(_plural_1);
            _builder.append(_firstUpper_1, "");
            _builder.append("\', \'action\' => \'index\']) ?></li>");
            _builder.newLineIfNotEmpty();
            _builder.append("<li><?= $this->Html->link(__(\'New ");
            String _name_2 = attr.getName();
            String _lowerCase_2 = _name_2.toLowerCase();
            String _firstUpper_2 = StringExtensions.toFirstUpper(_lowerCase_2);
            _builder.append(_firstUpper_2, "");
            _builder.append("\'), [\'controller\' => \'");
            String _name_3 = attr.getName();
            String _lowerCase_3 = _name_3.toLowerCase();
            String _plural_2 = Inflector.plural(_lowerCase_3);
            String _firstUpper_3 = StringExtensions.toFirstUpper(_plural_2);
            _builder.append(_firstUpper_3, "");
            _builder.append("\', \'action\' => \'add\']) ?></li>");
            _builder.newLineIfNotEmpty();
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
            _builder.append("<li><?= $this->Html->link(__(\'List ");
            String _name_4 = e.getName();
            String _lowerCase_4 = _name_4.toLowerCase();
            String _plural_3 = Inflector.plural(_lowerCase_4);
            String _firstUpper_4 = StringExtensions.toFirstUpper(_plural_3);
            _builder.append(_firstUpper_4, "");
            _builder.append("\'), [\'controller\' => \'");
            String _name_5 = e.getName();
            String _lowerCase_5 = _name_5.toLowerCase();
            String _plural_4 = Inflector.plural(_lowerCase_5);
            String _firstUpper_5 = StringExtensions.toFirstUpper(_plural_4);
            _builder.append(_firstUpper_5, "");
            _builder.append("\', \'action\' => \'index\']) ?></li>");
            _builder.newLineIfNotEmpty();
            _builder.append("<li><?= $this->Html->link(__(\'New ");
            String _name_6 = e.getName();
            String _lowerCase_6 = _name_6.toLowerCase();
            String _firstUpper_6 = StringExtensions.toFirstUpper(_lowerCase_6);
            _builder.append(_firstUpper_6, "");
            _builder.append("\'), [\'controller\' => \'");
            String _name_7 = e.getName();
            String _lowerCase_7 = _name_7.toLowerCase();
            String _plural_5 = Inflector.plural(_lowerCase_7);
            String _firstUpper_7 = StringExtensions.toFirstUpper(_plural_5);
            _builder.append(_firstUpper_7, "");
            _builder.append("\', \'action\' => \'add\']) ?></li>");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
}
