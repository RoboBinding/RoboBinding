package org.robobinding.viewattribute;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractListeners<T> {
    protected final List<T> listeners;

    public AbstractListeners() {
	listeners = Lists.newArrayList();
    }

    public void addListener(T listener) {
	listeners.add(listener);
    }
}
