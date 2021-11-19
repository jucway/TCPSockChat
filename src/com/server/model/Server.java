package com.server.model;

import com.common.Message;
import com.common.MsgType;
import com.common.User;
import com.server.tools.JDBC_Util;
import com.server.tools.ManageClientThread;
import com.server.tools.ServerConClientThread;
import com.server.view.ServerFrame;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server implements Runnable{

    private ServerSocket server;
    private Socket client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean isRunning;

    public Server(){
        System.out.println("---------------Server(5986)---------------");
        isRunning = true;
        new Thread(this).start();
    }

    /**
     * 结束线程运行
     */
    public void myStop() {
        isRunning = false;
        close(server);
    }

    @Override
    public void run() {
        try {
            //1.设置服务器套接字 ServerSocket(int port)创建绑定到指定端口的服务器套接字
            server = new ServerSocket(5986);
            while(isRunning) {
                //2.阻塞式等待客户端连接  (返回值)Socket accept()侦听要连接到此套接字的客户端并接受它。
                client = server.accept();
                System.out.println("一个客户端已连接....");
                input = new ObjectInputStream(client.getInputStream());
                output = new ObjectOutputStream(client.getOutputStream());
                User u = (User)input.readObject();
                System.out.println(u.toString());
//                System.out.println(u.getFlag());

                if(u.getFlag().equals("Login")){
                    System.out.println("进入到登录");
                doUserLogin(u);}
                else if(u.getFlag().equals("Register")){
                    doRegister(u);
                }
//                else if(u.getFlag().equals("doFileSend")){
//                    doFileSend(server);
//                }
            }
        } catch (IOException e) {
            close(output,input,client,server);//释放资源
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * 处理用户登录
     * @param u
     */
    private void doUserLogin(User u){
        Message msg = new Message();
        JDBC_Util jc = new JDBC_Util();
        //该用户未登录
        if(null == ManageClientThread.getClientThread(u.getUid())){
            try{
                String qname = jc.checkUserInfo(u);
                if(null != qname){//登录成功
                    msg.setType(MsgType.LOGIN_SUCCEED);
                    msg.setContent(qname + "-" + jc.getFriendsList(u.getUid()));
                    output.writeObject(msg);
                    //客户端连接成功就为其创建线程保持与服务器端通讯
                    ServerConClientThread th = new ServerConClientThread(client);
                    th.start();
                    //将其添加到线程集合
                    ManageClientThread.addClientThread(u.getUid(),th);
                    //通知其他用户
                    th.notifyOthers(u.getUid());
                    ServerFrame.showMsg("用户"+u.getUid()+"成功登录！");
                }else{
                    msg.setType(MsgType.LOGIN_FAILED);
                    output.writeObject(msg);
                    close(output,input,client);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{//该用户已登录
            try {
                msg.setType(MsgType.ALREADY_LOGIN);
                output.writeObject(msg);
                close(output,input,client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void doRegister(User u){
        Message msg = new Message();
        JDBC_Util jc = new JDBC_Util();
        //该用户未登录
        if(null == ManageClientThread.getClientThread(u.getUid())){
            try{
                String qname = jc.RegisterInfo(u);
                if(null != qname){//注册成功
                    msg.setType(MsgType.REGISTER_SUCCEED);
                    msg.setContent(qname + "-" + jc.getFriendsList(u.getUid()));
                    output.writeObject(msg);
                    //客户端连接成功就为其创建线程保持与服务器端通讯
                    ServerConClientThread th = new ServerConClientThread(client);
                    th.start();
                    //将其添加到线程集合
                    ManageClientThread.addClientThread(u.getUid(),th);
                    //通知其他用户
//                    th.notifyOthers(u.getUid());
                    ServerFrame.showMsg("用户"+u.getUid()+"成功注册！");
                }else{
                    msg.setType(MsgType.REGISTER_FAILED);
                    output.writeObject(msg);
                    close(output,input,client);
                }
            }catch(IOException | SQLException e){
                e.printStackTrace();
            }
        }else{//该用户已登录
            try {
                msg.setType(MsgType.ALREADY_REGISTER);
                output.writeObject(msg);
                close(output,input,client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//
//
//    /*
//    处理发送文件
//     */
//    private void doFileSend(Socket socket){
//        Message msg = new Message();
//        JDBC_Util jc = new JDBC_Util();
//
//        try {
//            // 打开Socket的输入流
//            InputStream is = socket.getInputStream();
//            // 使用DataInputStream 先读取输入流最前面的字符串(文件名)。（与客户端约定好的传输规则）
//            DataInputStream dis = new DataInputStream(is);
//            // 先读取输入流最前面的字符串数据(文件名)
//            String name = dis.readUTF();
//            // 确定文件名后，创建服务端保存的路径描述(默认保存在当前工程目录下)
//            File file = new File(name);
//            // 创建文件输出字节流
//            FileOutputStream fos = new FileOutputStream(file);
//            // 创建文件缓冲输出流，
//            BufferedOutputStream bos = new BufferedOutputStream(fos);
//            // 得到Socket的输入流，读取Socket传输过来的文件数据
//            BufferedInputStream bis = new BufferedInputStream(is);
//            int read = -1;
//            byte[] b = new byte[1024];
//            while ((read = bis.read(b, 0, b.length)) != -1) {// 读取文件数据
//                bos.write(b, 0, read);// 保存数据到文件中
//            }
//            bos.flush();// 刷新缓冲区
//            bos.close();// 关闭输出流
//            is.close();// 关闭输入流
//            // 一个完整的文件接收完成...
//            //Socket 输出到客户端
//            ServerFrame.showMsg("文件："+name+"接受成功！");
//        } catch (IOException e) {
//            e.printStackTrace();
////            ServerFrame.showMsg("文件："+name+"接受失败！");
//        }
//
//    }
    /**
     * 用于关闭多个io流
     * @param ios
     */
    private void close(Closeable... ios) {//可变长参数
        for(Closeable io: ios) {
            try {
                if(null != io)
                    io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
