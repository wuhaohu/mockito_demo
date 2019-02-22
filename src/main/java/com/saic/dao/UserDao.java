package com.saic.dao;


import com.saic.model.UserDomain;

import java.util.List;

/**
 * @author: create by nadao
 * @version: v1.0
 * @description: com.saic
 * @date:2019-02-21
 */
public interface UserDao {


    int insert(UserDomain record);



    List<UserDomain> selectUsers();
}