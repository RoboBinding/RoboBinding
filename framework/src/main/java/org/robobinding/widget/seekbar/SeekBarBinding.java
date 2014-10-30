package org.robobinding.widget.seekbar;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.widget.SeekBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class SeekBarBinding implements ViewBinding<SeekBar> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<SeekBar> mappings) {
		mappings.mapTwoWayProperty(TwoWayProgressAttribute.class, "progress");

		mappings.mapEvent(OnSeekBarChangeAttribute.class, "onSeekBarChange");
	}

}