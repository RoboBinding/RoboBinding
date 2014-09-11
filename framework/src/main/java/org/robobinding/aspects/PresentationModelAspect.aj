package org.robobinding.aspects;

import org.aspectj.lang.annotation.AdviceName;
import org.robobinding.internal.java_beans.Introspector;
import org.robobinding.property.ObservableBean;
import org.robobinding.property.PropertyChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

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
	declare parents: @PresentationModel !(ObservableBean+) implements PresentationModelMixin;

	/**
	 * Raise an error to prevent using {@link PropertyChangeSupport} outside the framework.
	 */
	pointcut fieldDeclarationOfPropertyChangeSupport() : set(PropertyChangeSupport *.*);

	declare error : fieldDeclarationOfPropertyChangeSupport() && !within(PresentationModelChangeSupport) && !within(org.robobinding.property.*)
		: "PropertyChangeSupport is intented to be used internally by framework only. " +
				"Please use org.robobinding.presentationmodel.PresentationModelChangeSupport instead.";

	/**
	 * Raise an error to prevent using {@link ObservableBean} with {@link PresentationModel} annotation.
	 */
	pointcut subclassOfObservableBeanWithPresentationModelAnnotation() : staticinitialization((@PresentationModel ObservableBean+) && !(PresentationModelMixin+));

	declare error: subclassOfObservableBeanWithPresentationModelAnnotation()
		: "You can either implement ObservableBean manually or annotate your presentation model with @PresentationModel " +
				"to let weaver generate code for you. Doing both may led to unexpected change event behaviour";


	/**
	 * Append property change notifications for setters without {@link CustomSetter} annotation.
	 */
	pointcut nonCustomSetter(PresentationModelMixin presentationModel) : execution (!@CustomSetter public void PresentationModelMixin+.set*(*)) && this(presentationModel);

	@AdviceName("firePropertyChange")
	after (PresentationModelMixin presentationModel) : nonCustomSetter(presentationModel)
	{
		String methodName = thisJoinPointStaticPart.getSignature().getName();
		String propertyName = Introspector.decapitalize(methodName.substring(3));
		presentationModel.__firePropertyChange(propertyName);
	}
}
