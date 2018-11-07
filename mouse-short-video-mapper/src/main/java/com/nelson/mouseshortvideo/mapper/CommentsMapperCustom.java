package com.nelson.mouseshortvideo.mapper;

import java.util.List;

import com.nelson.mouseshortvideo.pojo.Comments;
import com.nelson.mouseshortvideo.utils.MyMapper;
import com.nelson.mouseshortvideo.vo.CommentsVO;
import org.apache.ibatis.annotations.Param;

public interface CommentsMapperCustom extends MyMapper<Comments> {
    public List<CommentsVO> queryComments(@Param("videoId") String videoId);
}