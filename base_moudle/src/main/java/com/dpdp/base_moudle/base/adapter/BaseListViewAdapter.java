package com.dpdp.base_moudle.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-09
 * <p>
 * Summary:通用 简易 listView / GridView adapter
 */
public abstract class BaseListViewAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> list;

    public BaseListViewAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return list != null ? list.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder viewHolder=null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(getItemLayoutResId(),parent,false);
            viewHolder = new BaseViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (BaseViewHolder) convertView.getTag();
        }

        onBindItemData(viewHolder,position,getItem(position));

        return convertView;
    }

    /**
     * 绑定数据
     * @param viewHolder viewHolder 实例
     * @param position 下标
     * @param item item 数据
     */
    protected abstract void onBindItemData(BaseViewHolder viewHolder, int position, T item);

    /**
     * 布局 id
     * @return
     */
    protected abstract int getItemLayoutResId();


}
