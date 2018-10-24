package com.nelson.mouseshortvideo.controller;

import com.nelson.mouseshortvideo.pojo.Users;
import com.nelson.mouseshortvideo.utils.RedisOperator;
import com.nelson.mouseshortvideo.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class BasicController {
    protected static final String USER_REDIS_SESSTION = "USER_REDIS_SESSTION";
    protected static final int REDIS_KEY_TIMEOUT = 1000 * 60 * 30;
    protected static final String FILE_SPACE = "E:/mouse_short_video";
    protected static final String FFMPEG_EXE = "E:\\ffmpeg\\bin\\ffmpeg.exe";
    @Autowired
    public RedisOperator mRedisOperator;
}
