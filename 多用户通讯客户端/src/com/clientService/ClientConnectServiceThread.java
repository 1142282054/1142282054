package com.clientService;

import com.Utils.Utility;
import com.comment.MesType;
import com.comment.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author MinZhi
 * @version 1.0
 */
public class ClientConnectServiceThread extends Thread{
    private Socket socket;
    public ClientConnectServiceThread(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void receiveFile(Message message){
//        System.out.print("请输入接收路径（src\\fileReceive\\）:");
//        String secondSrc = Utility.readString(15);
//        String src = "src\\fileReceive\\" + secondSrc;
        String src = "src\\fileReceive\\pic01.jpg";
        File file = new File(src);
        if (!file.isFile()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //写入外存
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(message.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        boolean loop = true;
        while (loop){
            //接收信息
            try {
                inputStream = socket.getInputStream();
                objectInputStream = new ObjectInputStream(inputStream);
                Message message = (Message) objectInputStream.readObject();

                switch (message.getMesType()){
                    case MesType.MESSAGE_RET_ONLINE_FRIEND:
                        /*
                        格式：用户1 用户2 用户3
                         */
                        String[] onLineUsers = new String(message.getContent(), StandardCharsets.UTF_8).split(" ");
                        for (int i = 0; i < onLineUsers.length; i++) {
                            System.out.println("用户：" + onLineUsers[i]);
                        }
                        break;
                    case MesType.MESSAGE_COMMENT_MES:
                        //接收私聊信息
                        System.out.println(message.getSender() + "对你发送消息：\n" + (new String(message.getContent(),StandardCharsets.UTF_8)));
                        break;
                    case MesType.MESSAGE_GROUP_MES:
                        //接收群发消息
                        System.out.println(message.getSender() + "群发消息：\n" +(new String(message.getContent())));
                        break;
                    case MesType.MESSAGE_SEND_FILE:
                        //接收文件
                        System.out.println(message.getSender() + "对你发送文件");
                        receiveFile(message);
                        break;
                    case MesType.MESSAGE_LEAVE_MES:
                        //接收留言
                        System.out.println(message.getSender() + "对你留言：\n" +
                                (new String(message.getContent(),StandardCharsets.UTF_8)));
                        break;
                    default:
                        System.out.println("未知");
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
