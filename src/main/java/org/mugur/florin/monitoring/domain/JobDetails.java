package org.mugur.florin.monitoring.domain;

import java.time.Instant;

public class JobDetails {

    private final String name;

    private final JobExecutions executions;

    private Boolean enabled;

    private Boolean running;

    private String jobRateProperty;

    private Instant lastProgress;

    public JobDetails(String name) {
        this.name = name;

        executions = new JobExecutions();
    }

    public void setJobRateProperty(String jobRateProperty) {
        this.jobRateProperty = jobRateProperty;
    }

    public String getJobRateProperty() {
        return jobRateProperty;
    }

    public JobExecutions getExecutions() {
        return executions;
    }

    public String getName() {
        return name;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public Boolean getRunning() {
        return running;
    }

    public Instant getLastProgress() {
        return lastProgress;
    }

    public void setLastProgress(Instant lastProgress) {
        this.lastProgress = lastProgress;
    }
}
