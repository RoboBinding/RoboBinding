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
package org.robobinding.viewattribute.adapterview;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class MockAdapterViewListeners extends AdapterViewListeners {
    private final AdapterView<?> adapterView;
    public boolean addOnItemSelectedListenerInvoked;
    public boolean addOnItemClickListenerInvoked;

    public MockAdapterViewListeners(AdapterView<?> adapterView) {
	super(adapterView);
	this.adapterView = adapterView;
    }

    @Override
    public void addOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
	adapterView.setOnItemSelectedListener(onItemSelectedListener);
	addOnItemSelectedListenerInvoked = true;
    }

    @Override
    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
	adapterView.setOnItemClickListener(onItemClickListener);
	addOnItemClickListenerInvoked = true;
    }

}
