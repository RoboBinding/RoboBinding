/**
 * ViewAlbumPresentationModelAspect.aj
 * 22 Nov 2011 Copyright Cheng Wei and Robert Taylor
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
package robobinding.presentationmodelaspects;

import org.aspectj.lang.annotation.AdviceName;

import robobinding.internal.java_beans.Introspector;
import robobinding.property.ObservableProperties;
import robobinding.property.PropertyChangeSupport;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
privileged public aspect PresentationModelAspect
{
	declare parents: @PresentationModel * implements PresentationModelMixin;

	
	pointcut fieldDeclarationOfPropertyChangeSupport() : set(PropertyChangeSupport *.*) && !within(PresentationModelMixin);
	
	declare error : fieldDeclarationOfPropertyChangeSupport()  && !within(robobinding.property.*)
		: "PresentationModelChangeSupport is not intented to be used by public";
	
	
	pointcut subclassOfObservableProperties() : staticinitialization(ObservableProperties+ && !(PresentationModelMixin+));
	
	declare error: subclassOfObservableProperties() && !within(robobinding.property.*)
		: "ObservableProperties is not intented to be used by public";
	
	
	pointcut nonCustomSetter(PresentationModelMixin presentationModel) : execution (!@CustomSetter public void PresentationModelMixin+.set*(*)) && this(presentationModel);
	
	@AdviceName("firePropertyChange")
	after (PresentationModelMixin presentationModel) : nonCustomSetter(presentationModel) 
	{
		String methodName = thisJoinPointStaticPart.getSignature().getName();
		String propertyName = Introspector.decapitalize(methodName.substring(3));
		presentationModel.firePropertyChange(propertyName);
	}
}
