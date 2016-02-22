package org.robobinding.codegen.presentationmodel;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class OrderedPresentationModelInfo extends PresentationModelInfo {
	public OrderedPresentationModelInfo(PresentationModelInfo presentationModelInfo) {
		super(presentationModelInfo.getPresentationModelTypeName(), 
			presentationModelInfo.getPresentationModelObjectTypeName(),
			newTreeSet(new PropertyInfoComparator(), presentationModelInfo.properties()), 
			newTreeSet(new DataSetPropertyInfoComparator(), presentationModelInfo.dataSetProperties()), 
			newTreeSet(new PropertyDependencyInfoComparator(), presentationModelInfo.propertyDependencies()), 
			newTreeSet(new EventMethodInfoComparator(), presentationModelInfo.eventMethods()),
			presentationModelInfo.shoulPreinitializeViews());
	}
	
	private static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator, Collection<? extends E> elements) {
		TreeSet<E> set = Sets.newTreeSet(comparator);
		set.addAll(elements);
		return set;
	}
}
