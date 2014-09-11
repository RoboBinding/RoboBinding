package org.robobinding.aspects;

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
		private PresentationModelChangeSupport PresentationModelMixin.__presentationModelChangeSupport;

		public void PresentationModelMixin.addPropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener)
		{
			__presentationModelChangeSupport.addPropertyChangeListener(propertyName, propertyChangeListener);
		}

		public void PresentationModelMixin.removePropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener)
		{
			__presentationModelChangeSupport.removePropertyChangeListener(propertyName, propertyChangeListener);
		}
		
		private void PresentationModelMixin.__firePropertyChange(String propertyName)
		{
			__presentationModelChangeSupport.firePropertyChange(propertyName);
		}
		
		void PresentationModelMixin.__refreshPresentationModel() {
			__presentationModelChangeSupport.refreshPresentationModel();
		}

		pointcut presentationModelCreation(PresentationModelMixin presentationModel) : execution(
				PresentationModelMixin+.new(..)) && this(presentationModel) && within(PresentationModelMixin+);

		@AdviceName("initializePresentationModelChangeSupport")
		after(PresentationModelMixin presentationModel) : presentationModelCreation(presentationModel)
		{
			if(presentationModel.__presentationModelChangeSupport == null)
			{
				presentationModel.__presentationModelChangeSupport = new PresentationModelChangeSupport(presentationModel);
			}
		}
		
		/**
		 * Reuse user-defined PresentationModelChangeSupport if found.
		 */
		pointcut setPresentationModelChangeSupport(PresentationModelMixin presentationModel, PresentationModelChangeSupport userDefinedChangeSupport) : set(
				PresentationModelChangeSupport PresentationModelMixin+.*) && target(presentationModel) && args(userDefinedChangeSupport);
			

		@AdviceName("reuseUserDefindedPresentationModelChangeSupport")
        after(PresentationModelMixin presentationModel, PresentationModelChangeSupport userDefinedChangeSupport) : setPresentationModelChangeSupport(presentationModel, userDefinedChangeSupport){
        	if((userDefinedChangeSupport != null) && 
        			(presentationModel.__presentationModelChangeSupport != userDefinedChangeSupport)) {
        		
        		if(presentationModel.__presentationModelChangeSupport != null) {
        			throw new RuntimeException("A presentation model can only have one '" + PresentationModelChangeSupport.class.getSimpleName()+"'");
        		}
        		presentationModel.__presentationModelChangeSupport = userDefinedChangeSupport;
        	}
        }
	}

}
