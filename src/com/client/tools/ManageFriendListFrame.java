package com.client.tools;

import com.client.view.FriendList;
import java.util.Hashtable;

/**
 * ���������û��������б����
 */
public class ManageFriendListFrame {

    private static Hashtable<String, FriendList> friendListFrames = new Hashtable<>();

    public static void addFriendListFrame(String frameName,FriendList fl){
        friendListFrames.put(frameName,fl);
    }

    public static FriendList getFriendListFrame(String frameName){
        return friendListFrames.get(frameName);
    }

    public static FriendList removeFriendListFrame(String frameName){
        return friendListFrames.remove(frameName);
    }
}
