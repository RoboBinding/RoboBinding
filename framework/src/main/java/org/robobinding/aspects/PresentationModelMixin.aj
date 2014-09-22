package org.robobinding.aspects;

import java.text.MessageFormat;

import org.aspectj.lang.annotation.AdviceName;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.property.ObservableBean;
import org.robobinding.property.PropertyChangeListener;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robort Taylor
 * @author Cheng Wei
 */
public interface PresentationModelMixin extends ObservableBean
{
	static aspect Impl
	{
		private PresentationModelChangeSupport PresentationModelMixin.__changeSupport;
		private boolean PresentationModelMixin.__isUserChangeSupportDefined;

		public void PresentationModelMixin.addPropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener)
		{
			__changeSupport.addPropertyChangeListener(propertyName, propertyChangeListener);
		}

		public void PresentationModelMixin.removePropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener)
		{
			__changeSupport.removePropertyChangeListener(propertyName, propertyChangeListener);
		}
		
		private void PresentationModelMixin.__firePropertyChange(String propertyName)
		{
			__changeSupport.firePropertyChange(propertyName);
		}
		
		void PresentationModelMixin.__refreshPresentationModel() {
			__changeSupport.refreshPresentationModel();
		}

		pointcut presentationModelCreation(PresentationModelMixin presentationModel) : initialization(
				PresentationModelMixin+.new(..)) && this(presentationModel) && within(PresentationModelMixin+);

		@AdviceName("initializePresentationModelChangeSupport")
		before(PresentationModelMixin presentationModel) : presentationModelCreation(presentationModel)
		{
			if(presentationModel.__changeSupport == null)
			{
				presentationModel.__changeSupport = new PresentationModelChangeSupport(presentationModel);
			}
		}
		
		/**
		 * Reuse user-defined PresentationModelChangeSupport if found.
		 */
		pointcut setPresentationModelChangeSupport(PresentationModelMixin presentationModel, PresentationModelChangeSupport userDefinedChangeSupport) : set(
				PresentationModelChangeSupport PresentationModelMixin+.*) && target(presentationModel) && args(userDefinedChangeSupport);
			
		@AdviceName("reuseUserDefindedPresentationModelChangeSupport")
		void around(PresentationModelMixin presentationModel, PresentationModelChangeSupport userDefinedChangeSupport) : setPresentationModelChangeSupport(presentationModel, userDefinedChangeSupport){
			if((!"__changeSupport".equals(thisJoinPoint.getSignature().getName())) 
					&& (userDefinedChangeSupport != null)) {
				if(presentationModel.__isUserChangeSupportDefined)
				{
					throw new RuntimeException(MessageFormat.format("{0} defines more than one {1}, but only one is allowed in a PresentationModel.",
							presentationModel.getClass().getName(), PresentationModelChangeSupport.class.getSimpleName()));
				}
				
				presentationModel.__isUserChangeSupportDefined = true;
        		proceed(presentationModel, presentationModel.__changeSupport);
        	}else {
        		proceed(presentationModel, userDefinedChangeSupport);
        	}
        }
	}

}
