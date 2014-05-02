package org.robobinding.itempresentationmodel;

import static com.google.common.base.Preconditions.checkNotNull;
import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class TypedCursorAdapter<T> extends CursorWrapper implements TypedCursor<T> {
    private RowMapper<T> rowMapper;
    private Cursor cursor;

    public TypedCursorAdapter(Cursor cursor, RowMapper<T> rowMapper) {
	super(validateCursorAndReturnIt(cursor));

	checkNotNull(rowMapper, "rowMapper cannot be null");
	this.rowMapper = rowMapper;
	this.cursor = cursor;
    }

    private static Cursor validateCursorAndReturnIt(Cursor cursor) {
	checkNotNull(cursor, "cursor cannot be null");
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
