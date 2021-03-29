package com.zhb.nettychat.web.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhb.nettychat.model.vo.ResponseJson;
import com.zhb.nettychat.service.ChatService;
import com.zhb.nettychat.util.ChatRedisUtils;
import com.zhb.nettychat.util.ChatType;
import com.zhb.nettychat.util.Constant;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Qualifier("webSocketServerHandler")
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServerHandler.class);

    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatRedisUtils chatRedisUtils;

    /**
     * 描述：读取完连接的消息后，对消息进行处理。
     * 这里主要是处理WebSocket请求
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame webSocketFrame) throws Exception {
        //关闭请求
        if (webSocketFrame instanceof CloseWebSocketFrame) {
            //
            WebSocketServerHandshaker handshaker =
                    Constant.webSocketHandshakerMap.get(ctx.channel().id().asLongText());
            if (handshaker == null) {
                sendErrorMessage(ctx, "不存在的客户端连接！");
            } else {
                //
                handshaker.close(ctx.channel(), ((CloseWebSocketFrame) webSocketFrame).retain());
            }
            return;
        }
        //ping请求
        if (webSocketFrame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(webSocketFrame.content().retain()));
            return;
        }
        //只支持文本格式，不支持二进制消息
        if (!(webSocketFrame instanceof TextWebSocketFrame)) {
            sendErrorMessage(ctx, "仅支持文本格式，不支持二进制消息");
            return;
        }
        //服务端收到新消息
        String request = ((TextWebSocketFrame) webSocketFrame).text();
        LOGGER.info("服务端收到了信息消息： " + request);
        JSONObject param = null;
        try {
            param = JSONObject.parseObject(request);
        } catch (Exception e) {
            sendErrorMessage(ctx, "JSON字符串转换出错！");
            e.printStackTrace();
        }
        if (param == null) {
            sendErrorMessage(ctx, "参数为空！");
            return;
        }

        String type = (String) param.get("type");
        switch (type) {
            case "REGISTER":
                chatService.register(param, ctx);
                break;
            case "SINGLE_SENDING":
                chatService.singleSend(param, ctx);
                break;
            case "GROUP_SENDING":
                chatService.groupSend(param, ctx);
                break;
            case "FILE_MSG_SINGLE_SENDING":
                chatService.FileMsgSingleSend(param, ctx);
                break;
            case "FILE_MSG_GROUP_SENDING":
                chatService.FileMsgGroupSend(param, ctx);
                break;
            default:
                chatService.typeError(ctx);
                break;
        }
        readRedisCache();
    }


    public void readRedisCache() {
        if (chatRedisUtils.hgetCacheChatMessage(Constant.USERS_ID) != null) {
            Map<Object, Object> chatMap = chatRedisUtils.hgetCacheChatMessage(Constant.USERS_ID);
            //System.out.println("缓存中的信息： " + chatMap);
            String msg;
            for (Map.Entry<Object, Object> entry : chatMap.entrySet()) {
                //做处理
                String item = entry.getKey() + "";
                StringBuilder key = new StringBuilder();
                //如果是文件
                if (item.substring(0, 4).equals("file")) {
                    StringBuilder originalFilename = new StringBuilder();
                    StringBuilder fileSize = new StringBuilder();
                    StringBuilder fileUrl = new StringBuilder();
                    //找出key-toUserId的值
                    for (int i = 4; i < item.length(); i++) {
                        if (item.charAt(i) != '-') {
                            key.append(item.charAt(i));
                        } else {
                            break;
                        }
                    }
                    msg = entry.getValue() + "";
                    //找出文件的值
                    for (int i = 4; i < msg.length(); i++) {
                        while (msg.charAt(i) != '-') {
                            originalFilename.append(msg.charAt(i));
                            i++;
                        }
                        i++;
                        while (msg.charAt(i) != '+') {
                            fileSize.append(msg.charAt(i));
                            i++;
                        }
                        i++;
                        while (i < msg.length()) {
                            fileUrl.append(msg.charAt(i));
                            i++;
                        }
                    }
                    //System.out.println("ori: " + originalFilename + " fileSize; " + fileSize + "Url: " + fileUrl);
                    String responseJson = new ResponseJson().success()
                            .setData("fromUserId", key)
                            .setData("originalFilename", originalFilename)
                            .setData("fileSize", fileSize)
                            .setData("fileUrl", fileUrl)
                            .setData("type", ChatType.FILE_MSG_SINGLE_SENDING)
                            .toString();
                    sendMessage(Constant.USERS_ID, responseJson);
                    //如果是文字或者表情
                } else {
                    for (int i = 0; i < item.length(); i++) {
                        if (item.charAt(i) != '-') {
                            key.append(item.charAt(i));
                        } else {
                            break;
                        }
                    }
                    msg = entry.getValue() + "";
                    String responseJson = new ResponseJson().success()
                            .setData("fromUserId", key)
                            .setData("content", msg)
                            .setData("type", ChatType.SINGLE_SENDING)
                            .toString();
                    sendMessage(Constant.USERS_ID, responseJson);
                }


            }
            chatRedisUtils.deleteCacheChatMessage(Constant.USERS_ID);
        }
    }

    /**
     * 发生错误时发送错误信息
     *
     * @param ctx channelcontext
     * @param msg 错误信息
     */
    private void sendErrorMessage(ChannelHandlerContext ctx, String msg) {
        String responseJson = new ResponseJson()
                .error(msg)
                .toString();
        ctx.channel().writeAndFlush(new TextWebSocketFrame(responseJson));
    }

    /**
     * 给指定用户发内容
     * 后续可以掉这个方法推送消息给客户端
     */
    public void sendMessage(String toUserId, String responseJson) {
        ChannelHandlerContext toUserCtx = Constant.onlineUserMap.get(toUserId);
        toUserCtx.channel().writeAndFlush(new TextWebSocketFrame(responseJson));
    }

    /**
     * 描述：处于不活动的状态，客户端断开连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        chatService.remove(ctx);
    }

    /**
     * 异常处理：关闭channel
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
