package com.zhb.nettychat.service;

import com.zhb.nettychat.model.vo.ResponseJson;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public interface SecurityService {

    void login(String username, String password, HttpSession session);

    ResponseJson logout(HttpSession session);
}
