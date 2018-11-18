package com.nelson.mouseshortvideo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "欢迎接口")
public class IndexController {

    @ApiOperation("欢迎接口")
    @RequestMapping("/")
    public String index(){
        return "WellCome To Rat Short Video,Enjoy The Mini Application";
    }
}
