package com.serverService;

import com.comment.MesType;
import com.comment.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author MinZhi
 * @version 1.0
 */
public class ServerConnectServiceThread extends Thread{
    private Socket socket;
    private boolean loop = true;

    ServerConnectServiceThread(Socket socket){
        this.socket = socket;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        while (loop){
            try {
                //获取信息
                inputStream = socket.getInputStream();
                objectInputStream = new ObjectInputStream(inputStream);
                Message message = (Message) objectInputStream.readObject();

                switch (message.getMesType()){
                    case MesType.MESSAGE_GET_ONLINE_FRIEND://获取用户在线列表
                        System.out.println(message.getSender() + "获取在线用户列表");
                        //初始化用户列表信息
                        Message message1 = new Message();
                        message1.setMesType(MesType.MESSAGE_RET_ONLINE_FRIEND);
                            //获取内容content
                        String onLineFriend = ManageConnectServiceThread.getOnLineFriend();
                        message1.setContent(onLineFriend.getBytes(StandardCharsets.UTF_8));
                        //发送
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(message1);
                        break;
                    case MesType.MESSAGE_CLIENT_EXIT://退出请求
                        System.out.println(message.getSender() + "退出~");
                        /*
                        1、loop退出
                            （1）get方法.setLoop()
                            （2）本类setLoop（）
                        2、if/else break出while循环
                         */
                        ManageConnectServiceThread.get(message.getSender()).setLoop(false);
                        ManageConnectServiceThread.delete(message.getSender());
                        break;
                    case MesType.MESSAGE_COMMENT_MES://转发消息
                        System.out.println(message.getSender() + "给" + message.getGetter() + "发送消息~");
                        //找出接收者的socket，并发送消息
                        Socket socket = ManageConnectServiceThread.get(message.getGetter()).socket;
                        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream1.writeObject(message);
                        break;
                    case MesType.MESSAGE_GROUP_MES:
                        System.out.println(message.getSender() + "群发消息");
                        //遍历socket 并发送消息
                        ManageConnectServiceThread.sendGroupMes(message);
                        break;
                    case MesType.MESSAGE_SEND_FILE:
                        System.out.println(message.getSender() + "对" + message.getGetter() + "发送文件~");
                        ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(
                                ManageConnectServiceThread.get(message.getGetter()).getSocket().getOutputStream());
                        objectOutputStream2.writeObject(message);
                        break;
                    case MesType.MESSAGE_LEAVE_MES:
                        System.out.println(message.getSender() + "给" + message.getGetter()+"发送留言~");
                        LeaveMessageService.add(message);
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
