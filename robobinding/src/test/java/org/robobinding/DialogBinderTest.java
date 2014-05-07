package org.robobinding;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.presentationmodel.DialogPresentationModel;

import android.app.Dialog;
import android.view.View;
import android.view.Window;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class DialogBinderTest {
    private BinderImplementor binderImplementor;
    private Dialog dialog;
    private int layoutId;

    @Before
    public void setUp() {
	binderImplementor = mock(BinderImplementor.class);
	layoutId = 0;
	dialog = mock(Dialog.class);
    }

    @Test
    public void whenInflateAndBind_thenSetContentViewShouldBeSetToResultView() {
	Object presentationModel = new Object();
	View resultView = mock(View.class);
	when(binderImplementor.inflateAndBind(layoutId, presentationModel)).thenReturn(resultView);

	inflateAndBind(presentationModel);

	verify(dialog).setContentView(resultView);
    }

    @Test
    public void givenADialogPresentationModel_whenInflateAndBind_thenTitleOfDialogShouldEqualTitleProperty() {
	MockDialogPresentationModelWithTitle dialogPresentationModelWithTitle = new MockDialogPresentationModelWithTitle();

	inflateAndBind(dialogPresentationModelWithTitle);

	verify(dialog).setTitle(dialogPresentationModelWithTitle.getTitle());
    }

    @Test
    public void givenADialogPresentationModelWithANullTitleProperty_whenInflateAndBind_thenTitleOfDialogShouldBeGone() {
	MockDialogPresentationModelWithNullTitle dialogPresentationModelWithNullTitle = new MockDialogPresentationModelWithNullTitle();

	inflateAndBind(dialogPresentationModelWithNullTitle);

	verify(dialog).requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    private void inflateAndBind(Object presentationModel) {
	DialogBinder dialogBinder = new DialogBinder(dialog, binderImplementor);
	dialogBinder.inflateAndBind(layoutId, presentationModel);
    }

    public static class MockDialogPresentationModelWithTitle implements DialogPresentationModel {
	public String getTitle() {
	    return "dialogTitle";
	}
    }

    public static class MockDialogPresentationModelWithNullTitle implements DialogPresentationModel {
	public String getTitle() {
	    return null;
	}
    }
}
