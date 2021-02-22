package org.mugur.florin.monitoring.annotations;

public @interface Job {

    String name();

    String rateSeconds() default "";
}
