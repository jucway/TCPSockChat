package com.common;

import java.io.Serializable;

/**
 * ��������ͻ��˼������Զ�������������
 */
public class Message implements Serializable {

    private MsgType type;//��Ϣ����
    private String content;//��Ϣ����
    private String senderId;//������
    private String senderName;//������
    private String getterId;//������
    private String sendTime;//����ʱ��
    private byte[] Image;//����ʱ��
    private String sendtype;

    public Message() {
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }
    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        this.Image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendtype() {
        return sendtype;
    }

    public void setSendtype(String sendtype) {
        this.sendtype = sendtype;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getGetterId() {
        return getterId;
    }

    public void setGetterId(String getterId) {
        this.getterId = getterId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return type + "--"+sendTime + ":" + senderId + "��" + getterId + "˵��" + content;
    }
}
