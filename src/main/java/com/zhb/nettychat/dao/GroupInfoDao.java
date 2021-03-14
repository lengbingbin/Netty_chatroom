package com.zhb.nettychat.dao;

import com.zhb.nettychat.model.po.GroupInfo;

public interface GroupInfoDao {

    void loadGroupInfo();

    GroupInfo getByGroupId(String toGroupId);
}
