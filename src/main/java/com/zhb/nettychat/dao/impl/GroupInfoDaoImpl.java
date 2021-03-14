package com.zhb.nettychat.dao.impl;

import com.zhb.nettychat.dao.GroupInfoDao;
import com.zhb.nettychat.model.po.GroupInfo;
import com.zhb.nettychat.model.po.UserInfo;
import com.zhb.nettychat.service.ChatService;
import com.zhb.nettychat.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class GroupInfoDaoImpl implements GroupInfoDao {

    @Autowired
    private ChatService chatService;

    @Override
    public void loadGroupInfo() {
        List<GroupInfo> groupInfoList = chatService.getGroup(Constant.USERS_ID);
        GroupInfo group = groupInfoList.get(0);
        List<UserInfo> members = new ArrayList<UserInfo>();
        for (int i = 0; i < groupInfoList.size(); i++) {
            GroupInfo user = groupInfoList.get(i);
            UserInfo userInfo = new UserInfo(user.getUserId(), user.getUserName());
            members.add(userInfo);
        }
        GroupInfo groupInfo = new GroupInfo(group.getGroupId(), group.getGroupName(), group.getGroupAvatarUrl(), members);
        Constant.groupInfoMap.put(groupInfo.getGroupId(), groupInfo);
    }


    @Override
    public GroupInfo getByGroupId(String toGroupId) {
        return Constant.groupInfoMap.get(toGroupId);
    }
}
