package org.robobinding.itempresentationmodel;

import android.database.Cursor;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * @author Robert Taylor
 * 
 */
public interface TypedCursor<T> extends Cursor {
	T getObjectAtPosition(int position);
}
