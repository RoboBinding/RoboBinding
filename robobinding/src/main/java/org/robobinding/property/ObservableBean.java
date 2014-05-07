package org.robobinding.property;

import static com.google.common.base.Preconditions.checkState;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class ObservableBean implements ObservableProperties {
    private Object bean;

    public ObservableBean(Object bean) {
	this.bean = bean;
    }

    public Object getBean() {
	return bean;
    }

    public Class<?> getBeanClass() {
	return bean.getClass();
    }

    public String getBeanClassName() {
	return bean.getClass().getName();
    }

    @Override
    public void addPropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener) {
	checkObservable(propertyName);
	asObservableBean().addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public void removePropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener) {
	if (isObservable()) {
	    asObservableBean().removePropertyChangeListener(propertyName, listener);
	}
    }

    private boolean isObservable() {
	return bean instanceof ObservableProperties;
    }

    private ObservableProperties asObservableBean() {
	return (ObservableProperties) bean;
    }

    private void checkObservable(String propertyName) {
	checkState(
		isObservable(),
		"You are binding to property '"
			+ propertyName
			+ "' but presentation model '"
			+ bean.getClass().getName()
			+ "' is not observable. You either have to annotate your presentation model with @PresentationModel "
			+ "or implement interface ObservableProperties");
    }
}
