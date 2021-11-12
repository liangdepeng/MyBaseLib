package com.ldp.base_lib.baseui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/12
 * <p>
 * Summary: listView / GridView 通用适配器 要么就自己去写
 */
public abstract class BaseSimpleAdapter extends BaseAdapter {

    // 数据存储
    private final List<Object> data = new ArrayList<>();
    // 上下文
    private final Context context;
    // 列表单个项目布局
    private final int layoutResId;

    public BaseSimpleAdapter(Context context, int layoutResId) {
        this.context = context;
        this.layoutResId = layoutResId;
    }

    // 重设列表数据 会清空列表重新添加
    public void setData(List<Object> list) {
        data.clear();
        if (list != null && list.size() > 0) {
            data.addAll(list);
        }
        notifyDataSetChanged();
    }

    // 添加新的数据 会在现有基础上添加
    public void addData(List<Object> list) {
        if (list != null && list.size() > 0) {
            data.addAll(list);
            notifyDataSetChanged();
        }
    }

    // 获取列表数据
    public <T> List<T> getData(){
        return ((List<T>) data);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (layoutResId == 0)
                return new View(context);
            convertView = LayoutInflater.from(context).inflate(layoutResId, parent, false);
        }

        //  这边可以加 viewholder 方便缓存 需要请自己实现

        // 绑定列表数据
        onBindView(position, data.get(position));

        return convertView;
    }

    protected abstract void onBindView(int position, Object o);
}
