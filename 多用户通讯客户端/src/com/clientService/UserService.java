package com.clientService;

import com.comment.MesType;
import com.comment.Message;
import com.comment.User;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author MinZhi
 * @version 1.0
 * 用户登录
 */
public class UserService {
    private User user;
    private Socket socket;
    private Message message;

    public UserService() {
    }

    public UserService(String uid, String pwd) {
        //初始化user
        user = new User(uid,pwd);
        //连接服务器
        try {
            socket = new Socket(InetAddress.getLocalHost(),8888);
            //发送信息
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(user);
            //接收返回信息
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            message = (Message) objectInputStream.readObject();
            if (message.getMesType().equals(MesType.MESSAGE_LOGIN_SUCCEED)){
                //打开线程
                ClientConnectServiceThread serviceThread = new ClientConnectServiceThread(socket);
                serviceThread.start();
                //加入线程集合
                ManageClientConnectServiceThread.add(uid,serviceThread);
            }else{
                socket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onlineFriendList(){
        //获取socket对象
        Socket socket = ManageClientConnectServiceThread.get(user.getName()).getSocket();
        try {
            //发送请求消息
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Message message = new Message();
            message.setSender(user.getName());
            message.setMesType(MesType.MESSAGE_GET_ONLINE_FRIEND);
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void exitSystem(){
        //获取socket
        Socket socket = ManageClientConnectServiceThread.get(user.getName()).getSocket();
        //发送退出信息
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Message message = new Message();
            message.setSender(user.getName());
            message.setMesType(MesType.MESSAGE_CLIENT_EXIT);
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //退出
        ManageClientConnectServiceThread.delete(user.getName());
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("用户退出");
        System.exit(0);
    }
    public void confab(String sender,String getter,String content){
        //获取socket
        Socket socket = ManageClientConnectServiceThread.get(sender).getSocket();
        //发送私聊信息
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            //打包message对象
            Message message = new Message();
            message.setMesType(MesType.MESSAGE_COMMENT_MES);
            message.setSender(sender);
            message.setGetter(getter);
            message.setContent(content.getBytes(StandardCharsets.UTF_8));
            objectOutputStream.writeObject(message);
            //提示信息
            System.out.println("给( " + getter + " )发送消息");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void groupMes(String sender,String content){
        //获取socket
        Socket socket = ManageClientConnectServiceThread.get(sender).getSocket();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Message message = new Message();
            message.setSender(sender);
            message.setMesType(MesType.MESSAGE_GROUP_MES);
            message.setContent(content.getBytes(StandardCharsets.UTF_8));
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendFile(String sender,String getter,String src){
        //获取文件
        File file = new File(src);
        if (file.isFile()){
            //存进content中
            Message message = new Message();
            byte[] content = new byte[(int) file.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                int read = fileInputStream.read(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //封装message
            message.setMesType(MesType.MESSAGE_SEND_FILE);
            message.setSender(sender);
            message.setGetter(getter);
            message.setContent(content);
            //发送
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                        ManageClientConnectServiceThread.get(sender).getSocket().getOutputStream()
                );
                objectOutputStream.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("文件路径错误~");
        }
    }
    public void leaveMes(String sender,String getter,String content){
        //封装message
        Message message = new Message();
        message.setSender(sender);
        message.setGetter(getter);
        message.setMesType(MesType.MESSAGE_LEAVE_MES);
        message.setContent(content.getBytes(StandardCharsets.UTF_8));
        //发送
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Message getMessage() {
        return message;
    }
}
