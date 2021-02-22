package org.mugur.florin.monitoring;

import org.mugur.florin.monitoring.annotations.Job;
import org.mugur.florin.monitoring.domain.JobDetails;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JobRepository {

    private final Map<String, JobDetails> jobs = new HashMap<>();

    public JobDetails getJobDetails(Job job) {
        String jobName = job.name();
        return jobs.computeIfAbsent(jobName, name -> createJobDetails(name, job.rateSeconds()));
    }

    private JobDetails createJobDetails(String name, String rateSeconds) {
        JobDetails details = new JobDetails(name);
        details.setJobRateProperty(rateSeconds);

        return details;
    }

}
