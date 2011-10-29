/**
 * CursorImpl.java
 * 11 Oct 2011 Copyright Cheng Wei and Robert Taylor
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
package robobinding.sample.presentationmodel;

import java.util.List;

import robobinding.itempresentationmodel.TypedCursor;
import robobinding.sample.model.Album;
import android.database.AbstractCursor;

/**
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 *
 */
public class AlbumCursor extends AbstractCursor implements TypedCursor<Album>
{
	private final List<Album> data;

	public AlbumCursor(List<Album> data)
	{
		this.data = data;
	}
	
	@Override
	public Album getObjectAtPosition(int position)
	{
		return data.get(position);
	}
	
	@Override
	public int getCount()
	{
		return data.size();
	}

	@Override
	public String[] getColumnNames()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String getString(int column)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(int column)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getInt(int column)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLong(int column)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public float getFloat(int column)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDouble(int column)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isNull(int column)
	{
		throw new UnsupportedOperationException();
	}

	
}
