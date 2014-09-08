/**
 * NOTICE: THE FILE HAS BEEN MODIFIED TO SUIT THE NEEDS OF THE PROJECT.
 * 
 * original source code is from: http://www.sixlegs.com/blog/java/exclude-javadoc-tag.html
 */

package org.robobinding.doctaglet;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import com.sun.javadoc.Doc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.RootDoc;
import com.sun.tools.doclets.standard.Standard;
import com.sun.tools.javadoc.Main;

/**
 * filters out classes with @hide in the javadoc</li>
 */
public class Doclet {
    private static final String TAG_HIDE = "@hide";

    public static void main(String[] args) {
	String name = Doclet.class.getName();
	List<String> argList = new ArrayList<String>();
	argList.add("-taglet");
	argList.add(EventTaglet.class.getName());
	argList.add("-taglet");
	argList.add(PropTaglet.class.getName());
	for(String arg : args) {
	    argList.add(arg);
	    System.out.println("Doclet error:" + arg);
	}
	Main.execute(name, name, args);
    }

    public static boolean validOptions(String[][] options, DocErrorReporter reporter) throws java.io.IOException {
	return Standard.validOptions(options, reporter);
    }

    public static LanguageVersion languageVersion() {
	return Standard.languageVersion();
    }

    public static int optionLength(String option) {
	return Standard.optionLength(option);
    }

    public static boolean start(RootDoc root) throws java.io.IOException {
	return Standard.start((RootDoc) process(root, RootDoc.class));
    }

    private static boolean exclude(Doc doc) {
	if (doc.tags(TAG_HIDE).length > 0) {
	    return true;
	} else if ((doc instanceof ProgramElementDoc) 
		&& (((ProgramElementDoc) doc).containingPackage().tags(TAG_HIDE).length > 0)) {
	    return true;
	} else {
	    return false;
	}
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static Object process(Object obj, Class expect) {
	if (obj == null)
	    return null;
	Class cls = obj.getClass();
	if (cls.getName().startsWith("com.sun.")) {
	    return Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), new ExcludeHandler(obj));
	} else if (obj instanceof Object[]) {
	    Class componentType = expect.getComponentType();
	    Object[] array = (Object[]) obj;
	    List list = new ArrayList(array.length);
	    for (int i = 0; i < array.length; i++) {
		Object entry = array[i];
		if ((entry instanceof Doc) && exclude((Doc) entry))
		    continue;
		list.add(process(entry, componentType));
	    }
	    return list.toArray((Object[]) Array.newInstance(componentType, list.size()));
	} else {
	    return obj;
	}
    }

    private static class ExcludeHandler implements InvocationHandler {
	private Object target;

	public ExcludeHandler(Object target) {
	    this.target = target;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	    if (args != null) {
		String methodName = method.getName();
		if (methodName.equals("compareTo") || methodName.equals("equals") || methodName.equals("overrides")
			|| methodName.equals("subclassOf")) {
		    args[0] = unwrap(args[0]);
		}
	    }
	    try {
		return process(method.invoke(target, args), method.getReturnType());
	    } catch (InvocationTargetException e) {
		throw e.getTargetException();
	    }
	}

	private Object unwrap(Object proxy) {
	    if (proxy instanceof Proxy)
		return ((ExcludeHandler) Proxy.getInvocationHandler(proxy)).target;
	    return proxy;
	}
    }

}