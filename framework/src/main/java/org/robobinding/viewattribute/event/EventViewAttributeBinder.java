package org.robobinding.viewattribute.event;

import org.robobinding.BindingContext;
import org.robobinding.attribute.Command;
import org.robobinding.attribute.EventAttribute;
import org.robobinding.viewattribute.AttributeBindingException;
import org.robobinding.viewattribute.ViewAttributeBinder;
import org.robobinding.widgetaddon.ViewAddOn;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class EventViewAttributeBinder implements ViewAttributeBinder {
	private final Object view;
	private final ViewAddOn viewAddOn;
	private final EventViewAttribute<Object, ViewAddOn> viewAttribute;
	private final EventAttribute attribute;

	public EventViewAttributeBinder(Object view, ViewAddOn viewAddOn, 
			EventViewAttribute<Object, ViewAddOn> viewAttribute, EventAttribute attribute) {
		this.view = view;
		this.viewAddOn = viewAddOn;
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

	void performBind(BindingContext bindingContext) {
		Command command = attribute.findCommand(bindingContext, viewAttribute.getEventType());
		if (command != null) {
			viewAttribute.bind(viewAddOn, command, view);
		} else {
			viewAttribute.bind(viewAddOn, getNoArgsCommand(bindingContext), view);
		}
	}

	private Command getNoArgsCommand(BindingContext bindingContext) {
		Command noArgsCommand = attribute.findCommand(bindingContext);

		if (noArgsCommand == null) {
			String commandName = attribute.getCommandName();
			throw new IllegalArgumentException("Could not find method " + commandName + "() or " + commandName + "(" + getAcceptedParameterTypesDescription()
					+ ") in class " + bindingContext.getPresentationModelClassName());
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
