package com.clientView;

import com.Utils.Utility;
import com.clientService.UserService;
import com.comment.MesType;


/**
 * @author MinZhi
 * @version 1.0
 */
public class View {
    public static void main(String[] args) {
        new View().mainMenu();
    }

    UserService userService = new UserService();
    String key = "";
    String content = "";

    public void mainMenu(){
        //界面
        boolean loop = true;
        while (loop){
            //一级菜单
            System.out.println("==============欢迎登录网络用户通讯系统=============");
            System.out.println("\t\t1、登录系统");
            System.out.println("\t\t9、退出系统");
            System.out.print("请输入你的选择：");
            key = Utility.readString(1);
            if (key.equals("1")){
                System.out.print("请输入用户号：");
                String uid = Utility.readString(15);
                System.out.print("请输入密 码：");
                String pwd = Utility.readString(20);
                //发送信息验证
                userService = new UserService(uid, pwd);
                //返回消息验证
                if (userService.getMessage().getMesType().equals(MesType.MESSAGE_LOGIN_SUCCEED)){
                    System.out.println("欢迎 " + uid + "用户 登录");
                    //二级菜单
                    System.out.println("==========网络通讯系统二级菜单（用户 " + uid + " )===============");
                    System.out.println("\t\t1、显示在线用户列表");
                    System.out.println("\t\t2、群发消息");
                    System.out.println("\t\t3、私聊消息");
                    System.out.println("\t\t4、发送文件");
                    System.out.println("\t\t5、离线留言");
                    System.out.println("\t\t9、退出系统");
                    while (loop){
                        System.out.print("请输入你的选择：");
                        key = Utility.readString(1);
                        switch (key){
                            case "1":
                                System.out.println("=======当前在线用户列表=======");
                                userService.onlineFriendList();
                                break;
                            case "2":
                                System.out.print("请输入想要说的话：");
                                content = Utility.readString(50);
                                userService.groupMes(uid,content);
                                break;
                            case "3":
                                System.out.print("请输入想要聊天的用户（在线）：");
                                String getter = Utility.readString(10);
                                System.out.print("请输入想要说的话：");
                                content = Utility.readString(50);
                                userService.confab(uid,getter,content);
                                break;
                            case "4":
                                System.out.print("请选择你要发送文件用户（在线）：");
                                getter = Utility.readString(15);
                                System.out.print("请输入发送文件路径（d:\\xxx\\xx.jpg）：");
                                String src = Utility.readString(25);
                                userService.sendFile(uid,getter,src);
                                break;
                            case "5":
                                System.out.print("请选择你要留言的用户(离线)：");
                                getter = Utility.readString(15);
                                System.out.print("请输入留言：");
                                content = Utility.readString(50);
                                userService.leaveMes(uid,getter,content);
                                break;
                            case "9":
                                System.out.println("=========退出系统===========");
                                userService.exitSystem();
                                break;
                            default:
                                break;
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }else {//用户登录失败
                    System.out.println("登录失败");
                }
            }else if (key.equals("9")){//退出系统选择
                System.out.println("============退出系统============");
                loop = false;
            }else{
                System.out.println("输入错误");
            }
        }
    }
}
