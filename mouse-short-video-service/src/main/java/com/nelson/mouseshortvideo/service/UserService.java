package com.nelson.mouseshortvideo.service;

import com.nelson.mouseshortvideo.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface UserService {
    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    public boolean queryUserNameIsExits(String username);

    /**
     * 保存用户
     * @param users
     */
    public void saveUser(Users users);

    /**
     * 验证用户登陆
     * @param user
     * @return
     */
    Users login(Users user);

    void updateUser(Users user);
}
