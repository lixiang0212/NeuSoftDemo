package com.lx.neusoftdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lixiang on 2017/2/28.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<DataInfo.DataBean> data;
    private LayoutInflater inflater;

    public MyAdapter(Context context, List<DataInfo.DataBean> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public DataInfo.DataBean getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TempHolder holder;
        if(view==null){
            holder = new TempHolder();
            view = inflater.inflate(R.layout.item,null);
            holder.tv_time = (TextView) view.findViewById(R.id.item_time);
            holder.tv_msg = (TextView) view.findViewById(R.id.item_msg);
            view.setTag(holder);
        }else {
            holder = (TempHolder) view.getTag();
        }
        DataInfo.DataBean dataBean = data.get(i);
        holder.tv_time.setText(dataBean.getTime());
        holder.tv_msg.setText(dataBean.getContext());
        return view;
    }

    class TempHolder{
        private TextView tv_time,tv_msg;
    }

}
