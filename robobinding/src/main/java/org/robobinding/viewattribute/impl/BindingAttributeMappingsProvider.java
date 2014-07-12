package org.robobinding.viewattribute.impl;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public interface BindingAttributeMappingsProvider<T extends View> {
    InitailizedBindingAttributeMappings<T> createBindingAttributeMappings();
}
