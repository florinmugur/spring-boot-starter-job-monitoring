package org.mugur.florin.monitoring.domain;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class JobExecutions {

    private List<JobExecution> executions = new ArrayList<>();

    private int totalExecutions;

    private Instant lastProgress;

    private JobExecution longestExecution;

    public void setLastProgress(Instant lastProgress) {
        this.lastProgress = lastProgress;
    }

    public Instant getLastProgress() {
        return lastProgress;
    }

    public void setStart(Instant start) {
        JobExecution execution = new JobExecution();
        execution.setStart(start);
        executions.add(0, execution);
    }

    public void setFinished(Instant finish) {
        JobExecution execution = executions.get(0);
        execution.setFinish(finish);

        Duration duration = execution.getDuration();
        if (longestExecution == null || duration.compareTo(longestExecution.getDuration()) > 0) {
            longestExecution = execution;
        }
    }

    public List<JobExecution> getExecutions() {
        return executions;
    }

    public JobExecution getLongestExecution() {
        return longestExecution;
    }

    public int getTotalExecutions() {
        return totalExecutions;
    }


}
