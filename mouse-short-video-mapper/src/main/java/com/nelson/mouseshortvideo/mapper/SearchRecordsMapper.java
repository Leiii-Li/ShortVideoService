package com.nelson.mouseshortvideo.mapper;

import com.nelson.mouseshortvideo.pojo.SearchRecords;
import com.nelson.mouseshortvideo.utils.MyMapper;

import java.util.List;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
    List<SearchRecords> queryAll();
}