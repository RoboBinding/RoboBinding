package org.robobinding.property;

import java.text.MessageFormat;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ObservableBeanWrap implements ObservableBean {
    private final Object bean;
    
    public ObservableBeanWrap(Object bean) {
	this.bean = bean;
    }
    
    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	checkObservable(propertyName);
	asObservableBean().addPropertyChangeListener(propertyName, listener);
    }

    private void checkObservable(String propertyName) {
        if (!isObservable()) {
            throw new RuntimeException(MessageFormat.format(
        	    "You are binding to property '{0}' but presentation model '{1}' is not observable. " 
        		    + "You either have to annotate your presentation model with @PresentationModel" 
        		    + " or implement interface {2}", propertyName, getBeanClassName(), ObservableBean.class.getName()));
        }	
    }
    
    private boolean isObservable() {
        return bean instanceof ObservableBean;
    }

    @Override
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	if (isObservable()) {
	    asObservableBean().removePropertyChangeListener(propertyName, listener);
	}
    }

    private ObservableBean asObservableBean() {
	return (ObservableBean) bean;
    }

    private String getBeanClassName() {
	return bean.getClass().getName();
    }

}
