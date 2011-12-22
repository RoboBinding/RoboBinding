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

import org.robobinding.viewattribute.AbstractListeners;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnRatingBarChangeListeners extends AbstractListeners<OnRatingBarChangeListener> implements OnRatingBarChangeListener
{
	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
	{
		for (OnRatingBarChangeListener onRatingBarChangeListener : listeners)
			onRatingBarChangeListener.onRatingChanged(ratingBar, rating, fromUser);
	}

	public static OnRatingBarChangeListeners convert(OnRatingBarChangeListener existingListener)
	{
		if (existingListener instanceof OnRatingBarChangeListeners)
		{
			return (OnRatingBarChangeListeners)existingListener;
		} else
		{
			OnRatingBarChangeListeners listeners = new OnRatingBarChangeListeners();
			listeners.addListener(existingListener);
			return listeners;
		}
	}
}
