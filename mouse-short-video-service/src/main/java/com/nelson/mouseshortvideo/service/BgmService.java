package com.nelson.mouseshortvideo.service;

import com.nelson.mouseshortvideo.pojo.Bgm;
import com.nelson.mouseshortvideo.pojo.Users;

import java.util.List;

public interface BgmService {
    List<Bgm> queryBgmList();

    Bgm queryBgmById(String bgmId);
}
