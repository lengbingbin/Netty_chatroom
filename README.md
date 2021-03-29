# 前言

该项目是基于Netty的聊天室实战项目，提供常规聊天功能，可供学习使用

# 项目简介

技术选型：

  后端：Springboot + Netty + Mysql
  
  前端：html + javascript+css
  
# 功能：

1.私聊，包括文字和表情

2.群聊

3.文件传送

# 效果图

登录界面

![image](https://user-images.githubusercontent.com/37926364/111079509-3ee6b500-8535-11eb-8d97-04584fcca6f4.png)

聊天室界面

![image](https://user-images.githubusercontent.com/37926364/111079455-0c3cbc80-8535-11eb-89d3-008fb26a56f8.png)

# 使用方式

1.在.yml文件中将数据库连接的相关信息，如username和password修改为自己本地数据库的用户名和密码，若端口号3306也不同，也需要一同修改

2.在tb_user表中自行设置用户的用户名和密码，以便登录验证，头像图片路径存储举例："../img/avatar/Member001.jpg"

3.默认端口为8091，如有需要更改，在.yml文件中更改即可

4.登录后用户信息采用的是传统的session存储，如果需要更改为JWT验证，项目中已有相关代码，可自行替换

