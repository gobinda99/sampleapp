package com.sample.gobinda.sampleandroidapplication.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;
import com.sample.gobinda.sampleandroidapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment implements Validator.ValidationListener {

    @Bind(R.id.user_name)
    @Required(order = 1, messageResId = R.string.username_required)
    @Regex(order = 2, pattern = "[^@]+", messageResId = R.string.username_invalid)
    EditText mUserName;


    @Bind(R.id.password)
    @Required(order = 3, messageResId = R.string.password_required)
    @TextRule(order = 5, minLength = 6, messageResId = R.string.password_length)
    @Regex(order = 4, pattern = "^[a-zA-Z0-9_]*$", messageResId = R.string.password_invalid)
    EditText mPassword;

    private Validator mValidator;

    private Callback mCallback;

    public interface Callback {

        void done(String userName);
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement LoginFragment.Callback");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mValidator = new Validator(this);
        mValidator.setValidationListener(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPassword.setOnEditorActionListener(mDoneKeyListener);

    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onValidationSucceeded() {
        String userName = mUserName.getText().toString().trim();
        String password = mPassword.getText().toString();
        mCallback.done(userName);
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        if (failedView instanceof EditText) {
            failedView.requestFocus();
            ((EditText) failedView).setError(failedRule.getFailureMessage());
        }
    }

    @OnClick(R.id.sign_in)
    void onSignIn(Button btn) {
        mValidator.validate();
    }


    public boolean isKeyboardShown(View view) {
        InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputManager.isActive(view);
    }


    public void hideSoftKeyboard() {
        // hide soft input keyboard
        View focused = getActivity().getCurrentFocus();
        if (focused instanceof EditText) {
            EditText et = (EditText) focused;

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }


    private TextView.OnEditorActionListener mDoneKeyListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView t, int actionId, KeyEvent event) {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (isKeyboardShown(mPassword)) {
                    hideSoftKeyboard();
                }
                mValidator.validate();
                handled = true;
            }
            return handled;
        }
    };
}
