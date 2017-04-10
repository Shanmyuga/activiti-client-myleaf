package com.bpm.activiti.client.model;

public class DeploymentInfo {

	
	private String deploymentName;
	
	private String deployId;
	
	private String deployURL;

	public String getDeploymentName() {
		return deploymentName;
	}

	public void setDeploymentName(String deploymentName) {
		this.deploymentName = deploymentName;
	}

	public String getDeployId() {
		return deployId;
	}

	public void setDeployId(String deployId) {
		this.deployId = deployId;
	}

	public String getDeployURL() {
		return deployURL;
	}

	public void setDeployURL(String deployURL) {
		this.deployURL = deployURL;
	}
}
