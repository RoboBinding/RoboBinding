package org.robobinding.aspects;

import org.aspectj.lang.annotation.AdviceName;
import org.robobinding.property.ObservableBean;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public aspect ItemPresentationModelAspect
{
	declare parents: !(ObservableBean+) && ItemPresentationModel+ && !ItemPresentationModel implements PresentationModelMixin;

	/**
	 * Fire itemPresentationModel refresh after updateData.
	 */
	pointcut updateData(PresentationModelMixin itemPresentationModel) : execution (* ItemPresentationModel+.updateData(int,*)) && this(itemPresentationModel) && within(PresentationModelMixin+);

	@AdviceName("fireItemPresentationModelRefresh")
	after(PresentationModelMixin itemPresentationModel) : updateData(itemPresentationModel)
	{
		itemPresentationModel.refreshPresentationModel();
	}

}
