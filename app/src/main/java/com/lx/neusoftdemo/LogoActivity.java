package com.lx.neusoftdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MainFragment mainFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private LayoutInflater inflater;
    private SqlUtils sqlUtils;
    private CurrentUser currentUser;
    private LocalUser localUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        initView();
        initFragment();
        navigationViewListener();
    }
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
        inflater = LayoutInflater.from(this);
        sqlUtils = new SqlUtils(this);
        currentUser = new CurrentUser(this);
        localUser = new LocalUser(this);
    }
    private void initFragment() {
        mainFragment = new MainFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout,mainFragment);
        fragmentTransaction.show(mainFragment);
        fragmentTransaction.commit();
    }
    /**侧滑菜单中的选项监听*/
    private void navigationViewListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.setting:
                        Toast.makeText(LogoActivity.this,"设置",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.update:
                        View view = inflater.inflate(R.layout.dialog,null);
                        final EditText et_password = (EditText) view.findViewById(R.id.dialog_etPassword);
                        final EditText et_passwordT = (EditText) view.findViewById(R.id.dialog_etPasswordT);
                        new AlertDialog.Builder(LogoActivity.this)
                                .setTitle("修改密码")
                                .setView(view)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String a = et_password.getText().toString();
                                        String b = et_passwordT.getText().toString();
                                        if(a.equals(b)){
                                            User user = currentUser.getUser();
                                            sqlUtils.updateUser(new User(user.getName(),a));
                                            Toast.makeText(LogoActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent();
                                            intent.setClass(LogoActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                      return;
                                    }
                                })
                                .show();
                        break;
                    case R.id.back:
                        Toast.makeText(LogoActivity.this,"退出当前帐号",Toast.LENGTH_SHORT).show();
                        localUser.saveUser(new User(0,"abc"));
                        Intent intent = new Intent();
                        intent.setClass(LogoActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }return true;
            }
        });
    }


}
