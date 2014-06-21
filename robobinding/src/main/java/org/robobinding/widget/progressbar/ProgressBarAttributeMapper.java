package org.robobinding.widget.progressbar;

import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappings;

import android.widget.ProgressBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ProgressBarAttributeMapper implements BindingAttributeMapper<ProgressBar> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<ProgressBar> mappings) {
	mappings.mapProperty(MaxAttribute.class, "max");
	mappings.mapProperty(ProgressAttribute.class, "progress");
	mappings.mapProperty(SecondaryProgressAttribute.class, "secondaryProgress");
    }

}