package org.robobinding.aspects;

import org.aspectj.lang.annotation.AdviceName;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robort Taylor
 * @author Cheng Wei
 */
public interface PresentationModelMixin extends HasPresentationModelChangeSupport
{
	static aspect Impl
	{
		private PresentationModelChangeSupport PresentationModelMixin.__changeSupport;

		public PresentationModelChangeSupport PresentationModelMixin.getPresentationModelChangeSupport()
		{
			return __changeSupport;
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
	}

}
