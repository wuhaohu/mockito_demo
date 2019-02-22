package com.mockito.Demo;

import com.saic.model.UserDomain;
import com.saic.service.user.UserService;
import com.saic.service.user.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author: create by nadao
 * @version: v1.0
 * @description: com.saic
 * @date:2019-02-21
 */

public class MockitoTest {
    @InjectMocks
    private UserService userService= new UserServiceImpl();

    @Mock
    private UserDomain userDomain;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        when(userService.addUser(any(UserDomain.class)))
                .thenReturn(1);
    }

    @Test
    public void contextLoads(){
        UserDomain rookie = new UserDomain();
        Assert.assertEquals(1,userService.addUser(rookie));
    }
}
