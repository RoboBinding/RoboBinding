package org.robobinding.itempresentationmodel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface DataSetObservable {
	int size();
	Object get(int index);
	void addListener(DataSetChangeListener listener);
	void removeListener(DataSetChangeListener listener);
}
