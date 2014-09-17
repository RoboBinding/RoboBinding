package org.robobinding;

import android.util.AttributeSet;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface ViewCreationListener {
	void onViewCreated(View view, AttributeSet attrs);
}