package com.client.tools;

import com.client.view.Chat;
import com.client.view.FriendList;
import com.common.Message;
import com.common.MsgType;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * �ͻ����������ͨ���߳�
 */
public class ClientToServerThread extends Thread{

    private Socket client;
    private volatile boolean isRunning;

    public ClientToServerThread(Socket client){
        this.client = client;
        this.isRunning = true;
    }

    public Socket getClient() {
        return client;
    }

    public void myStop(){
        isRunning = false;
    }

    @Override
    public void run() {
        try {
            while(isRunning){
                ObjectInputStream input = new ObjectInputStream(this.client.getInputStream());
                Message msg = (Message) input.readObject();
                //�ж���Ϣ����
                if(msg.getType() == MsgType.RET_ONLINE_FRIENDS) {
                    String uid = msg.getGetterId();
                    System.out.println("find FriendList uid="+uid);
                    FriendList fl = ManageFriendListFrame.getFriendListFrame(uid);
                    //��һ���û�����֪ͨ�����û�ʱ�������û������ߣ�����Ϊ��
                    if(null != fl){
                        fl.updateOnlineFriends(msg);
                    }
                }else if(msg.getType() == MsgType.COMMON_MESSAGE) {
                    String frameName = msg.getGetterId()+msg.getSenderId();
                    System.out.println("find Chat framename="+frameName);
                    ManageChatFrame.getChatFrame(frameName).showMessage(msg,false);
                }else if(msg.getType() == MsgType.NOT_ONLINE) {
                    Chat chat = ManageChatFrame.getChatFrame(msg.getSenderId() + msg.getGetterId());
                    JOptionPane.showMessageDialog(chat, "�ú���δ���ߣ���δʵ���������칦�ܣ�");
                }else if(msg.getType() == MsgType.SERVER_CLOSE){
                    String toId = msg.getGetterId();
                    //�Զ�����
                    ManageFriendListFrame.getFriendListFrame(toId).sendUnloadMsgToServer();
                    ManageThread.removeThread(toId);
                    ManageFriendListFrame.removeFriendListFrame(toId);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
