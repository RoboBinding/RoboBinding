package org.robobinding;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.robobinding.util.Preconditions.checkValidResourceId;

import org.robobinding.presentationmodel.DialogPresentationModel;

import android.app.Dialog;
import android.view.View;
import android.view.Window;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class DialogBinder {
    private final Dialog dialog;
    private final BinderImplementor binderImplementor;

    public DialogBinder(Dialog dialog, BinderImplementor binderImplementor) {
	this.dialog = dialog;
	this.binderImplementor = binderImplementor;
    }

    public void inflateAndBind(int layoutId, Object presentationModel) {
	checkValidResourceId(layoutId, "invalid layoutId '" + layoutId + "'");
	checkNotNull(presentationModel, "presentationModel must not be null");
	
	if (presentationModel instanceof DialogPresentationModel) {
	    DialogPresentationModel dialogPresentationModel = (DialogPresentationModel) presentationModel;

	    if (dialogPresentationModel.getTitle() == null)
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    else
		dialog.setTitle(dialogPresentationModel.getTitle());
	}

	View rootView = binderImplementor.inflateAndBind(layoutId, presentationModel);
	dialog.setContentView(rootView);
    }
}
