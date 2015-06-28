package org.robobinding.widgetaddon;

import java.text.MessageFormat;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAddOnInjector implements ViewAddOns {
	private final ViewAddOns viewAddOns;

	public ViewAddOnInjector(ViewAddOns viewAddOns) {
		this.viewAddOns = viewAddOns;
	}

	@SuppressWarnings("unchecked")
	public void injectIfRequired(Object viewAttribute, Object view) {
		if (viewAttribute instanceof ViewAddOnAware) {
			ViewAddOn viewAddOn = getMostSuitable(view);

			ViewAddOnAware<ViewAddOn> viewAddOnAwareAttribute = (ViewAddOnAware<ViewAddOn>) viewAttribute;

			try {
				viewAddOnAwareAttribute.setViewAddOn(viewAddOn);
			} catch (ClassCastException e) {
				String  message = MessageFormat.format("Is ''{0}'' a view attribute of view ''{1}''; "
						+ "or did you forget to register the ViewAddOn by org.robobinding.binder.BinderFactoryBuilder? "
						+ "The closest ViewAddOn ''{2}'' we found does not match the view attribute.", 
						viewAttribute.getClass().getName(), 
						view.getClass().getName(), 
						viewAddOn.getClass().getName());
				throw new RuntimeException(message, e);
			}
		}
	}

	@Override
	public ViewAddOn getMostSuitable(Object view) {
		return viewAddOns.getMostSuitable(view);
	}
}
