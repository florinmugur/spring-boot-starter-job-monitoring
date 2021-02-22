package org.mugur.florin.monitoring.annotations;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.mugur.florin.monitoring.AbstractJob;
import org.mugur.florin.monitoring.JobRepository;
import org.mugur.florin.monitoring.domain.JobDetails;
import org.mugur.florin.monitoring.domain.JobExecutions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

@Aspect
@Component
public class JobAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final Clock clock;
    private final JobRepository repository;

    private final Map<String, Job> jobs = new HashMap<>();

    public JobAspect(Clock clock, JobRepository repository) {
        this.clock = clock;
        this.repository = repository;
    }

    @Around("@annotation(Job)")
    public Object aroundJob(ProceedingJoinPoint joinPoint) throws Throwable {
        Job job = getMethodAnnotation(joinPoint, Job.class);
        jobs.putIfAbsent(job.name(), job);

        JobDetails details = repository.getJobDetails(job);
        details.setEnabled(isEnabled(joinPoint.getThis()));
        details.setRunning(true);
        JobExecutions executions = details.getExecutions();
        executions.setStart(clock.instant());

        try {
            return joinPoint.proceed();
        } finally {
            details.setRunning(false);
            executions.setFinished(clock.instant());
        }
    }

    @Around("@annotation(JobProgress)")
    public Object aroundJobProgress(ProceedingJoinPoint joinPoint) throws Throwable {
        JobProgress progress = getMethodAnnotation(joinPoint, JobProgress.class);
        Job job = jobs.get(progress.jobName());
        if (job != null) {
            JobDetails details = repository.getJobDetails(job);
        } else {
            String message = format("Error while registering progress, no job found with name: %s", progress.jobName());
        }

        return joinPoint.proceed();
    }

    private Boolean isEnabled(Object jobService) {
        if (jobService instanceof AbstractJob) {
            AbstractJob job = (AbstractJob) jobService;
            return job.isEnabled();
        }

        return null;
    }

    private <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotation) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        return method.getAnnotation(annotation);
    }

}
