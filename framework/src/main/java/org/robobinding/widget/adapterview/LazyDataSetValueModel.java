package org.robobinding.widget.adapterview;

import org.robobinding.property.DataSetPropertyChangeListener;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.DataSetValueModelWrapper;
import org.robobinding.property.PropertyChangeListener;
import org.robobinding.property.PropertyChangeListenerAdapters;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class LazyDataSetValueModel extends DataSetValueModelWrapper {
	private SizeState sizeState;
	
	public LazyDataSetValueModel(final DataSetValueModel delegate) {
		super(delegate);
		
		sizeState = ZERO;
		
		final SizeState sizeOfValueModel = new SizeState() {
			@Override
			public int size() {
				return delegate.size();
			}
		};
		
		DataSetPropertyChangeListener listener = PropertyChangeListenerAdapters.adapt(new PropertyChangeListener() {
			
			@Override
			public void propertyChanged() {
				sizeState = sizeOfValueModel;
			}
		});
		delegate.addPropertyChangeListener(listener);
	}
	
	@Override
	public int size() {
		return sizeState.size();
	}
	
	private static interface SizeState {
		int size();
	}
	
	private SizeState ZERO = new SizeState() {
		public int size() {
			return 0;
		}
	};
}
