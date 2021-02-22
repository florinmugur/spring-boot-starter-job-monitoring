package org.mugur.florin.monitoring.annotations;

public @interface JobProgress {

    String jobName();

    int expectedRateSeconds() default 300;
}
