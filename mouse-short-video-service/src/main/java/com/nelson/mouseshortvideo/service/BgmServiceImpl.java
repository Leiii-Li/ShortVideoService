package com.nelson.mouseshortvideo.service;

import com.nelson.mouseshortvideo.mapper.BgmMapper;
import com.nelson.mouseshortvideo.mapper.UsersMapper;
import com.nelson.mouseshortvideo.pojo.Bgm;
import com.nelson.mouseshortvideo.pojo.Users;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class BgmServiceImpl implements BgmService {
    @Autowired
    private BgmMapper mBgmMapper;

    @Override
    public List<Bgm> queryBgmList() {
        List<Bgm> bgmList = mBgmMapper.selectAll();
        if (bgmList == null) {
            bgmList = new ArrayList<>();
        }
        return bgmList;
    }

    @Override
    public Bgm queryBgmById(String bgmId) {
        return mBgmMapper.selectByPrimaryKey(bgmId);
    }
}
