package com.bpm.activiti.client.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bpm.activiti.client.model.StartProcessModel;
import com.bpm.activiti.client.model.Variable;
import com.bpm.activiti.client.model.processlist.ProcessInstanceList;
import com.bpm.activiti.client.service.ActivitiRestService;
import com.bpm.activiti.task.response.model.TaskResponse;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
@RequestMapping("/app")
public class ActivitiClientController {

	@Autowired
	private ActivitiRestService service;

	@GetMapping("/login")
	public String login(Model model,
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {

		ProcessInstanceList deployinfo = service.getProcessLists();
		
		model.addAttribute("name", name);
		model.addAttribute("proclist", deployinfo.getData());
		model.addAttribute("sidenavForm", "fragments/blanksidenav");
		model.addAttribute("viewForm", "fragments/customLoginForm");

		return "hello";
	}
	
	
	@GetMapping("/home")
	public String home(Model model,
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
String username = SecurityContextHolder.getContext().getAuthentication().getName();
System.out.println(username);
		TaskResponse taskinfo = service.getTasksForaUser(username);
		
		System.out.println(taskinfo.getSize());
		
		model.addAttribute("name", name);
		model.addAttribute("tasklist",taskinfo.getData());
		model.addAttribute("sidenavForm", "fragments/tasksidebar");
		model.addAttribute("viewForm", "fragments/blankform");

		return "hello";
	}

	@GetMapping("/startProcess")
	public String startProcess(Model model,
			@RequestParam(value = "taskid", required = true) String taskid) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(username);
				TaskResponse taskinfo = service.getTasksForaUser(username);
				Map<String,String> map = new HashMap<String,String>();
				map.put("cust_id1", "Name");
				map.put("taskId", taskid);
				TaskFormWrapper wrapper = new TaskFormWrapper();
				wrapper.setDatamap(map);
				model.addAttribute("taskform",wrapper);
				model.addAttribute("datamap",map);
			model.addAttribute("viewForm", "fragments/ApplicationQuestionaire");
			model.addAttribute("processdefid", taskid);
			model.addAttribute("sidenavForm", "fragments/tasksidebar");
		
		
		return "hello";
	}

	@RequestMapping(value = "/secure/tasks", method = RequestMethod.POST)

	public String processInstance(Model model,@ModelAttribute TaskFormWrapper form) throws IOException {
    Map<String,String> map = form.getDatamap();
		StartProcessModel startmodel = new StartProcessModel();
		startmodel.setProcessDefinitionId(map.get("processDefinitionId"));
		
		
		 String mapAsJson = new ObjectMapper().writeValueAsString(map);
	        System.out.println(mapAsJson);
	        
	        Variable variable = new Variable();
	        variable.setName("PROCESS_DATA");
	        variable.setValue(mapAsJson);
		  startmodel.getVariables().add(variable);
		  //service.addVariablesToTask(taskId, variables);
		model.addAttribute("viewForm", "fragments/process_success");
		model.addAttribute("sidenavForm", "fragments/tasksidebar");
		return "hello";
	}
}
