package com.qy.j4u.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 适配器基类
 * Created by abc on 2016/11/2.
 */

public abstract class ForUAdapter<T> extends BaseAdapter {

    protected List<T> data;
    private int layoutId;

    public ForUAdapter(List<T> data, int layoutId) {
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
        this.layoutId = layoutId;
    }

    /**
     * 更新数据
     *
     * @param data
     */
    public void updateData(List<T> data) {
        if (data != null) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }


    /**
     * 添加数据
     *
     * @param data
     */
    public void addData(List<T> data) {
        if (data != null) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return this.data != null ? this.data.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        bindData(viewHolder,data.get(position),position);

        return convertView;
    }

    public abstract void bindData(ViewHolder viewHolder,T t, int position);


    public class ViewHolder{
        Map<Integer,View> views;
        View itemView;
        ViewHolder(View itemView) {
            views = new HashMap<>();
            this.itemView = itemView;
        }

        public View getView(int id) {
            View view;
            if (views.containsKey(id)) {
                view = views.get(id);
            } else {
                view = itemView.findViewById(id);
                views.put(id, view);
            }
            return view;
        }

    }

}
