package com.serverService;

import com.comment.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author MinZhi
 * @version 1.0
 */
public class LeaveMessageService {
    //留言库
    private static HashSet<Message> leaveDataBase = new HashSet<>();


    /**
     * 留言集合添加信息
      * @param message 添加的信息
     */
    public static void add(Message message){
        leaveDataBase.add(message);
    }

    /**
     * 根据用户id检查是否有留言
     * @param uid 用户id
     */
    public static void leaveMessage(String uid){
        //遍历leaveDB是否有uid的message对象
        Iterator<Message> iterator = leaveDataBase.iterator();
        while (iterator.hasNext()) {
            Message next = iterator.next();
            if (next.getGetter().equals(uid)){
                // 发送
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                            ManageConnectServiceThread.get(uid).getSocket().getOutputStream());
                    objectOutputStream.writeObject(next);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}