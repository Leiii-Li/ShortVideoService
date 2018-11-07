package com.nelson.mouseshortvideo.service;

import com.nelson.mouseshortvideo.pojo.Bgm;
import com.nelson.mouseshortvideo.pojo.Comments;
import com.nelson.mouseshortvideo.pojo.Videos;
import com.nelson.mouseshortvideo.vo.CommentsVO;

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

    /**
     * @Description: 用户喜欢/点赞视频
     */
    public void userLikeVideo(String userId, String videoId, String videoCreaterId);

    /**
     * @Description: 用户不喜欢/取消点赞视频
     */
    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId);

    /**
     * @Description: 查询用户是否喜欢点赞视频
     */
    public boolean isUserLikeVideo(String userId, String videoId);


    /**
     * @Description: 留言分页
     */
    public List<CommentsVO> getAllComments(String videoId);
}
