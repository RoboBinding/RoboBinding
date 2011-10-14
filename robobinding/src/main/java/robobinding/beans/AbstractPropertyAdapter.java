package robobinding.beans;

import java.beans.PropertyChangeListener;

import robobinding.presentationmodel.ObservableProperties;

abstract class AbstractPropertyAdapter<T> implements PropertyAdapter<T>
{
	protected final Object bean;
	public AbstractPropertyAdapter(Object bean)
	{
		this.bean = bean;
	}

	@Override
	public void addValueChangeListener(PropertyChangeListener listener)
	{
		ObservableProperties observableBean = getObservableBean();
		observableBean.addPropertyChangeListener(getPropertyName(), listener);
	}

	@Override
	public void removeValueChangeListener(PropertyChangeListener listener)
	{
		if(bean instanceof ObservableProperties)
		{
			ObservableProperties observableBean = (ObservableProperties)bean;
			observableBean.removePropertyChangeListener(getPropertyName(), listener);
		}
	}

	private ObservableProperties getObservableBean()
	{
		if(bean instanceof ObservableProperties)
		{
			ObservableProperties observableBean = (ObservableProperties)bean;
			return observableBean;
		}
		throw new RuntimeException("The property changes of '"+bean.getClass().getName()+"' can not be observed, as it is not a subclass of 'ObservableProperties'");
	}
	protected abstract String getPropertyName();
}