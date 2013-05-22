/**
 * Copyright 2013 Cheng Wei, Robert Taylor
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
package org.robobinding.viewattribute.listview;

import static android.widget.ListView.CHOICE_MODE_MULTIPLE;
import static android.widget.ListView.CHOICE_MODE_MULTIPLE_MODAL;
import static android.widget.ListView.CHOICE_MODE_NONE;
import static android.widget.ListView.CHOICE_MODE_SINGLE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;

import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ChoiceModeAttributeTest extends AbstractPropertyViewAttributeTest<ListView, ChoiceModeAttribute> {
    @Test
    public void whenValueModelUpdated_thenSetChoiceMode() {
	int[] choiceModes = { CHOICE_MODE_NONE, CHOICE_MODE_SINGLE, CHOICE_MODE_MULTIPLE, CHOICE_MODE_MULTIPLE_MODAL };
	for (int choiceMode : choiceModes) {
	    attribute.valueModelUpdated(choiceMode);

	    assertThat(view.getChoiceMode(), is(choiceMode));
	}
    }

}
