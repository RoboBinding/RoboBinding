package org.robobinding.widget.seekbar;

import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappings;

import android.widget.SeekBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class SeekBarAttributeMapper implements BindingAttributeMapper<SeekBar> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<SeekBar> mappings) {
	mappings.mapProperty(TwoWayProgressAttribute.class, "progress");

	mappings.mapEvent(OnSeekBarChangeAttribute.class, "onSeekBarChange");
    }

}