package com.nelson.mouseshortvideo.service;

import com.nelson.mouseshortvideo.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface UserService {
    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     */
    public boolean queryUserNameIsExits(String username);

    /**
     * 保存用户
     *
     * @param users
     */
    public void saveUser(Users users);

    /**
     * 验证用户登陆
     *
     * @param user
     * @return
     */
    Users login(Users user);

    /**
     * 更新用户
     *
     * @param user
     */
    void updateUser(Users user);

    /**
     * 根据UserId查询用户
     *
     * @param userId
     * @return
     */
    Users query(String userId);

    /**
     * @Description: 查询用户是否喜欢点赞视频
     */
    public boolean isUserLikeVideo(String userId, String videoId);

    /**
     * @Description: 增加用户和粉丝的关系
     */
    public void saveUserFanRelation(String userId, String fanId);

    /**
     * @Description: 删除用户和粉丝的关系
     */
    public void deleteUserFanRelation(String userId, String fanId);

    /**
     * @Description: 查询用户是否关注
     */
    public boolean queryIfFollow(String userId, String fanId);

    Users queryUserInfo(String publishUserId);
}
