package com.bpm.activiti.client.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.bpm.activiti.client.model.DeployMentList;
import com.bpm.activiti.client.model.StartProcessModel;
import com.bpm.activiti.client.model.TaskProcessVariable;
import com.bpm.activiti.client.model.formdetails.FormDetails;
import com.bpm.activiti.client.model.processlist.ProcessInstanceList;
import com.bpm.activiti.client.model.task.TaskComplete;
import com.bpm.activiti.task.response.model.TaskDatum;
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
		ResponseEntity<DeployMentList> deployData = template.exchange(baseURL + "repository/deployments",
				HttpMethod.GET, request, DeployMentList.class);

		return deployData.getBody();
	}

	public ProcessInstanceList getProcessLists() {
		HttpEntity<String> request = new HttpEntity<String>(httpHeader);
		ResponseEntity<ProcessInstanceList> deployData = template.exchange(baseURL + "repository/process-definitions",
				HttpMethod.GET, request, ProcessInstanceList.class);

		return deployData.getBody();
	}

	public String getProcessFormKey(String processDefinitionId) {
		HttpEntity<String> request = new HttpEntity<String>(httpHeader);
		ResponseEntity<FormDetails> deployData = template.exchange(
				baseURL + "form/form-data?processDefinitionId=" + processDefinitionId, HttpMethod.GET, request,
				FormDetails.class);

		return deployData.getBody().getFormKey();
	}


	public TaskProcessVariable[] getProcessInstanceVariables(String processInstanceId) {
		HttpEntity<String> request = new HttpEntity<String>(httpHeader);
		ResponseEntity<TaskProcessVariable[]> deployData = template.exchange(
				baseURL + "runtime/process-instances/"+processInstanceId+"/variables", HttpMethod.GET, request,
				TaskProcessVariable[].class);

		return deployData.getBody();
	}

	public TaskProcessVariable getVariableExist(String taskId, String variableName) {

		HttpEntity<String> request = new HttpEntity<String>(httpHeader);
		ResponseEntity<TaskProcessVariable> deployData = null;

		try {

			String fullurl = baseURL + "runtime/tasks/" + taskId + "/variables/" + variableName + "?scope=global";
			deployData = template.exchange(fullurl, HttpMethod.GET, request, TaskProcessVariable.class);
		} catch (HttpStatusCodeException exception) {

			int statusCode = exception.getStatusCode().value();
			if (statusCode == 404) {
				return null;
			}
		}

		return deployData.getBody();

	}

	public void addUpdateVariablesToTask(String taskId, List<TaskProcessVariable> variables) {

		for(TaskProcessVariable taskprocess: variables) {
			TaskProcessVariable variable = getVariableExist(taskId,taskprocess.getName());
			if (variable != null) {
				variable.setValue(taskprocess.getValue());
				HttpEntity request = new HttpEntity(variable, httpHeader);

				ResponseEntity<String> deployData = template.exchange(baseURL + "runtime/tasks/" + taskId + "/variables/"+variable.getName(),
						HttpMethod.PUT, request, String.class);

				System.out.println("variable updated " + taskprocess.getName() + "  "+  taskprocess.getValue());
			}
			else {
				List<TaskProcessVariable> templist = new ArrayList<TaskProcessVariable>();
				templist.add(taskprocess);
				HttpEntity request = new HttpEntity(templist, httpHeader);

				ResponseEntity<String> deployData = template.exchange(baseURL + "runtime/tasks/" + taskId + "/variables",
						HttpMethod.POST, request, String.class);
			}

			System.out.println("variable added " + taskprocess.getName() + "  "+  taskprocess.getValue());
		}


	}
	
	
	public void startProcess(StartProcessModel model) {

		HttpEntity request = new HttpEntity(model, httpHeader);

		ResponseEntity<String> deployData = template.exchange(baseURL + "runtime/process-instances",
				HttpMethod.POST, request, String.class);

	}

	public void completeTask(String taskId, TaskComplete complete) {

		HttpEntity request = new HttpEntity(complete, httpHeader);

		ResponseEntity<String> deployData = template.exchange(baseURL + "runtime/tasks/" + taskId, HttpMethod.POST,
				request, String.class);

	}

	public TaskResponse getTasksForaUser(String userid) {

		HttpEntity request = new HttpEntity(httpHeader);

		ResponseEntity<TaskResponse> deployData = template.exchange(baseURL + "runtime/tasks?assignee=" + userid,
				HttpMethod.GET, request, TaskResponse.class);

		return deployData.getBody();

	}

	public TaskResponse getTasksForaGroup(String groupId) {

		HttpEntity request = new HttpEntity(httpHeader);

		ResponseEntity<TaskResponse> deployData = template.exchange(baseURL + "runtime/tasks?candidateGroup=GROUP_" + groupId,
				HttpMethod.GET, request, TaskResponse.class);

		return deployData.getBody();

	}

	public TaskDatum getTaskDetails(String taskid) {

		HttpEntity request = new HttpEntity(httpHeader);

		ResponseEntity<TaskDatum> deployData = template.exchange(baseURL + "runtime/tasks/" + taskid, HttpMethod.GET,
				request, TaskDatum.class);

		return deployData.getBody();

	}



}
