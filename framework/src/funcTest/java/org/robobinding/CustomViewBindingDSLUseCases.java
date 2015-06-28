package org.robobinding;

import org.robobinding.annotation.ViewBinding;
import org.robobinding.binder.BinderFactoryBuilder;
import org.robobinding.customviewbinding.CustomViewBinding;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;
import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.widgetaddon.DefaultViewAddOnFactory;
import org.robobinding.widgetaddon.ViewAddOn;

import android.widget.ImageView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CustomViewBindingDSLUseCases {
    public void addOrOverrideViewBinding() {
		new BinderFactoryBuilder().add(
				new MyCustomViewBinding()
					.forView(ImageView.class)
					.withViewAddOn(new DefaultViewAddOnFactory(ImageViewAddOn.class)));
    }
    
    public void extendsExistingViewBinding() {
		new BinderFactoryBuilder().add(
			new MyCustomViewBinding()
				.extend(ImageView.class)
				.withViewAddOn(ImageViewAddOn.class));
	    }
    
    @ViewBinding(simpleOneWayProperties={"imageAlpha", "maxWidth", "maxHeight"})
    private static class MyCustomViewBinding extends CustomViewBinding<ImageView> {
    }
    
    static class ImageViewAddOn implements ViewAddOn {
    	public ImageViewAddOn(ImageView view) {
		}
    }
    
    /**
     * Expected auto-generated code.
     */
    static class MyCustomViewBinding$$VB implements org.robobinding.viewbinding.ViewBinding<ImageView> {
    	private final MyCustomViewBinding customViewBinding;
    	
    	public MyCustomViewBinding$$VB(MyCustomViewBinding customViewBinding) {
			this.customViewBinding = customViewBinding;
		}
    	
    	@Override
    	public void mapBindingAttributes(BindingAttributeMappings<ImageView> mappings) {
    		mappings.mapOneWayProperty(ImageAlphaAttribute.class, "imageAlpha");
    		mappings.mapOneWayProperty(MaxWidthAttribute.class, "maxWidth");
    		mappings.mapOneWayProperty(MaxHeightAttribute.class, "maxHeight");
    		
    		customViewBinding.mapBindingAttributes(mappings);
    	}
    	
    	private static class ImageAlphaAttribute implements OneWayPropertyViewAttribute<ImageView, Integer> {
    		@Override
    		public void updateView(ImageView view, Integer newAlpha) {
    			view.setImageAlpha(newAlpha);
    		}
    	}
    	
    	private static class MaxWidthAttribute implements OneWayPropertyViewAttribute<ImageView, Integer> {
    		@Override
    		public void updateView(ImageView view, Integer newMaxWidth) {
    			view.setMaxWidth(newMaxWidth);
    		}
    	}
    	
    	private static class MaxHeightAttribute implements OneWayPropertyViewAttribute<ImageView, Integer> {
    		@Override
    		public void updateView(ImageView view, Integer newMaxHeight) {
    			view.setMaxHeight(newMaxHeight);
    		}
    	}
    }
    
}
