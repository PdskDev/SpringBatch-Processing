package com.nadetdev.springbatch.controller;


import java.util.List;

import com.nadetdev.springbatch.request.JobParamsRequest;
import com.nadetdev.springbatch.service.JobService;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job")
public class JobController {
	
	@Autowired
	JobService jobservice;
	
	@GetMapping("/start/{jobName}")
	public String startJob(@PathVariable String jobName, @RequestBody List<JobParamsRequest> jobParamsRequestList) 
			throws JobExecutionAlreadyRunningException, 
	JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException  {
		
		jobservice.startJob(jobName, jobParamsRequestList);
		
		return "Job Strated...";
		
		
	}

}
