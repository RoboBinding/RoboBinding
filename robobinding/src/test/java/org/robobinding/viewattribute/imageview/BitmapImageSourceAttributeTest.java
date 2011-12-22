/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package org.robobinding.viewattribute.imageview;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.AbstractTypeMappedOneWayPropertyAttributeTest;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class BitmapImageSourceAttributeTest extends AbstractTypeMappedOneWayPropertyAttributeTest<Bitmap, Drawable>
{
	private ImageView imageView;
	private Context context;
	
	@Before
	public void setUp()
	{
		imageView = new ImageView(context);
	}
	
	@Override
	protected Drawable getViewState()
	{
		return imageView.getDrawable();
	}

	@Override
	protected void populateBindingExpectations(TypeMappedBindingSamples<Bitmap, Drawable> bindingSamples)
	{
		context = new Activity();
		
		Bitmap bottomBarBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bottom_bar);
		Bitmap titleBarBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.title_bar);
		
		Drawable bottomBarDrawable = new BitmapDrawable(bottomBarBitmap);
		Drawable titleBarDrawable = new BitmapDrawable(titleBarBitmap);
		
		bindingSamples.addMapping(bottomBarBitmap, bottomBarDrawable);
		bindingSamples.addMapping(titleBarBitmap, titleBarDrawable);
	}

	@Override
	protected AbstractPropertyViewAttribute<Bitmap> newAttributeInstance(String bindingAttributeValue)
	{
		ImageSourceAttribute imageSourceAttribute = new ImageSourceAttribute(imageView, bindingAttributeValue, true);
		return imageSourceAttribute.new BitmapImageSourceAttribute();
	}

}
