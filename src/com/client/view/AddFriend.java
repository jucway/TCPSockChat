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
 * 	客户端登录页面
 * 	点击登录按钮实现按钮，已实现用户身份校验
 * @author wangwei
 *
 */
public class AddFriend<showImage> extends JFrame implements ActionListener {

    private static final long serialVersionUID = 2L;

    private JLabel jlb_north;//北部背景图片标签
    private JLabel jlb_code;//二维码图片
    private JButton btn_exit,btn_min;//右上角最小化和关闭按钮
    private JLabel jlb_photo;//中部账号密码框左边企鹅图片标签
    private JTextField qqNum;//账号输入框
    private JTextField qqPwd;//密码输入框
    private JLabel qq_nick;//账号输入框后的"注册账号"
    private JLabel qq;//密码输入框后的"忘记密码"
    private JCheckBox remPwd;//"记住密码"单选框
    private JCheckBox autoLog;//"自动登录"单选框
    private JButton btn_login;//南部登录按钮
    boolean isDragged = false;//记录鼠标是否是拖拽移动
    private Point frame_temp;//鼠标当前相对窗体的位置坐标
    private Point frame_loc;//窗体的位置坐标
    public String myid;
    public void setmyid(String myid){
        this.myid = myid;}

    public AddFriend(String myid) {
        setmyid(myid);
        //获取此窗口容器
        Container c = this.getContentPane();
        //设置布局
        c.setLayout(null);
//
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

        //处理账号输入框
        qqNum = new JTextField(); // 处理账号的输入
        qqNum.setBounds(120,195,194,30);
//        qqNum.setText("QQ号码/手机");
        qqNum.setForeground(Color.GRAY);
        c.add(qqNum);
        //处理密码输入框
        qqPwd = new JTextField(); //
        qqPwd.setBounds(120,240,194,30);
        c.add(qqPwd);
        //处理账号
//        qq.setForeground(Color.GRAY);
        qq = new JLabel("账号:");
        qq.setForeground(Color.GRAY);
        qq.setBounds(70,240,50, 30);
        c.add(qq);
        //处理昵称
        qq_nick = new JLabel("昵称:");
        qq_nick.setForeground(Color.GRAY);
        qq_nick.setBounds(70,197,50,30);
        c.add(qq_nick);


        //处理南部登录按钮
        btn_login = new JButton(new ImageIcon("F:/java/MyQqChat/image/addfriend/add.png"));
        btn_login.setBounds(120,299,194,30);
        btn_login.addActionListener(this);
        c.add(btn_login);





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
        this.setSize(430,345);//设置窗体大小
        this.setUndecorated(true);//去掉自带装饰框
        this.setVisible(true);//设置窗体可见
        this.setLocationRelativeTo(null);
    }

    /**
     * 点击登录进行处理
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btn_login) {//点击登录
            String qqnick = qqNum.getText().trim();//获取输入账号
            String qqid = qqPwd.getText().trim();//获取密码
            GroupChat gc = new GroupChat();
            gc.setnick(qqnick);
            gc.setId(qqid);


            Message msg = new Message();
            msg.setContent("新用户"+" "+qqnick+"("+qqid+") 加入群聊");
//            msg.setSendTime(df.format(new Date()));
            try {
                ObjectOutput out = new ObjectOutputStream(ManageThread.getThread(myid).getClient().getOutputStream());
                out.writeObject(msg);
                System.out.println("用户添加成功！！！");
                showAddFriend(msg,true);

            } catch (IOException c) {
                c.printStackTrace();
            }
            //接收检验结果
            this.dispose();//关闭登录界面
        }
    }
    public void showAddFriend(Message msg, boolean fromSelf) {
        GroupChat gC = new GroupChat();
        showAddFriend(gC.getPanel_Msg(), msg, fromSelf);//先显示到聊天内容面板
        showAddFriend(gC.getPanel_Record(), msg, fromSelf);//再显示到聊天记录面板
    }
    private void showAddFriend(JTextPane jtp,Message msg, boolean fromSelf) {

        //设置显示格式
        SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attrset, "仿宋");
        StyleConstants.setFontSize(attrset,14);
        Document docs = jtp.getDocument();
        String info = null;
        try {
            if(fromSelf){//发出去的消息内容
                info = "我  ";//自己账号：紫色
                StyleConstants.setForeground(attrset, Color.MAGENTA);
                docs.insertString(docs.getLength(), info, attrset); StyleConstants.setForeground(attrset, Color.red);
                info = msg.getSendTime()+":\n";//发送时间：绿色
                StyleConstants.setForeground(attrset, Color.black);
                docs.insertString(docs.getLength(), info, attrset);
                info = " "+msg.getContent()+"\n";//发送内容：黑色
                StyleConstants.setFontSize(attrset,16);
                StyleConstants.setForeground(attrset, Color.green);
                docs.insertString(docs.getLength(), info, attrset);
            }else{//接收到的消息内容
                info = msg.getSenderName()+"("+msg.getSenderId()+")  ";//对方账号：红色
                StyleConstants.setForeground(attrset, Color.red);
                docs.insertString(docs.getLength(), info, attrset); StyleConstants.setForeground(attrset, Color.red);
                info = msg.getSendTime()+":\n";//发送时间：绿色
                StyleConstants.setForeground(attrset, Color.black);
                docs.insertString(docs.getLength(), info, attrset);
                if(msg.getSendtype().equals("image")){
                    String image = " "+"发送了文件(图片)"+"\n";

                    StyleConstants.setFontSize(attrset,16);
                    StyleConstants.setForeground(attrset, Color.blue);
                    docs.insertString(docs.getLength(), image, attrset);
                }//发送内容：蓝色}
                else {
                    info = " "+msg.getContent()+"\n";//发送内容：蓝色
                    StyleConstants.setFontSize(attrset,16);
                    StyleConstants.setForeground(attrset, Color.blue);
                    docs.insertString(docs.getLength(), info, attrset);}//发送内容：蓝色
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }





//    public static void main(String[] args) {
//        new AddFriend();
//    }
}
