package com.dpdp.base_moudle.base.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dpdp.base_moudle.R;
import com.dpdp.base_moudle.utils.DeviceUtil;
import com.dpdp.base_moudle.utils.NumberUtil;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-09
 * <p>
 * Summary: ViewHolder 缓存view 链式调用
 * <p>
 */
public class BaseRVViewHolder extends RecyclerView.ViewHolder {

    private final View rootView;
    private final SparseArray<View> views;

    public BaseRVViewHolder(View rootView) {
        super(rootView);
        this.rootView = rootView;
        views = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = rootView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public BaseRVViewHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        if (view != null)
            view.setText(text);
        return this;
    }

    public BaseRVViewHolder setTextColor(int viewId, @ColorInt int color) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setTextColor(color);
        }
        return this;
    }

    public BaseRVViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
        return this;
    }

    public BaseRVViewHolder setNetImageResource(@NonNull Context context, int viewId, String internetImageUrl, float corner) {
        ImageView view = getView(viewId);
        if (view != null) {
            RoundedCorners roundedCorners = new RoundedCorners(DeviceUtil.dp2px(corner));
            RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners)
                    .placeholder(R.drawable.ui_empty_pic)
                    .error(R.drawable.ui_empty_pic);

            Glide.with(context)
                    .load(internetImageUrl)
                    .apply(requestOptions)
                    .into(view);
        }
        return this;
    }

    public BaseRVViewHolder setNativeImageResource(int viewId, int resource) {
        return setNativeImageResource(null, viewId, resource, 0f);
    }

    public BaseRVViewHolder setNativeImageResource(@Nullable Context context, int viewId, int resource, float corner) {
        ImageView view = getView(viewId);

        if (view == null)
            return this;

        if (NumberUtil.compareFloat(0f, corner) == 0) {
            view.setImageResource(resource);
            return this;
        }

        if (context == null)
            return this;

        Glide.with(context)
                .load(resource)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(DeviceUtil.dp2px(corner))))
                .into(view);
        return this;
    }

    //...
}
