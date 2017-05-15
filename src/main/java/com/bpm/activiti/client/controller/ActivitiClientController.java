package com.bpm.activiti.client.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch.TaskInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bpm.activiti.client.model.TaskProcessVariable;
import com.bpm.activiti.client.model.task.TaskComplete;
import com.bpm.activiti.client.service.ActivitiRestService;
import com.bpm.activiti.task.response.model.TaskDatum;
import com.bpm.activiti.task.response.model.TaskResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
@RequestMapping("/app")
public class ActivitiClientController {

	@Autowired
	private ActivitiRestService service;

	@GetMapping("/login")
	public String login(Model model,
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {

		
		
		model.addAttribute("name", name);
		
		model.addAttribute("sidenavForm", "fragments/blanksidenav");
		model.addAttribute("viewForm", "fragments/customLoginForm");

		return "hello";
	}
	
	
	@GetMapping("/home")
	public String home(Model model,
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
TaskResponse taskinfo = getTasksfortheuserGroup();
		model.addAttribute("name", name);
		model.addAttribute("tasklist",taskinfo.getData());
		model.addAttribute("sidenavForm", "fragments/tasksidebar");
		model.addAttribute("viewForm", "fragments/blankform");

		return "hello";
	}

	@GetMapping("/startProcess")
	public String startProcess(Model model,
			@RequestParam(value = "taskid", required = true) String taskid) {
		
		TaskResponse taskinfo = getTasksfortheuserGroup();
			TaskDatum data =	service.getTaskDetails(taskid);
			String formkey = (String) data.getFormKey();
		
				TaskFormWrapper wrapper = new TaskFormWrapper();
			
			
				System.out.println(taskid);
				TaskProcessVariable tvariable = service.getVariableExist(taskid, "TASK_PROCESS_DATA");
				Map<String,String> map = null;
				if(tvariable != null) {
					ObjectMapper mapper = new ObjectMapper();
					try {
						 map = mapper.readValue(tvariable.getValue(), new TypeReference<Map<String, String>>(){});
						 map.put("taskId", taskid);
					} catch (IOException e) {
						
					}
				}else {
					map = new HashMap<String,String>();
					map.put("taskId", taskid);
				}
				
				wrapper.setDatamap(map);
				model.addAttribute("taskform",wrapper);
				model.addAttribute("datamap",map);
				model.addAttribute("tasklist",taskinfo.getData());
			model.addAttribute("viewForm", "fragments/"+formkey);
			model.addAttribute("processdefid", taskid);
			model.addAttribute("sidenavForm", "fragments/tasksidebar");
		
		
		return "hello";
	}

	@RequestMapping(value = "/secure/tasks", method = RequestMethod.POST)

	public String saveTaskData(Model model,@ModelAttribute TaskFormWrapper form) throws IOException {
    Map<String,String> map = form.getDatamap();
	
	
		
		 String mapAsJson = new ObjectMapper().writeValueAsString(map);
	        System.out.println(mapAsJson);
	        
	   
	        TaskDatum data =	service.getTaskDetails(map.get("taskId"));
	        String formkey = (String) data.getFormKey();
		TaskProcessVariable tvariable = service.getVariableExist(map.get("taskId"), "TASK_PROCESS_DATA");
		
		
		if(tvariable == null) {
			tvariable = new TaskProcessVariable();
		}
		tvariable.setName("TASK_PROCESS_DATA");
		tvariable.setValue(mapAsJson);
		tvariable.setScope("global");
		tvariable.setType("string");
		TaskProcessVariable tvariable2 = new TaskProcessVariable();
		tvariable2.setName("status");
		tvariable2.setValue(map.get("status"));
		tvariable2.setScope("global");
		tvariable2.setType("string");
		  List<TaskProcessVariable> tvariables = new ArrayList<TaskProcessVariable>();
		  tvariables.add(tvariable);
		  tvariables.add(tvariable2);
		  service.addUpdateVariablesToTask(map.get("taskId"), tvariables);
		  if("Submit".equals(map.get("actiontag"))) {
			  TaskComplete complete = new TaskComplete();
			  complete.setAction("complete");
			
			  service.completeTask(map.get("taskId"), complete);
			  model.addAttribute("viewForm", "fragments/process_success");
		  }else {
			  model.addAttribute("viewForm", "fragments/"+formkey);
		  }
			  
			  
			  
		  TaskResponse taskinfo = getTasksfortheuserGroup();
			model.addAttribute("tasklist",taskinfo.getData());
			model.addAttribute("taskform",form);
		
	
		model.addAttribute("sidenavForm", "fragments/tasksidebar");
		return "hello";
	}
	
	
	public TaskResponse getTasksfortheuserGroup() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().getAuthority();
				TaskResponse taskinfo = service.getTasksForaUser(username);
				
				TaskResponse groupTasks = service.getTasksForaGroup(role.toLowerCase().substring(5));
				System.out.println(taskinfo.getSize());
				taskinfo.getData().addAll(groupTasks.getData());
				return taskinfo;
	}
}
