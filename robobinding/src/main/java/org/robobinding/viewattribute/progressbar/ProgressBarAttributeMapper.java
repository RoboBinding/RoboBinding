package org.robobinding.viewattribute.progressbar;

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
	mappings.mapPropertyAttribute(MaxAttribute.class, "max");
	mappings.mapPropertyAttribute(ProgressAttribute.class, "progress");
	mappings.mapPropertyAttribute(SecondaryProgressAttribute.class, "secondaryProgress");
    }

}