package org.robobinding.binder;

import java.util.Map;

import org.robobinding.PendingAttributesForView;
import org.robobinding.PendingAttributesForViewImpl;

import android.view.View;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PendingAttributesForViewBuilder {
	private View view;
	private Map<String, String> pendingAttributes;

	private PendingAttributesForViewBuilder(View view) {
		this.view = view;
		pendingAttributes = Maps.newHashMap();
	}

	public PendingAttributesForViewBuilder withAttribute(String name, String value) {
		pendingAttributes.put(name, value);
		return this;
	}

	public PendingAttributesForView build() {
		return new PendingAttributesForViewImpl(view, pendingAttributes);
	}

	public static PendingAttributesForViewBuilder aPendingAttributesForView(View view) {
		return new PendingAttributesForViewBuilder(view);
	}
}
