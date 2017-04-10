package com.bpm.activiti.client.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"id",
"name",
"deploymentTime",
"category",
"url",
"tenantId"
})
public class Datum {

@JsonProperty("id")
private String id;
@JsonProperty("name")
private String name;
@JsonProperty("deploymentTime")
private String deploymentTime;
@JsonProperty("category")
private Object category;
@JsonProperty("url")
private String url;
@JsonProperty("tenantId")
private String tenantId;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setId(String id) {
this.id = id;
}

@JsonProperty("name")
public String getName() {
return name;
}

@JsonProperty("name")
public void setName(String name) {
this.name = name;
}

@JsonProperty("deploymentTime")
public String getDeploymentTime() {
return deploymentTime;
}

@JsonProperty("deploymentTime")
public void setDeploymentTime(String deploymentTime) {
this.deploymentTime = deploymentTime;
}

@JsonProperty("category")
public Object getCategory() {
return category;
}

@JsonProperty("category")
public void setCategory(Object category) {
this.category = category;
}

@JsonProperty("url")
public String getUrl() {
return url;
}

@JsonProperty("url")
public void setUrl(String url) {
this.url = url;
}

@JsonProperty("tenantId")
public String getTenantId() {
return tenantId;
}

@JsonProperty("tenantId")
public void setTenantId(String tenantId) {
this.tenantId = tenantId;
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