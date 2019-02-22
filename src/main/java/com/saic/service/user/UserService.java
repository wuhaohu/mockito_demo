package com.saic.service.user;

import com.github.pagehelper.PageInfo;
import com.saic.model.UserDomain;

/**
 * @author: create by nadao
 * @version: v1.0
 * @description: com.saic
 * @date:2019-02-21
 */
public interface UserService {

    int addUser(UserDomain user);

    PageInfo<UserDomain> findAllUser(int pageNum, int pageSize);
}
