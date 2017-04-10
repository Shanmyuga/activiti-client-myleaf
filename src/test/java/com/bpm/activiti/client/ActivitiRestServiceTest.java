package com.bpm.activiti.client;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.bpm.activiti.client.model.DeployMentList;
import com.bpm.activiti.client.service.ActivitiRestService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ActivitiRestServiceTest {

	@Autowired
	private ActivitiRestService restService;
	
	@Test
	public void testDeployList() {
		
	DeployMentList list = restService.getDeployments();
	assertEquals(1, list.getTotal().intValue());
	}
}
