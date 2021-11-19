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
//        // 窗体属性
//        jf.setTitle("注册界面");
//        jf.setSize(400,500);
//        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jf.setLocationRelativeTo(null);
//        jf.setResizable(true);
//        // 流式布局
//        FlowLayout fl = new FlowLayout();
//        jf.setLayout(fl);
//        // 组件
//        // 按钮
//        btn = new JButton(new ImageIcon("F:/java/MyQqChat/image/login/register.png"));
//        btn.setBounds(170,450,60,33);
//        //图片
//        ImageIcon img = new ImageIcon("F:/java/MyQqChat/image/login/login.jpg");
//        JLabel imgJla= new JLabel(img);
//        JLabel email = new JLabel("账号：");
//        JLabel name = new JLabel("昵称：");
//        JLabel pwd = new JLabel("密码：");
//        JLabel pwd1 = new JLabel("确认密码：");
//        JLabel num = new JLabel("验证码：");
//        JLabel sex = new JLabel("性别");
//        //输入框
////        JTextField nameField = new JTextField();
////        JTextField emailField = new JTextField();
//        JTextField numField = new JTextField();
//        // 账号输入
//        emailField = new JTextField();
//        // 昵称输入
//        nameField = new JTextField();
//        // 密码输入
//        pwdField = new JTextField();
////        JPasswordField pwdField = new JPasswordField();
//        JPasswordField pwdField1 = new JPasswordField();
//        //组件(输入框)属性
//        Dimension dim = new Dimension(300,30);
//        nameField.setPreferredSize(dim);
//        pwdField.setPreferredSize(dim);
//        emailField.setPreferredSize(dim);
//        numField.setPreferredSize(dim);
//        pwdField1.setPreferredSize(dim);
//        //复选框
//        JCheckBox jch = new JCheckBox("我已阅读并同意相关服务条款");
//        //单选框
//        JRadioButton jra1 = new JRadioButton("男");
//        JRadioButton jra2 = new JRadioButton("女");
//        //添加组件
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
//        jf.setVisible(true);//可视化
//    }
//
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource()==btn){
//            String uid = emailField.getText().trim(); //获取账号
//            String rpwd = pwdField.getText().trim(); // 获取密码
//            String rname = nameField.getText().trim(); //获取昵称
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
 * 	客户端登录页面
 * 	点击登录按钮实现按钮，已实现用户身份校验
 * @author wangwei
 *
 */
public class Register extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JLabel jlb_north;//北部背景图片标签
    private JLabel jlb_code;//二维码图片
    private JButton btn_exit,btn_min;//右上角最小化和关闭按钮
    private JLabel jlb_photo;//中部账号密码框左边企鹅图片标签
    private JTextField qqNum;//账号输入框
    private JTextField qqName;//账号输入框
    private JPasswordField qqPwd;//密码输入框
    private JPasswordField qqPwd1;//密码输入框
    private JLabel after_qqNum;//账号输入框后的"注册账号"
    private JLabel after_qqPwd;//密码输入框后的"忘记密码"
    private JCheckBox remPwd;//"记住密码"单选框
    private JCheckBox autoLog;//"自动登录"单选框
    private JButton btn_register;//南部登录按钮
    boolean isDragged = false;//记录鼠标是否是拖拽移动
    private Point frame_temp;//鼠标当前相对窗体的位置坐标
    private Point frame_loc;//窗体的位置坐标

    public Register() {
        //获取此窗口容器
        Container c = this.getContentPane();
        //设置布局
        c.setLayout(null);
//
//        c.setName("注册");
        c.setLocation(10,20);
        //处理北部背景图片标签
        jlb_north = new JLabel(new ImageIcon("F:/java/MyQqChat/image/login/login.jpg"));
        jlb_north.setBounds(0,0,430,182);
        c.add(jlb_north);
        //处理右上角最小化和关闭按钮
        btn_min = new JButton(new ImageIcon("F:/java/MyQqChat/image/login/min.jpg"));
        btn_min.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //注册监听器,点击实现窗口最小化
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        btn_min.setBounds(373, 0, 28, 29);
        c.add(btn_min);

        btn_exit = new JButton(new ImageIcon("F:/java/MyQqChat/image/login/exit.png"));
        btn_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //注册监听器,点击实现窗口关闭
                System.exit(0);
            }
        });
        btn_exit.setBounds(401, 0, 28, 29);
        c.add(btn_exit);

        //处理中部账号密码框左边企鹅图片标签
        //处理账号输入框
        JLabel email = new JLabel("账号：");
        email.setBounds(80,195,50,30);
//        email.setForeground(Color.green);
        c.add(email);
//        JLabel name = new JLabel("昵称：");
//        JLabel pwd = new JLabel("密码：");
//        JLabel pwd1 = new JLabel("确认密码：");
//        JLabel num = new JLabel("验证码：");
//        JLabel sex = new JLabel("性别");

        qqNum = new JTextField(); // 处理账号的输入
        qqNum.setBounds(120,195,194,30);
//        qqNum.setText("QQ号码/手机");
//        qqNum.setForeground(Color.GRAY);
        c.add(qqNum);

        JLabel name = new JLabel("昵称：");
        name.setBounds(80,240,50,30);
        c.add(name);

        qqName = new JTextField();
        qqName.setBounds(120, 240, 194, 30);
        c.add(qqName);
        //处理密码输入框
        JLabel pwd = new JLabel("密码");
        pwd.setBounds(80,285,50,30);
        c.add(pwd);
        qqPwd = new JPasswordField(); //
        qqPwd.setBounds(120,285,194,30);
        c.add(qqPwd);

        // 再次输入密码
        JLabel pwd1 = new JLabel("再次输入密码：");
        pwd1.setBounds(30,330,100,30);
        c.add(pwd1);

        qqPwd1 = new JPasswordField();
        qqPwd1.setBounds(120,330,194,30);
        c.add(qqPwd1);

        JCheckBox jch = new JCheckBox("我已阅读并同意相关服务条款");
        jch.setBounds(120,370,200,30);
        c.add(jch);
//        //处理账号输入框后的"注册账号"
//        after_qqNum = new JLabel("注册账号");
//        after_qqNum.setForeground(Color.GRAY);
//        after_qqNum.setBounds(340,197,78,30);
//        c.add(after_qqNum);
//        //处理密码输入框后的"忘记密码"
//        after_qqPwd = new JLabel("忘记密码");
//        after_qqPwd.setForeground(Color.GRAY);
//        after_qqPwd.setBounds(340,240,78,30);
//        c.add(after_qqPwd);
//        //处理"记住密码"单选框
//        remPwd = new JCheckBox("记住密码");
//        remPwd.setBounds(123,277,85,15);
//        c.add(remPwd);
//        //处理"自动登录"单选框
//        autoLog = new JCheckBox("自动登录");
//        autoLog.setBounds(236,277,85,15);
//        c.add(autoLog);
//        处理南部登录按钮
        btn_register = new JButton(new ImageIcon("F:/java/MyQqChat/image/login/register.png"));
        btn_register.setBounds(170,410,90,33);
        btn_register.addActionListener(this);
        c.add(btn_register);
//		380 299 40 40

//
//        //处理二维码
//        jlb_code = new JLabel(new ImageIcon("F:/java/MyQqChat/image/login/code.png"));
//        jlb_code.setBounds(380,299,40,40);
//        c.add(jlb_code);

        //注册鼠标事件监听器
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //鼠标释放
                isDragged = false;
                //光标恢复
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e) {
                //鼠标按下
                //获取鼠标相对窗体位置
                frame_temp = new Point(e.getX(),e.getY());
                isDragged = true;
                //光标改变为移动形式
                if(e.getY() < 182)
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        });
        //注册鼠标事件监听器
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //指定范围内点击鼠标可拖拽
                if(e.getY() < 182){
                    //如果是鼠标拖拽移动
                    if(isDragged) {
                        frame_loc = new Point(getLocation().x+e.getX()-frame_temp.x,
                                getLocation().y+e.getY()-frame_temp.y);
                        //保证鼠标相对窗体位置不变,实现拖动
                        setLocation(frame_loc);
                    }
                }
            }
        });

        this.setIconImage(new ImageIcon("F:/java/MyQqChat/image/login/Q.png").getImage());//修改窗体默认图标
        this.setSize(430,500);//设置窗体大小
        this.setUndecorated(true);//去掉自带装饰框
        this.setVisible(true);//设置窗体可见
    }

    /**
     * 点击登录进行处理
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btn_register){//点击登录
            String uid = qqNum.getText().trim();//获取输入账号
            String pwd = new String(qqPwd.getPassword());//获取密码
            String uname = new String(qqName.getText()); // 获取昵称
            //接收检验结果
            String flag = "Register";
            LoginUser loginU = new LoginUser();
            Message msg = loginU.sendRegisterToServer(this, uid , pwd, flag);
            if(null != msg){

                new Login();
                this.dispose();//关闭登录界面
            }
        }
    }

    /**
     * 发送一个获取在线好友的请求包
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
