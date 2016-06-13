package org.robobinding.itempresentationmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.robobinding.util.Preconditions;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ListObservable<E> extends ListWrapper<E> 
	implements List<E>, DataSetObservable {
	private DataSetChangeListeners listeners;

	public ListObservable(List<E> list) {
		super(notNullList(list));
		
		listeners = new DataSetChangeListeners();
	}
	
	private static <E> List<E> notNullList(List<E> list) {
		Preconditions.checkNotNull(list, "The argument list must not be null");
		return list;
	}
	
	@Override
	public boolean add(E e) {
        super.add(e);
        
        listeners.notifyItemRangeInserted(this, this.size() - 1, 1);
        return true;
    }

	@Override
    public void add(int index, E e) {
        super.add(index, e);
        listeners.notifyItemRangeInserted(this, index, 1);
    }

	@Override
    public boolean addAll(Collection<? extends E> c) {
        int oldSize = this.size();
        
        boolean added = super.addAll(c);
        if(added) {
        	listeners.notifyItemRangeInserted(this, oldSize, this.size() - oldSize);
        }

        return added;
    }

	@Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean added = super.addAll(index, c);
        if(added) {
        	listeners.notifyItemRangeInserted(this, index, c.size());
        }

        return added;
    }

	@Override
	public E set(int index, E e) {
	    E oldElement = super.set(index, e);
	    
	    listeners.notifyItemRangeChanged(this, index, 1);
	
	    return oldElement;
	}

	@Override
	public E remove(int index) {
	    E e = super.remove(index);
	    listeners.notifyItemRangeRemoved(this, index, 1);
	    return e;
	}

	@Override
	public boolean remove(Object object) {
	    int index = this.indexOf(object);
	    if(index >= 0) {
	        this.remove(index);
	        return true;
	    } else {
	        return false;
	    }
	}

	@Override
    public void clear() {
        int oldSize = this.size();
        super.clear();
        if(oldSize != 0) {
            listeners.notifyItemRangeRemoved(this, 0, oldSize);
        }

    }

	@Override
	public void addListener(DataSetChangeListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(DataSetChangeListener listener) {
		listeners.remove(listener);
	}
	
	public static <E> ListObservable<E> newArrayList() {
		return new ListObservable<E>(new ArrayList<E>());
	}
	
	public static <E> ListObservable<E> newLinkedList() {
		return new ListObservable<E>(new LinkedList<E>());
	}
	
}
