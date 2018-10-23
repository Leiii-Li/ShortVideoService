package com.nelson.mouseshortvideo.service;

import com.nelson.mouseshortvideo.pojo.Bgm;
import com.nelson.mouseshortvideo.pojo.Videos;

import java.util.List;

public interface VideoService {
    /**
     * @Description: 保存视频
     */
    public String saveVideo(Videos video);

    /**
     * @Description: 修改视频的封面
     */
    public void updateVideo(String videoId, String coverPath);
}
