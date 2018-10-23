package com.nelson.mouseshortvideo.controller;

import com.nelson.mouseshortvideo.pojo.Users;
import com.nelson.mouseshortvideo.service.UserService;
import com.nelson.mouseshortvideo.utils.MD5Utils;
import com.nelson.mouseshortvideo.utils.MouseShortVideoResult;
import com.nelson.mouseshortvideo.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api(value = "用户注册登陆登出接口")
public class RegisterLoginController extends BasicController {
    @Autowired
    private UserService mUserService;

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public MouseShortVideoResult register(@RequestBody Users user) throws Exception {
        System.out.println(" Request Register Interface");
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return MouseShortVideoResult.errorMsg("用户名或密码不能为空");
        }
        boolean exits = mUserService.queryUserNameIsExits(username);
        if (exits) {
            return MouseShortVideoResult.errorMsg("用户名已存在");
        }
        user.setNickname(username);
        user.setPassword(MD5Utils.getMD5Str(password));
        user.setFansCounts(0);
        user.setFollowCounts(0);
        user.setReceiveLikeCounts(0);
        mUserService.saveUser(user);
        user.setPassword("");
        // 将当前会话保存至Redis
        return MouseShortVideoResult.ok(setUserRedisSessionToken(user));
    }

    @PostMapping("/login")
    @ApiOperation("用户登陆")
    public MouseShortVideoResult login(@RequestBody Users user) throws Exception {
        user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
        if (mUserService.login(user)) {
            user.setPassword("");
            // 将当前会话保存至Redis
            return MouseShortVideoResult.ok(setUserRedisSessionToken(user));
        } else {
            return MouseShortVideoResult.errorMsg("用户名或密码错误");
        }
    }

    @PostMapping("/logout")
    @ApiOperation("用户注销")
    public MouseShortVideoResult logout(@RequestParam(name = "userId") String userId) throws Exception {
        System.out.println("LogOut : " + userId);
        mRedisOperator.del(USER_REDIS_SESSTION + ":" + userId);
        return MouseShortVideoResult.ok();
    }
    private UserVo setUserRedisSessionToken(Users userModel) {
        String uniqueToken = UUID.randomUUID().toString();
        mRedisOperator.set(USER_REDIS_SESSTION + ":" + userModel.getId(), uniqueToken, 1000 * 60 * 30);
        UserVo userVO = new UserVo();
        BeanUtils.copyProperties(userModel, userVO);
        userVO.setUserToken(uniqueToken);
        return userVO;
    }
}
