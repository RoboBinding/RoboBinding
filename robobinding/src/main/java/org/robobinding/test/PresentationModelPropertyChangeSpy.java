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
package org.robobinding.test;

import org.robobinding.property.PresentationModelPropertyChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PresentationModelPropertyChangeSpy implements PresentationModelPropertyChangeListener {
    private boolean propertyChanged;
    private int propertyChangedCount;

    PresentationModelPropertyChangeSpy() {
    }

    @Override
    public void propertyChanged() {
	propertyChanged = true;
	propertyChangedCount++;
    }

    public boolean isPropertyChanged() {
	return propertyChanged;
    }

    public int getPropertyChangedCount() {
	return propertyChangedCount;
    }

}
