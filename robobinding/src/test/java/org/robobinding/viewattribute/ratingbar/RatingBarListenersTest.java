/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package org.robobinding.viewattribute.ratingbar;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.RandomValues;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class RatingBarListenersTest {
    @Test
    public void shouldSupportMultipleOnRatingBarChangeListenersInRatingBar() {
	RatingBar ratingBar = new RatingBar(null);
	RatingBarListeners ratingBarListeners = new RatingBarListeners(ratingBar);

	MockOnRatingBarChangeListener listener1 = new MockOnRatingBarChangeListener();
	MockOnRatingBarChangeListener listener2 = new MockOnRatingBarChangeListener();

	ratingBarListeners.addOnRatingBarChangeListener(listener1);
	ratingBarListeners.addOnRatingBarChangeListener(listener2);

	ratingBar.setRating(RandomValues.anyFloat());

	assertTrue(listener1.ratingBarEventFired);
	assertTrue(listener2.ratingBarEventFired);
    }

    private static class MockOnRatingBarChangeListener implements OnRatingBarChangeListener {
	private boolean ratingBarEventFired;

	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
	    ratingBarEventFired = true;
	}

    }
}
