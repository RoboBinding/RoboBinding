package org.robobinding.itempresentationmodel;

import org.robobinding.util.Preconditions;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class TypedCursorAdapter<T> extends CursorWrapper implements TypedCursor<T> {
	private final RowMapper<T> rowMapper;
	private final Cursor cursor;

	public TypedCursorAdapter(Cursor cursor, RowMapper<T> rowMapper) {
		super(validateCursorAndReturnIt(cursor));

		Preconditions.checkNotNull(rowMapper, "rowMapper cannot be null");
		this.rowMapper = rowMapper;
		this.cursor = cursor;
	}

	private static Cursor validateCursorAndReturnIt(Cursor cursor) {
		Preconditions.checkNotNull(cursor, "cursor cannot be null");
		return cursor;
	}

	@Override
	public T getObjectAtPosition(int position) {
		int oldPosition = cursor.getPosition();
		if (!cursor.moveToPosition(position)) {
			throw new RuntimeException("invalid position '" + position + "'");
		}
		try {
			return rowMapper.mapRow(cursor);
		} finally {
			cursor.moveToPosition(oldPosition);
		}
	}

}
