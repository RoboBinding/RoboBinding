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
package org.robobinding.viewattribute.ratingbar;

import org.robobinding.viewattribute.AbstractAttributeProvider;
import org.robobinding.viewattribute.ratingbar.RatingAttribute.FloatRatingAttribute;
import org.robobinding.viewattribute.ratingbar.RatingAttribute.IntegerRatingAttribute;

import android.widget.RatingBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RatingBarAttributeProvider extends AbstractAttributeProvider<RatingBar>
{
	@Override
	protected void populateViewAttributeMappings(ViewAttributeMappings<RatingBar> mappings)
	{
		TypePropertyMap typePropertyMapping = new TypePropertyMap("rating");
		typePropertyMap.add(Integer.class, IntegerRatingAttribute.class);
		typePropertyMap.add(Float.class, FloatRatingAttribute.class);
		
		mappings.addTypePropertyMapping("rating", typePropertyMapping);
		
		mappings.addPropertyMapping("numStars", NumStarsAttribute.class);
		
		mappings.addCommandMapping("onRatingBarChange", OnRatingBarChangeAttribute.class);
	}
//	private static final String ON_RATING_BAR_CHANGE = "onRatingBarChange";
//	private static final String NUM_STARS = "numStars";
//	private static final String RATING = "rating";
//	
//	private static class ViewAttributeMappings
//	{
//		private List<ViewAttributeMapping> viewAttributeMappings = Lists.newArrayList();
//		public void add(ViewAttributeMapping... mappings)
//		{
//			for (ViewAttributeMapping mapping : mappings)
//				viewAttributeMappings.add(mapping);
//		}
//		public void process(BindingAttributeResolver bindingAttributeResolver)
//		{
//			for (ViewAttributeMapping mapping : viewAttributeMappings)
//				mapping.initializeAndResolveAttribute(bindingAttributeResolver);
//		}
//	}
//	
//	private static class ViewAttributeMapping<T extends View>
//	{
//		private String attributeName;
//		private Class<? extends ViewAttribute> viewAttributeClass;
//		public ViewAttributeMapping(String attributeName, Class<? extends ViewAttribute> viewAttributeClass)
//		{
//			this.attributeName = attributeName;
//			this.viewAttributeClass = viewAttributeClass;
//		}
//		public void initializeAndResolveAttribute(T view, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews)
//		{
//			try
//			{
//				ViewAttribute<RatingBar> viewAttribute = viewAttributeClass.newInstance();
//				String attributeValue = bindingAttributeResolver.resolveAttribute(attributeName, viewAttribute);
//				
//				viewAttribute.setView(view);
//				viewAttribute.setAttributeValue(attributeValue);
//				
//				if (viewAttribute instanceof PropertyViewAttribute)
//					((PropertyViewAttribute)viewAttribute).setPreInitializeViews(preInitializeViews);
//				
//			} catch (InstantiationException e)
//			{
//				// TODO Give your view attribute a default constructor!
//				e.printStackTrace();
//			} catch (IllegalAccessException e)
//			{
//				// TODO Make your view attribute class public!
//				e.printStackTrace();
//			}			
//		}
//	}
//	
//	private ViewAttributeMapping newMapping(String attributeName, Class<? extends ViewAttribute> viewAttributeClass)
//	{
//		return new ViewAttributeMapping(attributeName, viewAttributeClass);
//	}
//	
//	public void populateViewAttributeMappings(ViewAttributeMappings mappings)
//	{
//		mappings.add(newMapping(RATING, RatingAttribute.class), 
//				newMapping(NUM_STARS, NumStarsAttribute.class), 
//				newMapping(ON_RATING_BAR_CHANGE, OnRatingBarChangeAttribute.class));
//	}
//	
//	@Override
//	public void resolveSupportedBindingAttributes(RatingBar ratingBar, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews)
//	{
//		if (bindingAttributeResolver.hasAttribute(RATING))
//		{
//			String attributeValue = bindingAttributeResolver.findAttributeValue(RATING);
//			bindingAttributeResolver.resolveAttribute(RATING, new RatingAttribute(ratingBar, attributeValue, preInitializeViews));
//		}
//		if (bindingAttributeResolver.hasAttribute(NUM_STARS))
//		{
//			String attributeValue = bindingAttributeResolver.findAttributeValue(NUM_STARS);
//			bindingAttributeResolver.resolveAttribute(NUM_STARS, new NumStarsAttribute(ratingBar, attributeValue, preInitializeViews));
//		}
//		if (bindingAttributeResolver.hasAttribute(ON_RATING_BAR_CHANGE))
//		{
//			String attributeValue = bindingAttributeResolver.findAttributeValue(ON_RATING_BAR_CHANGE);
//			bindingAttributeResolver.resolveAttribute(ON_RATING_BAR_CHANGE, new OnRatingBarChangeAttribute(ratingBar, attributeValue));
//		}
//	}

}
