package com.dpdp.base_moudle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dpdp.base_moudle.databinding.ActivityTestBindBinding;
import com.dpdp.base_moudle.image.GlideUtils;
import com.dpdp.base_moudle.utils.DeviceUtil;
import com.dpdp.base_moudle.utils.ToastUtil;

public class TestBindActivity extends AppCompatActivity {

    private ActivityTestBindBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBindBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.helloBindTv.setText("hello_binding");
        initView();
    }

    private void initView() {
        binding.helloBindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showMsg("clicked!");
            }
        });

        GlideUtils.load(this, R.mipmap.ui_default_logo, binding.normalImageIv);
        GlideUtils.loadAsRound(this, R.mipmap.ui_default_logo, binding.roundImageIv);
        GlideUtils.loadAsCorner(this, R.mipmap.ui_default_logo, DeviceUtil.dp2px(10f), binding.cornerImageIv);
    }
}