package com.felis.resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ResourceApplicationTests {

	@Autowired
	private PictureService pictureService;
	@Test
	void contextLoads() {
	}

}
