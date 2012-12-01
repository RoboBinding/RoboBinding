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
package sample.robobinding;

import sample.robobinding.activity.HomeActivity;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractSampleAppTest extends ActivityInstrumentationTestCase2<HomeActivity>
{
	protected Solo solo;

	public AbstractSampleAppTest()
	{
		super("sample.robobinding", HomeActivity.class);
	}
	
	protected void setUp() throws Exception
	{
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
		solo.finishOpenedActivities();
	}
	
	protected void clickOnButtonWithLabel(String label)
	{
		solo.clickOnButton(label);
	}

	protected void clickOnButtonWithLabel(int resId)
	{
		clickOnButtonWithLabel(getString(resId));
	}
	
	protected String getString(int resId)
	{
		return getActivity().getString(resId);
	}
}
