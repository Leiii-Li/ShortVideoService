package com.nelson.mouseshortvideo.interceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nelson.mouseshortvideo.utils.JsonUtils;
import com.nelson.mouseshortvideo.utils.MouseShortVideoResult;
import com.nelson.mouseshortvideo.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class MiniInterceptor implements HandlerInterceptor {

    @Autowired
    public RedisOperator redis;

    protected static final String USER_REDIS_SESSTION = "USER_REDIS_SESSTION";

    /**
     * 拦截请求，在controller调用之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object arg2) throws Exception {
        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");

        System.out.print("进入拦截 ： " + request.getRequestURI() + " UserId : " + userId + "  UserToken : " + userToken);

        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)) {
            redis.get(USER_REDIS_SESSTION + ":" + userId);
            String uniqueToken = redis.get(USER_REDIS_SESSTION + ":" + userId);
            if (StringUtils.isEmpty(uniqueToken) && StringUtils.isBlank(uniqueToken)) {
                System.out.println("请登录...");
                returnErrorResponse(response, new MouseShortVideoResult().errorTokenMsg("请登录..."));
                return false;
            } else {
                if (!uniqueToken.equals(userToken)) {
                    System.out.println("账号被挤出...");
                    returnErrorResponse(response, new MouseShortVideoResult().errorTokenMsg("账号被挤出..."));
                    return false;
                }
            }
        } else {
            System.out.println("请登录...");
            returnErrorResponse(response, new MouseShortVideoResult().errorTokenMsg("请登录..."));
            return false;
        }
        // 更新过期时间
        redis.set(USER_REDIS_SESSTION + ":" + userId, userToken, 1000 * 60 * 30);

        /**
         * 返回 false：请求被拦截，返回
         * 返回 true ：请求OK，可以继续执行，放行
         */
        return true;
    }

    public void returnErrorResponse(HttpServletResponse response, MouseShortVideoResult result)
            throws IOException, UnsupportedEncodingException {
        OutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 请求controller之后，渲染视图之前
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
    }

    /**
     * 请求controller之后，视图渲染之后
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

}
