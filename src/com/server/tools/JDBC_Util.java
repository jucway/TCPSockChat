package com.server.tools;

import com.common.User;

import java.sql.*;

/**
 * ����һ������JDBC����MySql�Ĺ�����
 * ʵ�����ж����ݿ�Ĳ����������ṩ����
 */
public class JDBC_Util {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;

	public JDBC_Util(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //��������
            con = DriverManager.getConnection("jdbc:mysql://localhost/chat?useSSL=FALSE&serverTimezone=Asia/Shanghai","root","123456");
            System.out.println("���ӳɹ�");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * ����uid��pwd�����ݿ��к�ʵ�û���Ϣ
     * ����qq�ǳƷ���
     * @param u
     * @return
     */
    public String checkUserInfo(User u){
        String qname = null;
        int uid = Integer.parseInt(u.getUid());
        String pwd = u.getPwd();
        String sql = "select uname from  t_user where uid=? and pwd=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1,uid);
            ps.setString(2,pwd);
            rs = ps.executeQuery();

            while (rs.next()){
                qname = rs.getString("uname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            release(con,ps,rs);
        }
        return qname;
    }
// todo :register
//
    public String RegisterInfo(User u) throws SQLException {
        String qname = null;
        int id = Integer.parseInt(u.getUid());
        String rpwd = u.getPwd();
//        String sql = "insert into t_user(uid, pwd) VALUES(id, rpwd)";
        try {
            String sql = "insert into t_user(uid,pwd)" + " values('" + id + "','" + rpwd + "')";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("ע��ɹ�");
            qname = "ע��ɹ�";

            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();

        }

        return qname;
    }
    /**
     * ����ȫ�������б�
     * @return
     */
    public String getFriendsList(String uid){
        StringBuilder contents = new StringBuilder();
        int mqq = Integer.parseInt(uid);
        String sql = "select fname,fqq from  t_friends where mqq=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1,mqq);
            rs = ps.executeQuery();
            while(rs.next()){
                contents.append(rs.getString("fname")+"("+rs.getString("fqq")+") ");
            }
            return contents.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            release(con,ps,rs);
        }
        return null;
    }
    /**
     * �ͷ���Դ
     * @param conn
     * @param st
     * @param rs
     */
	public void release(Connection conn,Statement st,ResultSet rs) {
		//�ر���Դ
		try {
			if(null != rs)
				rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(null != st)
				st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(null != conn)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    /**
     * �ͷ���Դ����
     * @param conn
     * @param st
     */
	public void release(Connection conn,Statement st) {
		//�ر���Դ
		try {
			if(null != st)
				st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(null != conn)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public static void main(String[] args) {
        JDBC_Util jc = new JDBC_Util();
        System.out.println(jc);
    }

}


