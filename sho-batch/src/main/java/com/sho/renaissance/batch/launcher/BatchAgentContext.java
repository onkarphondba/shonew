/**
 * 
 */
package com.sho.renaissance.batch.launcher;

import java.io.Serializable;
import java.util.Map;

/**Class to hold parameters of Job to be executed by AgentJavaProcessHelper 
 * @author gdhimate
 *
 */
public class BatchAgentContext implements Serializable {

	private static final long serialVersionUID = -456880115406007213L;

	private String contextFileName;
	private String jobName;
	private Map<String, String> jobParams;

	public String getContextFileName() {
		return contextFileName;
	}

	public void setContextFileName(String contextFileName) {
		this.contextFileName = contextFileName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Map<String, String> getJobParams() {
		return jobParams;
	}

	public void setJobParams(Map<String, String> jobParams) {
		this.jobParams = jobParams;
	}

}
