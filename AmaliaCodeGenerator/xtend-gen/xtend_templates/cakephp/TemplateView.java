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
public class TemplateView {
  private Project project;
  
  private Entity entity;
  
  public TemplateView(final Project project) {
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
    CharSequence _listItems = this.listItems();
    _builder.append(_listItems, "\t        ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t    ");
    _builder.append("</ul>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</nav>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<div class=\"");
    String _name = this.entity.getName();
    String _lowerCase = _name.toLowerCase();
    String _plural = Inflector.plural(_lowerCase);
    _builder.append(_plural, "\t");
    _builder.append(" view large-9 medium-8 columns content\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t    ");
    _builder.append("<h3><?= h($");
    String _name_1 = this.entity.getName();
    String _lowerCase_1 = _name_1.toLowerCase();
    _builder.append(_lowerCase_1, "\t    ");
    _builder.append("->id) ?></h3>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t    ");
    _builder.append("<table class=\"vertical-table\">");
    _builder.newLine();
    _builder.append("\t        ");
    CharSequence _tableBody = this.tableBody();
    _builder.append(_tableBody, "\t        ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t    ");
    _builder.append("</table>");
    _builder.newLine();
    _builder.append("\t    ");
    CharSequence _related = this.related();
    _builder.append(_related, "\t    ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</div>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence related() {
    StringConcatenation _builder = new StringConcatenation();
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
            _builder.append("<div class=\"related\">");
            _builder.newLine();
            _builder.append("    ");
            _builder.append("<h4><?= __(\'Related ");
            String _name = e.getName();
            String _lowerCase = _name.toLowerCase();
            String _plural = Inflector.plural(_lowerCase);
            String _firstUpper = StringExtensions.toFirstUpper(_plural);
            _builder.append(_firstUpper, "    ");
            _builder.append("\') ?></h4>");
            _builder.newLineIfNotEmpty();
            _builder.append("    ");
            _builder.append("<?php if (!empty($");
            String _name_1 = this.entity.getName();
            String _lowerCase_1 = _name_1.toLowerCase();
            _builder.append(_lowerCase_1, "    ");
            _builder.append("->");
            String _name_2 = e.getName();
            String _lowerCase_2 = _name_2.toLowerCase();
            String _plural_1 = Inflector.plural(_lowerCase_2);
            _builder.append(_plural_1, "    ");
            _builder.append(")): ?>");
            _builder.newLineIfNotEmpty();
            _builder.append("    ");
            _builder.append("<table cellpadding=\"0\" cellspacing=\"0\">");
            _builder.newLine();
            _builder.append("        ");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("            ");
            CharSequence _relatedHeaders = this.relatedHeaders(e);
            _builder.append(_relatedHeaders, "            ");
            _builder.newLineIfNotEmpty();
            _builder.append("        ");
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("        ");
            _builder.append("<?php foreach ($");
            String _name_3 = this.entity.getName();
            String _lowerCase_3 = _name_3.toLowerCase();
            _builder.append(_lowerCase_3, "        ");
            _builder.append("->");
            String _name_4 = e.getName();
            String _lowerCase_4 = _name_4.toLowerCase();
            String _plural_2 = Inflector.plural(_lowerCase_4);
            _builder.append(_plural_2, "        ");
            _builder.append(" as $");
            String _name_5 = e.getName();
            String _lowerCase_5 = _name_5.toLowerCase();
            String _plural_3 = Inflector.plural(_lowerCase_5);
            _builder.append(_plural_3, "        ");
            _builder.append("): ?>");
            _builder.newLineIfNotEmpty();
            _builder.append("        ");
            _builder.append("<tr>");
            _builder.newLine();
            CharSequence _relatedData = this.relatedData(e);
            _builder.append(_relatedData, "");
            _builder.newLineIfNotEmpty();
            _builder.append("</tr>");
            _builder.newLine();
            _builder.append("<?php endforeach; ?>");
            _builder.newLine();
            _builder.append("</table>");
            _builder.newLine();
            _builder.append("<?php endif; ?>");
            _builder.newLine();
            _builder.append("</div>");
            _builder.newLine();
          }
        }
      } else {
        {
          List<Attribute> _referencedAttributes = this.entity.referencedAttributes();
          for(final Attribute attr : _referencedAttributes) {
            DomainModel _domainModel_2 = this.project.getDomainModel();
            String _name_6 = attr.getName();
            Entity relatedEnt = _domainModel_2.getEntityByName(_name_6);
            _builder.newLineIfNotEmpty();
            _builder.append("<div class=\"related\">");
            _builder.newLine();
            _builder.append("   ");
            _builder.append("<h3>");
            String _name_7 = relatedEnt.getName();
            String _lowerCase_6 = _name_7.toLowerCase();
            String _firstUpper_1 = StringExtensions.toFirstUpper(_lowerCase_6);
            _builder.append(_firstUpper_1, "   ");
            _builder.append("</h3>");
            _builder.newLineIfNotEmpty();
            _builder.append("   ");
            _builder.append("<table class=\"vertical-table\">");
            _builder.newLine();
            {
              ArrayList<Attribute> _attributes = relatedEnt.getAttributes();
              for(final Attribute attrEnt : _attributes) {
                _builder.append("   \t\t");
                _builder.append("<tr>");
                _builder.newLine();
                _builder.append("   \t\t");
                _builder.append("  ");
                _builder.append("<th><?= __(\'");
                String _name_8 = attrEnt.getName();
                String _firstUpper_2 = StringExtensions.toFirstUpper(_name_8);
                _builder.append(_firstUpper_2, "   \t\t  ");
                _builder.append("\') ?></th>");
                _builder.newLineIfNotEmpty();
                _builder.append("   \t\t");
                _builder.append("<td><?= h($");
                String _name_9 = this.entity.getName();
                String _lowerCase_7 = _name_9.toLowerCase();
                _builder.append(_lowerCase_7, "   \t\t");
                _builder.append("->");
                String _name_10 = relatedEnt.getName();
                String _lowerCase_8 = _name_10.toLowerCase();
                _builder.append(_lowerCase_8, "   \t\t");
                _builder.append("->");
                String _name_11 = attrEnt.getName();
                String _lowerCase_9 = _name_11.toLowerCase();
                _builder.append(_lowerCase_9, "   \t\t");
                _builder.append(") ?></td>");
                _builder.newLineIfNotEmpty();
                _builder.append("   \t\t");
                _builder.append("</tr>");
                _builder.newLine();
              }
            }
            _builder.append("   ");
            _builder.append("</table>");
            _builder.newLine();
            _builder.append("</div>");
            _builder.newLine();
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence relatedData(final Entity related) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<td><?= h($");
    String _name = related.getName();
    String _lowerCase = _name.toLowerCase();
    String _plural = Inflector.plural(_lowerCase);
    _builder.append(_plural, "");
    _builder.append("->id) ?></td>");
    _builder.newLineIfNotEmpty();
    {
      ArrayList<Attribute> _attributes = related.getAttributes();
      for(final Attribute attr : _attributes) {
        {
          Boolean _isReference = attr.isReference();
          if ((_isReference).booleanValue()) {
            _builder.append("<td><?= h($");
            String _name_1 = related.getName();
            String _lowerCase_1 = _name_1.toLowerCase();
            String _plural_1 = Inflector.plural(_lowerCase_1);
            _builder.append(_plural_1, "");
            _builder.append("->");
            String _name_2 = attr.getName();
            String _lowerCase_2 = _name_2.toLowerCase();
            _builder.append(_lowerCase_2, "");
            _builder.append("_id) ?></td>");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("<td><?= h($");
            String _name_3 = related.getName();
            String _lowerCase_3 = _name_3.toLowerCase();
            String _plural_2 = Inflector.plural(_lowerCase_3);
            _builder.append(_plural_2, "");
            _builder.append("->");
            String _name_4 = attr.getName();
            String _lowerCase_4 = _name_4.toLowerCase();
            _builder.append(_lowerCase_4, "");
            _builder.append(") ?></td>");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("<td class=\"actions\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<?= $this->Html->link(__(\'View\'), [\'controller\' => \'");
    String _name_5 = related.getName();
    String _lowerCase_5 = _name_5.toLowerCase();
    String _plural_3 = Inflector.plural(_lowerCase_5);
    String _firstUpper = StringExtensions.toFirstUpper(_plural_3);
    _builder.append(_firstUpper, "\t");
    _builder.append("\', \'action\' => \'view\', $");
    String _name_6 = related.getName();
    String _lowerCase_6 = _name_6.toLowerCase();
    String _plural_4 = Inflector.plural(_lowerCase_6);
    _builder.append(_plural_4, "\t");
    _builder.append("->id]) ?>\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<?= $this->Html->link(__(\'Edit\'), [\'controller\' => \'");
    String _name_7 = related.getName();
    String _lowerCase_7 = _name_7.toLowerCase();
    String _plural_5 = Inflector.plural(_lowerCase_7);
    String _firstUpper_1 = StringExtensions.toFirstUpper(_plural_5);
    _builder.append(_firstUpper_1, "\t");
    _builder.append("\', \'action\' => \'edit\', $");
    String _name_8 = related.getName();
    String _lowerCase_8 = _name_8.toLowerCase();
    String _plural_6 = Inflector.plural(_lowerCase_8);
    _builder.append(_plural_6, "\t");
    _builder.append("->id]) ?>\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<?= $this->Form->postLink(__(\'Delete\'), [\'controller\' => \'");
    String _name_9 = related.getName();
    String _lowerCase_9 = _name_9.toLowerCase();
    String _plural_7 = Inflector.plural(_lowerCase_9);
    String _firstUpper_2 = StringExtensions.toFirstUpper(_plural_7);
    _builder.append(_firstUpper_2, "\t");
    _builder.append("\', \'action\' => \'delete\', $");
    String _name_10 = related.getName();
    String _lowerCase_10 = _name_10.toLowerCase();
    String _plural_8 = Inflector.plural(_lowerCase_10);
    _builder.append(_plural_8, "\t");
    _builder.append("->id], [\'confirm\' => __(\'Are you sure you want to delete # {0}?\', $");
    String _name_11 = related.getName();
    String _lowerCase_11 = _name_11.toLowerCase();
    String _plural_9 = Inflector.plural(_lowerCase_11);
    _builder.append(_plural_9, "\t");
    _builder.append("->id)]) ?>");
    _builder.newLineIfNotEmpty();
    _builder.append("</td>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence relatedHeaders(final Entity related) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<th><?= __(\'Id\') ?></th>");
    _builder.newLine();
    {
      ArrayList<Attribute> _attributes = related.getAttributes();
      for(final Attribute attr : _attributes) {
        {
          Boolean _isReference = attr.isReference();
          if ((_isReference).booleanValue()) {
            _builder.append("<th><?= __(\'");
            String _name = attr.getName();
            String _lowerCase = _name.toLowerCase();
            String _firstUpper = StringExtensions.toFirstUpper(_lowerCase);
            _builder.append(_firstUpper, "");
            _builder.append(" Id\') ?></th>");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("<th><?= __(\'");
            String _name_1 = attr.getName();
            String _lowerCase_1 = _name_1.toLowerCase();
            String _firstUpper_1 = StringExtensions.toFirstUpper(_lowerCase_1);
            _builder.append(_firstUpper_1, "");
            _builder.append("\') ?></th>");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("<th class=\"actions\"><?= __(\'Actions\') ?></th>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence tableBody() {
    StringConcatenation _builder = new StringConcatenation();
    {
      ArrayList<Attribute> _attributes = this.entity.getAttributes();
      for(final Attribute attr : _attributes) {
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("<th><?= __(\'");
        String _name = attr.getName();
        String _lowerCase = _name.toLowerCase();
        String _firstUpper = StringExtensions.toFirstUpper(_lowerCase);
        _builder.append(_firstUpper, "  ");
        _builder.append("\') ?></th>");
        _builder.newLineIfNotEmpty();
        {
          Boolean _isReference = attr.isReference();
          if ((_isReference).booleanValue()) {
            _builder.append("<td><?= $");
            String _name_1 = this.entity.getName();
            String _lowerCase_1 = _name_1.toLowerCase();
            _builder.append(_lowerCase_1, "");
            _builder.append("->has(\'");
            String _name_2 = attr.getName();
            String _lowerCase_2 = _name_2.toLowerCase();
            _builder.append(_lowerCase_2, "");
            _builder.append("\') ? $this->Html->link($");
            String _name_3 = this.entity.getName();
            String _lowerCase_3 = _name_3.toLowerCase();
            _builder.append(_lowerCase_3, "");
            _builder.append("->");
            String _name_4 = attr.getName();
            String _lowerCase_4 = _name_4.toLowerCase();
            _builder.append(_lowerCase_4, "");
            _builder.append("->id, [\'controller\' => \'");
            String _name_5 = attr.getName();
            String _lowerCase_5 = _name_5.toLowerCase();
            String _plural = Inflector.plural(_lowerCase_5);
            String _firstUpper_1 = StringExtensions.toFirstUpper(_plural);
            _builder.append(_firstUpper_1, "");
            _builder.append("\', \'action\' => \'view\', $");
            String _name_6 = this.entity.getName();
            String _lowerCase_6 = _name_6.toLowerCase();
            _builder.append(_lowerCase_6, "");
            _builder.append("->");
            String _name_7 = attr.getName();
            String _lowerCase_7 = _name_7.toLowerCase();
            _builder.append(_lowerCase_7, "");
            _builder.append("->id]) : \'\' ?></td>");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("<td><?= h($");
            String _name_8 = this.entity.getName();
            String _lowerCase_8 = _name_8.toLowerCase();
            _builder.append(_lowerCase_8, "");
            _builder.append("->");
            String _name_9 = attr.getName();
            String _lowerCase_9 = _name_9.toLowerCase();
            _builder.append(_lowerCase_9, "");
            _builder.append(") ?></td>");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("</tr>");
        _builder.newLine();
      }
    }
    _builder.append("      ");
    _builder.append("<tr>");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("<th><?= __(\'Id\') ?></th>");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("<td><?= $this->Number->format($");
    String _name_10 = this.entity.getName();
    String _lowerCase_10 = _name_10.toLowerCase();
    _builder.append(_lowerCase_10, " ");
    _builder.append("->id) ?></td>");
    _builder.newLineIfNotEmpty();
    _builder.append("      ");
    _builder.append("</tr>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence listItems() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<li class=\"heading\"><?= __(\'Actions\') ?></li>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<li><?= $this->Html->link(__(\'Edit ");
    String _name = this.entity.getName();
    String _lowerCase = _name.toLowerCase();
    String _firstUpper = StringExtensions.toFirstUpper(_lowerCase);
    _builder.append(_firstUpper, "\t\t");
    _builder.append("\'), [\'action\' => \'edit\', $");
    String _name_1 = this.entity.getName();
    String _lowerCase_1 = _name_1.toLowerCase();
    _builder.append(_lowerCase_1, "\t\t");
    _builder.append("->id]) ?> </li>");
    _builder.newLineIfNotEmpty();
    _builder.append("<li><?= $this->Form->postLink(__(\'Delete ");
    String _name_2 = this.entity.getName();
    String _lowerCase_2 = _name_2.toLowerCase();
    String _firstUpper_1 = StringExtensions.toFirstUpper(_lowerCase_2);
    _builder.append(_firstUpper_1, "");
    _builder.append("\'), [\'action\' => \'delete\', $");
    String _name_3 = this.entity.getName();
    String _lowerCase_3 = _name_3.toLowerCase();
    _builder.append(_lowerCase_3, "");
    _builder.append("->id], [\'confirm\' => __(\'Are you sure you want to delete # {0}?\', $");
    String _name_4 = this.entity.getName();
    String _lowerCase_4 = _name_4.toLowerCase();
    _builder.append(_lowerCase_4, "");
    _builder.append("->id)]) ?> </li>");
    _builder.newLineIfNotEmpty();
    _builder.append("<li><?= $this->Html->link(__(\'List ");
    String _name_5 = this.entity.getName();
    String _lowerCase_5 = _name_5.toLowerCase();
    String _plural = Inflector.plural(_lowerCase_5);
    String _firstUpper_2 = StringExtensions.toFirstUpper(_plural);
    _builder.append(_firstUpper_2, "");
    _builder.append("\'), [\'action\' => \'index\']) ?> </li>");
    _builder.newLineIfNotEmpty();
    _builder.append("<li><?= $this->Html->link(__(\'New ");
    String _name_6 = this.entity.getName();
    String _lowerCase_6 = _name_6.toLowerCase();
    String _firstUpper_3 = StringExtensions.toFirstUpper(_lowerCase_6);
    _builder.append(_firstUpper_3, "");
    _builder.append("\'), [\'action\' => \'add\']) ?> </li>");
    _builder.newLineIfNotEmpty();
    {
      boolean _hasReferenceAttributes = this.entity.hasReferenceAttributes();
      if (_hasReferenceAttributes) {
        {
          List<Attribute> _referencedAttributes = this.entity.referencedAttributes();
          for(final Attribute attr : _referencedAttributes) {
            _builder.append("<li><?= $this->Html->link(__(\'List ");
            String _name_7 = attr.getName();
            String _lowerCase_7 = _name_7.toLowerCase();
            String _plural_1 = Inflector.plural(_lowerCase_7);
            String _firstUpper_4 = StringExtensions.toFirstUpper(_plural_1);
            _builder.append(_firstUpper_4, "");
            _builder.append("\'), [\'controller\' => \'");
            String _name_8 = attr.getName();
            String _lowerCase_8 = _name_8.toLowerCase();
            String _plural_2 = Inflector.plural(_lowerCase_8);
            String _firstUpper_5 = StringExtensions.toFirstUpper(_plural_2);
            _builder.append(_firstUpper_5, "");
            _builder.append("\', \'action\' => \'index\']) ?></li>");
            _builder.newLineIfNotEmpty();
            _builder.append("<li><?= $this->Html->link(__(\'New ");
            String _name_9 = attr.getName();
            String _lowerCase_9 = _name_9.toLowerCase();
            String _firstUpper_6 = StringExtensions.toFirstUpper(_lowerCase_9);
            _builder.append(_firstUpper_6, "");
            _builder.append("\'), [\'controller\' => \'");
            String _name_10 = attr.getName();
            String _lowerCase_10 = _name_10.toLowerCase();
            String _plural_3 = Inflector.plural(_lowerCase_10);
            String _firstUpper_7 = StringExtensions.toFirstUpper(_plural_3);
            _builder.append(_firstUpper_7, "");
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
            String _name_11 = e.getName();
            String _lowerCase_11 = _name_11.toLowerCase();
            String _plural_4 = Inflector.plural(_lowerCase_11);
            String _firstUpper_8 = StringExtensions.toFirstUpper(_plural_4);
            _builder.append(_firstUpper_8, "");
            _builder.append("\'), [\'controller\' => \'");
            String _name_12 = e.getName();
            String _lowerCase_12 = _name_12.toLowerCase();
            String _plural_5 = Inflector.plural(_lowerCase_12);
            String _firstUpper_9 = StringExtensions.toFirstUpper(_plural_5);
            _builder.append(_firstUpper_9, "");
            _builder.append("\', \'action\' => \'index\']) ?></li>");
            _builder.newLineIfNotEmpty();
            _builder.append("<li><?= $this->Html->link(__(\'New ");
            String _name_13 = e.getName();
            String _lowerCase_13 = _name_13.toLowerCase();
            String _firstUpper_10 = StringExtensions.toFirstUpper(_lowerCase_13);
            _builder.append(_firstUpper_10, "");
            _builder.append("\'), [\'controller\' => \'");
            String _name_14 = e.getName();
            String _lowerCase_14 = _name_14.toLowerCase();
            String _plural_6 = Inflector.plural(_lowerCase_14);
            String _firstUpper_11 = StringExtensions.toFirstUpper(_plural_6);
            _builder.append(_firstUpper_11, "");
            _builder.append("\', \'action\' => \'add\']) ?></li>");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
}
