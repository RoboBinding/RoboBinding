package org.robobinding.aspects;

import org.aspectj.lang.annotation.AdviceName;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.internal.java_beans.Introspector;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.property.PropertyChangeSupport;

/**
 * This aspect will be weaved into the classes with {@link PresentationModel} annotation and with AspectJ enabled.
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
privileged public aspect PresentationModelAspect
{
	declare parents: @PresentationModel !(HasPresentationModelChangeSupport+) implements PresentationModelMixin;

	/**
	 * Raise an error to prevent using {@link PropertyChangeSupport} outside the framework.
	 */
	pointcut fieldDeclarationOfPropertyChangeSupport() : set(PropertyChangeSupport *.*);

	declare error : fieldDeclarationOfPropertyChangeSupport() && !within(PresentationModelChangeSupport) && !within(org.robobinding.property.*)
		: "PropertyChangeSupport is intented to be used internally by framework only. " +
				"Please use org.robobinding.presentationmodel.PresentationModelChangeSupport instead.";

	/**
	 * Append property change notifications for setters without {@link CustomSetter} annotation.
	 */
	pointcut nonCustomSetter(HasPresentationModelChangeSupport presentationModel) : execution (!@CustomSetter public void @PresentationModel HasPresentationModelChangeSupport+.set*(*)) && this(presentationModel);

	@AdviceName("firePropertyChange")
	after (HasPresentationModelChangeSupport presentationModel) : nonCustomSetter(presentationModel)
	{
		String methodName = thisJoinPointStaticPart.getSignature().getName();
		String propertyName = Introspector.decapitalize(methodName.substring(3));
		PresentationModelChangeSupport changeSupport = presentationModel.getPresentationModelChangeSupport();
		changeSupport.firePropertyChange(propertyName);
	}
}
