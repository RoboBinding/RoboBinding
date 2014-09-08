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
		private PresentationModelChangeSupport PresentationModelMixin.presentationModelChangeSupport;

		public void PresentationModelMixin.addPropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener)
		{
			presentationModelChangeSupport.addPropertyChangeListener(propertyName, propertyChangeListener);
		}

		public void PresentationModelMixin.removePropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener)
		{
			presentationModelChangeSupport.removePropertyChangeListener(propertyName, propertyChangeListener);
		}

		public void PresentationModelMixin.refreshPresentationModel()
		{
			presentationModelChangeSupport.refreshPresentationModel();
		}

		private void PresentationModelMixin.firePropertyChange(String propertyName)
		{
			presentationModelChangeSupport.firePropertyChange(propertyName);
		}

		pointcut presentationModelCreation(PresentationModelMixin presentationModel) : initialization(
				PresentationModelMixin+.new(..)) && this(presentationModel) && within(PresentationModelMixin+);

		@AdviceName("initializePresentationModelChangeSupport")
		after(PresentationModelMixin presentationModel) returning : presentationModelCreation(presentationModel)
		{
			if(presentationModel.presentationModelChangeSupport == null)
			{
				presentationModel.presentationModelChangeSupport = new PresentationModelChangeSupport(presentationModel);
			}
		}
	}

}