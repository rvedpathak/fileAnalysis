package com.demo.task;

import com.demo.task.events.UUIDExtractorFromFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class TaskApplicationTests {

	@Autowired
	UUIDExtractorFromFile uuidExtractorFromFile;
	@Test
	void contextLoads() {
	}

	@Test
	public void testExtractor(){
		String fileID = "3b38f38e-b727-4c56-942d-8cb0a742790c";
		System.out.println("testExtractor");
		List<String> uuidList = uuidExtractorFromFile.extractUUIDs(fileID);
		Assert.notEmpty(uuidList, "Unable to extract");
	}
}
