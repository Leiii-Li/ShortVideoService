package com.nelson.mouseshortvideo.service;

import com.nelson.mouseshortvideo.pojo.Comments;
import com.nelson.mouseshortvideo.pojo.Videos;
import com.nelson.mouseshortvideo.utils.PagedResult;
import com.nelson.mouseshortvideo.vo.CommentsVO;
import com.nelson.mouseshortvideo.vo.VideoVo;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface VideoVoService {
    public PagedResult queryAll(Integer pageIndex, Integer pageCount,String desc,String userId);
    public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize);
    public void saveComment(Comments comment);
}
