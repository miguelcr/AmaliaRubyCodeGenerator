package xtend_templates.cakephp;

import com.google.common.base.Objects;
import exceptions.ParserException;
import inflector.Inflector;
import main.Project;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import structure.domain_model.DomainModel;
import structure.domain_model.Entity;

@SuppressWarnings("all")
public class Routes {
  private Project project;
  
  private Entity entity;
  
  public Routes(final Project project) {
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
    _builder.append("use Cake\\Core\\Plugin;");
    _builder.newLine();
    _builder.append("use Cake\\Routing\\Router;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("Router::defaultRouteClass(\'DashedRoute\');");
    _builder.newLine();
    _builder.newLine();
    _builder.append("Router::scope(\'/\', function ($routes) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("Router::connect(\'/\', array(\'controller\' => \'");
    String _name = this.entity.getName();
    String _lowerCase = _name.toLowerCase();
    String _plural = Inflector.plural(_lowerCase);
    _builder.append(_plural, "\t");
    _builder.append("\', \'action\' => \'index\'));");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("$routes->fallbacks(\'DashedRoute\');");
    _builder.newLine();
    _builder.append("});");
    _builder.newLine();
    _builder.newLine();
    _builder.append("Plugin::routes();");
    _builder.newLine();
    return _builder;
  }
}
