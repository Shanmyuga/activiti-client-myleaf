package com.bpm.activiti.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bpm.activiti.client.model.DeployMentList;
import com.bpm.activiti.client.model.processlist.ProcessInstanceList;
import com.bpm.activiti.client.service.ActivitiRestService;

@Controller
public class ActivitiClientController {

	@Autowired
	private ActivitiRestService service;
	
	
	 @RequestMapping("/home")
	    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
		 
		ProcessInstanceList deployinfo =  service.getProcessLists();
		 System.out.println(deployinfo.getTotal());
	        model.addAttribute("name", name);
	        model.addAttribute("proclist", deployinfo.getData());
	        model.addAttribute("viewForm", "fragments/blankform");
	        
	        return "hello";
	    }
	 
	 
	
	 
	 
	 @RequestMapping("/startProcess")
	    public String startProcess(Model model, @RequestParam(value="processDefinitionId", required=true ) String processDefinitionId) {
		 ProcessInstanceList deployinfo =  service.getProcessLists();
	        String formKey= service.getProcessFormKey(processDefinitionId);
	        if(formKey == null) {
	        	 model.addAttribute("viewForm", "fragments/blankform");
	        }
	        else {
	        model.addAttribute("viewForm", formKey);
	        }
	        
	        model.addAttribute("proclist", deployinfo.getData());
	        return "hello";
	    }
}
