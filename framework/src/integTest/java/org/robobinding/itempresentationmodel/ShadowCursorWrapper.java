/**
 *
 */
package org.robobinding.itempresentationmodel;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Implements(CursorWrapper.class)
public class ShadowCursorWrapper {
    private Cursor cursor;

    public void __constructor__(Cursor cursor) {
	this.cursor = cursor;
    }

    @Implementation
    public int getCount() {
	return cursor.getCount();
    }

    @Implementation
    public int getPosition() {
	return cursor.getPosition();
    }

    @Implementation
    public boolean move(int offset) {
	return cursor.move(offset);
    }

    @Implementation
    public boolean moveToPosition(int position) {
	return cursor.moveToPosition(position);
    }

    @Implementation
    public boolean moveToFirst() {
	return cursor.moveToFirst();
    }

    @Implementation
    public boolean moveToLast() {
	return cursor.moveToLast();
    }

    @Implementation
    public boolean moveToNext() {
	return cursor.moveToNext();
    }

    @Implementation
    public boolean moveToPrevious() {
	return cursor.moveToPrevious();
    }

    @Implementation
    public boolean isFirst() {
	return cursor.isFirst();
    }

    @Implementation
    public boolean isLast() {
	return cursor.isLast();
    }

    @Implementation
    public boolean isBeforeFirst() {
	return cursor.isBeforeFirst();
    }

    @Implementation
    public boolean isAfterLast() {
	return cursor.isAfterLast();
    }

    @Implementation
    public int getColumnIndex(String columnName) {
	return cursor.getColumnIndex(columnName);
    }

    @Implementation
    public int getColumnIndexOrThrow(String columnName) {
	return cursor.getColumnIndexOrThrow(columnName);
    }

    @Implementation
    public String getColumnName(int columnIndex) {
	return cursor.getColumnName(columnIndex);
    }

    @Implementation
    public String[] getColumnNames() {
	return cursor.getColumnNames();
    }

    @Implementation
    public int getColumnCount() {
	return cursor.getColumnCount();
    }

    @Implementation
    public byte[] getBlob(int columnIndex) {
	return cursor.getBlob(columnIndex);
    }

    @Implementation
    public String getString(int columnIndex) {
	return cursor.getString(columnIndex);
    }

    @Implementation
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
	cursor.copyStringToBuffer(columnIndex, buffer);
    }

    @Implementation
    public short getShort(int columnIndex) {
	return cursor.getShort(columnIndex);
    }

    @Implementation
    public int getInt(int columnIndex) {
	return cursor.getInt(columnIndex);
    }

    @Implementation
    public long getLong(int columnIndex) {
	return cursor.getLong(columnIndex);
    }

    @Implementation
    public float getFloat(int columnIndex) {
	return cursor.getFloat(columnIndex);
    }

    @Implementation
    public double getDouble(int columnIndex) {
	return cursor.getDouble(columnIndex);
    }

    @Implementation
    public boolean isNull(int columnIndex) {
	return cursor.isNull(columnIndex);
    }

    @Implementation
    public void deactivate() {
	cursor.deactivate();
    }

    @Implementation
    public boolean requery() {
	return cursor.requery();
    }

    @Implementation
    public void close() {
	cursor.close();
    }

    @Implementation
    public boolean isClosed() {
	return cursor.isClosed();
    }

    @Implementation
    public void registerContentObserver(ContentObserver observer) {
	cursor.registerContentObserver(observer);
    }

    @Implementation
    public void unregisterContentObserver(ContentObserver observer) {
	cursor.unregisterContentObserver(observer);
    }

    @Implementation
    public void registerDataSetObserver(DataSetObserver observer) {
	cursor.registerDataSetObserver(observer);
    }

    @Implementation
    public void unregisterDataSetObserver(DataSetObserver observer) {
	cursor.unregisterDataSetObserver(observer);
    }

    @Implementation
    public void setNotificationUri(ContentResolver cr, Uri uri) {
	cursor.setNotificationUri(cr, uri);
    }

    @Implementation
    public boolean getWantsAllOnMoveCalls() {
	return cursor.getWantsAllOnMoveCalls();
    }

    @Implementation
    public Bundle getExtras() {
	return cursor.getExtras();
    }

    @Implementation
    public Bundle respond(Bundle extras) {
	return cursor.respond(extras);
    }
}
