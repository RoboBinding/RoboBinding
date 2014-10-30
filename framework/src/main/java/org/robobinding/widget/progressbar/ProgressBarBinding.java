package org.robobinding.widget.progressbar;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.widget.ProgressBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ProgressBarBinding implements ViewBinding<ProgressBar> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<ProgressBar> mappings) {
		mappings.mapOneWayProperty(MaxAttribute.class, "max");
		mappings.mapOneWayProperty(ProgressAttribute.class, "progress");
		mappings.mapOneWayProperty(SecondaryProgressAttribute.class, "secondaryProgress");
	}

}