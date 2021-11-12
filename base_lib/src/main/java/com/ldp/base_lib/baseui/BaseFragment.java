package com.ldp.base_lib.baseui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/12
 * <p>
 * Summary:
 */
public class BaseFragment extends Fragment {

    private final String tag = "lifecycle_for_fragment";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(tag, "---onAttach " + getClass().getSimpleName() + " ---");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(tag, "---onCreate " + getClass().getSimpleName() + " ---");
    }

    /**
     * 显示加载弹窗
     */
    public final void showLoadingDialog() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoadingDialog();
        }
    }

    /**
     * 关闭加载弹窗
     */
    public final void dismissLoadingDialog() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).dismissLoadingDialog();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.e(tag, "---onCreateView " + getClass().getSimpleName() + " ---");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(tag, "---onViewCreated " + getClass().getSimpleName() + " ---");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(tag, "---onActivityCreated " + getClass().getSimpleName() + " ---");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(tag, "---onStart " + getClass().getSimpleName() + " ---");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(tag, "---onResume " + getClass().getSimpleName() + " ---");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(tag, "---onPause " + getClass().getSimpleName() + " ---");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(tag, "---onStop " + getClass().getSimpleName() + " ---");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(tag, "---onDestroyView " + getClass().getSimpleName() + " ---");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(tag, "---onDestroy " + getClass().getSimpleName() + " ---");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(tag, "---onDetach " + getClass().getSimpleName() + " ---");
    }
}
