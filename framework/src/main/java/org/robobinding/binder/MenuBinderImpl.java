package org.robobinding.binder;

import static org.robobinding.util.Preconditions.checkValidResourceId;

import org.robobinding.MenuBinder;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;

import com.google.common.base.Preconditions;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MenuBinderImpl implements MenuBinder {
	private final BindingMenuInflater bindingMenuInflater;
	private final ViewBindingLifecycle viewBindingLifecycle;
	private final PresentationModelObjectLoader presentationModelObjectLoader;

	public MenuBinderImpl(BindingMenuInflater bindingMenuInflater, ViewBindingLifecycle viewBindingLifecycle,
			PresentationModelObjectLoader presentationModelObjectLoader) {
		this.bindingMenuInflater = bindingMenuInflater;
		this.viewBindingLifecycle = viewBindingLifecycle;
		this.presentationModelObjectLoader = presentationModelObjectLoader;
	}

	public void inflateAndBind(int menuRes, Object presentationModel) {
		checkMenuRes(menuRes);
		checkPresentationModel(presentationModel);
		AbstractPresentationModelObject presentationModelObject = presentationModelObjectLoader.load(presentationModel);
		
		InflatedView inflatedView = bindingMenuInflater.inflate(menuRes);

		viewBindingLifecycle.run(inflatedView, presentationModelObject);
	}

	private void checkMenuRes(int menuRes) {
		checkValidResourceId(menuRes, "invalid menuRes '" + menuRes + "'");
	}

	private void checkPresentationModel(Object presentationModel) {
		Preconditions.checkNotNull(presentationModel, "presentationModel must not be null");
	}
}
