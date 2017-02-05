package com.sho.renaissance.batch.tasklet;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class SkipStepOnRestartDecider implements JobExecutionDecider {
	
	private Boolean restartFlag = false;
	
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        if (restartFlag) {
            return FlowExecutionStatus.FAILED;
        }
        else {
            return FlowExecutionStatus.COMPLETED;
        }
    }
    
    public void setRestartFlag(Boolean restartFlag){
    	this.restartFlag = restartFlag; 
    }
    
    public Boolean getRestartFlag(){
    	return this.restartFlag;
    }
}