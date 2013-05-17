/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute.seekbar;

import org.robobinding.viewattribute.view.ViewListeners;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SeekBarListeners extends ViewListeners {
    SeekBar seekBar;
    private OnSeekBarChangeListeners onSeekBarChangeListeners;

    public SeekBarListeners(SeekBar seekBar) {
	super(seekBar);
	this.seekBar = seekBar;
    }

    public void addOnSeekBarChangeListener(OnSeekBarChangeListener listener) {
	ensureOnSeekBarChangeListenersInitialized();
	onSeekBarChangeListeners.addListener(listener);
    }

    private void ensureOnSeekBarChangeListenersInitialized() {
	if (onSeekBarChangeListeners == null) {
	    onSeekBarChangeListeners = new OnSeekBarChangeListeners();
	    seekBar.setOnSeekBarChangeListener(onSeekBarChangeListeners);
	}
    }
}
