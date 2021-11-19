package com.server.tools;

import com.common.Message;
import com.common.MsgType;
import com.server.view.ServerFrame;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * ��������ͻ���ͨ���߳�
 */
public class ServerConClientThread extends Thread {

    private Socket client;
    private volatile boolean isRunning;

    public ServerConClientThread(Socket client) {
        this.client = client;
        this.isRunning = true;
    }

    public Socket getClient() {
        return client;
    }

    public void myStop(){
        isRunning = false;
    }

    /**
     * ���Լ����߻����ߵ���Ϣ֪ͨ����
     * @param uid
     */
    public void notifyOthers(String uid){
        ObjectOutputStream out = null;
        Message msg = new Message();
        msg.setType(MsgType.RET_ONLINE_FRIENDS);
        msg.setContent(ManageClientThread.getOnLineList());
        for (Object o : ManageClientThread.getClientThreads().keySet()) {
            try {
                String id = o.toString();
                if(!id.equals(uid)){//����֪ͨ�Լ�
                    msg.setGetterId(id);
                    out = new ObjectOutputStream(ManageClientThread.getClientThread(id).client.getOutputStream());
                    out.writeObject(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        try {
            while(isRunning){
                ObjectInputStream input = new ObjectInputStream(this.client.getInputStream());
                Message msg = (Message) input.readObject();
                //�ж���Ϣ����
                if(msg.getType() == MsgType.GET_ONLINE_FRIENDS) {
                    msg.setType(MsgType.RET_ONLINE_FRIENDS);
                    msg.setGetterId(msg.getSenderId());
                    msg.setContent(ManageClientThread.getOnLineList());
                    ObjectOutputStream output = new ObjectOutputStream(ManageClientThread.getClientThread(msg.getGetterId()).client.getOutputStream());
                    output.writeObject(msg);
                    System.out.println("�����б�ɹ�");
                }else if(msg.getType() == MsgType.COMMON_MESSAGE) {
                    System.out.println(msg.toString());
                    ServerConClientThread thread = ManageClientThread.getClientThread(msg.getGetterId());//�ҵ������ߵ��߳�
                    if(null == thread){//���û�������
                        //֪ͨ�����ߺ��Ѳ�����
                        ObjectOutputStream output = new ObjectOutputStream(ManageClientThread.getClientThread(msg.getSenderId()).client.getOutputStream());
                        msg.setType(MsgType.NOT_ONLINE);
                        output.writeObject(msg);
                        System.out.println("֪ͨ�ɹ�");
                    }else{
                        ObjectOutputStream output = new ObjectOutputStream(thread.client.getOutputStream());
                        output.writeObject(msg);
                        System.out.println("ת���ɹ�");
                    }
                }else if(msg.getType() == MsgType.UNLOAD_LOGIN) {
                    String fromId = msg.getSenderId();
                    //�������߳�
                    myStop();
                    ManageClientThread.removeClientThread(fromId);
                    notifyOthers(fromId);
                    System.out.println(fromId+" �˳���¼");
                    ServerFrame.showMsg("�û�"+fromId+"�˳���¼��");
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}