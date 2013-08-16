/**
 * Copyright 2013 Cheng Wei, Robert Taylor
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
package org.robobinding.viewattribute.view;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.viewattribute.seekbar.SeekBarListeners;

import android.view.View;
import android.widget.SeekBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewListenersMapBuilderTest {
    @Test
    public void whenPutViewListenersForViews_thenTheMappingsAreAdded() {
	ViewListenersMapBuilder builder = new ViewListenersMapBuilder();
	builder.put(View.class, ViewListeners.class);
	builder.put(SeekBar.class, SeekBarListeners.class);
	
	ViewListenersMap map = builder.build();
	
	assertThat(map.mappings.size(), is(2));
    }
    
    @SuppressWarnings("rawtypes")
    @Test
    public void whenPutViewListenersForAViewSecondTime_thenKeepTheLatestMappings() {
	ViewListenersMapBuilder builder = new ViewListenersMapBuilder();
	builder.put(View.class, ViewListeners.class);
	builder.put(View.class, CustomViewListeners.class);
	
	ViewListenersMap map = builder.build();
	
	assertThat(map.mappings.size(), is(1));
	assertThat(map.getViewListenersClass(View.class), equalTo((Class) CustomViewListeners.class));
    }
    
    private static class CustomViewListeners extends ViewListeners {
	public CustomViewListeners(View view) {
	    super(view);
	}
    }

}
