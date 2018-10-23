package com.nelson.mouseshortvideo.service;

import com.nelson.mouseshortvideo.mapper.UsersMapper;
import com.nelson.mouseshortvideo.mapper.VideosMapper;
import com.nelson.mouseshortvideo.pojo.Users;
import com.nelson.mouseshortvideo.pojo.Videos;
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
    @Autowired
    private Sid mSid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveVideo(Videos video) {

        String id = mSid.nextShort();
        video.setId(id);
        mVideoMapper.insertSelective(video);

        return id;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateVideo(String videoId, String coverPath) {
        Videos video = new Videos();
        video.setId(videoId);
        video.setCoverPath(coverPath);
        mVideoMapper.updateByPrimaryKeySelective(video);
    }
}
