package com.clientService;

import java.util.HashMap;

/**
 * @author MinZhi
 * @version 1.0
 */
public class ManageClientConnectServiceThread {
    private static HashMap<String,ClientConnectServiceThread> hashMap = new HashMap();

    public static void add(String uid,ClientConnectServiceThread thread){
        hashMap.put(uid,thread);
    }
    public static ClientConnectServiceThread get(String uid){
        return hashMap.get(uid);
    }
    public static void delete(String uid){
        hashMap.remove(uid);
    }
}
