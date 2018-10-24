package com.nelson.mouseshortvideo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nelson.mouseshortvideo.mapper.VideosMapperCustom;
import com.nelson.mouseshortvideo.utils.PagedResult;
import com.nelson.mouseshortvideo.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoVoServiceImpl implements VideoVoService {
    @Autowired
    VideosMapperCustom mCustomVideosMapper;

    @Override
    public PagedResult queryAll(Integer pageIndex, Integer pageCount) {
        PageHelper.startPage(pageIndex,pageCount);
        List<VideoVo> videoVos = mCustomVideosMapper.queryAllVideos();
        PageInfo<VideoVo> pageList   = new PageInfo<>(videoVos);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(pageIndex);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(videoVos);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }
}
