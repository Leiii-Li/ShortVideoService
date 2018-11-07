package com.nelson.mouseshortvideo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nelson.mouseshortvideo.mapper.CommentsMapper;
import com.nelson.mouseshortvideo.mapper.VideosMapperCustom;
import com.nelson.mouseshortvideo.pojo.Comments;
import com.nelson.mouseshortvideo.utils.PagedResult;
import com.nelson.mouseshortvideo.vo.CommentsVO;
import com.nelson.mouseshortvideo.vo.VideoVo;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class VideoVoServiceImpl implements VideoVoService {
    @Autowired
    VideosMapperCustom mCustomVideosMapper;

    @Autowired
    Sid mSid;

    @Autowired
    CommentsMapper mCommentsMapperl;

    @Override
    public PagedResult queryAll(Integer pageIndex, Integer pageCount, String videoDesc, String userId) {
        PageHelper.startPage(pageIndex, pageCount);
        List<VideoVo> videoVos = mCustomVideosMapper.queryAllVideos(videoDesc, userId);
        PageInfo<VideoVo> pageList = new PageInfo<>(videoVos);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(pageIndex);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(videoVos);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideoVo> list = mCustomVideosMapper.queryMyLikeVideos(userId);

        PageInfo<VideoVo> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    @Override
    public void saveComment(Comments comment) {
        String id = mSid.nextShort();
        comment.setId(id);
        comment.setCreateTime(new Date());
        mCommentsMapperl.insert(comment);
    }
}
