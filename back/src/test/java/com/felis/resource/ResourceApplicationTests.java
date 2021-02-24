package com.felis.resource;

import ch.qos.logback.core.util.SystemInfo;
import cn.hutool.system.SystemUtil;
import com.felis.resource.service.PictureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ResourceApplicationTests {

	@Autowired
	private PictureService pictureService;
	@Test
	void contextLoads() {
//		System.out.println(SystemUtil.getOsInfo());
	}

}
