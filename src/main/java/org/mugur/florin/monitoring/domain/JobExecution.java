package org.mugur.florin.monitoring.domain;

import java.time.Duration;
import java.time.Instant;

public class JobExecution {

    private Instant start;
    private Instant finish;
    private Duration duration;
    private String error;

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getStart() {
        return start;
    }

    public void setFinish(Instant finish) {
        this.finish = finish;

        duration = Duration.between(start, this.finish);
    }

    public Instant getFinish() {
        return finish;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public Duration getDuration() {
        return duration;
    }
}
