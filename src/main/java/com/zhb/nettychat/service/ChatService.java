package com.zhb.nettychat.service;

import com.alibaba.fastjson.JSONObject;
import com.zhb.nettychat.model.po.*;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.math3.analysis.function.Add;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatService {

    void register(JSONObject param, ChannelHandlerContext ctx);

    void singleSend(JSONObject param, ChannelHandlerContext ctx);

    void groupSend(JSONObject param, ChannelHandlerContext ctx);

    void FileMsgSingleSend(JSONObject param, ChannelHandlerContext ctx);

    void FileMsgGroupSend(JSONObject param, ChannelHandlerContext ctx);

    void remove(ChannelHandlerContext ctx);

    void typeError(ChannelHandlerContext ctx);

    List<UserInfo> getUserInfo(String userId);

    List<GroupInfo> getGroupInfo(String userId);

    List<GroupInfo> getGroup(String userId);

    UserInfo checkUser(String username, String password);

    String getUserByName(String username);

    int gettotaluser();
}
