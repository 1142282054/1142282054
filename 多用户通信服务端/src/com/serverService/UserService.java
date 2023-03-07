package com.serverService;

import com.comment.MesType;
import com.comment.Message;
import com.comment.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author MinZhi
 * @version 1.0
 */
public class UserService {
    public static void main(String[] args) {
        UserService userService = new UserService();
    }
    private ServerSocket serverSocket;
    private static HashMap<String,String> userDataBase;

    static {
        userDataBase = new HashMap<>();
        userDataBase.put("min","100");
        userDataBase.put("wei","200");
    }
    private boolean checkLogin(String uid,String pwd){
        if (!userDataBase.containsKey(uid)){
            return false;
        }
        if (!userDataBase.get(uid).equals(pwd)){
            return false;
        }
        return true;
    }
    public UserService() {
        try {
            System.out.println("服务器开启");
            this.serverSocket = new ServerSocket(8888);
            while(true){

                Socket socket = serverSocket.accept();
                //接收信息
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                User user = (User) objectInputStream.readObject();
                //验证
                Message message = new Message();
                if (checkLogin(user.getName(), user.getPwd())){
                    System.out.println(user.getName() + "登录~");
                    message.setMesType(MesType.MESSAGE_LOGIN_SUCCEED);
                    //打开线程
                    ServerConnectServiceThread connectServiceThread = new ServerConnectServiceThread(socket);
                    connectServiceThread.start();
                    ManageConnectServiceThread.add(user.getName(),connectServiceThread);

                }else {
                    message.setMesType(MesType.MESSAGE_LOGIN_FAIL);
                    socket.close();
                }
                //发送验证信息
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(message);

                //查看是否有留言信息
                LeaveMessageService.leaveMessage(user.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
