package org.robobinding.binder;

import org.robobinding.BindingContext;
import org.robobinding.BindingContextFactory;
import org.robobinding.BindingContextFactoryA;
import org.robobinding.BindingContextFactoryB;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.presentationmodel.PresentationModelAdapterFactory;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class BindingContextFactoryAdapters {
	private final PresentationModelAdapterFactory factory;
	private final PresentationModelObjectLoader loader;

	public BindingContextFactoryAdapters(PresentationModelAdapterFactory factory, PresentationModelObjectLoader loader) {
		this.factory = factory;
		this.loader = loader;
	}

	public BindingContextFactoryA adaptA(BindingContextFactory bindingContextFactory) {
		return new BindingContextFactoryAAdapter(factory, bindingContextFactory);
	}
	
	public BindingContextFactoryB adaptB(BindingContextFactory bindingContextFactory) {
		return new BindingContextFactoryBAdapter(loader, adaptA(bindingContextFactory));
	}	
	
	private static class BindingContextFactoryAAdapter implements BindingContextFactoryA {
		private final PresentationModelAdapterFactory factory;
		private final BindingContextFactory delegate;
		
		public BindingContextFactoryAAdapter(PresentationModelAdapterFactory factory, BindingContextFactory delegate) {
			this.factory = factory;
			this.delegate = delegate;
		}

		@Override
		public BindingContext create(AbstractPresentationModelObject presentationModel) {
			PresentationModelAdapter presentationModelAdapter = factory.create(presentationModel);
			return delegate.create(presentationModelAdapter);
		}
	}
	
	private static class BindingContextFactoryBAdapter implements BindingContextFactoryB {
		private final PresentationModelObjectLoader loader;
		private final BindingContextFactoryA delegate;

		public BindingContextFactoryBAdapter(PresentationModelObjectLoader loader, BindingContextFactoryA delegate) {
			this.loader = loader;
			this.delegate = delegate;
		}
		
		@Override
		public BindingContext create(Object presentationModel) {
			AbstractPresentationModelObject presentationModelObject = loader.load(presentationModel);
			return delegate.create(presentationModelObject);
		}
	}

}
