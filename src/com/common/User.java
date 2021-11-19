
package com.common;

/**
 * 用户信息类
 */
public class User implements java.io.Serializable {

    private String uid;
    private String pwd;
    private String Flag;
    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        this.Flag = flag;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public User(String uid, String pwd, String flag) {
        this.uid = uid;
        this.pwd = pwd;
        this.Flag = flag;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", pwd='" + pwd + '\'' +
                ", flag='"+ Flag+'\''+
                '}';
    }
}
