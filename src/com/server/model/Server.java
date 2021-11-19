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
     * �����߳�����
     */
    public void myStop() {
        isRunning = false;
        close(server);
    }

    @Override
    public void run() {
        try {
            //1.���÷������׽��� ServerSocket(int port)�����󶨵�ָ���˿ڵķ������׽���
            server = new ServerSocket(5986);
            while(isRunning) {
                //2.����ʽ�ȴ��ͻ�������  (����ֵ)Socket accept()����Ҫ���ӵ����׽��ֵĿͻ��˲���������
                client = server.accept();
                System.out.println("һ���ͻ���������....");
                input = new ObjectInputStream(client.getInputStream());
                output = new ObjectOutputStream(client.getOutputStream());
                User u = (User)input.readObject();
                System.out.println(u.toString());
//                System.out.println(u.getFlag());

                if(u.getFlag().equals("Login")){
                    System.out.println("���뵽��¼");
                doUserLogin(u);}
                else if(u.getFlag().equals("Register")){
                    doRegister(u);
                }
//                else if(u.getFlag().equals("doFileSend")){
//                    doFileSend(server);
//                }
            }
        } catch (IOException e) {
            close(output,input,client,server);//�ͷ���Դ
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * �����û���¼
     * @param u
     */
    private void doUserLogin(User u){
        Message msg = new Message();
        JDBC_Util jc = new JDBC_Util();
        //���û�δ��¼
        if(null == ManageClientThread.getClientThread(u.getUid())){
            try{
                String qname = jc.checkUserInfo(u);
                if(null != qname){//��¼�ɹ�
                    msg.setType(MsgType.LOGIN_SUCCEED);
                    msg.setContent(qname + "-" + jc.getFriendsList(u.getUid()));
                    output.writeObject(msg);
                    //�ͻ������ӳɹ���Ϊ�䴴���̱߳������������ͨѶ
                    ServerConClientThread th = new ServerConClientThread(client);
                    th.start();
                    //������ӵ��̼߳���
                    ManageClientThread.addClientThread(u.getUid(),th);
                    //֪ͨ�����û�
                    th.notifyOthers(u.getUid());
                    ServerFrame.showMsg("�û�"+u.getUid()+"�ɹ���¼��");
                }else{
                    msg.setType(MsgType.LOGIN_FAILED);
                    output.writeObject(msg);
                    close(output,input,client);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{//���û��ѵ�¼
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
        //���û�δ��¼
        if(null == ManageClientThread.getClientThread(u.getUid())){
            try{
                String qname = jc.RegisterInfo(u);
                if(null != qname){//ע��ɹ�
                    msg.setType(MsgType.REGISTER_SUCCEED);
                    msg.setContent(qname + "-" + jc.getFriendsList(u.getUid()));
                    output.writeObject(msg);
                    //�ͻ������ӳɹ���Ϊ�䴴���̱߳������������ͨѶ
                    ServerConClientThread th = new ServerConClientThread(client);
                    th.start();
                    //������ӵ��̼߳���
                    ManageClientThread.addClientThread(u.getUid(),th);
                    //֪ͨ�����û�
//                    th.notifyOthers(u.getUid());
                    ServerFrame.showMsg("�û�"+u.getUid()+"�ɹ�ע�ᣡ");
                }else{
                    msg.setType(MsgType.REGISTER_FAILED);
                    output.writeObject(msg);
                    close(output,input,client);
                }
            }catch(IOException | SQLException e){
                e.printStackTrace();
            }
        }else{//���û��ѵ�¼
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
//    �������ļ�
//     */
//    private void doFileSend(Socket socket){
//        Message msg = new Message();
//        JDBC_Util jc = new JDBC_Util();
//
//        try {
//            // ��Socket��������
//            InputStream is = socket.getInputStream();
//            // ʹ��DataInputStream �ȶ�ȡ��������ǰ����ַ���(�ļ���)������ͻ���Լ���õĴ������
//            DataInputStream dis = new DataInputStream(is);
//            // �ȶ�ȡ��������ǰ����ַ�������(�ļ���)
//            String name = dis.readUTF();
//            // ȷ���ļ����󣬴�������˱����·������(Ĭ�ϱ����ڵ�ǰ����Ŀ¼��)
//            File file = new File(name);
//            // �����ļ�����ֽ���
//            FileOutputStream fos = new FileOutputStream(file);
//            // �����ļ������������
//            BufferedOutputStream bos = new BufferedOutputStream(fos);
//            // �õ�Socket������������ȡSocket����������ļ�����
//            BufferedInputStream bis = new BufferedInputStream(is);
//            int read = -1;
//            byte[] b = new byte[1024];
//            while ((read = bis.read(b, 0, b.length)) != -1) {// ��ȡ�ļ�����
//                bos.write(b, 0, read);// �������ݵ��ļ���
//            }
//            bos.flush();// ˢ�»�����
//            bos.close();// �ر������
//            is.close();// �ر�������
//            // һ���������ļ��������...
//            //Socket ������ͻ���
//            ServerFrame.showMsg("�ļ���"+name+"���ܳɹ���");
//        } catch (IOException e) {
//            e.printStackTrace();
////            ServerFrame.showMsg("�ļ���"+name+"����ʧ�ܣ�");
//        }
//
//    }
    /**
     * ���ڹرն��io��
     * @param ios
     */
    private void close(Closeable... ios) {//�ɱ䳤����
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
