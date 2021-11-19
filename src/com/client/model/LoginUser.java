package com.client.model;

import com.client.tools.ClientToServerThread;
import com.client.tools.ManageThread;
import com.common.Message;
import com.common.MsgType;
import com.common.User;

import javax.swing.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.regex.Pattern;

/**
 * ���û�����ĵ�¼��Ϣ����У�飬���ϸ�ʽ���͵���������������У�������䷵��
 */
public class LoginUser {

    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public LoginUser() {
        try {
            client = new Socket("localhost", 5986);
//            input = new ObjectInputStream(new ObjectInputStream(client.getInputStream())); // ������ ������������
//            output = new ObjectOutputStream(client.getOutputStream()); //����� ������������
//
            output = new ObjectOutputStream(client.getOutputStream());
            output.flush();
            input = new ObjectInputStream(client.getInputStream());
//            output = new OutputStream(client.getOutputStream());
//            input = new InputStream(client.getInputStream());

        }
        catch (IOException e) {
            System.out.println("���ӷ�����ʧ�ܣ�");
            e.printStackTrace();
        }
    }

    /**
     * ����û�������Ϣ�Ƿ����
     * �����򽫵�¼��Ϣ��浽�����з���
     * ����������ʾ��ʾ��Ϣ
     *
     * @param f
     * @param uid
     * @param pwd
     * @return
     */
    private User checkLoginInfo(JFrame f, String uid, String pwd, String flag) {
        User u = null;
        if (!uid.equals("") && !pwd.equals("")) {
            String pattern = "[0-9]{3,10}";//�˺�Ϊ1-10λ����
            if (Pattern.matches(pattern, uid)) {//�˺źϷ�
                u = new User(uid, pwd, flag);
            } else {//�˺Ų��Ϸ�
                JOptionPane.showMessageDialog(f, "�˺�Ϊ3��10λ�������У����������룡");
            }
        } else if (uid.equals("")) {//�˺�Ϊ��
            JOptionPane.showMessageDialog(f, "�������˺ţ�");
        } else if (pwd.equals("")) {//����Ϊ��
            JOptionPane.showMessageDialog(f, "���������룡");
        }
        return u;
    }

    private User checkRegisterInfo(JFrame f, String uid, String pwd, String flag) {
        User u = null;
        if (!uid.equals("") && !pwd.equals("")) {
            String pattern = "[0-9]{3,10}";//�˺�Ϊ1-10λ����
            if (Pattern.matches(pattern, uid)) {//�˺źϷ�
                u = new User(uid, pwd, flag);
            } else {//�˺Ų��Ϸ�
                JOptionPane.showMessageDialog(f, "�˺�Ϊ3��10λ�������У����������룡");
            }
        } else if (uid.equals("")) {//�˺�Ϊ��
            JOptionPane.showMessageDialog(f, "�������˺ţ�");
        } else if (pwd.equals("")) {//����Ϊ��
            JOptionPane.showMessageDialog(f, "���������룡");
        }

        return u;
    }

    public Message sendRegisterToServer(JFrame f, String uid, String rpwd, String flag){
        User u = checkRegisterInfo(f, uid, rpwd,flag);
        if (u != null) {
            try {
                output.writeObject(u);//���͵�������
                System.out.println("ok " + u.toString());
                Message msg = (Message) input.readObject();//���շ��ؽ��
                if (msg.getType() == MsgType.REGISTER_SUCCEED) {//ע��ɹ�
                    ClientToServerThread th = new ClientToServerThread(client);
                    th.start();//�����������ͨ���߳�
                    ManageThread.addThread(uid, th);
                    JOptionPane.showMessageDialog(f, "�û��˺ţ� "+u.getUid()+" ע��ɹ���");
                    return msg;

                } else if (msg.getType() == MsgType.ALREADY_REGISTER) {
                    JOptionPane.showMessageDialog(f, "���û���ע�ᣬ�����ظ�������");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * ��ͨ��У��ĵ�¼��Ϣ���͵�������
     * �����õ�����Ϣ������(������ǰ�û������к���)
     *
     * @param f
     * @param uid
     * @param pwd
     */
    public Message sendLoginInfoToServer(JFrame f, String uid, String pwd, String flag) {
        User u = checkRegisterInfo(f, uid, pwd,flag);
        if (u != null) {
            try {
                output.writeObject(u);//���͵�������
                System.out.println("ok " + u.toString());
                Message msg = (Message) input.readObject();//���շ��ؽ��
                if (msg.getType() == MsgType.LOGIN_SUCCEED) {//��¼�ɹ�
                    ClientToServerThread th = new ClientToServerThread(client);
                    th.start();//�����������ͨ���߳�
                    ManageThread.addThread(uid, th);
                    return msg;
                } else if (msg.getType() == MsgType.LOGIN_FAILED) {
                    JOptionPane.showMessageDialog(f, "�˺Ż���������������������룡");
                } else if (msg.getType() == MsgType.ALREADY_LOGIN) {
                    JOptionPane.showMessageDialog(f, "���û��ѵ�¼�������ظ�������");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
