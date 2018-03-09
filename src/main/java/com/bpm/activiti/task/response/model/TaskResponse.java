package com.bpm.activiti.task.response.model;

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
"data",
"total",
"start",
"sort",
"order",
"size"
})
public class TaskResponse {

@JsonProperty("data")
private List<TaskDatum> data = null;
@JsonProperty("total")
private Integer total;
@JsonProperty("start")
private Integer start;
@JsonProperty("sort")
private String sort;
@JsonProperty("order")
private String order;
@JsonProperty("size")
private Integer size;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("data")
public List<TaskDatum> getData() {
return data;
}

@JsonProperty("data")
public void setData(List<TaskDatum> data) {
this.data = data;
}

@JsonProperty("total")
public Integer getTotal() {
return total;
}

@JsonProperty("total")
public void setTotal(Integer total) {
this.total = total;
}

@JsonProperty("start")
public Integer getStart() {
return start;
}

@JsonProperty("start")
public void setStart(Integer start) {
this.start = start;
}

@JsonProperty("sort")
public String getSort() {
return sort;
}

@JsonProperty("sort")
public void setSort(String sort) {
this.sort = sort;
}

@JsonProperty("order")
public String getOrder() {
return order;
}

@JsonProperty("order")
public void setOrder(String order) {
this.order = order;
}

@JsonProperty("size")
public Integer getSize() {
return size;
}

@JsonProperty("size")
public void setSize(Integer size) {
this.size = size;
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