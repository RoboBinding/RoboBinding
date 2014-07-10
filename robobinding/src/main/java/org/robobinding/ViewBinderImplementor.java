package org.robobinding;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Geovani de Souza
 */
public interface ViewBinderImplementor extends BinderImplementor {

	View bind(View view, Object presentationModel);
}
