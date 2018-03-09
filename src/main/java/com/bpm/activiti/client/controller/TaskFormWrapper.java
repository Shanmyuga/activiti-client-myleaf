package com.bpm.activiti.client.controller;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public class TaskFormWrapper {

	private Map<String,String> datamap;

	public Map<String, String> getDatamap() {
		return datamap;
	}

	public List<MultipartFile> getImages() {
		return images;
	}

	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}

	public void setDatamap(Map<String, String> datamap) {
		this.datamap = datamap;
	}

	private List<MultipartFile> images;


}
