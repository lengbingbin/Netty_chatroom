package com.zhb.nettychat.controller;

import com.zhb.nettychat.dao.impl.GroupInfoDaoImpl;
import com.zhb.nettychat.model.po.GroupInfo;
import com.zhb.nettychat.model.po.UserInfo;
import com.zhb.nettychat.service.ChatService;
import com.zhb.nettychat.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    private Map<String, Object> result = new HashMap<String, Object>();
    @Autowired
    private GroupInfoDaoImpl groupInfoDao;


    @RequestMapping(value = "/chatroom", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public String chatroom() {
        return "/chatroom";
    }


    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(value = "/userInfo", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> getUserInfo() {
        Object userId = Constant.USERS_TOKEN;
        groupInfoDao.loadGroupInfo();
        List<UserInfo> friendList = chatService.getUserInfo((String) userId);
        List<GroupInfo> groupInfoList = chatService.getGroupInfo((String) userId);
        UserInfo user = chatService.checkUser(Constant.USERS_NAME, Constant.USERS_PWD);
        result.put("friendList", friendList);
        result.put("userInfo", user);
        result.put("groupInfoList", groupInfoList);
        result.put("result", true);
        return result;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/gettotaluser", method = RequestMethod.GET)
    @ResponseBody        //用于转换对象为JSON
    public Map<String, Object> gettotaluser() {
        Map<String, Object> map = new HashMap<>();
        int total = chatService.gettotaluser();
        System.out.println("total:" + total);
        map.put("msg", "获取成功");
        map.put("result", true);
        map.put("total", total);
        return map;
    }

}
