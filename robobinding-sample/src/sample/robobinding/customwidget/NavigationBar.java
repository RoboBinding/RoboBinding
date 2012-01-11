/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package sample.robobinding.customwidget;

import org.robobinding.binder.Binder;
import org.robobinding.customwidget.BindableView;
import org.robobinding.customwidget.CustomBindingAttributeMappings;
import org.robobinding.viewattribute.view.OnClickAttribute;

import sample.robobinding.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class NavigationBar extends RelativeLayout implements BindableView<NavigationBar>
{
	private final Button leftButton;
	private final Button rightButton;
	
	public NavigationBar(Context context, AttributeSet attrs)
	{
		this(context, attrs, R.layout.navigation_bar);
	}

	public NavigationBar(Context context, AttributeSet attrs, int layoutId)
	{
		super(context, attrs);
		
		leftButton = (Button)findViewById(R.id.left_button);
		rightButton = (Button)findViewById(R.id.right_button);
		
		Binder.bindViewAndAttachToRoot(context, layoutId, this, this);
	}
	
	@Override
	public void mapBindingAttributes(CustomBindingAttributeMappings<NavigationBar> mappings)
	{
		mappings.mapCommandAttribute(leftButton, OnClickAttribute.class, "leftButtonAction");
		mappings.mapCommandAttribute(rightButton, OnClickAttribute.class, "rightButtonAction");
	}

}
