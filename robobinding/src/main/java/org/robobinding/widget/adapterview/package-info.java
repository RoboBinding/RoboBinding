/**
 * Binding implementations and Supported Binding Attributes for
 * <a href="http://developer.android.com/reference/android/widget/AdapterView.html">android.widget.AdapterView</a>.
 *
 * @prop emptyViewLayout; Static resource; no
 * @prop emptyViewPresentationModel; Object; no
 * @prop emptyViewVisibility; Boolean, Integer; no
 * @prop itemLayout; Integer, Static resource; no
 * @prop itemMapping; Static string. Used for specifying binding information for the itemLayout.
 *  Syntax: [widget1_in_itemLayout.attributeName1:{property1_of_itemPresentationModel}, ...,
 *  widgetN_in_itemLayout.attributeNameN:{propertyN_of_itemPresentationModel}]; Not bindable
 * @prop source; Array, List, {@link org.robobinding.itempresentationmodel.TypedCursor}; no
 * @prop selectedItemPosition; Integer; yes
 *
 *
 * @event onItemClick; {@link org.robobinding.widget.adapterview.ItemClickEvent}
 * @event onItemSelected; {@link org.robobinding.widget.adapterview.ItemClickEvent}
 *
 */
package org.robobinding.widget.adapterview;