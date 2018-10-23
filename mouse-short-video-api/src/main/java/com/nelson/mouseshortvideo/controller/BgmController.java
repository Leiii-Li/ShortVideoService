package com.nelson.mouseshortvideo.controller;

import com.nelson.mouseshortvideo.service.BgmService;
import com.nelson.mouseshortvideo.utils.MouseShortVideoResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "背景音乐查询接口")
@RequestMapping("/bgm")
public class BgmController {

    @Autowired
    BgmService mBgmService;

    @ApiOperation(value = "获取背景音乐列表", notes = "获取背景音乐列表的接口")
    @PostMapping("/list")
    public MouseShortVideoResult list() {
        return MouseShortVideoResult.ok(mBgmService.queryBgmList());
    }

}
