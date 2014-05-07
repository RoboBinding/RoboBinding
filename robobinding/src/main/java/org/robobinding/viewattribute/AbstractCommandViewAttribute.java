package org.robobinding.viewattribute;

import org.robobinding.BindingContext;
import org.robobinding.attribute.Command;
import org.robobinding.attribute.CommandAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractCommandViewAttribute<T extends View> implements ViewAttribute {
    protected T view;
    private CommandAttribute attribute;

    public void initialize(CommandViewAttributeConfig<T> config) {
	this.view = config.getView();
	this.attribute = config.getAttribute();
    }

    @Override
    public void bindTo(BindingContext bindingContext) {
	try {
	    performBind(bindingContext);
	} catch (RuntimeException e) {
	    throw new AttributeBindingException(attribute.getName(), e);
	}
    }

    private void performBind(PresentationModelAdapter presentationModelAdapter) {
	Command command = attribute.findCommand(presentationModelAdapter, getPreferredCommandParameterType());
	if (command != null) {
	    bind(command);
	} else {
	    bind(getNoArgsCommand(presentationModelAdapter));
	}
    }

    private Command getNoArgsCommand(PresentationModelAdapter presentationModelAdapter) {
	Command noArgsCommand = attribute.findCommand(presentationModelAdapter);

	if (noArgsCommand == null) {
	    String commandName = attribute.getCommandName();
	    throw new IllegalArgumentException("Could not find method " + commandName + "() or " + commandName + "("
		    + getAcceptedParameterTypesDescription() + ") in class " + presentationModelAdapter.getPresentationModelClassName());
	}

	return noArgsCommand;
    }

    private String getAcceptedParameterTypesDescription() {
	Class<?> clazz = getPreferredCommandParameterType();
	StringBuilder descriptionBuilder = new StringBuilder(clazz.getSimpleName());

	while (clazz.getSuperclass() != Object.class) {
	    clazz = clazz.getSuperclass();
	    descriptionBuilder.append('/').append(clazz.getSimpleName());
	}
	return descriptionBuilder.toString();
    }

    protected abstract void bind(Command command);

    protected abstract Class<?> getPreferredCommandParameterType();

    @Override
    public final void preInitializeView(BindingContext bindingContext) {
    }
}
