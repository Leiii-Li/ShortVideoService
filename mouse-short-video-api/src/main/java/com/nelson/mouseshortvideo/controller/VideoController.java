package com.nelson.mouseshortvideo.controller;

import com.nelson.mouseshortvideo.enums.VideoStatusEnum;
import com.nelson.mouseshortvideo.pojo.*;
import com.nelson.mouseshortvideo.service.*;
import com.nelson.mouseshortvideo.utils.FetchVideoCover;
import com.nelson.mouseshortvideo.utils.MergeVideoMp3;
import com.nelson.mouseshortvideo.utils.MouseShortVideoResult;
import com.nelson.mouseshortvideo.utils.PagedResult;
import com.nelson.mouseshortvideo.vo.CommentsVO;
import com.nelson.mouseshortvideo.vo.PublisherVideo;
import com.nelson.mouseshortvideo.vo.UsersVO;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.events.Comment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@Api(value = "用户相关业务接口")
public class VideoController extends BasicController {

    @Autowired
    private BgmService bgmService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;
    @Autowired
    private SearchRecodeService mSearchRecodeService;

    @Autowired
    private VideoVoService mVideosVoService;

    @ApiOperation(value = "上传视频", notes = "上传视频的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "bgmId", value = "背景音乐id", required = false,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoSeconds", value = "背景音乐播放长度", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoWidth", value = "视频宽度", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoHeight", value = "视频高度", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "desc", value = "视频描述", required = false,
                    dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/user/uploadVideo", headers = "content-type=multipart/form-data")
    public MouseShortVideoResult upload(String userId,
                                        String bgmId, double videoSeconds,
                                        int videoWidth, int videoHeight,
                                        String desc,
                                        @ApiParam(value = "短视频", required = true)
                                                MultipartFile file) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return MouseShortVideoResult.errorMsg("用户id不能为空...");
        }

        // 文件保存的命名空间
//		String fileSpace = "C:/imooc_videos_dev";
        // 保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/video";
        String coverPathDB = uploadPathDB;

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        // 文件上传的最终保存路径
        String finalVideoPath = "";
        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                // abc.mp4
                String arrayFilenameItem[] = fileName.split("\\.");
                String fileNamePrefix = "";
                for (int i = 0; i < arrayFilenameItem.length - 1; i++) {
                    fileNamePrefix += arrayFilenameItem[i];
                }
                // fix bug: 解决小程序端OK，PC端不OK的bug，原因：PC端和小程序端对临时视频的命名不同
//				String fileNamePrefix = fileName.split("\\.")[0];

                if (StringUtils.isNotBlank(fileName)) {

                    finalVideoPath = FILE_SPACE + uploadPathDB + "/" + fileName;
                    // 设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);
                    coverPathDB = coverPathDB + "/" + fileNamePrefix + ".jpg";

                    File outFile = new File(finalVideoPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } else {
                return MouseShortVideoResult.errorMsg("上传出错...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MouseShortVideoResult.errorMsg("上传出错...");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        // 判断bgmId是否为空，如果不为空，
        // 那就查询bgm的信息，并且合并视频，生产新的视频
        if (StringUtils.isNotBlank(bgmId)) {
            Bgm bgm = bgmService.queryBgmById(bgmId);
            String mp3InputPath = FILE_SPACE + bgm.getPath();

            MergeVideoMp3 tool = new MergeVideoMp3(FFMPEG_EXE);
            String videoInputPath = finalVideoPath;

            String videoOutputName = UUID.randomUUID().toString() + ".mp4";
            uploadPathDB = "/" + userId + "/video" + "/" + videoOutputName;
            finalVideoPath = FILE_SPACE + uploadPathDB;
            tool.convertor(videoInputPath, mp3InputPath, videoSeconds, finalVideoPath);
        }
        System.out.println("uploadPathDB=" + uploadPathDB);
        System.out.println("finalVideoPath=" + finalVideoPath);
        // 对视频进行截图
        FetchVideoCover videoInfo = new FetchVideoCover(FFMPEG_EXE);
        videoInfo.getCover(finalVideoPath, FILE_SPACE + coverPathDB);
        // 保存视频信息到数据库
        Videos video = new Videos();
        video.setAudioId(bgmId);
        video.setUserId(userId);
        video.setVideoSeconds((float) videoSeconds);
        video.setVideoHeight(videoHeight);
        video.setVideoWidth(videoWidth);
        video.setVideoDesc(desc);
        video.setVideoPath(uploadPathDB);
        video.setCoverPath(coverPathDB);
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setCreateTime(new Date());

        String videoId = videoService.saveVideo(video);

        return MouseShortVideoResult.ok(videoId);
    }

    @ApiOperation(value = "视频查询接口", notes = "视频查询的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页码", required = false,
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "desc", value = "视频描述", required = false,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "视频描述", required = false,
                    dataType = "String", paramType = "query")
    })
    @PostMapping("/queryAll")
    public MouseShortVideoResult queryAllVideos(String desc, String userId, Integer pageIndex) {
        if (pageIndex == null) {
            pageIndex = 1;
        }
        PagedResult pagedResult = mVideosVoService.queryAll(pageIndex, 10, desc, userId);
        return MouseShortVideoResult.ok(pagedResult);
    }

    @PostMapping("/hots")
    public MouseShortVideoResult hotList() {
        List<SearchRecords> recordList = mSearchRecodeService.queryAll();
        List<String> result = new ArrayList<>();
        for (SearchRecords record : recordList) {
            result.add(record.getContent());
        }
        return MouseShortVideoResult.ok(result);
    }


    @PostMapping(value = "/user/userLike")
    public MouseShortVideoResult userLike(String userId, String videoId, String videoCreaterId)
            throws Exception {
        videoService.userLikeVideo(userId, videoId, videoCreaterId);
        return MouseShortVideoResult.ok();
    }

    /**
     * @Description: 我收藏(点赞)过的视频列表
     */
    @PostMapping("/user/showMyLike")
    public MouseShortVideoResult showMyLike(String userId, Integer page, Integer pageSize) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return MouseShortVideoResult.ok();
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 6;
        }
        PagedResult videosList = mVideosVoService.queryMyLikeVideos(userId, page, pageSize);
        return MouseShortVideoResult.ok(videosList);
    }

    @PostMapping(value = "/user/userUnLike")
    public MouseShortVideoResult userUnLike(String userId, String videoId, String videoCreaterId) throws Exception {
        videoService.userUnLikeVideo(userId, videoId, videoCreaterId);
        return MouseShortVideoResult.ok();
    }

    @PostMapping(value = "/user/saveComment")
    public MouseShortVideoResult saveComment(@RequestBody Comments comment) {
        mVideosVoService.saveComment(comment);
        return MouseShortVideoResult.ok();
    }

    @PostMapping("/getVideoComments")
    public MouseShortVideoResult getVideoComments(String videoId) {
        List<CommentsVO> comments = videoService.getAllComments(videoId);
        return MouseShortVideoResult.ok(comments);
    }

    @PostMapping("/queryPublisher")
    public MouseShortVideoResult queryPublisher(String loginUserId, String videoId,
                                                String publishUserId) throws Exception {

        if (StringUtils.isBlank(publishUserId)) {
            return MouseShortVideoResult.errorMsg("");
        }

        // 1. 查询视频发布者的信息
        Users userInfo = userService.queryUserInfo(publishUserId);
        UsersVO publisher = new UsersVO();
        BeanUtils.copyProperties(userInfo, publisher);

        // 2. 查询当前登录者和视频的点赞关系
        boolean userLikeVideo = userService.isUserLikeVideo(loginUserId, videoId);

        PublisherVideo bean = new PublisherVideo();
        bean.setPublisher(publisher);
        bean.setUserLikeVideo(userLikeVideo);

        return MouseShortVideoResult.ok(bean);
    }

}
