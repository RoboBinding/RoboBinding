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
package sample.robobinding.model;

import sample.robobinding.R;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public enum Genre
{
	UNSPECIFIED("Unspecified", R.drawable.question_mark), 
	ROCK("Rock", R.drawable.rock), 
	CLASSICAL("Classical", R.drawable.classical), 
	POP ("Pop", R.drawable.pop);
	
	private final String label;
	private final int iconResId;

	private Genre(String label, int iconResId)
	{
		this.label = label;
		this.iconResId = iconResId;
	}

	public String getLabel()
	{
		return label;
	}

	public int getIconResId()
	{
		return iconResId;
	}

    public static int indexOf(Genre genre) {
        
        for (int i = 0; i < values().length; i++)
        {
            if (values()[i] == genre)
                return i;
        }
        
        return -1;
    }
}
