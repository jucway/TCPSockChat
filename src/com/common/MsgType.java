package com.common;

/**
 * ����ͨ���漰����ȫ����Ϣ����
 */
public enum MsgType {
    LOGIN_SUCCEED,//��¼�ɹ�
    LOGIN_FAILED,//��¼ʧ��
    ALREADY_LOGIN,//�ѵ�¼
    UNLOAD_LOGIN,//�˳���¼
    GET_ONLINE_FRIENDS,//��ȡ���ߺ����б�
    RET_ONLINE_FRIENDS,//�������ߺ���
    NOT_ONLINE,//������
    SERVER_CLOSE,//�������ر�
    COMMON_MESSAGE,//��ͨ��Ϣ
    REGISTER_SUCCEED,//ע��ɹ�
    REGISTER_FAILED,//ע��ʧ��
    ALREADY_REGISTER //��ע��

}
