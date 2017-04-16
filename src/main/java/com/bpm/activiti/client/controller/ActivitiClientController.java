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
import com.bpm.activiti.client.model.formdetails.FormDetails;
import com.bpm.activiti.client.model.formdetails.InitialForm;
import com.bpm.activiti.client.service.ActivitiRestService;

@Controller
public class ActivitiClientController {

	static int counter = 0;
	static int formCounter = 0;

	public static final String BLANK_FORM = "fragments/blankform::formContent";

	@Autowired
	private ActivitiRestService service;

	/*
	@ModelAttribute("initialForm")
	public InitialForm prepareInitialForm() {
		formCounter++;
		System.out.println("prepareInitialForm called -->" + formCounter);
		return new InitialForm();
	}
	*/

	//@ModelAttribute("proclist")
	public List<Datum> getProcessLists() {
		counter++;
		System.out.println("getProcessLists called -->" + counter);
		return service.getProcessLists().getData();

	}

	@RequestMapping("/")
	public String showHome(Model model) {
		model.addAttribute("proclist", getProcessLists());
		return "home";
	}

	@RequestMapping("/startProcess")
	public String startProcess(
			Model model,
			@RequestParam(value = "processDefinitionId", required = true) String processDefinitionId) {
		FormDetails form = service.getProcessForm(processDefinitionId);
		String formKey = form.getFormKey();
		if (formKey == null) {
			return BLANK_FORM;
		} else {
			model.addAttribute("initialForm", new InitialForm());
			model.addAttribute("formProperties", form.getFormProperties());
			return "fragments/" + formKey + "::formContent";
		}
	}

	@RequestMapping("/refreshProcessList")
	public String refreshProcessList(Model model) {
		model.addAttribute("proclist", getProcessLists());
		return "fragments/sidenav::data-sidenav";
	}

	@PostMapping("/submitForm")
	public String submitForm(Model model, InitialForm initForm) {
		return showHome(model);
	}
}
