package com.edsoft.vrcomande2;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConfigurazioniActivity extends AppCompatActivity {

    private EditText inputIP, inputPorta;
    private TextInputLayout inputLayoutIP, inputLayoutPorta;
    private CoordinatorLayout coordinatorLayout;
    private int i=0;
    private String stringa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurazioni);

        set_action_bar();
        get_id();
        ceck_ip();
    }

    //Assegna alle variabili wiget l'ID
    private void get_id() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayoutConfigurazioni);
        inputIP = (EditText) findViewById(R.id.input_text_ip);
        inputPorta = (EditText) findViewById(R.id.input_text_tcp);
        inputLayoutIP = (TextInputLayout) findViewById(R.id.input_conf_layout_ip);
        inputLayoutPorta = (TextInputLayout) findViewById(R.id.input_conf_layout_tcp);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_conf_guida:
                //tasto del menu informazioni
                controllo_pulsante(i=0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_configurazioni, menu);
        return true;
    }

    public void click_conf_registra(View view) {
        controllo_pulsante(i = 1);
    }

    public void click_conf_salva(View view){
        controllo_pulsante(i=2);
    }

    public void click_conf_chiudi(View view){
        controllo_pulsante(i=3);
    }

    //Questa metodo controlla quale pulsante è stato premuto e setta la stringa
    public void controllo_pulsante(int a) {
        stringa = "";
        switch (a) {
            case 0:
                stringa ="Hai premuto Il pulsante della guida," +
                        " se vedi questo messaggio vuol dire che " +
                        "questa funzione è in fase di sviluppo!!";
                crea_snackbar();
                return;
            case 1:
                stringa = "Questo pulsante regitrerà il palmare sul server";
                crea_snackbar();
                return ;
            case 2:
                stringa = "questo pulsante salverà le informazioni";
                crea_snackbar();
                return;
            case 3:
                stringa = "Questo pulsante chiuderà questa activity";
                crea_snackbar();
                return;
        }
    }

    //questo metodo crea la scnackBar da visualizzare
    public void crea_snackbar(){

        Snackbar snackbar = Snackbar.make(coordinatorLayout, stringa, Snackbar.LENGTH_LONG);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.yellow_500));
        snackbar.show();
    }

    //Imposta i parametri della action bar
    public void set_action_bar(){
        ActionBar actionBar=getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_settings);
        actionBar.setTitle(R.string.configurazioni_titolo);
        actionBar.setSubtitle(R.string.configurazioni_subTitol);
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;
        private int cont;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    break;
                case R.id.input_password:
                    break;
            }
        }
    }

    //Controlla che L'IP sia corretto
    private void ceck_ip() {
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       android.text.Spanned dest, int dstart, int dend) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);
                    if (!resultingTxt.matches ("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (int i=0; i<splits.length; i++) {
                            if (Integer.valueOf(splits[i]) > 255) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }
        };
        inputIP.setFilters(filters);
    }

    //Visualizza errore per l'ip sbagliato
    private boolean validateIP() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.login_err_msg_user));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

}
