package com.zhb.nettychat.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public interface SecurityService {

    void login(String username, String password, HttpSession session);
}
