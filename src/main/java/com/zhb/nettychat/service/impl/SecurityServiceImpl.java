package com.zhb.nettychat.service.impl;

import com.zhb.nettychat.dao.UserMapper;
import com.zhb.nettychat.model.po.UserInfo;
import com.zhb.nettychat.model.vo.ResponseJson;
import com.zhb.nettychat.service.SecurityService;
import com.zhb.nettychat.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.MessageFormat;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserMapper userMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public void login(String username, String password, HttpSession session) {
        UserInfo userInfo = userMapper.checkUser(username, password);
        session.setAttribute(Constant.USER_TOKEN, userInfo.getUserId());
        Constant.USERS_TOKEN = userInfo.getUserId();
    }

    @Override
    public ResponseJson logout(HttpSession session) {
        Object userId = Constant.USERS_ID;
        if (userId == null) {
            return new ResponseJson().error("请先登录！");
        }
        Constant.USERS_ID = "userId";
        LOGGER.info(MessageFormat.format("userId为 {0} 的用户已注销登录!", userId));
        return new ResponseJson().success();
    }
}
