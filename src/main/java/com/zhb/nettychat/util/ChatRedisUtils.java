package com.zhb.nettychat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 从Redis工具类抽一层本项目需要使用的Redis方法
 */
@Component
public class ChatRedisUtils {

    private final Logger log = LoggerFactory.getLogger(ChatRedisUtils.class);

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 功能描述：将JavaBean对象的信息缓存进Redis
     *
     * @param chatVO 聊天信息JavaBean
     * @return 是否保存成功
     */
    public boolean saveCacheChatMessage(String key,/* ChatVO*/String chatVO) {
        //判断key是否存在
        if (redisUtils.hasKey(key)) {
            //将javabean对象添加到缓存的list中
            long redisSize = redisUtils.lGetListSize(key);
            System.out.println("redis当前数据条数" + redisSize);
            Long index = redisUtils.rightPushValue(key, chatVO);
            System.out.println("redis执行rightPushList返回值：" + index);
            return redisSize < index;
        } else {
            //不存在key时，将chatVO存进缓存，并设置过期时间
            boolean isCache = redisUtils.lSet(key, chatVO);
            //保存成功，设置过期时间
            if (isCache) {
                redisUtils.expire(key, 3L, TimeUnit.DAYS);
            }
            return isCache;
        }
    }

    /**
     * 功能描述：将JavaBean对象的信息缓存进Redis
     *
     * @param chatVO 聊天信息JavaBean
     * @return 是否保存成功
     */
    public boolean hsaveCacheChatMessage(String key, String item/* ChatVO*/, String chatVO) {
        //判断key是否存在
        if (redisUtils.hasKey(key)) {
            //将javabean对象添加到缓存的list中
            //long redisSize = redisUtils.lGetListSize(key);
            //System.out.println("redis当前数据条数" + redisSize);
            boolean res = redisUtils.hset(key, item, chatVO);
            return res;
        } else {
            //不存在key时，将chatVO存进缓存，并设置过期时间
            boolean isCache = redisUtils.hset(key, item, chatVO, 3L);
            //保存成功，设置过期时间
            if (isCache) {
                redisUtils.expire(key, 3L, TimeUnit.DAYS);
            }
            return isCache;
        }
    }

    /**
     * 功能描述：从缓存中读取聊天信息
     *
     * @param key 缓存聊天信息的键
     * @return 缓存中聊天信息list
     */
    public List<Object> getCacheChatMessage(String key) {
        List<Object> chatList = null;
        //判断key是否存在
        if (redisUtils.hasKey(key)) {
            //读取缓存中的聊天内容
            chatList = redisUtils.lGet(key, 0, redisUtils.lGetListSize(key));
        } else {
            System.out.println("此次解答无聊天信息");
            log.info("redis缓存中无此键值:" + key);
        }
        return chatList;
    }

    /**
     * 功能描述：从缓存中读取聊天信息
     *
     * @param key 缓存聊天信息的键
     * @return 缓存中聊天信息list
     */
    public Map<Object, Object> hgetCacheChatMessage(String key) {
        Map<Object, Object> chatMap = null;
        //判断key是否存在
        if (redisUtils.hasKey(key)) {
            //读取缓存中的聊天内容
            chatMap = redisUtils.hmget(key);
        } else {
            System.out.println("此次解答无聊天信息");
            log.info("redis缓存中无此键值:" + key);
        }
        return chatMap;
    }

    /**
     * 功能描述： 在缓存中删除聊天信息
     *
     * @param key 缓存聊天信息的键
     */
    public void deleteCacheChatMessage(String key) {
        //判断key是否存在
        if (redisUtils.hasKey(key)) {
            redisUtils.del(key);
        }
    }

    /**
     * 功能描述： 创建已发送消息房间号
     * 根据ChatVO中的fromUserId和toUserId生成聊天房间号：问题id-小号用户id-大号用户id
     * 例如“1-2”： 小号在前，大号在后；保证房间号唯一
     *
     * @param fromUserId 发送方id
     */
    public String createChatNumber(Integer fromUserId) {
        StringBuilder key = new StringBuilder();
        TimeUtils timeUtils = new TimeUtils();
        key = key.append(fromUserId.toString()).append("-").append(timeUtils.getCurrentTime());
        return key.toString();
    }

    /**
     * 功能描述：创建离线聊天记录的房间号（redis的键）
     * 拼接方式：发送方用户id-签证标识
     *
     * @param toUserId 发送方用户id
     * @return 用户离线消息房间号
     */
    public String createOffLineNumber(Integer toUserId) {
        //return toUserId + "-" + MsgSignFlagEnum.unsign.type;
        return toUserId.toString();
    }

    /**
     * 功能描述：从redis读取缓存信息集合(List<Object>),并且存储到新的键中  oldKey——>newKey
     */
    public void signQuestionMessageList(String oldKey, String newKey) {
        redisUtils.rightPushList(newKey, getCacheChatMessage(oldKey));
    }

    /**
     * 功能描述：从redis读取每一条缓存信息,并且存储到新的键中  oldKey——>newKey
     */
    public void signQuestionMessage(String oldKey, String newKey) {
        redisUtils.rightPushValue(newKey, getCacheChatMessage(oldKey));
    }
}
