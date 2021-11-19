package com.client.view;

import com.client.model.LoginUser;
import com.client.tools.ManageFriendListFrame;
import com.client.tools.ManageThread;
import com.common.Message;
import com.common.MsgType;
import com.common.User;

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
public class Login extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	private JLabel jlb_north;//��������ͼƬ��ǩ
	private JLabel jlb_code;//��ά��ͼƬ
	private JButton btn_exit,btn_min;//���Ͻ���С���͹رհ�ť
	private JLabel jlb_photo;//�в��˺������������ͼƬ��ǩ
	private JTextField qqNum;//�˺������
	private JPasswordField qqPwd;//���������
	private JLabel after_qqNum;//�˺��������"ע���˺�"
	private JLabel after_qqPwd;//�����������"��������"
	private JCheckBox remPwd;//"��ס����"��ѡ��
	private JCheckBox autoLog;//"�Զ���¼"��ѡ��
	private JButton btn_login;//�ϲ���¼��ť
	boolean isDragged = false;//��¼����Ƿ�����ק�ƶ�
	private Point frame_temp;//��굱ǰ��Դ����λ������
	private Point frame_loc;//�����λ������
	private JButton btn_register; //ע���˺�
	
	public Login() {
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
		
		//�����в��˺������������ͼƬ��ǩ
		jlb_photo = new JLabel(new ImageIcon("F:/java/MyQqChat/image/login/cw.png"));
		jlb_photo.setBounds(20, 192, 82, 78);
		c.add(jlb_photo);
		//�����˺������
		qqNum = new JTextField(); // �����˺ŵ�����
		qqNum.setBounds(120,195,194,30);
//		qqNum.setText("QQ����/�ֻ�");
		qqNum.setForeground(Color.GRAY);
		c.add(qqNum);
		//�������������
		qqPwd = new JPasswordField(); //
		qqPwd.setBounds(120,240,194,30);

		c.add(qqPwd);
//		�����˺��������"ע���˺�"
		after_qqNum = new JLabel("ע���˺�");
		after_qqNum.setForeground(Color.GRAY);
		after_qqNum.setBounds(340,197,78,30);
		c.add(after_qqNum);
//		//�����˺��������"ע���˺�"
//		btn_register = new JButton("ע���˺�");
//		btn_register.setForeground(Color.GRAY);
//		btn_register.setBounds(340,197,78,30);
//		c.add(btn_register);
		//���������������"��������"
		after_qqPwd = new JLabel("��������");
		after_qqPwd.setForeground(Color.GRAY);
		after_qqPwd.setBounds(340,240,78,30);
		c.add(after_qqPwd);
		//����"��ס����"��ѡ��
		remPwd = new JCheckBox("��ס����");
		remPwd.setBounds(123,277,85,15);
		c.add(remPwd);
		//����"�Զ���¼"��ѡ��
		autoLog = new JCheckBox("�Զ���¼");
		autoLog.setBounds(236,277,85,15);
		c.add(autoLog);
		//�����ϲ���¼��ť
		btn_login = new JButton(new ImageIcon("F:/java/MyQqChat/image/login/loginbutton.png"));
		btn_login.setBounds(120,299,194,30);
		btn_login.addActionListener(this);
		c.add(btn_login);
//		380 299 40 40
		btn_register = new JButton(new ImageIcon("F:/java/MyQqChat/image/login/reg_login.png"));
		btn_register.setBounds(30, 277, 62, 20);
		btn_register.addActionListener(this);
		c.add(btn_register);
//		30 277 62 20
		//�����ά��
		jlb_code = new JLabel(new ImageIcon("F:/java/MyQqChat/image/login/code.png"));
		jlb_code.setBounds(380,299,40,40);
		c.add(jlb_code);
		
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

        if(e.getSource() == btn_login){//�����¼
            String uid = qqNum.getText().trim();//��ȡ�����˺�
            String pwd = new String(qqPwd.getPassword());//��ȡ����
            //���ռ�����
			String flag = "Login";
			LoginUser loginU = new LoginUser();
            Message msg = loginU.sendLoginInfoToServer(this, uid , pwd,flag);
            if(null != msg){
                String[] info = msg.getContent().split("-");
                msg.setContent(info[1]);//��������Ϊȫ������
                FriendList fl = new FriendList(info[0], uid, msg);//�����б����
                ManageFriendListFrame.addFriendListFrame(uid,fl);
                //���ͻ�ȡ���ߺ�����Ϣ��
                getOnlineFriends(uid);
                this.dispose();//�رյ�¼����
            }
        }
		else if(e.getSource() == btn_register){
			new Register();
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
        new Login();
    }
}
