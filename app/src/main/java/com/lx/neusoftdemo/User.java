package com.lx.neusoftdemo;

/**
 * Created by lixiang on 2017/2/27.
 */

public class User {
    //User的属性
    private int name;
    private String password;
   /**构造方法*/
    public User(int name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
