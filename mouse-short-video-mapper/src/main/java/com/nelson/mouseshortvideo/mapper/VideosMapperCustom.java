package com.nelson.mouseshortvideo.mapper;

import com.nelson.mouseshortvideo.pojo.Videos;
import com.nelson.mouseshortvideo.utils.MyMapper;
import com.nelson.mouseshortvideo.vo.VideoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideosMapperCustom extends MyMapper<VideoVo> {
    /**
     * @Description: 条件查询所有视频列表
     */
    public List<VideoVo> queryAllVideos(@Param("videoDesc") String videoDesc,
                                       @Param("userId") String userId);

    /**
     * @Description: 查询关注的视频
     */
    public List<VideoVo> queryMyFollowVideos(String userId);

    /**
     * @Description: 查询点赞视频
     */
    public List<VideoVo> queryMyLikeVideos(@Param("userId") String userId);

    /**
     * @Description: 对视频喜欢的数量进行累加
     */
    public void addVideoLikeCount(String videoId);

    /**
     * @Description: 对视频喜欢的数量进行累减
     */
    public void reduceVideoLikeCount(String videoId);
}
