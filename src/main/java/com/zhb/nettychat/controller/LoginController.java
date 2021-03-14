package com.zhb.nettychat.controller;

import com.zhb.nettychat.model.po.UserInfo;
import com.zhb.nettychat.service.ChatService;
import com.zhb.nettychat.service.SecurityService;
import com.zhb.nettychat.util.Constant;
import com.zhb.nettychat.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/chat")
public class LoginController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private SecurityService securityService;
    private Map<String, Object> result = new HashMap<String, Object>();

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    /**
     * 登录验证
     *
     * @param data 前端传回来的数据
     * @return 返回用户相关信息
     */
    @PostMapping(value = "/login")
    @CrossOrigin(origins = "*", maxAge = 3600)
    @ResponseBody
    public Map<String, Object> login(HttpSession session, @RequestBody Map<String, String> data) {
        System.out.println("data：" + data);
        Map<String, String> payload = new HashMap<>();
        String username = data.get("username");
        String password = data.get("password");
        securityService.login(username, password, session);
        UserInfo user = chatService.checkUser(username, password);
        String getUserName = chatService.getUserByName(username);
        if (getUserName == null) {
            result.put("result", false);
            result.put("msg", "该用户不存在");
        } else if (user == null) {
            result.put("result", false);
            result.put("msg", "密码输入错误");
        } else {
            String token = JWTUtils.getToken(payload);
            Constant.USERS_NAME = username;
            Constant.USERS_PWD = password;
            Constant.USERS_ID = user.getUserId();
            result.put("token", token);
            result.put("user", user);
            result.put("result", true);
        }
        return result;
    }

}

