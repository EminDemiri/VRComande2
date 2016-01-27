package com.edsoft.vrcomande2;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button button_login;
    private EditText inputUser, inputPassword;
    private TextInputLayout inputLayoutUser, inputLayoutPassword;
    private CoordinatorLayout coordinatorLayout;
    private Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);

        set_ActionBar();
        set_Component();
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        inputLayoutUser.startAnimation(anim);
    }

    private void submitForm() {
        if (!validateText(inputUser, inputLayoutUser, getString(R.string.login_err_msg_user))) {
            return;
        }

        if (!validateText(inputPassword, inputLayoutPassword, getString(R.string.login_err_msg_pass))) {
            return;
        }
        inputLayoutUser.startAnimation(anim);
        crea_snackbar("Il pulsante ha controllato le scringhe inserite");
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
        inputUser.addTextChangedListener(new MyTextWatcher(inputUser));
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
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

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_user:
                    validateText(inputUser,inputLayoutUser,getString(R.string.login_err_msg_user));
                    break;
                case R.id.input_password:
                    validateText(inputPassword,inputLayoutPassword,getString(R.string.login_err_msg_pass));
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
            inputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void crea_snackbar(String strr)/**crea e manda a video una snackBar */ {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, strr, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.yellow_500));
        snackbar.show();
    }
}