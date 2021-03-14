package com.zhb.nettychat.model.po;

import java.util.List;

public class GroupInfo {
    private String groupId;
    private String groupName;
    private String groupAvatarUrl;
    private List<UserInfo> members;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    public GroupInfo(String groupId, String groupAvatarUrl, String groupName, String userId, String userName) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupAvatarUrl = groupAvatarUrl;
        this.userId = userId;
        this.userName = userName;
    }

    public GroupInfo(String groupId, String groupName, String groupAvatarUrl, List<UserInfo> members) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupAvatarUrl = groupAvatarUrl;
        this.members = members;
    }

    public GroupInfo(String groupId, String groupName, String groupAvatarUrl) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupAvatarUrl = groupAvatarUrl;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupAvatarUrl() {
        return groupAvatarUrl;
    }

    public void setGroupAvatarUrl(String groupAvatarUrl) {
        this.groupAvatarUrl = groupAvatarUrl;
    }

    /**
     * something wrong
     *
     * @return
     */
    public List<UserInfo> getMembers() {
        return members;
    }

    public void setMembers(List<UserInfo> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "GroupInfo{" +
                "groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupAvatarUrl='" + groupAvatarUrl + '\'' +
                ", members=" + members +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
