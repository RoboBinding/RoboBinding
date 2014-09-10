package org.robobinding.itempresentationmodel;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.robobinding.property.PropertyAccessor;
import org.robobinding.property.PropertyDescriptor;
import org.robobinding.property.PropertyUtils;

import android.database.AbstractCursor;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BeanCursor<T> extends AbstractCursor implements TypedCursor<T> {

    private final List<T> beans;
    private final Map<String, PropertyDescriptor> propertyDescriptorMap;

    BeanCursor(List<T> beans, Map<String, PropertyDescriptor> propertyDescriptorMap) {
	this.beans = beans;
	this.propertyDescriptorMap = propertyDescriptorMap;
    }

    @Override
    public int getCount() {
	return beans.size();
    }

    private transient volatile String[] propertyNamesCache;

    @Override
    public String[] getColumnNames() {
	if (propertyNamesCache == null) {
	    Set<String> propertyNames = propertyDescriptorMap.keySet();
	    propertyNamesCache = propertyNames.toArray(new String[0]);
	}
	return propertyNamesCache;
    }

    @Override
    public String getString(int column) {
	return (String) getColumnValue(column);
    }

    @Override
    public short getShort(int column) {
	return (Short) getColumnValue(column);
    }

    @Override
    public int getInt(int column) {
	return (Integer) getColumnValue(column);
    }

    @Override
    public long getLong(int column) {
	return (Long) getColumnValue(column);
    }

    @Override
    public float getFloat(int column) {
	return (Float) getColumnValue(column);
    }

    @Override
    public double getDouble(int column) {
	return (Double) getColumnValue(column);
    }

    @Override
    public boolean isNull(int column) {
	Object value = getColumnValue(column);
	return value == null;
    }

    private Object getColumnValue(int column) {
	Preconditions.checkArgument(column < getNumColumns(), "column '" + column + "' is out of bound");
	PropertyAccessor propertyAccesor = mapColumnToPropertyAccessor(column);
	return propertyAccesor.getValue();
    }

    private PropertyAccessor mapColumnToPropertyAccessor(int column) {
	String propertyName = mapColumnToPropertyName(column);
	PropertyDescriptor descriptor = propertyDescriptorMap.get(propertyName);
	
	PropertyAccessor propertyAccesor = new PropertyAccessor(getBean(), descriptor);
	return propertyAccesor;
    }

    private String mapColumnToPropertyName(int column) {
	return getColumnName(column);
    }

    private int getNumColumns() {
	return propertyDescriptorMap.size();
    }

    private Object getBean() {
	return beans.get(getPosition());
    }

    @Override
    public T getObjectAtPosition(int position) {
	Preconditions.checkArgument(position < getCount(), "Invalid requested position '" + position + "', as the cursor size is '" + getCount() + "'");
	return beans.get(position);
    }
    
    public static <T> BeanCursor<T> create(Collection<T> beans, Class<T> beanClass) {
	Preconditions.checkNotNull(beans, "beans can not be null");
	Preconditions.checkNotNull(beanClass, "beanClass can not be null");
	return new BeanCursor<T>(Lists.newArrayList(beans), PropertyUtils.getPropertyDescriptorMap(beanClass));
	
    }
}
