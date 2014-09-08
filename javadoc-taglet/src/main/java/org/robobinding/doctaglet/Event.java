package org.robobinding.doctaglet;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class Event {
    private String name;
    private String className;
    public Event(String name, String className) {
	this.name = name;
	this.className = className;
    }
    public String getName() {
        return name;
    }
    public String getClassName() {
        return className;
    }
}
