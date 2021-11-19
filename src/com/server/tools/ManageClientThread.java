package com.server.tools;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * ����������ͻ��˵��߳�
 */
public class ManageClientThread {

    private static Hashtable<String,ServerConClientThread> threads = new Hashtable<>();

    public static Hashtable<String, ServerConClientThread> getClientThreads() {
        return threads;
    }

    public static void addClientThread(String uid, ServerConClientThread thread){
        threads.put(uid,thread);
    }

    public static ServerConClientThread getClientThread(String uid){
        return threads.get(uid);
    }

    public static void removeClientThread(String uid){
        threads.remove(uid);
    }

    /**
     * ���ص�ǰ����ȫ���û�
     * @return
     */
    public static String getOnLineList(){
        StringBuilder sb = new StringBuilder();
        Iterator it = threads.keySet().iterator();
        while(it.hasNext()){
            sb.append(it.next()+" ");
        }
        return sb.toString();
    }
}
