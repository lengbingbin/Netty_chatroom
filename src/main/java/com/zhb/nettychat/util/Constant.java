package com.zhb.nettychat.util;

import com.zhb.nettychat.model.po.GroupInfo;
import com.zhb.nettychat.model.po.UserInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Constant {

    public static final String USER_TOKEN = "userId";

    public static String USERS_TOKEN = "userId";

    public static String USERS_NAME = "userName";

    public static String USERS_PWD = "userPwd";

    public static String USERS_ID = "userId";

    public static Map<String, WebSocketServerHandshaker> webSocketHandshakerMap =
            new ConcurrentHashMap<String, WebSocketServerHandshaker>();

    public static Map<String, ChannelHandlerContext> onlineUserMap =
            new ConcurrentHashMap<String, ChannelHandlerContext>();

    public static Map<String, GroupInfo> groupInfoMap =
            new ConcurrentHashMap<String, GroupInfo>();

    public static Map<String, UserInfo> userInfoMap =
            new HashMap<String, UserInfo>();
}
