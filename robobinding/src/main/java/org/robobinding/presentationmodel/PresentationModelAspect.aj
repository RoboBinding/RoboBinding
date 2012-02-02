/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.presentationmodel;

import org.aspectj.lang.annotation.AdviceName;
import org.robobinding.internal.java_beans.Introspector;
import org.robobinding.property.ObservableProperties;
import org.robobinding.property.PresentationModelPropertyChangeSupport;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
privileged public aspect PresentationModelAspect
{
	declare parents: @PresentationModel !(ObservableProperties+) implements PresentationModelMixin;

	pointcut fieldDeclarationOfPresentationModelPropertyChangeSupport() : set(PresentationModelPropertyChangeSupport *.*);
	
	declare error : fieldDeclarationOfPresentationModelPropertyChangeSupport() && !within(PresentationModelChangeSupport) && !within(org.robobinding.property.*)
		: "PresentationModelPropertyChangeSupport is intented to be used internally by framework only. " +
				"Please use robobinding.presentationmodel.PresentationModelChangeSupport instead.";
	
	
	pointcut subclassOfObservablePropertiesWithPresentationModelAnnotation() : staticinitialization((@PresentationModel ObservableProperties+) && !(PresentationModelMixin+));
	
	declare error: subclassOfObservablePropertiesWithPresentationModelAnnotation()
		: "You can either implement ObservableProperties manually or annotate your presentation model with @PresentationModel " +
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
