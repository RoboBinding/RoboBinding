package org.robobinding.presentationmodel;

import org.aspectj.lang.annotation.AdviceName;
import org.robobinding.internal.java_beans.Introspector;
import org.robobinding.property.ObservableBean;
import org.robobinding.property.PropertyChangeSupport;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
privileged public aspect PresentationModelAspect
{
	declare parents: @PresentationModel !(ObservableBean+) implements PresentationModelMixin;

	pointcut fieldDeclarationOfPresentationModelPropertyChangeSupport() : set(PropertyChangeSupport *.*);

	declare error : fieldDeclarationOfPresentationModelPropertyChangeSupport() && !within(PresentationModelChangeSupport) && !within(org.robobinding.property.*)
		: "PropertyChangeSupport is intented to be used internally by framework only. " +
				"Please use robobinding.presentationmodel.PresentationModelChangeSupport instead.";


	pointcut subclassOfObservablePropertiesWithPresentationModelAnnotation() : staticinitialization((@PresentationModel ObservableBean+) && !(PresentationModelMixin+));

	declare error: subclassOfObservablePropertiesWithPresentationModelAnnotation()
		: "You can either implement ObservableBean manually or annotate your presentation model with @PresentationModel " +
				"to let weaver generate code for you. Doing both may led to unexpected change event behaviour";


	pointcut nonCustomSetter(PresentationModelMixin presentationModel) : execution (!@CustomSetter public void PresentationModelMixin+.set*(*)) && this(presentationModel);

	@AdviceName("firePropertyChange")
	after (PresentationModelMixin presentationModel) : nonCustomSetter(presentationModel)
	{
		String methodName = thisJoinPointStaticPart.getSignature().getName();
		String propertyName = Introspector.decapitalize(methodName.substring(3));
		presentationModel.firePropertyChange(propertyName);
	}
}
