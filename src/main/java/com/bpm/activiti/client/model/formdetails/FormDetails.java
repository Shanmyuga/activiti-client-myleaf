package com.bpm.activiti.client.model.formdetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"formKey",
"deploymentId",
"processDefinitionId",
"processDefinitionUrl",
"taskId",
"taskUrl",
"formProperties"
})
public class FormDetails {

@JsonProperty("formKey")
private String formKey;
@JsonProperty("deploymentId")
private String deploymentId;
@JsonProperty("processDefinitionId")
private String processDefinitionId;
@JsonProperty("processDefinitionUrl")
private String processDefinitionUrl;
@JsonProperty("taskId")
private Object taskId;
@JsonProperty("taskUrl")
private Object taskUrl;
@JsonProperty("formProperties")
private List<FormProperty> formProperties = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("formKey")
public String getFormKey() {
return formKey;
}

@JsonProperty("formKey")
public void setFormKey(String formKey) {
this.formKey = formKey;
}

@JsonProperty("deploymentId")
public String getDeploymentId() {
return deploymentId;
}

@JsonProperty("deploymentId")
public void setDeploymentId(String deploymentId) {
this.deploymentId = deploymentId;
}

@JsonProperty("processDefinitionId")
public String getProcessDefinitionId() {
return processDefinitionId;
}

@JsonProperty("processDefinitionId")
public void setProcessDefinitionId(String processDefinitionId) {
this.processDefinitionId = processDefinitionId;
}

@JsonProperty("processDefinitionUrl")
public String getProcessDefinitionUrl() {
return processDefinitionUrl;
}

@JsonProperty("processDefinitionUrl")
public void setProcessDefinitionUrl(String processDefinitionUrl) {
this.processDefinitionUrl = processDefinitionUrl;
}

@JsonProperty("taskId")
public Object getTaskId() {
return taskId;
}

@JsonProperty("taskId")
public void setTaskId(Object taskId) {
this.taskId = taskId;
}

@JsonProperty("taskUrl")
public Object getTaskUrl() {
return taskUrl;
}

@JsonProperty("taskUrl")
public void setTaskUrl(Object taskUrl) {
this.taskUrl = taskUrl;
}

@JsonProperty("formProperties")
public List<FormProperty> getFormProperties() {
return formProperties;
}

@JsonProperty("formProperties")
public void setFormProperties(List<FormProperty> formProperties) {
this.formProperties = formProperties;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}