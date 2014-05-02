package org.robobinding.attribute;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.robobinding.viewattribute.BindingAttributeValues;

import android.content.Context;
import android.content.res.Resources;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockResourcesBuilder {
    private Context mockContext;
    private Resources mockResources;

    public static MockResourcesBuilder aContextOfResources() {
	return new MockResourcesBuilder();
    }

    private MockResourcesBuilder() {
	mockContext = mock(Context.class);

	mockResources = mock(Resources.class);
	when(mockContext.getResources()).thenReturn(mockResources);
    }

    public MockResourcesBuilder withDefaultPackage() {
	return withDefaultPackage(BindingAttributeValues.DEFAULT_RESOURCE_PACKAGE);
    }

    public MockResourcesBuilder withDefaultPackage(String packageName) {
	when(mockContext.getPackageName()).thenReturn(packageName);
	return this;
    }

    public int declareLayoutResource(String resourceName) {
	return declareResource(resourceName, BindingAttributeValues.LAYOUT_RESOURCE_TYPE, BindingAttributeValues.DEFAULT_RESOURCE_PACKAGE);
    }

    public int declareResource(String resourceName, String resourceType, String resourcePackage) {
	int layoutId = nextResourceId();
	when(mockResources.getIdentifier(resourceName, resourceType, resourcePackage)).thenReturn(layoutId);
	return layoutId;
    }

    private int resourceIdCounter = 1;

    private int nextResourceId() {
	return resourceIdCounter++;
    }

    public Context build() {
	return mockContext;
    }
}
