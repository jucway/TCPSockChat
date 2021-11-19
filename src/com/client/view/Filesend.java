//package com.client.view;
//
//import com.client.model.LoginUser;
//import com.client.tools.ManageFriendListFrame;
//import com.client.tools.ManageThread;
//import com.common.Message;
//import com.common.MsgType;
//
//import java.awt.Color;
//import java.awt.Container;
//import java.awt.Cursor;
//import java.awt.Point;
//import java.awt.event.*;
//import java.io.IOException;
//import java.io.ObjectOutputStream;
//
//import javax.swing.*;
//
///**
// * 	客户端登录页面
// * 	点击登录按钮实现按钮，已实现用户身份校验
// * @author wangwei
// *
// */
//
//public class Filesend extends JFram implements ActionListener{
//
//
//    private static final long serialVersionUID = 1L;
//
//    private JLabel jlb_north;//北部背景图片标签
//    private JButton btn_exit,btn_min;//右上角最小化和关闭按钮
//    private JLabel jlb_photo;//中部账号密码框左边企鹅图片标签
//    private JTextField filename;//账号输入框
//    private JButton btn_send;//南部发送按钮
//    boolean isDragged = false;//记录鼠标是否是拖拽移动
//    private Point frame_temp;//鼠标当前相对窗体的位置坐标
//    private Point frame_loc;//窗体的位置坐标
//
//    public Filesend() {
//        //获取此窗口容器
//        Container c = this.getContentPane();
//        //设置布局
//        c.setLayout(null); //设置布局，相当与各画布
//
//        //处理北部背景图片标签
//        jlb_north = new JLabel(new ImageIcon("image/filesend/back.png")); //设置背景图
//        jlb_north.setBounds(0,0,430,262);
//        c.add(jlb_north);
//        //处理右上角最小化和关闭按钮
//        btn_min = new JButton(new ImageIcon("image/filesend/min.jpg"));
//        btn_min.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //注册监听器,点击实现窗口最小化
//                setExtendedState(JFrame.ICONIFIED);
//            }
//        });
//        btn_min.setBounds(373, 0, 28, 29);
//        c.add(btn_min);
//
//        btn_exit = new JButton(new ImageIcon("image/filesend/exit.png"));
//        btn_exit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //注册监听器,点击实现窗口关闭
//                System.exit(0);
//            }
//        });
//        btn_exit.setBounds(401, 0, 28, 29);
//        c.add(btn_exit);
//
//        //处理中部账号密码框左边企鹅图片标签
//        jlb_photo = new JLabel(new ImageIcon("image/login/wenj.png"));
//        jlb_photo.setBounds(20, 280, 82, 78);
//        c.add(jlb_photo);
//        //处理账号输入框
//        qqNum = new JTextField(); // 处理账号的输入
//        qqNum.setBounds(120,285,194,30);
//        c.add(qqNum);
//
//        //处理南部登录按钮
//        btn_send = new JButton(new ImageIcon("image/filesend/send.png"));
//        btn_send.setBounds(410,285,30,30);
//        btn_send.addActionListener(this);
//        c.add(btn_send);
//
//        //注册鼠标事件监听器
//        this.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                //鼠标释放
//                isDragged = false;
//                //光标恢复
//                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//            }
//            @Override
//            public void mousePressed(MouseEvent e) {
//                //鼠标按下
//                //获取鼠标相对窗体位置
//                frame_temp = new Point(e.getX(),e.getY());
//                isDragged = true;
//                //光标改变为移动形式
//                if(e.getY() < 182)
//                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
//            }
//        });
//        //注册鼠标事件监听器
//        this.addMouseMotionListener(new MouseMotionAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                //指定范围内点击鼠标可拖拽
//                if(e.getY() < 182){
//                    //如果是鼠标拖拽移动
//                    if(isDragged) {
//                        frame_loc = new Point(getLocation().x+e.getX()-frame_temp.x,
//                                getLocation().y+e.getY()-frame_temp.y);
//                        //保证鼠标相对窗体位置不变,实现拖动
//                        setLocation(frame_loc);
//                    }
//                }
//            }
//        });
//
//        this.setIconImage(new ImageIcon("image/filesend/Q.png").getImage());//修改窗体默认图标
//        this.setSize(430,345);//设置窗体大小
//        this.setUndecorated(true);//去掉自带装饰框
//        this.setVisible(true);//设置窗体可见
//    }
//    @Override //处理点击发送按钮
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource() == btn_send){//点击发送
//            String uid = qqNum.getText().trim();//获取输入文件名
//
//            Message msg = new LoginUser().sendLoginInfoToServer(this, uid , pwd);
//
//                this.dispose();//关闭登录界面
//            }
//        }
//}
//    /**
//     * 点击登录进行处理
//     * @param e
//     */
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource() == btn_login){//点击登录
//            String uid = qqNum.getText().trim();//获取输入账号
//            String pwd = new String(qqPwd.getPassword());//获取密码
//            //接收检验结果
//            Message msg = new LoginUser().sendLoginInfoToServer(this, uid , pwd);
//            if(null != msg){
//                String[] info = msg.getContent().split("-");
//                msg.setContent(info[1]);//后面内容为全部好友
//                FriendList fl = new FriendList(info[0], uid, msg);//进入列表界面
//                ManageFriendListFrame.addFriendListFrame(uid,fl);
//                //发送获取在线好友信息包
//                getOnlineFriends(uid);
//                this.dispose();//关闭登录界面
//            }
//        }
//    }
//
//    /**
//     * 发送一个获取在线好友的请求包
//     * @param fromId
//     */
//    public void getOnlineFriends(String fromId){
//        Message msg = new Message();
//        msg.setType(MsgType.GET_ONLINE_FRIENDS);
//        msg.setSenderId(fromId);
//        try {
//            ObjectOutputStream out = new ObjectOutputStream(ManageThread.getThread(fromId).getClient().getOutputStream());
//            out.writeObject(msg);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        new Login();
//    }
//}
