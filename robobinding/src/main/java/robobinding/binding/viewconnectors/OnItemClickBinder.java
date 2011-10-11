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
package robobinding.binding.viewconnectors;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import robobinding.presentationmodel.ItemClickEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class OnItemClickBinder
{
	public OnItemClickBinder(AdapterView<?> adapterView, String commandName, final Object presentationModel)
	{
//		try
//		{
//			final Method command = presentationModel.getClass().getMethod(commandName, ItemClickEvent.class);
//			
//			adapterView.setOnItemClickListener(new OnItemClickListener() {
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//				{
//					ItemClickEvent itemClickEvent = new ItemClickEvent(parent, view, position, id);
//					try
//					{
//						command.invoke(presentationModel, itemClickEvent);
//					} 
//					catch (IllegalArgumentException e)
//					{
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} 
//					catch (IllegalAccessException e)
//					{
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} 
//					catch (InvocationTargetException e)
//					{
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			});
//		} 
//		catch (SecurityException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		catch (NoSuchMethodException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
