package com.zhb.nettychat.dao;

import com.zhb.nettychat.model.po.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    List<UserInfo> getuserinfo(@Param("userId") String userId);

    List<GroupInfo> getgroupinfo(@Param("userId") String userId);

    List<GroupInfo> getgroup(@Param("userId") String userId);

    UserInfo checkUser(@Param("username") String username, @Param("password") String password);

    String getUserByName(@Param("username") String username);

    int gettotaluser();


}
