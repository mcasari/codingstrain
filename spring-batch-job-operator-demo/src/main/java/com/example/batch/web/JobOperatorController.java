package com.example.batch.web;

import org.springframework.batch.core.launch.JobOperator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobOperatorController {

    private final JobOperator jobOperator;

    public JobOperatorController(JobOperator jobOperator) {
        this.jobOperator = jobOperator;
    }

    @PostMapping("/{jobName}/start")
    public Long start(@PathVariable String jobName,
                      @RequestParam String params) throws Exception {
        return jobOperator.start(jobName, params);
    }

    @PostMapping("/{executionId}/stop")
    public void stop(@PathVariable Long executionId) throws Exception {
        jobOperator.stop(executionId);
    }

    @PostMapping("/{executionId}/restart")
    public Long restart(@PathVariable Long executionId) throws Exception {
        return jobOperator.restart(executionId);
    }
}
