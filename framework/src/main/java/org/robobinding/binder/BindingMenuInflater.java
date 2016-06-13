package org.robobinding.binder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.robobinding.PendingAttributesForView;
import org.robobinding.PendingAttributesForViewImpl;
import org.robobinding.util.Lists;
import org.robobinding.widget.menuitemgroup.MenuItemGroup;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingMenuInflater {
	private static final String ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android";
	private static final int NO_ID = 0;

	private static final String XML_GROUP = "group";
	private static final String XML_ITEM = "item";
	private static final String XML_MENU = "menu";

	private final Context context;
	private final Menu menu;
	private final MenuInflater inflater;
	private final BindingAttributeParser bindingAttributeParser;
	private final BindingAttributeResolver bindingAttributeResolver;

	public BindingMenuInflater(Context context, Menu menu, MenuInflater inflater, BindingAttributeParser bindingAttributeParser,
			BindingAttributeResolver bindingAttributeResolver) {
		this.context = context;
		this.menu = menu;
		this.inflater = inflater;
		this.bindingAttributeParser = bindingAttributeParser;
		this.bindingAttributeResolver = bindingAttributeResolver;
	}

	public InflatedView inflate(int menuRes) {
		inflater.inflate(menuRes, menu);

		try {
			return new BindingInflater(menuRes).inflate();
		} catch (XmlPullParserException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private class BindingInflater {
		private final XmlResourceParser parser;
		private final AttributeSet attrs;
		private final ViewHierarchyInflationErrorsException errors;
		private final List<ResolvedBindingAttributesForView> resolvedBindingAttributesForChildViews;

		public BindingInflater(int menuRes) {
			parser = context.getResources().getLayout(menuRes);
			attrs = Xml.asAttributeSet(parser);

			errors = new ViewHierarchyInflationErrorsException();
			resolvedBindingAttributesForChildViews = Lists.newArrayList();
		}

		public InflatedView inflate() throws XmlPullParserException, IOException {
			int eventType = parser.next();
			boolean reachedEndOfMenu = false;
			String tagName;
			while (!reachedEndOfMenu) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					tagName = parser.getName();
					if (tagName.equals(XML_GROUP)) {
						resolveAndAddGroup(attrs);
					} else if (tagName.equals(XML_ITEM)) {
						resolveAndAddItem(attrs);
					}
					break;
				case XmlPullParser.END_TAG:
					tagName = parser.getName();
					if (tagName.equals(XML_MENU)) {
						reachedEndOfMenu = true;
					}
					break;
				default:
					break;
				}

				eventType = parser.next();
			}

			return new InflatedView(resolvedBindingAttributesForChildViews, errors);
		}

		private void resolveAndAddGroup(AttributeSet attrs) {
			Map<String, String> bindingAttributes = bindingAttributeParser.parse(attrs);
			if (!bindingAttributes.isEmpty()) {
				int groupId = readId();
				MenuItemGroup group = new MenuItemGroup(menu, groupId);

				resolveAndAdd(new PendingAttributesForViewImpl(group, bindingAttributes));
			}
		}

		private int readId() {
			int id = parser.getAttributeResourceValue(ANDROID_NAMESPACE, "id", NO_ID);
			if (NO_ID == id) {
				String tagName = parser.getName();
				throw new RuntimeException("android:id is required for menu '" + tagName + "' when apply binding");
			}
			return id;
		}

		private void resolveAndAdd(PendingAttributesForView pendingAttributesForView) {
			ViewResolutionResult viewResolutionResult = bindingAttributeResolver.resolve(pendingAttributesForView);
			viewResolutionResult.addPotentialErrorTo(errors);
			resolvedBindingAttributesForChildViews.add(viewResolutionResult.getResolvedBindingAttributes());
		}

		private void resolveAndAddItem(AttributeSet attrs) {
			Map<String, String> bindingAttributes = bindingAttributeParser.parse(attrs);
			if (!bindingAttributes.isEmpty()) {
				int itemId = readId();
				MenuItem menuItem = menu.findItem(itemId);

				resolveAndAdd(new PendingAttributesForViewImpl(menuItem, bindingAttributes));
			}
		}
	}
}
