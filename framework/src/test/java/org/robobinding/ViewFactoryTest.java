package org.robobinding;

/*
 import mockit.Injectable;
 import mockit.NonStrictExpectations;
 import mockit.Verifications;
 import mockit.integration.junit4.JMockit;

 import org.junit.Test;
 import org.junit.runner.RunWith;

 import android.util.AttributeSet;
 import android.view.LayoutInflater;
 import android.view.View;
 */
/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
/*
 * @RunWith(JMockit.class) public class ViewFactoryTest {
 * 
 * @Injectable LayoutInflater layoutInflater;
 * 
 * @Test(expected = RuntimeException.class) public void
 * whenCreateAViewThrowsAClassNotFoundException_thenPropagateAsRuntimeException
 * () throws ClassNotFoundException { new NonStrictExpectations() {{
 * layoutInflater.createView(anyString, anyString, (AttributeSet)any); result =
 * new ClassNotFoundException(); }};
 * 
 * ViewFactory viewFactory = new ViewFactory(layoutInflater, null, null);
 * 
 * viewFactory.onCreateView(null, null, null); }
 * 
 * @Test public void
 * givenViewCreationListenerRegistered_whenCreateView_thenListenerShouldBeCorrectlyNotified
 * ( final @Injectable ViewCreationListener listener, final @Injectable View
 * view, final @Injectable ViewNameResolver viewNameResolver, final @Injectable
 * AttributeSet attrs) throws ClassNotFoundException {
 * 
 * new NonStrictExpectations() {{ layoutInflater.createView(anyString,
 * anyString, (AttributeSet)any); result = view; }};
 * 
 * ViewFactory viewFactory = new ViewFactory(layoutInflater, viewNameResolver,
 * listener);
 * 
 * viewFactory.onCreateView(null, null, attrs);
 * 
 * new Verifications() {{ listener.onViewCreated(view, attrs); times = 1; }}; }
 * }
 */