package org.robobinding.viewattribute.impl;

import org.robobinding.viewattribute.BindingAttributeMappings;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface BindingAttributeMappingsWithCreate<T extends View> extends BindingAttributeMappings<T> {
    InitailizedBindingAttributeMappings<T> createInitailizedBindingAttributeMappings();
}
