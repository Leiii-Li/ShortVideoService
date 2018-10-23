package com.nelson.mouseshortvideo.service;

import com.nelson.mouseshortvideo.pojo.Users;
import com.nelson.mouseshortvideo.mapper.UsersMapper;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper mUserMapper;

    @Autowired
    private Sid mSid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUserNameIsExits(String username) {
        Users users = new Users();
        users.setUsername(username);
        Users result = mUserMapper.selectOne(users);
        return result == null ? false : true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(Users users) {
        String id = mSid.nextShort();
        users.setId(id);
        mUserMapper.insert(users);
    }

    @Override
    public Users login(Users user) {
        Users dbUser = mUserMapper.selectOne(user);
        if (dbUser!=null && dbUser.getUsername().equals(user.getUsername()) && dbUser.getPassword().equals(user.getPassword())) {
            user = dbUser;
            user.setPassword("");
            return user;
        }
        return null;
    }

    @Override
    public void updateUser(Users user) {
        mUserMapper.updateByPrimaryKeySelective(user);
    }
}
