package org.robobinding.widgetaddon;


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
			ViewAddOn viewAddOn = viewAddOns.getMostSuitable(view);

			ViewAddOnAware<ViewAddOn> viewAddOnAwareAttribute = (ViewAddOnAware<ViewAddOn>) viewAttribute;

			try {
				viewAddOnAwareAttribute.setViewAddOn(viewAddOn);
			} catch (ClassCastException e) {
				throw new RuntimeException("Is '" + viewAttribute.getClass().getName() + "' a view attribute of view '" + view.getClass().getName()
						+ "'; or did you forget to register viewListeners by org.robobinding.binder.BinderFactoryBuilder? The closest viewListeners '"
						+ viewAddOn.getClass().getName() + "' we found does not match the view attribute.", e);
			}
		}
	}

	@Override
	public ViewAddOn getMostSuitable(Object view) {
		return viewAddOns.getMostSuitable(view);
	}
}
