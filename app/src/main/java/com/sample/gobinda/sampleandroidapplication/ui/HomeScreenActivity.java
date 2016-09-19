package com.sample.gobinda.sampleandroidapplication.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.sample.gobinda.sampleandroidapplication.R;
import com.sample.gobinda.sampleandroidapplication.commons.PrefHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeScreenActivity extends FragmentActivity {

    @Bind(R.id.user_name)
    TextView mUserName;

    @Bind(R.id.image)
    ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        ButterKnife.bind(this);
        setupData();

    }

    private void setupData() {
        String userName = PrefHelper.getUserName();
        if (!TextUtils.isEmpty(userName)) {
            mUserName.setText("Welcome, " + userName);
            Glide.with(this).load("https://images.google.com/images/branding/googleg/1x/googleg_standard_color_128dp.png")
                    .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                    .fitCenter()
                    .into(mImage);
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }


    @OnClick(R.id.logout)
    void onLogout(Button btn) {
        PrefHelper.removeUserName();
        finish();
    }


}
