package com.edsoft.vrcomande2;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.rey.material.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button button_login;
    private EditText inputUser, inputPassword;
    private TextInputLayout inputLayoutUser, inputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        set_ActionBar();
        set_Component();
    }

    private void set_ActionBar()        //Setup ActionBar
    {
        ActionBar actionBar=getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.login);
        actionBar.setTitle(R.string.login_title);
        actionBar.setSubtitle(R.string.login_subTitle);
    }

    private void set_Component()        //Setup ID componenti
    {
        inputUser= (EditText) findViewById(R.id.input_user);
        inputLayoutUser = (TextInputLayout) findViewById(R.id.input_layout_user);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        button_login = (Button) findViewById(R.id.btn_signup);
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            switch (view.getId()) {
                case R.id.input_user:
                    break;
                case R.id.input_password:
                    break;
            }
        }
    }

    private boolean validateText(EditText input, TextInputLayout inputLayout, String messaggio) {
        if (input.getText().toString().trim().isEmpty()) {
            inputLayout.setError(messaggio);
            requestFocus(input);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}