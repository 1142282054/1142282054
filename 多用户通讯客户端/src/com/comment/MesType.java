package com.comment;

/**
 * @author MinZhi
 * @version 1.0
 */
public interface MesType {
    String MESSAGE_LOGIN_SUCCEED = "1";//登录成功
    String MESSAGE_LOGIN_FAIL = "2";//登录失败
    String MESSAGE_COMMENT_MES = "3";//普通信息包
    String MESSAGE_GET_ONLINE_FRIEND = "4";//请求获取在线用户列表
    String MESSAGE_RET_ONLINE_FRIEND = "5";//发送用户在线列表
    String MESSAGE_CLIENT_EXIT = "6";//客户端请求退出
    String MESSAGE_GROUP_MES = "7";//群发消息
    String MESSAGE_SEND_FILE = "8";//请求发送文件
    String MESSAGE_LEAVE_MES = "9";//离线留言
}
