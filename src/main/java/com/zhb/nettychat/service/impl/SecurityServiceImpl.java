package com.zhb.nettychat.service.impl;

import com.zhb.nettychat.dao.UserMapper;
import com.zhb.nettychat.model.po.UserInfo;
import com.zhb.nettychat.service.SecurityService;
import com.zhb.nettychat.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void login(String username, String password, HttpSession session) {
        UserInfo userInfo = userMapper.checkUser(username, password);
        session.setAttribute(Constant.USER_TOKEN, userInfo.getUserId());
        Constant.USERS_TOKEN = userInfo.getUserId();
    }
}
