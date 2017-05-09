package com.bpm.activiti.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bpm.activiti.client.model.DeployMentList;
import com.bpm.activiti.client.model.StartProcessModel;
import com.bpm.activiti.client.model.formdetails.FormDetails;
import com.bpm.activiti.client.model.processlist.ProcessInstanceList;
import com.bpm.activiti.task.response.model.TaskResponse;


@Service
public class ActivitiRestService {
	
	  @Value("${activiti.baseURL}")
	private String baseURL;
	
	  
	  @Autowired
	  private HttpHeaders httpHeader;
	
	@Autowired
	private RestTemplate template;
	
	public DeployMentList getDeployments() {
		HttpEntity<String> request = new HttpEntity<String>(httpHeader);
		ResponseEntity<DeployMentList> deployData = template.exchange(baseURL+"repository/deployments", HttpMethod.GET, request, DeployMentList.class);
		
		return deployData.getBody();
	}

	
	public ProcessInstanceList getProcessLists() {
		HttpEntity<String> request = new HttpEntity<String>(httpHeader);
		ResponseEntity<ProcessInstanceList> deployData = template.exchange(baseURL+"repository/process-definitions", HttpMethod.GET, request, ProcessInstanceList.class);
		
		return deployData.getBody();
	}
	
	
	public String getProcessFormKey(String processDefinitionId) {
		HttpEntity<String> request = new HttpEntity<String>(httpHeader);
		ResponseEntity<FormDetails> deployData = template.exchange(baseURL+"form/form-data?processDefinitionId="+processDefinitionId, HttpMethod.GET, request, FormDetails.class);
		
		return  deployData.getBody().getFormKey();
	}
	
	
	public void startProcess(StartProcessModel model) {
		
		HttpEntity request = new HttpEntity(model,httpHeader);
	
		ResponseEntity<String> deployData = template.exchange(baseURL+"runtime/process-instances", HttpMethod.POST, request, String.class);
		
		  
	}
	
	public TaskResponse getTasksForaUser(String  userid) {
		
		HttpEntity request = new HttpEntity(httpHeader);
	
		ResponseEntity<TaskResponse> deployData = template.exchange(baseURL+"runtime/tasks?assignee="+userid, HttpMethod.GET, request, TaskResponse.class);
		
		return deployData.getBody();
		  
	}
}
