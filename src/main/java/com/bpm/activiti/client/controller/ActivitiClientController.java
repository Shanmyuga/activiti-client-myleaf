package com.bpm.activiti.client.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		
		model.addAttribute("sidenavForm", "fragments/blanksidenav");
		model.addAttribute("viewForm", "fragments/blankform");

		return "hello";
	}

	@GetMapping("/startProcess")
	public String startProcess(Model model,
			@RequestParam(value = "processDefinitionId", required = true) String processDefinitionId) {
		ProcessInstanceList deployinfo = service.getProcessLists();
		String formKey = service.getProcessFormKey(processDefinitionId);
		if (formKey != null) {
			model.addAttribute("viewForm", "fragments/ApplicationQuestionaire");
			model.addAttribute("processdefid", processDefinitionId);
		} else {
			model.addAttribute("viewForm", formKey);
		}
		model.addAttribute("processdefid", processDefinitionId);
		model.addAttribute("proclist", deployinfo.getData());
		return "hello";
	}

	@RequestMapping(value = "/tasks", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE })

	public String processInstance(Model model, @RequestBody Map<String,String> map) throws IOException {
      
		StartProcessModel startmodel = new StartProcessModel();
		startmodel.setProcessDefinitionId(map.get("processDefinitionId"));
		
		
		 String mapAsJson = new ObjectMapper().writeValueAsString(map);
	        System.out.println(mapAsJson);
	        
	        Variable variable = new Variable();
	        variable.setName("PROCESS_DATA");
	        variable.setValue(mapAsJson);
		  startmodel.getVariables().add(variable);
		  service.startProcess(startmodel);
		model.addAttribute("viewForm", "fragments/process_success");
		return "hello";
	}
}
