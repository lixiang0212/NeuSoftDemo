package com.lx.neusoftdemo;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private EditText et_find;
    private Button btn_find;
    private ListView listView;
    private String result;
    private DataInfo dataInfo;
    private Gson gson;
    private List<DataInfo.DataBean> data;
    private MyAdapter adapter;
    private Handler handler = new Handler();




    public MainFragment() {
        // Required empty public constructor
    }
    //绑定视图
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }
    /*找到控件，设置监听*/
    private void initView(View view) {
        gson = new Gson();
        data = new ArrayList<>();
        adapter = new MyAdapter(getActivity(),data);
        listView = (ListView) view.findViewById(R.id.listView);
        et_find = (EditText) view.findViewById(R.id.fg_etFind);
        btn_find = (Button) view.findViewById(R.id.fg_btnFind);
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        result = Manager.request();
                        dataInfo = gson.fromJson(result,DataInfo.class);
                        data = dataInfo.getData();
                        handler.post(new Runnable() {
                           @Override
                           public void run() {
                            adapter = new MyAdapter(getActivity(),data);
                            listView.setAdapter(adapter);
                           }
                       });
                    }
                }).start();
            }
        });
    }
}
