package org.robobinding.itempresentationmodel;

import android.database.Cursor;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface RowMapper<T> {
	T mapRow(Cursor cursor);
}
