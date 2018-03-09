package com.bpm.activiti.client.controller;

import com.bpm.activiti.client.model.AttachMentModel;
import com.bpm.activiti.client.model.StartProcessModel;
import com.bpm.activiti.client.model.TaskProcessVariable;
import com.bpm.activiti.client.model.Variable;
import com.bpm.activiti.client.model.processlist.ProcessInstanceList;
import com.bpm.activiti.client.model.task.TaskComplete;
import com.bpm.activiti.client.service.ActivitiRestService;
import com.bpm.activiti.task.response.model.TaskDatum;
import com.bpm.activiti.task.response.model.TaskResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;


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
        model.addAttribute("tasklist", taskinfo.getData());
        model.addAttribute("sidenavForm", "fragments/tasksidebar");
        model.addAttribute("viewForm", "fragments/blankform");

        return "hello";
    }

    @GetMapping("/secure/processhome")
    public String startProcessHome(Model model) {
        ProcessInstanceList deployinfo = service.getProcessLists();


        model.addAttribute("viewForm", "fragments/blankform");

        model.addAttribute("sidenavForm", "fragments/procsidebar");
        model.addAttribute("proclist", deployinfo.getData());
        return "hello";
    }

    @GetMapping("/secure/startProcessInstance")
    public String startProcessInstance(Model model, @RequestParam(value = "processDefinitionId", required = true) String processDefinitionId) {
        ProcessInstanceList deployinfo = service.getProcessLists();

        String formkey = service.getProcessFormKey(processDefinitionId);
        System.out.println(formkey);
        TaskFormWrapper wrapper = new TaskFormWrapper();
        Map<String, String> map = new HashMap<String, String>();
        map.put("processDefinitionId", processDefinitionId);
        wrapper.setDatamap(map);
        model.addAttribute("taskform", wrapper);
        model.addAttribute("viewForm", "fragments/" + formkey);
        model.addAttribute("datamap", map);
        model.addAttribute("sidenavForm", "fragments/procsidebar");
        model.addAttribute("proclist", deployinfo.getData());
        return "hello";
    }


    @GetMapping("/startProcess")
    public String startProcess(Model model,
                               @RequestParam(value = "taskid", required = true) String taskid,
                               @RequestParam(value = "processInstanceId", required = true) String processInstanceId
    ) {

        TaskResponse taskinfo = getTasksfortheuserGroup();
        TaskDatum data = service.getTaskDetails(taskid);
        String formkey = (String) data.getFormKey();

        TaskFormWrapper wrapper = new TaskFormWrapper();

        TaskProcessVariable[] variables = service.getProcessInstanceVariables(processInstanceId);

        System.out.println(taskid);
        TaskProcessVariable tvariable = service.getVariableExist(taskid, "TASK_PROCESS_DATA");
        Map<String, String> map = null;
        ObjectMapper mapper = new ObjectMapper();
        if (tvariable != null) {
             mapper = new ObjectMapper();
            try {
                map = mapper.readValue(tvariable.getValue(), new TypeReference<Map<String, String>>() {
                });
                map.put("taskId", taskid);
            } catch (IOException e) {

            }
        } else {
            map = new HashMap<String, String>();
            map.put("taskId", taskid);
        }
        for (TaskProcessVariable variable : variables) {
            if(variable.getName().equals("attachments")) {
                try {
                    List<AttachMentModel> myObjects = mapper.readValue(variable.getValue(), mapper.getTypeFactory().constructCollectionType(List.class, AttachMentModel.class));
                    model.addAttribute("attachlist", myObjects);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                map.put(variable.getName(), variable.getValue());
            }
        }
        map.put("taskId", taskid);
        wrapper.setDatamap(map);
        model.addAttribute("taskform", wrapper);
        model.addAttribute("datamap", map);
        model.addAttribute("tasklist", taskinfo.getData());
        model.addAttribute("viewForm", "fragments/" + formkey);
        model.addAttribute("processdefid", taskid);
        model.addAttribute("sidenavForm", "fragments/tasksidebar");


        return "hello";
    }


    @RequestMapping(value = "/secure/startprocess", method = RequestMethod.POST)

    public String startPRocessInstance(Model model, @ModelAttribute TaskFormWrapper form) throws IOException {
        Map<String, String> map = form.getDatamap();
        List<MultipartFile> afiles = form.getImages();


        String mapAsJson = new ObjectMapper().writeValueAsString(map);
        System.out.println(mapAsJson);


        StartProcessModel spmodel = new StartProcessModel();
        spmodel.setProcessDefinitionId(map.get("processDefinitionId"));
        List<AttachMentModel> amodels = afiles.stream().map(afile -> {
            AttachMentModel amodel = new AttachMentModel();
            amodel.setFileType(afile.getContentType());
            amodel.setFileName(afile.getName());
            amodel.setProcessInstanceId(map.get("processDefinitionId"));
            try {
                amodel.setFiledata(Base64.getEncoder().encodeToString(afile.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return amodel;

        }).collect(Collectors.toList());
        String fileattachmentjson = new ObjectMapper().writeValueAsString(amodels);

        map.forEach((k, v) -> {
            Variable var = new Variable();
            var.setName(k);
            var.setValue(v);
            spmodel.getVariables().add(var);
        });

        Variable var = new Variable();
        var.setName("attachments");
        var.setValue(fileattachmentjson);
        spmodel.getVariables().add(var);

        model.addAttribute("viewForm", "fragments/processstart_success");


        ProcessInstanceList deployinfo = service.getProcessLists();

        service.startProcess(spmodel);

        model.addAttribute("proclist", deployinfo.getData());
        model.addAttribute("sidenavForm", "fragments/tasksidebar");
        return "hello";
    }

    @RequestMapping(value = "/secure/tasks", method = RequestMethod.POST)

    public String saveTaskData(Model model, @ModelAttribute TaskFormWrapper form) throws IOException {
        Map<String, String> map = form.getDatamap();


        String mapAsJson = new ObjectMapper().writeValueAsString(map);
        System.out.println(mapAsJson);


        TaskDatum data = service.getTaskDetails(map.get("taskId"));
        String formkey = (String) data.getFormKey();
        TaskProcessVariable tvariable = service.getVariableExist(map.get("taskId"), "TASK_PROCESS_DATA");


        if (tvariable == null) {
            tvariable = new TaskProcessVariable();
        }
        tvariable.setName("TASK_PROCESS_DATA");
        tvariable.setValue(mapAsJson);
        tvariable.setScope("global");
        tvariable.setType("string");


        List<TaskProcessVariable> tvariables = new ArrayList<TaskProcessVariable>();
        tvariables.add(tvariable);
        map.forEach((k, v) -> {
            TaskProcessVariable tvariable2 = new TaskProcessVariable();
            tvariable2.setName(k);
            tvariable2.setValue(v);
            tvariable2.setScope("global");
            tvariable2.setType("string");
            tvariables.add(tvariable2);
        });
        service.addUpdateVariablesToTask(map.get("taskId"), tvariables);
        if ("Submit".equals(map.get("actiontag"))) {
            TaskComplete complete = new TaskComplete();
            complete.setAction("complete");

            service.completeTask(map.get("taskId"), complete);
            model.addAttribute("viewForm", "fragments/process_success");
        } else {
            model.addAttribute("viewForm", "fragments/" + formkey);
        }


        TaskResponse taskinfo = getTasksfortheuserGroup();
        model.addAttribute("tasklist", taskinfo.getData());
        model.addAttribute("taskform", form);


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

    @RequestMapping(value="/secure/download", method=RequestMethod.GET)
    public void load(@RequestParam(value = "taskid", required = true) String taskid,
                       @RequestParam(value = "processInstanceId", required = true) String processInstanceId,@RequestParam(value = "filename", required = true) String filename ,HttpServletResponse response) {
        TaskProcessVariable[] variables = service.getProcessInstanceVariables(processInstanceId);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(taskid);
        TaskProcessVariable tvariable = service.getVariableExist(taskid, "TASK_PROCESS_DATA");
        if(tvariable !=null) {
            try {
                List<AttachMentModel> myObjects = mapper.readValue(tvariable.getValue(), mapper.getTypeFactory().constructCollectionType(List.class, AttachMentModel.class));
               AttachMentModel amodel = myObjects.stream().filter(item->item.getFileName().equals(filename)).findAny().get();
                writeResponseStream(response,amodel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
       TaskProcessVariable variable =      Arrays.asList(variables).stream().filter(item ->item.getName().equals("attachments")).findAny().get();
       if(variable != null) {
           try {
               List<AttachMentModel> myObjects = mapper.readValue(variable.getValue(), mapper.getTypeFactory().constructCollectionType(List.class, AttachMentModel.class));
               AttachMentModel amodel = myObjects.stream().filter(item->item.getFileName().equals(filename)).findAny().get();
               writeResponseStream(response,amodel);
           } catch (IOException e) {
               e.printStackTrace();
           }

       }
        }

    }


    public void writeResponseStream(HttpServletResponse response,AttachMentModel attachMentModel ) throws IOException {

        String contentDisposition = "attachment; filename=\""+attachMentModel.getFileName()+"\"";
        response.setContentType(attachMentModel.getFileType()); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.
        response.setHeader("Content-disposition",contentDisposition); // The Save As popup magic is done here. You can give it any filename you want, this only won't work in MSIE, it will use current request URL as filename instead.

        // Write file to response.
        OutputStream output = response.getOutputStream();

            output.write(Base64.getDecoder().decode(attachMentModel.getFiledata()));


            output.close();


    }
}
