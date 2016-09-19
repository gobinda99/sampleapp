package com.sample.gobinda.sampleandroidapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.devspark.appmsg.AppMsg;
import com.sample.gobinda.sampleandroidapplication.R;
import com.sample.gobinda.sampleandroidapplication.commons.PrefHelper;


public class AuthenticationActivity extends FragmentActivity implements LoginFragment.Callback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isRegisteredOrLogin()) return;
        setContentView(R.layout.activity_authentication);
        showLoginFragment();

    }

    private void showLoginFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commit();
    }


    @Override
    public void done(String userName) {
        AppMsg.makeText(this, getString(R.string.login_successful), AppMsg.STYLE_CONFIRM).show();
        PrefHelper.setUserName(userName);
        startActivity(new Intent(this, HomeScreenActivity.class));
        finish();
    }

    private boolean isRegisteredOrLogin() {
        String userName = PrefHelper.getUserName();
        if (!TextUtils.isEmpty(userName)) {
            startActivity(new Intent(this, HomeScreenActivity.class));
            finish();

            return true;
        }

        return false;
    }
}
