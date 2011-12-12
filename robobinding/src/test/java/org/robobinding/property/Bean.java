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
package org.robobinding.property;

import java.util.List;

import org.robobinding.ItemPresentationModel;
import org.robobinding.itempresentationmodel.TypedCursor;



/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class Bean
{
	public static final String READ_ONLY_PROPERTY = "readOnlyProperty";
	public static final String WRITE_ONLY_PROPERTY = "writeOnlyProperty";
	public static final String ANNOTATED_PROPERTY = "annotatedProperty";
	public static final String NOT_ANNOTATED_PROPERTY = "notAnnotatedProperty";
	public static final String PROPERTY = "property";
	public static final String READ_WRITE_PROPERTY = "readWriteProperty";
	public static final String LIST_DATA_SET_PROPERTY = "listDataSetProperty";
	public static final String CURSOR_DATA_SET_PROPERTY = "cursorDataSetProperty";
	public static final String UNSUPPORTED_DATA_SET_PROPERTY = "UnsupportedDataSetProperty";
	public static final String DATA_SET_PROPERTY_WITHOUT_ITEM_PRESENTATION_MODEL_ANNOTATION = "DataSetPropertyWithoutItemPresentationModelAnnotation";
	
	public boolean writeOnlyPropertyValue = true;
	public boolean getReadOnlyProperty()
	{
		return true;
	}
	public void setWriteOnlyProperty(boolean newValue)
	{
		writeOnlyPropertyValue = newValue;
	}
	@PropertyAnnotation
	public boolean getAnnotatedProperty()
	{
		return true;
	}
	public boolean getNotAnnotatedProperty()
	{
		return true;
	}
	public boolean getProperty()
	{
		return true;
	}
	public boolean getReadWriteProperty()
	{
		return true;
	}
	public void setReadWriteProperty(boolean b)
	{
	}
	@ItemPresentationModel(ItemPresentationModelImpl.class)
	public List<Object> getListDataSetProperty()
	{
		return null;
	}
	@ItemPresentationModel(ItemPresentationModelImpl.class)
	public TypedCursor<Object> getCursorDataSetProperty()
	{
		return null;
	}
	@ItemPresentationModel(ItemPresentationModelImpl.class)
	public Object[] getUnsupportedDataSetProperty()
	{
		return null;
	}
	public List<Object> getDataSetPropertyWithoutItemPresentationModelAnnotation()
	{
		return null;
	}
}
