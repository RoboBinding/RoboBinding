package org.robobinding.property;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface PropertySupply {
	
	SimpleProperty tryToCreateProperty(String propertyName);

	DataSetProperty tryToCreateDataSetProperty(String propertyName);
}
