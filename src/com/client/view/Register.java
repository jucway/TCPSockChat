//package com.client.view;
//import com.client.model.LoginUser;
//import com.common.Message;
//
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JCheckBox;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPasswordField;
//import javax.swing.JTextField;
//import javax.swing.JRadioButton;
//public class Register extends JFrame implements ActionListener {
//    private JButton btn;
//    private JTextField emailField;
//    private JTextField pwdField;
//    private JTextField nameField;
//
//    public Register(){
//        JFrame jf=new JFrame();
//        // ��������
//        jf.setTitle("ע�����");
//        jf.setSize(400,500);
//        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jf.setLocationRelativeTo(null);
//        jf.setResizable(true);
//        // ��ʽ����
//        FlowLayout fl = new FlowLayout();
//        jf.setLayout(fl);
//        // ���
//        // ��ť
//        btn = new JButton(new ImageIcon("F:/java/MyQqChat/image/login/register.png"));
//        btn.setBounds(170,450,60,33);
//        //ͼƬ
//        ImageIcon img = new ImageIcon("F:/java/MyQqChat/image/login/login.jpg");
//        JLabel imgJla= new JLabel(img);
//        JLabel email = new JLabel("�˺ţ�");
//        JLabel name = new JLabel("�ǳƣ�");
//        JLabel pwd = new JLabel("���룺");
//        JLabel pwd1 = new JLabel("ȷ�����룺");
//        JLabel num = new JLabel("��֤�룺");
//        JLabel sex = new JLabel("�Ա�");
//        //�����
////        JTextField nameField = new JTextField();
////        JTextField emailField = new JTextField();
//        JTextField numField = new JTextField();
//        // �˺�����
//        emailField = new JTextField();
//        // �ǳ�����
//        nameField = new JTextField();
//        // ��������
//        pwdField = new JTextField();
////        JPasswordField pwdField = new JPasswordField();
//        JPasswordField pwdField1 = new JPasswordField();
//        //���(�����)����
//        Dimension dim = new Dimension(300,30);
//        nameField.setPreferredSize(dim);
//        pwdField.setPreferredSize(dim);
//        emailField.setPreferredSize(dim);
//        numField.setPreferredSize(dim);
//        pwdField1.setPreferredSize(dim);
//        //��ѡ��
//        JCheckBox jch = new JCheckBox("�����Ķ���ͬ����ط�������");
//        //��ѡ��
//        JRadioButton jra1 = new JRadioButton("��");
//        JRadioButton jra2 = new JRadioButton("Ů");
//        //������
//        jf.add(imgJla);
//        jf.add(email);
//        jf.add(emailField);
//        jf.add(name);
//        jf.add(nameField);
//        jf.add(pwd);
//        jf.add(pwdField);
//        jf.add(pwd1);
//        jf.add(pwdField1);
//        jf.add(num);
//        jf.add(numField);
//        jf.add(sex);
//        jf.add(jra1);
//        jf.add(jra2);
//        jf.add(jch);
//        jf.add(btn);
//        jf.setIconImage(new ImageIcon("F:/java/MyQqChat/image/login/Q.png").getImage());
//        jf.setVisible(true);//���ӻ�
//    }
//
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource()==btn){
//            String uid = emailField.getText().trim(); //��ȡ�˺�
//            String rpwd = pwdField.getText().trim(); // ��ȡ����
//            String rname = nameField.getText().trim(); //��ȡ�ǳ�
//            LoginUser longinU = new LoginUser();
//
//            Message msg = longinU.register(this, uid, rpwd);
//            if(msg!=null){
//
//            }
//        }
//
//    }
//    public static void main(String[] args) {
//         new Register();
////        registerUI.register();
//    }
//}










package com.client.view;

import com.client.model.LoginUser;
import com.client.tools.ManageFriendListFrame;
import com.client.tools.ManageThread;
import com.common.Message;
import com.common.MsgType;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.*;

/**
 * 	�ͻ��˵�¼ҳ��
 * 	�����¼��ťʵ�ְ�ť����ʵ���û����У��
 * @author wangwei
 *
 */
public class Register extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JLabel jlb_north;//��������ͼƬ��ǩ
    private JLabel jlb_code;//��ά��ͼƬ
    private JButton btn_exit,btn_min;//���Ͻ���С���͹رհ�ť
    private JLabel jlb_photo;//�в��˺������������ͼƬ��ǩ
    private JTextField qqNum;//�˺������
    private JTextField qqName;//�˺������
    private JPasswordField qqPwd;//���������
    private JPasswordField qqPwd1;//���������
    private JLabel after_qqNum;//�˺��������"ע���˺�"
    private JLabel after_qqPwd;//�����������"��������"
    private JCheckBox remPwd;//"��ס����"��ѡ��
    private JCheckBox autoLog;//"�Զ���¼"��ѡ��
    private JButton btn_register;//�ϲ���¼��ť
    boolean isDragged = false;//��¼����Ƿ�����ק�ƶ�
    private Point frame_temp;//��굱ǰ��Դ����λ������
    private Point frame_loc;//�����λ������

    public Register() {
        //��ȡ�˴�������
        Container c = this.getContentPane();
        //���ò���
        c.setLayout(null);
//
//        c.setName("ע��");
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

        //�����в��˺������������ͼƬ��ǩ
        //�����˺������
        JLabel email = new JLabel("�˺ţ�");
        email.setBounds(80,195,50,30);
//        email.setForeground(Color.green);
        c.add(email);
//        JLabel name = new JLabel("�ǳƣ�");
//        JLabel pwd = new JLabel("���룺");
//        JLabel pwd1 = new JLabel("ȷ�����룺");
//        JLabel num = new JLabel("��֤�룺");
//        JLabel sex = new JLabel("�Ա�");

        qqNum = new JTextField(); // �����˺ŵ�����
        qqNum.setBounds(120,195,194,30);
//        qqNum.setText("QQ����/�ֻ�");
//        qqNum.setForeground(Color.GRAY);
        c.add(qqNum);

        JLabel name = new JLabel("�ǳƣ�");
        name.setBounds(80,240,50,30);
        c.add(name);

        qqName = new JTextField();
        qqName.setBounds(120, 240, 194, 30);
        c.add(qqName);
        //�������������
        JLabel pwd = new JLabel("����");
        pwd.setBounds(80,285,50,30);
        c.add(pwd);
        qqPwd = new JPasswordField(); //
        qqPwd.setBounds(120,285,194,30);
        c.add(qqPwd);

        // �ٴ���������
        JLabel pwd1 = new JLabel("�ٴ��������룺");
        pwd1.setBounds(30,330,100,30);
        c.add(pwd1);

        qqPwd1 = new JPasswordField();
        qqPwd1.setBounds(120,330,194,30);
        c.add(qqPwd1);

        JCheckBox jch = new JCheckBox("�����Ķ���ͬ����ط�������");
        jch.setBounds(120,370,200,30);
        c.add(jch);
//        //�����˺��������"ע���˺�"
//        after_qqNum = new JLabel("ע���˺�");
//        after_qqNum.setForeground(Color.GRAY);
//        after_qqNum.setBounds(340,197,78,30);
//        c.add(after_qqNum);
//        //���������������"��������"
//        after_qqPwd = new JLabel("��������");
//        after_qqPwd.setForeground(Color.GRAY);
//        after_qqPwd.setBounds(340,240,78,30);
//        c.add(after_qqPwd);
//        //����"��ס����"��ѡ��
//        remPwd = new JCheckBox("��ס����");
//        remPwd.setBounds(123,277,85,15);
//        c.add(remPwd);
//        //����"�Զ���¼"��ѡ��
//        autoLog = new JCheckBox("�Զ���¼");
//        autoLog.setBounds(236,277,85,15);
//        c.add(autoLog);
//        �����ϲ���¼��ť
        btn_register = new JButton(new ImageIcon("F:/java/MyQqChat/image/login/register.png"));
        btn_register.setBounds(170,410,90,33);
        btn_register.addActionListener(this);
        c.add(btn_register);
//		380 299 40 40

//
//        //�����ά��
//        jlb_code = new JLabel(new ImageIcon("F:/java/MyQqChat/image/login/code.png"));
//        jlb_code.setBounds(380,299,40,40);
//        c.add(jlb_code);

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
        this.setSize(430,500);//���ô����С
        this.setUndecorated(true);//ȥ���Դ�װ�ο�
        this.setVisible(true);//���ô���ɼ�
    }

    /**
     * �����¼���д���
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btn_register){//�����¼
            String uid = qqNum.getText().trim();//��ȡ�����˺�
            String pwd = new String(qqPwd.getPassword());//��ȡ����
            String uname = new String(qqName.getText()); // ��ȡ�ǳ�
            //���ռ�����
            String flag = "Register";
            LoginUser loginU = new LoginUser();
            Message msg = loginU.sendRegisterToServer(this, uid , pwd, flag);
            if(null != msg){

                new Login();
                this.dispose();//�رյ�¼����
            }
        }
    }

    /**
     * ����һ����ȡ���ߺ��ѵ������
     * @param fromId
     */
    public void getOnlineFriends(String fromId){
        Message msg = new Message();
        msg.setType(MsgType.GET_ONLINE_FRIENDS);
        msg.setSenderId(fromId);
        try {
            ObjectOutputStream out = new ObjectOutputStream(ManageThread.getThread(fromId).getClient().getOutputStream());
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Register();
    }
}
