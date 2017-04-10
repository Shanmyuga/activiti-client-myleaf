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
"id",
"name",
"type",
"value",
"readable",
"writable",
"required",
"datePattern",
"enumValues"
})
public class FormProperty {

@JsonProperty("id")
private String id;
@JsonProperty("name")
private String name;
@JsonProperty("type")
private String type;
@JsonProperty("value")
private Object value;
@JsonProperty("readable")
private Boolean readable;
@JsonProperty("writable")
private Boolean writable;
@JsonProperty("required")
private Boolean required;
@JsonProperty("datePattern")
private Object datePattern;
@JsonProperty("enumValues")
private List<Object> enumValues = null;
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

@JsonProperty("type")
public String getType() {
return type;
}

@JsonProperty("type")
public void setType(String type) {
this.type = type;
}

@JsonProperty("value")
public Object getValue() {
return value;
}

@JsonProperty("value")
public void setValue(Object value) {
this.value = value;
}

@JsonProperty("readable")
public Boolean getReadable() {
return readable;
}

@JsonProperty("readable")
public void setReadable(Boolean readable) {
this.readable = readable;
}

@JsonProperty("writable")
public Boolean getWritable() {
return writable;
}

@JsonProperty("writable")
public void setWritable(Boolean writable) {
this.writable = writable;
}

@JsonProperty("required")
public Boolean getRequired() {
return required;
}

@JsonProperty("required")
public void setRequired(Boolean required) {
this.required = required;
}

@JsonProperty("datePattern")
public Object getDatePattern() {
return datePattern;
}

@JsonProperty("datePattern")
public void setDatePattern(Object datePattern) {
this.datePattern = datePattern;
}

@JsonProperty("enumValues")
public List<Object> getEnumValues() {
return enumValues;
}

@JsonProperty("enumValues")
public void setEnumValues(List<Object> enumValues) {
this.enumValues = enumValues;
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