package com.lx.neusoftdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static cn.smssdk.SMSSDK.getSupportedCountries;
import static cn.smssdk.SMSSDK.getVerificationCode;
import static cn.smssdk.SMSSDK.submitVerificationCode;

public class ApplyActivity extends Activity implements View.OnClickListener {
    private TextView tv_back;
    private Button btn_apply,btn_code;
    private EditText et_name,et_code,et_password,et_passwordT;
    private SqlUtils sqlUtils;
    //手机号和验证码
    private String phoneNumber,SmsCode,password;
    private int name;
    //控制按钮样式是否改变
    private boolean tag = true;
    //每次验证请求需要间隔60S
    private int i=60;
    //app key和app secret 需要填自己应用的对应的！
    private final String AppKey = "1ba01e899bff6";
    private final String AppSecret = "746504186ce3f7c94f846ec5aa6c1d1d";
    private EventHandler eventHandler;//Sms自带的事件
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1){
                case 0:
                    //客户端验证成功，可以进行注册,返回校验的手机和国家代码phone/country
                  //  Toast.makeText(ApplyActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    phoneNumber = et_name.getText().toString();
                    password = et_password.getText().toString();
                    name = Integer.parseInt(phoneNumber);
                    sqlUtils.addUser(new User(name,password));
                    Toast.makeText(ApplyActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    et_code.setText("");
                    et_password.setText("");
                    et_passwordT.setText("");
                    btn_apply.setText("确定");
                    break;
                case 1:
                    //获取验证码成功
                    Toast.makeText(ApplyActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    btn_apply.setText("确定");
                    break;
                case 2:
                    //返回支持发送验证码的国家列表
                    Toast.makeText(ApplyActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    btn_apply.setText("确定");
                    break;
                case 3:
//                    //验证码错误，注册失败
                    Toast.makeText(ApplyActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    btn_apply.setText("确定");

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_apply);
        //初始化短信验证SDK
        SMSSDK.initSDK(this,AppKey,AppSecret);
        initView();
        initEventHandler();
    }

    /**初始化相关控件，并设置点击事件*/
    private void initView() {
        sqlUtils = new SqlUtils(this);
        tv_back = (TextView) findViewById(R.id.tv_applyBack);
        tv_back.setOnClickListener(this);
        btn_code = (Button) findViewById(R.id.btn_userCode);
        btn_code.setOnClickListener(this);
        btn_apply = (Button) findViewById(R.id.btn_userApply);
        btn_apply.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_newUserName);
        et_code = (EditText) findViewById(R.id.et_userCode);
        et_password = (EditText) findViewById(R.id.et_newUserPassword);
        et_passwordT = (EditText) findViewById(R.id.et_newUserPasswordTwo);
    }
    /**短信验证对应的事件*/
    private void initEventHandler() {
        eventHandler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                super.afterEvent(event, result, data);
                if(result == SMSSDK.RESULT_COMPLETE){
                    //回调完成
                    if(event==SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        //提交验证码成功
                        Message msg = new Message();
                        msg.arg1 = 0;
                        msg.obj = "注册成功";
                        handler.sendMessage(msg);
                        Log.i("AAA","提交验证码成功");
                    }else if(event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        Message msg = new Message();
                        msg.arg1 = 1;
                        msg.obj = "获取验证码成功";
                        handler.sendMessage(msg);
                        Log.i("AAA","获取验证码成功");
                    }else if(event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        Message msg = new Message();
                        msg.arg1 = 2;
                        msg.obj = "返回支持发送验证码的国家列表";
                        handler.sendMessage(msg);
                        Log.i("AAA", "返回支持发送验证码的国家列表");
                    }
                }else {
                    //返回支持发送验证码的国家列表
                    Message msg = new Message();
                    msg.arg1 = 3;
                    msg.obj = "注册失败"+data;
                    handler.sendMessage(msg);
                    Log.d("AAA", "验证失败");
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

   /**点击事件的设置*/
    @Override
    public void onClick(View view) {
        phoneNumber = et_name.getText().toString();
        switch (view.getId()){
            case R.id.tv_applyBack:
               finish();
                break;
            case R.id.btn_userCode:
                if(phoneNumber.equals("")){
                    Toast.makeText(ApplyActivity.this,"手机号码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    if(isMobileNO(phoneNumber)){
                        btn_code.setClickable(true);
                        changeBtnGetCode();
                        getSupportedCountries();
                        getVerificationCode("86",phoneNumber);
                    }else {
                        Toast.makeText(ApplyActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_userApply:
                SmsCode = et_code.getText().toString();
                phoneNumber = et_name.getText().toString();
                password = et_password.getText().toString();
                String passwordT= et_passwordT.getText().toString();

                if(phoneNumber.equals("")||SmsCode.equals("")){
                    Toast.makeText(ApplyActivity.this,"请输入手机号和验证码",Toast.LENGTH_SHORT).show();
                }else if(password.equals("")||passwordT.equals("")){
                    Toast.makeText(ApplyActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }else if(password.equals(passwordT)) {
                    submitVerificationCode("86", phoneNumber, SmsCode);
                    btn_apply.setText("注册中...");
                }else {
                    Toast.makeText(ApplyActivity.this,"确认密码错误",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    /** 改变按钮样式*/
    private void changeBtnGetCode() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        //如果活动为空
                        if (ApplyActivity.this == null) {break;}
                        ApplyActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                              btn_code.setText("获取验证码(" + i + ")");
                              btn_code.setClickable(false);
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }}
                    tag = false;
                }
                i = 60;tag = true;
                if (ApplyActivity.this!= null) {
                    ApplyActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           btn_code.setText("获取验证码");
                           btn_code.setClickable(true);
                        }
                    });
                }}};thread.start();
    }
   /**验证输入的手机号是否有效*/
    private boolean isMobileNO(String phone) {
       /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(phone)) return false;
        else return phone.matches(telRegex);
    }
}
