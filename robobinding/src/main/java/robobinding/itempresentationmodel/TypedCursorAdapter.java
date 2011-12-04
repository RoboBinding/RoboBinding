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
package robobinding.itempresentationmodel;

import robobinding.internal.org_apache_commons_lang3.Validate;
import android.database.Cursor;
import android.database.CursorWrapper;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class TypedCursorAdapter<T> extends CursorWrapper implements TypedCursor<T>
{
	private RowMapper<T> rowMapper;
	private Cursor cursor;
	public TypedCursorAdapter(Cursor cursor, RowMapper<T> rowMapper)
	{
		super(validateCursorAndReturnIt(cursor));
		
		Validate.notNull(rowMapper, "rowMapper cannot be null");
		this.rowMapper = rowMapper;
		this.cursor = cursor;
	}
	private static Cursor validateCursorAndReturnIt(Cursor cursor)
	{
		Validate.notNull(cursor, "cursor cannot be null");
		return cursor;
	}
	@Override
	public T getObjectAtPosition(int position)
	{
		int oldPosition = cursor.getPosition();
		if(!cursor.moveToPosition(position))
		{
			throw new RuntimeException("invalid position '"+position+"'");
		}
		try
		{
			return rowMapper.mapRow(cursor);
		}finally
		{
			cursor.moveToPosition(oldPosition);
		}
	}
	
}
