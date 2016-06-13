package org.robobinding;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface BinderProvider {
	ItemBinder createItemBinder(BindingContextFactory factory);

	SubViewBinder createSubViewBinder(BindingContextFactory factory);
}
