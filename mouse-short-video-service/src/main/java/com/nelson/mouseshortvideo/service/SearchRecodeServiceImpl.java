package com.nelson.mouseshortvideo.service;

import com.nelson.mouseshortvideo.mapper.SearchRecordsMapper;
import com.nelson.mouseshortvideo.pojo.SearchRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchRecodeServiceImpl implements SearchRecodeService {
    @Autowired
    private SearchRecordsMapper mSearchRecrdMapper;


    @Override
    public List<SearchRecords> queryAll() {
        return mSearchRecrdMapper.queryAll();
    }
}
