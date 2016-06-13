package org.robobinding.binder;

import static org.robobinding.util.Preconditions.checkValidResourceId;

import org.robobinding.BindingContext;
import org.robobinding.BindingContextFactoryB;
import org.robobinding.MenuBinder;
import org.robobinding.util.Preconditions;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MenuBinderImpl implements MenuBinder {
	private final BindingMenuInflater bindingMenuInflater;
	private final ViewBindingLifecycle viewBindingLifecycle;
	private final BindingContextFactoryB bindingContextFactory;

	public MenuBinderImpl(BindingMenuInflater bindingMenuInflater, ViewBindingLifecycle viewBindingLifecycle,
			BindingContextFactoryB bindingContextFactory) {
		this.bindingMenuInflater = bindingMenuInflater;
		this.viewBindingLifecycle = viewBindingLifecycle;
		this.bindingContextFactory = bindingContextFactory;
	}

	public void inflateAndBind(int menuRes, Object presentationModel) {
		checkMenuRes(menuRes);
		checkPresentationModel(presentationModel);
		BindingContext bindingContext = bindingContextFactory.create(presentationModel);
		
		InflatedView inflatedView = bindingMenuInflater.inflate(menuRes);

		viewBindingLifecycle.run(inflatedView, bindingContext);
	}

	private void checkMenuRes(int menuRes) {
		checkValidResourceId(menuRes, "invalid menuRes '" + menuRes + "'");
	}

	private void checkPresentationModel(Object presentationModel) {
		Preconditions.checkNotNull(presentationModel, "presentationModel must not be null");
	}
}
