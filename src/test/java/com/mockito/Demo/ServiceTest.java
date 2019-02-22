package com.mockito.Demo;

import com.saic.model.UserDomain;
import com.saic.service.user.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

	@Autowired
    UserService userService;

	@Test
	public void contextLoads() {
		UserDomain newOne = new UserDomain();
		newOne.setUserId(123);
		newOne.setUserName("huahua");
		newOne.setPassword("123456");
		newOne.setPhone("13456789009");
		int number = userService.addUser(newOne);
		Assert.assertEquals(1,number);
	}

}
