package org.rays3d.integrator.integrators;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Named {

	/**
	 * The name to be given to the type
	 * 
	 * @return
	 */
	String value();
}
