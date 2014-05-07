package org.robobinding.presentationmodel;

import org.aspectj.lang.annotation.AdviceName;
import org.robobinding.property.ObservableProperties;
import org.robobinding.property.PresentationModelPropertyChangeListener;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robort Taylor
 * @author Cheng Wei
 */
public interface PresentationModelMixin extends ObservableProperties
{
	static aspect Impl
	{
		private PresentationModelChangeSupport PresentationModelMixin.presentationModelChangeSupport;

		public void PresentationModelMixin.addPropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener propertyChangeListener)
		{
			presentationModelChangeSupport.addPropertyChangeListener(propertyName, propertyChangeListener);
		}

		public void PresentationModelMixin.removePropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener propertyChangeListener)
		{
			presentationModelChangeSupport.removePropertyChangeListener(propertyName, propertyChangeListener);
		}

		public void PresentationModelMixin.refreshPresentationModel()
		{
			presentationModelChangeSupport.fireChangeAll();
		}

		private void PresentationModelMixin.firePropertyChange(String propertyName)
		{
			presentationModelChangeSupport.firePropertyChange(propertyName);
		}

		pointcut presentationModelCreation(PresentationModelMixin presentationModel) : initialization(
				PresentationModelMixin+.new(..)) && this(presentationModel) && within(PresentationModelMixin+);

		@AdviceName("initializePresentationModelChangeSupport")
		before(PresentationModelMixin presentationModel) : presentationModelCreation(presentationModel)
		{
			if(presentationModel.presentationModelChangeSupport == null)
			{
				presentationModel.presentationModelChangeSupport = new PresentationModelChangeSupport(presentationModel);
			}
		}
	}

}
