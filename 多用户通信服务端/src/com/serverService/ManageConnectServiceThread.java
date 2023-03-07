package com.serverService;

import com.comment.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author MinZhi
 * @version 1.0
 */
public class ManageConnectServiceThread {
    private static HashMap<String,ServerConnectServiceThread> hashMap = new HashMap();
    public static void add(String uid, ServerConnectServiceThread thread){
        hashMap.put(uid,thread);
    }
    public static ServerConnectServiceThread get(String uid){
        return hashMap.get(uid);
    }

    /**
     * 获取在线用户列表
     * @return 在线用户字符串
     */
    public static String getOnLineFriend(){
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> strings = hashMap.keySet();
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            stringBuilder.append(next + " ");
        }
        return stringBuilder.toString();
    }

    /**
     * 群发消息功能
     * @param message 需要群发的消息
     */
    public static void sendGroupMes(Message message){
        Collection<ServerConnectServiceThread> values = hashMap.values();
        Iterator<ServerConnectServiceThread> iterator = values.iterator();
        while (iterator.hasNext()) {
            ServerConnectServiceThread next = iterator.next();
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(next.getSocket().getOutputStream());
                objectOutputStream.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除线程
     * @param uid 用户id
     */
    public static void delete(String uid){
        hashMap.remove(uid);
    }
}
