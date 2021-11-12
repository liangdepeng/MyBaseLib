package com.dpdp.base_moudle.base.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dpdp.base_moudle.R;
import com.dpdp.base_moudle.interfaces.IBaseView;
import com.dpdp.base_moudle.utils.LogUtils;


/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-09
 * <p>
 * Summary: Fragment 基类
 */
public class BaseFragment extends Fragment implements IBaseView {

    private final String fragmentTag = getResetTag();
    /**
     * Activity 上下文
     */
    protected Activity mActivity;
    protected Context mContext;
    private TextView titleTv;
    protected boolean isFirstLoad = true;
    private View backV;
    private View backRealIv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(fragmentTag, fragmentTag + "  onCreate(@Nullable Bundle savedInstanceState)");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(fragmentTag, fragmentTag + "  onCreateView");
        return getContentLayoutView(inflater, container, savedInstanceState);
    }

    protected View getContentLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        Log.e(fragmentTag, fragmentTag + "  onAttach(Activity activity)");
        this.mActivity = activity;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(fragmentTag, fragmentTag + "  onAttach(Context context)");
        this.mContext = context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        boolean lastUserVisibleHint = getUserVisibleHint();
        if (lastUserVisibleHint != isVisibleToUser) {

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(fragmentTag, fragmentTag + " key:  onHiddenChanged    value: " + hidden);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(fragmentTag, fragmentTag + "  onActivityCreated(@Nullable Bundle savedInstanceState)");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(fragmentTag, fragmentTag + "  onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(fragmentTag, fragmentTag + "  onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(fragmentTag, fragmentTag + "  onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(fragmentTag, fragmentTag + "  onStop()");
    }

    @Override
    public void onDestroyView() {
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).dismissDialog();
        }
        super.onDestroyView();
        Log.e(fragmentTag, fragmentTag + "  onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(fragmentTag, fragmentTag + "  onDestroy()");
    }

    @Override
    public void onDetach() {
        Log.e(fragmentTag, fragmentTag + "   onDetach()");
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(fragmentTag, fragmentTag + "  onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)");
        initTitleBar(view);
    }

    private void initTitleBar(View view) {
        View titleBar = view.findViewById(R.id.title_bar);
        if (titleBar == null)
            return;
        titleTv = view.findViewById(R.id.title_tv);
        backV = view.findViewById(R.id.back_v);
        backRealIv = view.findViewById(R.id.back_real_v);
        setTitle(getPageTitle());

        int statusBarHeight = getStatusBarHeight();
        ViewGroup.LayoutParams layoutParams = titleBar.getLayoutParams();
        layoutParams.height = layoutParams.height + statusBarHeight;
        titleBar.setPadding(0, statusBarHeight, 0, 0);
    }

    public void setTitle(CharSequence title) {
        if (titleTv != null) {
            titleTv.setText(title);
        }
    }


    @Override
    public void showLoadingDialog() {
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).showLoadingDialog();
        }
    }

    @Override
    public void dismissDialog() {
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).dismissDialog();
        }
    }

    @Override
    public void showCustomLoadingDialog() {
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).showCustomLoadingDialog();
        }
    }

    @Override
    public void dismissCustomLoadingDialog() {
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).dismissCustomLoadingDialog();
        }
    }

    @Override
    public CharSequence getPageTitle() {
        return "";
    }

    @Override
    public void initDataView() {

    }

    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        LogUtils.d(fragmentTag, "状态栏高度：" + px2dp(statusBarHeight) + "dp");
        return statusBarHeight;
    }

    public float px2dp(float pxVal) {
        final float scale = getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    protected String parseUrl(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl) && !imageUrl.startsWith("http"))
            imageUrl = "http://120.26.8.235:88/pic/" + imageUrl;
        return imageUrl;
    }


    protected String getResetTag() {
        return BaseFragment.class.getSimpleName();
    }
}
