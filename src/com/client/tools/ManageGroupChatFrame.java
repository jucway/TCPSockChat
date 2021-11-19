package com.client.tools;

import com.client.view.Chat;
import com.client.view.GroupChat;

import java.util.Hashtable;

public class ManageGroupChatFrame {
    private static Hashtable<String, GroupChat> GroupchatFrames = new Hashtable<>();

    public static void addGroupChatFrame(String frameName,GroupChat groupchat){
        GroupchatFrames.put(frameName,groupchat);
    }

    public static GroupChat getGroupChatFrame(String frameName){
        return GroupchatFrames.get(frameName);
    }

    public static GroupChat removeGroupChatFrame(String frameName){
        return GroupchatFrames.remove(frameName);
    }
}

