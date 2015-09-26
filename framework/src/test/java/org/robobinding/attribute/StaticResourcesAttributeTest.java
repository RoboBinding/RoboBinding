package org.robobinding.attribute;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.robobinding.attribute.MockResourcesBuilder.aContextOfResources;
import static org.robobinding.attribute.StaticResourceTest.resourceValue;

import java.util.List;

import org.junit.Test;

import com.google.android.collect.Lists;
import com.google.common.base.Joiner;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class StaticResourcesAttributeTest {
	@Test
	public void shouldGetResourceIds() {
		String name1 = "layoutX", type1 = "layout";
		String name2 = "layoutY", type2 = "layout", package2 = "android";
		
		StaticResourcesAttribute attribute = new StaticResourcesAttribute("name", 
				resourcesValue(resourceValue(name1, type1), resourceValue(name2, type2, package2)));
		
		MockResourcesBuilder aContextOfResources = aContextOfResources().withDefaultPackage();
		int expectedResourceId1 = aContextOfResources.declareResource(name1, type1);
		int expectedResourceId2 = aContextOfResources.declareResource(name2, type2, package2);
		List<Integer> expectedResourceIds = Lists.newArrayList(expectedResourceId1, expectedResourceId2);

		List<Integer> resourceIds = attribute.getResourceIds(aContextOfResources.build());
		assertThat(resourceIds, equalTo(expectedResourceIds));
	}
	
	private String resourcesValue(String... resourceValues) {
		return "[" + Joiner.on(",").join(resourceValues) +"]";
	}
}
