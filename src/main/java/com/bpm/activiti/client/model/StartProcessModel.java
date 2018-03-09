package com.bpm.activiti.client.model;

import java.util.ArrayList;
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
"processDefinitionId",
"businessKey",
"variables"
})
public class StartProcessModel {

@JsonProperty("processDefinitionId")
private String processDefinitionId;
@JsonProperty("businessKey")
private String businessKey;
@JsonProperty("variables")
private List<Variable> variables = new ArrayList<Variable>();
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("processDefinitionId")
public String getProcessDefinitionId() {
return processDefinitionId;
}

@JsonProperty("processDefinitionId")
public void setProcessDefinitionId(String processDefinitionId) {
this.processDefinitionId = processDefinitionId;
}

@JsonProperty("businessKey")
public String getBusinessKey() {
return businessKey;
}

@JsonProperty("businessKey")
public void setBusinessKey(String businessKey) {
this.businessKey = businessKey;
}

@JsonProperty("variables")
public List<Variable> getVariables() {
return variables;
}

@JsonProperty("variables")
public void setVariables(List<Variable> variables) {
this.variables = variables;
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