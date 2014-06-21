package org.robobinding.viewattribute.event;

import org.robobinding.BindingContext;
import org.robobinding.attribute.Command;
import org.robobinding.attribute.EventAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.AttributeBindingException;
import org.robobinding.viewattribute.ViewAttributeBinder;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class EventViewAttributeBinder<T extends View> implements ViewAttributeBinder {
    private final T view;
    private final EventViewAttribute<T> viewAttribute;
    private final EventAttribute attribute;

    public EventViewAttributeBinder(T view, EventViewAttribute<T> viewAttribute, EventAttribute attribute) {
	this.view = view;
	this.viewAttribute = viewAttribute;
	this.attribute = attribute;
    }
    @Override
    public void bindTo(BindingContext bindingContext) {
	try {
	    performBind(bindingContext);
	} catch (RuntimeException e) {
	    throw new AttributeBindingException(attribute.getName(), e);
	}
    }

    void performBind(PresentationModelAdapter presentationModelAdapter) {
	Command command = attribute.findCommand(presentationModelAdapter, viewAttribute.getEventType());
	if (command != null) {
	    viewAttribute.bind(view, command);
	} else {
	    viewAttribute.bind(view, getNoArgsCommand(presentationModelAdapter));
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
	Class<?> clazz = viewAttribute.getEventType();
	StringBuilder descriptionBuilder = new StringBuilder(clazz.getSimpleName());

	while (clazz.getSuperclass() != Object.class) {
	    clazz = clazz.getSuperclass();
	    descriptionBuilder.append('/').append(clazz.getSimpleName());
	}
	return descriptionBuilder.toString();
    }


    @Override
    public void preInitializeView(BindingContext bindingContext) {
    }
}
