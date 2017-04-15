package com.bpm.activiti.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bpm.activiti.client.model.Datum;
import com.bpm.activiti.client.model.DeployMentList;
import com.bpm.activiti.client.model.formdetails.FormDetails;
import com.bpm.activiti.client.model.formdetails.InitialForm;
import com.bpm.activiti.client.model.processlist.ProcessInstanceList;
import com.bpm.activiti.client.service.ActivitiRestService;

@Controller
public class ActivitiClientController {

	static int counter=0;
	
	@Autowired
	private ActivitiRestService service;
	
	@ModelAttribute("initialForm")
	public InitialForm prepareInitialForm() {
		return new InitialForm();
	}
	
	@ModelAttribute("proclist")
	public List<Datum> getProcessLists() {
		counter++;
		System.out.println("getProcessLists called -->" + counter);
		return service.getProcessLists().getData();
		
	}

	@RequestMapping("/home")
	public String showHome(
			Model model) {

		prepareInitialForm();
		getProcessLists();

		model.addAttribute("viewForm", "fragments/blankform");
		
		
		return "home";
	}

	@RequestMapping("/startProcess")
	public String startProcess(
			Model model,
			@RequestParam(value = "processDefinitionId", required = true) String processDefinitionId) {
		//ProcessInstanceList deployinfo = service.getProcessLists();
		String renderFragment = "activitiForm :: fragments/blankform";
		FormDetails form = service.getProcessForm(processDefinitionId);
		String formKey = form.getFormKey();
		// service.getProcessFormKey(processDefinitionId);
		if (formKey == null) {
			model.addAttribute("viewForm", "fragments/blankform");
		} else {
			String fragmentToUpdate = "fragments/" + formKey;
			//model.addAttribute("initialForm", new InitialForm());
			model.addAttribute("formProperties", form.getFormProperties());
			model.addAttribute("viewForm", fragmentToUpdate);
			renderFragment = "activitiForm :: " + fragmentToUpdate;
		}

		//model.addAttribute("proclist", deployinfo.getData());
		return "home";
		//return renderFragment;
	}

	@PostMapping("/submitForm")
	public String submitForm(Model model, InitialForm initForm) {

		model.addAttribute("viewForm", "fragments/blankform");

		return showHome(model);
	}
}
