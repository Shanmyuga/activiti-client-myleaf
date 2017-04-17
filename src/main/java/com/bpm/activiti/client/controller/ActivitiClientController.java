package com.bpm.activiti.client.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.bpm.activiti.client.model.processlist.ProcessInstanceList;
import com.bpm.activiti.client.service.ActivitiRestService;

@Controller
public class ActivitiClientController {

	@Autowired
	private ActivitiRestService service;
	
	
	 @GetMapping("/home")
	    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
		 
		ProcessInstanceList deployinfo =  service.getProcessLists();
		 System.out.println(deployinfo.getTotal());
	        model.addAttribute("name", name);
	        model.addAttribute("proclist", deployinfo.getData());
	        model.addAttribute("viewForm", "fragments/blankform");
	        
	        return "hello";
	    }
	 
	 
	
	 
	 
	 @GetMapping("/startProcess")
	    public String startProcess(Model model, @RequestParam(value="processDefinitionId", required=true ) String processDefinitionId) {
		 ProcessInstanceList deployinfo =  service.getProcessLists();
	        String formKey= service.getProcessFormKey(processDefinitionId);
	        if(formKey == null) {
	        	 model.addAttribute("viewForm", "fragments/testform");
	        }
	        else {
	        model.addAttribute("viewForm", formKey);
	        }
	        
	        model.addAttribute("proclist", deployinfo.getData());
	        return "hello";
	    }
	 
	 @PostMapping("/startProcessInstance")
	    public String processInstance(Model model,@RequestBody String json) {
		System.out.println(json);
		
		 model.addAttribute("viewForm", "fragments/process_success");
	        return "hello";
	    }
}
