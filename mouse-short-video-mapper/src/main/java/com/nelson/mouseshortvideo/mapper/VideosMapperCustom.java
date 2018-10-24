package com.nelson.mouseshortvideo.mapper;

import com.nelson.mouseshortvideo.utils.MyMapper;
import com.nelson.mouseshortvideo.vo.VideoVo;

import java.util.List;

public interface VideosMapperCustom extends MyMapper<VideoVo> {
    List<VideoVo> queryAllVideos();
}
