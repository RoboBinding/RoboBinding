package org.robobinding.binder;

import static org.robobinding.util.CollectionUtils.isNotEmpty;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.robobinding.util.Lists;
import org.robobinding.viewattribute.AttributeBindingException;
import org.robobinding.viewattribute.grouped.AttributeGroupBindingException;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@SuppressWarnings("serial")
public class ViewBindingErrors extends RuntimeException {
	private Object view;
	private List<AttributeBindingException> attributeErrors;

	public ViewBindingErrors(Object view) {
		this.view = view;
		attributeErrors = Lists.newArrayList();
	}

	public Object getView() {
		return view;
	}

	public void assertNoErrors() {
		if (hasErrors()) {
			throw this;
		}
	}

	public boolean hasErrors() {
		return isNotEmpty(attributeErrors);
	}

	public int numErrors() {
		return attributeErrors.size();
	}

	void addAttributeError(AttributeBindingException attributeError) {
		attributeErrors.add(attributeError);
	}

	public void addAttributeGroupError(AttributeGroupBindingException e) {
		attributeErrors.addAll(e.getChildAttributeErrors());
	}

	public Collection<AttributeBindingException> getAttributeErrors() {
		return Collections.unmodifiableCollection(attributeErrors);
	}
	
	@Override
	public String getMessage() {
		if(hasErrors()) {
			return firstAttributeError().getMessage();
		}
		return super.getMessage();
	}
	
	private Throwable firstAttributeError() {
		return attributeErrors.get(0);
	}
	
	@Override
	public synchronized Throwable getCause() {
		if(hasErrors()) {
			return firstAttributeError();
		}
		return super.getCause();
	}
}
