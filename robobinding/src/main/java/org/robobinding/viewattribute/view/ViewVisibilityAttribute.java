package org.robobinding.viewattribute.view;

import org.robobinding.viewattribute.MultiTypePropertyViewAttributeConfig;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewVisibilityAttribute extends AbstractVisibilityAttribute {

    @Override
    public void initialize(MultiTypePropertyViewAttributeConfig<View> config) {
        super.initialize(config);

        this.visibility = new ViewVisibility(config.getView());
    }
}
