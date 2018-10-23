package com.nelson.mouseshortvideo.service;

import com.nelson.mouseshortvideo.mapper.UsersMapper;
import com.nelson.mouseshortvideo.mapper.VideosMapper;
import com.nelson.mouseshortvideo.pojo.Users;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideosMapper mVideoMapper;

}
