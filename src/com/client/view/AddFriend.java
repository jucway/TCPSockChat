package com.client.view;

import com.client.model.LoginUser;
import com.client.tools.ManageFriendListFrame;
import com.client.tools.ManageThread;
//import com.common.Message;
import com.common.MsgType;
import com.common.User;
import com.client.view.GroupChat;
import com.common.Message;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Date;

import com.client.view.GroupChat;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * 	�ͻ��˵�¼ҳ��
 * 	�����¼��ťʵ�ְ�ť����ʵ���û����У��
 * @author wangwei
 *
 */
public class AddFriend<showImage> extends JFrame implements ActionListener {

    private static final long serialVersionUID = 2L;

    private JLabel jlb_north;//��������ͼƬ��ǩ
    private JLabel jlb_code;//��ά��ͼƬ
    private JButton btn_exit,btn_min;//���Ͻ���С���͹رհ�ť
    private JLabel jlb_photo;//�в��˺������������ͼƬ��ǩ
    private JTextField qqNum;//�˺������
    private JTextField qqPwd;//���������
    private JLabel qq_nick;//�˺��������"ע���˺�"
    private JLabel qq;//�����������"��������"
    private JCheckBox remPwd;//"��ס����"��ѡ��
    private JCheckBox autoLog;//"�Զ���¼"��ѡ��
    private JButton btn_login;//�ϲ���¼��ť
    boolean isDragged = false;//��¼����Ƿ�����ק�ƶ�
    private Point frame_temp;//��굱ǰ��Դ����λ������
    private Point frame_loc;//�����λ������
    public String myid;
    public void setmyid(String myid){
        this.myid = myid;}

    public AddFriend(String myid) {
        setmyid(myid);
        //��ȡ�˴�������
        Container c = this.getContentPane();
        //���ò���
        c.setLayout(null);
//
        c.setLocation(10,20);
        //����������ͼƬ��ǩ
        jlb_north = new JLabel(new ImageIcon("F:/java/MyQqChat/image/login/login.jpg"));
        jlb_north.setBounds(0,0,430,182);
        c.add(jlb_north);
        //�������Ͻ���С���͹رհ�ť
        btn_min = new JButton(new ImageIcon("F:/java/MyQqChat/image/login/min.jpg"));
        btn_min.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ע�������,���ʵ�ִ�����С��
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        btn_min.setBounds(373, 0, 28, 29);
        c.add(btn_min);

        btn_exit = new JButton(new ImageIcon("F:/java/MyQqChat/image/login/exit.png"));
        btn_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ע�������,���ʵ�ִ��ڹر�
                System.exit(0);
            }
        });
        btn_exit.setBounds(401, 0, 28, 29);
        c.add(btn_exit);

        //�����˺������
        qqNum = new JTextField(); // �����˺ŵ�����
        qqNum.setBounds(120,195,194,30);
//        qqNum.setText("QQ����/�ֻ�");
        qqNum.setForeground(Color.GRAY);
        c.add(qqNum);
        //�������������
        qqPwd = new JTextField(); //
        qqPwd.setBounds(120,240,194,30);
        c.add(qqPwd);
        //�����˺�
//        qq.setForeground(Color.GRAY);
        qq = new JLabel("�˺�:");
        qq.setForeground(Color.GRAY);
        qq.setBounds(70,240,50, 30);
        c.add(qq);
        //�����ǳ�
        qq_nick = new JLabel("�ǳ�:");
        qq_nick.setForeground(Color.GRAY);
        qq_nick.setBounds(70,197,50,30);
        c.add(qq_nick);


        //�����ϲ���¼��ť
        btn_login = new JButton(new ImageIcon("F:/java/MyQqChat/image/addfriend/add.png"));
        btn_login.setBounds(120,299,194,30);
        btn_login.addActionListener(this);
        c.add(btn_login);





        //ע������¼�������
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //����ͷ�
                isDragged = false;
                //���ָ�
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                //��갴��
                //��ȡ�����Դ���λ��
                frame_temp = new Point(e.getX(),e.getY());
                isDragged = true;
                //���ı�Ϊ�ƶ���ʽ
                if(e.getY() < 182)
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        });
        //ע������¼�������
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //ָ����Χ�ڵ��������ק
                if(e.getY() < 182){
                    //����������ק�ƶ�
                    if(isDragged) {
                        frame_loc = new Point(getLocation().x+e.getX()-frame_temp.x,
                                getLocation().y+e.getY()-frame_temp.y);
                        //��֤�����Դ���λ�ò���,ʵ���϶�
                        setLocation(frame_loc);
                    }
                }
            }
        });

        this.setIconImage(new ImageIcon("F:/java/MyQqChat/image/login/Q.png").getImage());//�޸Ĵ���Ĭ��ͼ��
        this.setSize(430,345);//���ô����С
        this.setUndecorated(true);//ȥ���Դ�װ�ο�
        this.setVisible(true);//���ô���ɼ�
        this.setLocationRelativeTo(null);
    }

    /**
     * �����¼���д���
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btn_login) {//�����¼
            String qqnick = qqNum.getText().trim();//��ȡ�����˺�
            String qqid = qqPwd.getText().trim();//��ȡ����
            GroupChat gc = new GroupChat();
            gc.setnick(qqnick);
            gc.setId(qqid);


            Message msg = new Message();
            msg.setContent("���û�"+" "+qqnick+"("+qqid+") ����Ⱥ��");
//            msg.setSendTime(df.format(new Date()));
            try {
                ObjectOutput out = new ObjectOutputStream(ManageThread.getThread(myid).getClient().getOutputStream());
                out.writeObject(msg);
                System.out.println("�û���ӳɹ�������");
                showAddFriend(msg,true);

            } catch (IOException c) {
                c.printStackTrace();
            }
            //���ռ�����
            this.dispose();//�رյ�¼����
        }
    }
    public void showAddFriend(Message msg, boolean fromSelf) {
        GroupChat gC = new GroupChat();
        showAddFriend(gC.getPanel_Msg(), msg, fromSelf);//����ʾ�������������
        showAddFriend(gC.getPanel_Record(), msg, fromSelf);//����ʾ�������¼���
    }
    private void showAddFriend(JTextPane jtp,Message msg, boolean fromSelf) {

        //������ʾ��ʽ
        SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attrset, "����");
        StyleConstants.setFontSize(attrset,14);
        Document docs = jtp.getDocument();
        String info = null;
        try {
            if(fromSelf){//����ȥ����Ϣ����
                info = "��  ";//�Լ��˺ţ���ɫ
                StyleConstants.setForeground(attrset, Color.MAGENTA);
                docs.insertString(docs.getLength(), info, attrset); StyleConstants.setForeground(attrset, Color.red);
                info = msg.getSendTime()+":\n";//����ʱ�䣺��ɫ
                StyleConstants.setForeground(attrset, Color.black);
                docs.insertString(docs.getLength(), info, attrset);
                info = " "+msg.getContent()+"\n";//�������ݣ���ɫ
                StyleConstants.setFontSize(attrset,16);
                StyleConstants.setForeground(attrset, Color.green);
                docs.insertString(docs.getLength(), info, attrset);
            }else{//���յ�����Ϣ����
                info = msg.getSenderName()+"("+msg.getSenderId()+")  ";//�Է��˺ţ���ɫ
                StyleConstants.setForeground(attrset, Color.red);
                docs.insertString(docs.getLength(), info, attrset); StyleConstants.setForeground(attrset, Color.red);
                info = msg.getSendTime()+":\n";//����ʱ�䣺��ɫ
                StyleConstants.setForeground(attrset, Color.black);
                docs.insertString(docs.getLength(), info, attrset);
                if(msg.getSendtype().equals("image")){
                    String image = " "+"�������ļ�(ͼƬ)"+"\n";

                    StyleConstants.setFontSize(attrset,16);
                    StyleConstants.setForeground(attrset, Color.blue);
                    docs.insertString(docs.getLength(), image, attrset);
                }//�������ݣ���ɫ}
                else {
                    info = " "+msg.getContent()+"\n";//�������ݣ���ɫ
                    StyleConstants.setFontSize(attrset,16);
                    StyleConstants.setForeground(attrset, Color.blue);
                    docs.insertString(docs.getLength(), info, attrset);}//�������ݣ���ɫ
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }





//    public static void main(String[] args) {
//        new AddFriend();
//    }
}
