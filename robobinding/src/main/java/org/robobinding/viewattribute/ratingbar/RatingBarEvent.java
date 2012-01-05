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

import android.widget.RatingBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RatingBarEvent
{
	private RatingBar ratingBar;
	private float rating;
	private boolean fromUser;
	
	public RatingBarEvent(RatingBar ratingBar, float rating, boolean fromUser)
	{
		this.ratingBar = ratingBar;
		this.rating = rating;
		this.fromUser = fromUser;
	}

	public RatingBar getRatingBar()
	{
		return ratingBar;
	}

	public float getRating()
	{
		return rating;
	}

	public boolean isFromUser()
	{
		return fromUser;
	}

}
