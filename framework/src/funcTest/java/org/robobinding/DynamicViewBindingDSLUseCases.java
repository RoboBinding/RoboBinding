package org.robobinding;

import org.robobinding.binder.BinderFactoryBuilder;
import org.robobinding.dynamicbinding.DynamicViewBinding;
import org.robobinding.widget.imageview.ImageViewBinding;

import android.widget.ImageView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DynamicViewBindingDSLUseCases {
    public void addOrOverrideViewBinding() {
	new BinderFactoryBuilder().add(
		new DynamicViewBinding().forView(ImageView.class)
			//There are only three attributes available for ImageView.
			.oneWayProperties("imageAlpha", "attribute1", "attributeN"));
    }
    
    public void extendsExistingViewBinding() {
	new BinderFactoryBuilder().add(
		new DynamicViewBinding().extend(ImageView.class, new ImageViewBinding())
			//There are EXTRA three more attributes adding upon ImageViewAttributeMapper.
			.oneWayProperties("imageAlpha", "attribute1", "attributeN"));
    }
    
}
