package com.nelson.mouseshortvideo.mapper;

import com.nelson.mouseshortvideo.pojo.Users;
import com.nelson.mouseshortvideo.utils.MyMapper;

public interface UsersMapper extends MyMapper<Users> {

    /**
     * @Description: 用户受喜欢数累加
     */
    public void addReceiveLikeCount(String userId);

    /**
     * @Description: 用户受喜欢数累减
     */
    public void reduceReceiveLikeCount(String userId);

    void reduceFansCount(String userId);

    void reduceFollersCount(String fanId);

    void addFansCount(String userId);

    void addFollersCount(String fanId);
}