package com.zhb.nettychat.model.po;

import java.util.List;

public class UserInfo {


    private Integer id;
    private String userId;
    private String username;
    private String password;
    private String avatarUrl;
    private String sex;
    private Long man;
    private Long woman;
    private Long total;
    private List<UserInfo> friendList;
    private List<GroupInfo> groupList;


    public Long getMan() {
        return man;
    }

    public void setMan(Long man) {
        this.man = man;
    }

    public Long getWoman() {
        return woman;
    }

    public void setWoman(Long woman) {
        this.woman = woman;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }


    private int[] friendId;


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserInfo() {

    }

    public UserInfo(Long total, Long man, Long woman) {
        this.total = total;
        this.man = man;
        this.woman = woman;
    }

    public UserInfo(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public UserInfo(Integer id, String userId, String username, String password) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public UserInfo(String userId, String username, String avatarUrl) {
        this.userId = userId;
        this.username = username;
        this.avatarUrl = avatarUrl;
    }

    public UserInfo(Integer id, String userId, String username, String password, String avatarUrl, String sex) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.sex = sex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<UserInfo> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<UserInfo> friendList) {
        this.friendList = friendList;
    }

    public List<GroupInfo> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupInfo> groupList) {
        this.groupList = groupList;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
