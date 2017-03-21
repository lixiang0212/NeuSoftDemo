package com.lx.neusoftdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    /**定义出布局文件中要使用到的控件*/
    private EditText et_name,et_password;
    private Button btn_login;
    private TextView tv_userForget,tv_newUser;
    private CheckBox checkBox;
    private SqlUtils sqlUtils;
    private String name,password;
    private int id;
    private LocalUser localUser;
    private CurrentUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        localLogin();
    }
   /**判断是否需要自动登录*/
    private void localLogin() {
        User user = localUser.getUser();
        if(user!=null){
            if(sqlUtils.login(user)) {
                Toast.makeText(this, "自动登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(this, LogoActivity.class);
                startActivity(intent);
                finish();
            }else {
              //  Toast.makeText(this, "自动登录失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**通过控件的id找到对应布局中的id，并设置相应的点击事件*/
    private void initView() {
        //通过控件的id找到对应布局中的id，并设置相应的点击事件
        sqlUtils = new SqlUtils(this);
        localUser = new LocalUser(this);
        currentUser = new CurrentUser(this);
        et_name = (EditText) findViewById(R.id.et_userName);
        et_password = (EditText) findViewById(R.id.et_userPassword);
        btn_login = (Button) findViewById(R.id.btn_userLogin);
        btn_login.setOnClickListener(this);
        tv_userForget = (TextView) findViewById(R.id.tv_userForget);
        tv_userForget.setOnClickListener(this);
        tv_newUser = (TextView) findViewById(R.id.tv_userApply);
        tv_newUser.setOnClickListener(this);
        checkBox = (CheckBox) findViewById(R.id.cb_checkbox);
        checkBox.setSelected(false);
    }

   /**控件对应的点击事件*/
    @Override
    public void onClick(View view) {
        //通过Intent来做页面跳转
        Intent intent = new Intent();
        intent.setClass(this,ApplyActivity.class);
        switch (view.getId()){
            case R.id.btn_userLogin:
                name = et_name.getText().toString();
                password = et_password.getText().toString();
                if(name.equals("")||password.equals("")){
                    Toast.makeText(this,"请输入正确的手机号和密码",Toast.LENGTH_SHORT).show();
                }else {
                    id = getName(name);
                    User user = new User(id, password);
                    if (sqlUtils.login(user)) {
                        currentUser.saveUser(user);
                        if (checkBox.isChecked()) {
                            localUser.saveUser(user);
                        }
                        btn_login.setText("登录");
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        intent.setClass(this, LogoActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        btn_login.setText("登录");
                        Toast.makeText(this, "登录失败，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tv_userForget:
                startActivity(intent);
                break;
            case R.id.tv_userApply:
                startActivity(intent);
                break;
        }
    }
   /** 把输入的帐号强转为数字*/
    private int getName(String name){
        int ids = 0;
        try {
             ids = Integer.valueOf(name);
        }catch (Exception e){
           // Toast.makeText(this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
        }
        return ids;
    }
}
