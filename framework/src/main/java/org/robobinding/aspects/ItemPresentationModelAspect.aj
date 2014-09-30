package org.robobinding.aspects;

import org.aspectj.lang.annotation.AdviceName;
import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.property.ObservableBean;

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
	pointcut updateData(PresentationModelMixin itemPresentationModel, ItemContext itemContext) : execution (* ItemPresentationModel+.updateData(*, ItemContext)) 
		&& within(PresentationModelMixin+) && this(itemPresentationModel) && args(*, itemContext);

	@AdviceName("fireItemPresentationModelRefresh")
	after(PresentationModelMixin itemPresentationModel, ItemContext itemContext) : updateData(itemPresentationModel, itemContext)
	{
		if(itemContext.isPreInitializeViews()) {
			itemPresentationModel.__refreshPresentationModel();
		}
	}

}
