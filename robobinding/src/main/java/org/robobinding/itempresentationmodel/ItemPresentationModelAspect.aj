package org.robobinding.itempresentationmodel;

import org.aspectj.lang.annotation.AdviceName;
import org.robobinding.presentationmodel.PresentationModelMixin;
import org.robobinding.property.ObservableProperties;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public aspect ItemPresentationModelAspect
{
	declare parents: !(ObservableProperties+) && ItemPresentationModel+ && !ItemPresentationModel implements PresentationModelMixin;

	pointcut updateData(PresentationModelMixin itemPresentationModel) : execution (* ItemPresentationModel+.updateData(int,*)) && this(itemPresentationModel) && within(PresentationModelMixin+);

	@AdviceName("fireItemPresentationModelRefresh")
	after(PresentationModelMixin itemPresentationModel) : updateData(itemPresentationModel)
	{
		itemPresentationModel.refreshPresentationModel();
	}

}
