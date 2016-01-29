package com.edsoft.vrcomande2;


import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.edsoft.vrcomande2.core.dbutility.ConnessioneAlServer;
import com.edsoft.vrcomande2.core.dbutility.DBHelper;
import com.edsoft.vrcomande2.core.networkutility.TLVParser;
import com.edsoft.vrcomande2.core.networkutility.networkresult;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

import com.rey.material.widget.CheckBox;

public class ConfigurazioniActivity extends AppCompatActivity {

    private TextInputLayout inputLayoutIP, inputLayoutPorta;
    private com.rey.material.widget.CheckBox AutoUpdateDB;
    private CoordinatorLayout coordinatorLayout;
    private int i=0;
    Context context;

    CheckedTextView AttivaAutoUpdate;
    boolean _AutoUpdateDB;
    int _PortaServer;
    String _androidId;
    String _ipServer;
    boolean _modifiche;
    String _sn;
    String _titolo;
    Button bAnnullaConfigurazione;
    Button bRegistraTerminale;
    Button bSalvaConfigurazione;
    DBHelper databaseHelper;
    SQLiteDatabase db;
    EditText ipAddress;
    TextView label_idAndroid;
    MaterialDialog pd;
    EditText tcp_port;
    Socket nsocket;

    View.OnClickListener gestore = new View.OnClickListener() {
        public void onClick(View view) {
            try {
                switch (view.getId()) {
                    case R.id.btn_conf_registra:
                        RegistraTerminale();
                        return;
                    case R.id.btn_conf_salva:
                        SalvaConfigurazione();
                        return;
                    case R.id.btn_conf_chiudi:
                        finish();
                        return;
                    case R.id.autoApDateDB:
                        DatiModificati(true);
                        if (AutoUpdateDB.isChecked()) {
                            _AutoUpdateDB=true;
                        } else {
                            _AutoUpdateDB=false;
                        }
                        return;
                    default:
                        return;
                }

            } catch (Exception ex) {
                crea_snackbar("Gestore - messaggio : "+ ex.getMessage(), false);
                Log.e("ArticoliAct-gestore:",ex.getMessage());
            }
        }
    };

    public ConfigurazioniActivity() {
        this.databaseHelper = new DBHelper(this);
        this.context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurazioni);

        this.db = this.databaseHelper.getWritableDatabase();
        this._modifiche = false;
        this._ipServer = BuildConfig.FLAVOR;
        this._PortaServer = 0;
        this._sn = BuildConfig.FLAVOR;
        this._androidId = BuildConfig.FLAVOR;

        prepara_tutto();

        ipAddress.addTextChangedListener(new MyTextWatcher(ipAddress));
        tcp_port.addTextChangedListener(new MyTextWatcher(tcp_port));
    }

    private void prepara_tutto() //Richiama tutti i metodi di settaggio iniziale
    {
        set_action_bar();
        get_id_e_listener();
        ceck_ip();
        CaricaConfigurazione();
        InizializzaIPAndPorta();
    }

    private void get_id_e_listener()    //Assegna alle variabili l'ID e il listener del widget
    {
        this.coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayoutConfigurazioni);
        this.ipAddress = (EditText) findViewById(R.id.input_text_ip);
        this.ipAddress.setImeOptions(6);
        this.tcp_port = (EditText) findViewById(R.id.input_text_tcp);
        this.tcp_port.setImeOptions(6);
        this.inputLayoutIP = (TextInputLayout) findViewById(R.id.input_conf_layout_ip);
        this.inputLayoutPorta = (TextInputLayout) findViewById(R.id.input_conf_layout_tcp);
        this.AutoUpdateDB = (CheckBox) findViewById(R.id.autoApDateDB);
        this.AutoUpdateDB.setOnClickListener(this.gestore);
        this.bAnnullaConfigurazione = (Button) findViewById(R.id.btn_conf_chiudi);
        this.bAnnullaConfigurazione.setOnClickListener(this.gestore);
        this.bRegistraTerminale = (Button) findViewById(R.id.btn_conf_registra);
        this.bRegistraTerminale.setOnClickListener(this.gestore);
        this.bSalvaConfigurazione = (Button) findViewById(R.id.btn_conf_salva);
        this.bSalvaConfigurazione.setOnClickListener(this.gestore);
        this.label_idAndroid = (TextView) findViewById(R.id.idAndroid);
    }

    private void InizializzaIPAndPorta() {
        this.ipAddress.setText(this._ipServer);
        this.tcp_port.setText(Integer.toString(this._PortaServer));
        this.AutoUpdateDB.setChecked(this._AutoUpdateDB);
    }

    private void CaricaConfigurazione(){
        String errMsg = BuildConfig.FLAVOR;
        try {
            this._titolo = (String) getTitle();
            if (Build.VERSION.SDK_INT >= 9) {
                this._sn = Build.SERIAL;
                this._androidId = Settings.Secure.getString(this.context.getContentResolver(), "android_id");
            }
            this.label_idAndroid.setText("ID terminale: " + this._androidId);
            Cursor c = ConnessioneAlServer.getAllServer(this.db);
            while (c.moveToNext()) {
                this._ipServer = c.getString(0);
                this._PortaServer = c.getInt(1);
                if (c.getInt(2) == 1) {
                    this._AutoUpdateDB = true;
                } else {
                    this._AutoUpdateDB = false;
                }
                this.AutoUpdateDB.setChecked(this._AutoUpdateDB);
            }
            if (this._ipServer == BuildConfig.FLAVOR) {
                this._ipServer = "192.168.1.2";
                this.ipAddress.setText(_ipServer);
                DatiModificati(true);
            }
            if (this._PortaServer == 0) {
                this._PortaServer = 4444;
                DatiModificati(true);
            }
            c.close();
        } catch (Exception ex) {
            Log.e("CaricaReparti:", ex.toString());
        }
    }

    private void SalvaConfigurazione() {
        String AppoIP = this.ipAddress.getText().toString().trim();
        String AppoPorta = this.tcp_port.getText().toString().trim();
        _AutoUpdateDB = this.AutoUpdateDB.isChecked();
        if (validateIP() & validatePorta()) {
            this._ipServer = AppoIP;
            this._PortaServer = Integer.parseInt(AppoPorta);
            ConnessioneAlServer.insertCfgServer(this.db, this._ipServer, this._PortaServer, this._AutoUpdateDB);
            DatiModificati(false);
            crea_snackbar("I dati Sono stati correttamente salvati!!", false);
        }
    }

    private void RegistraTerminale() {
        new AlertDialogWrapper.Builder(this)
                .setTitle(R.string.configurazioni_dialog_registra_titolo)
                .setMessage(R.string.configurazioni_dialog_registra_messaggio)
                .setPositiveButton(R.string.configurazioni_dialog_registra_si, new DialogInterface.OnClickListener() {
                    @Override
                public void onClick(DialogInterface dialog, int which) {
                        pd = new MaterialDialog.Builder(context)
                                .title(R.string.configurazioni_progress_dialog_registra_titolo)
                                .content(R.string.configurazioni_progress_dialog_registra_content)
                                .progress(true, 0)
                                .show();
                        new SincTask().execute(new String[]{_androidId});
                    }
                })
                .setNegativeButton(R.string.configurazioni_dialog_registra_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    private class SincTask extends AsyncTask<String, String, genericresult> {
        private SincTask() {
        }

        protected genericresult doInBackground(String... params) {
            genericresult Ret = new genericresult(0, null, BuildConfig.FLAVOR);
            for (;;) {
                int PortaServer;
                int j;
                try {
                    String datiXml = params[0].replace('[', ' ').replace(']', ' ').trim();
                    //params = "";
                    String ipServer = BuildConfig.FLAVOR;
                    PortaServer = 0;
                    Cursor c = ConnessioneAlServer.getAllServer(db);
                    while (c.moveToNext()) {
                        ipServer = c.getString(0);
                        PortaServer = c.getInt(1);
                    }
                    if (ipServer == BuildConfig.FLAVOR || PortaServer == 0) {
                        ipServer = "192.168.1.2";
                        PortaServer = 4444;
                    }
                    publishProgress(new String[]{"Comunicazione con il server..."});
                    networkresult r = ConfigurazioniActivity.this.SendCmd(datiXml, ipServer, PortaServer);
                    Ret.result = r.result;
                    if (r.result != 0) {
                        Ret.errMesg = r.errMesg;
                    }
                } catch (Exception ex) {
                    Log.e("StartDbSincTask:", ex.getMessage());
                }
                return Ret;
            }
        }

        protected void onProgressUpdate(String... values) {
            try {
                pd.setContent(values[0]);
            } catch (Exception ex) {
                Log.e("onProgressUpdate:", ex.getMessage());
            }
        }

        protected void onPostExecute(genericresult result) {
            try {
                if (result.result != 0) {
                    crea_snackbar("result !=0 " + result.errMesg, true);
                } else if (result.errMesg.compareTo(BuildConfig.FLAVOR) != 0) {
                    Log.e("onPostExecute","al secondo if");
                    crea_snackbar("Errore  " + result.errMesg, true);
                } else {
                    crea_snackbar("il terminale è stato correttamente registrato.", true);
                }
                pd.dismiss();
            } catch (Exception e) {
                crea_snackbar(e.getMessage(),true);
            }
        }
    }

    private genericresult CollegaAlServer(String Sever, int PortaTCP, int timeout) {
        genericresult ret = new genericresult(0, null, BuildConfig.FLAVOR);
        try {
            SocketAddress sockaddr = new InetSocketAddress(Sever, PortaTCP);
            crea_socket();
            for (int index = 0; index < 5; index++) {
                nsocket.connect(sockaddr, timeout);
                if (nsocket.isConnected()) {
                    ret.Dati = nsocket;
                    Log.e("CollegaAlServer", "Il socket si è connesso");
                    return ret;
                }
                ret.result = -1;
                ret.errMesg = "Errore collegamento con il server";
                Log.e("ERRORE","Errore collegamento con il serer, private CollgaAlServer");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret.result = -1;
            ret.errMesg = e.getMessage();
        }
        return ret;
    }

    public void crea_socket() {
        nsocket = new Socket();
    }

    private networkresult SendCmd(String datiDaInviare, String Sever, int PortaTCP) {
        networkresult Ret = new networkresult(0, BuildConfig.FLAVOR, BuildConfig.FLAVOR);
        try {
            if (datiDaInviare == BuildConfig.FLAVOR) {
                Ret.result = -1;
                Ret.errMesg = "Nessun ID terminale rilevato.";
                Log.e("ERRORE", "Nesun ID terminale rilevato SendCmd");
            } else {
                genericresult r = new genericresult(0,null,BuildConfig.FLAVOR);
                Socket nsocket2 = null;
                genericresult novarum_risto_vrordina_genericresult = new genericresult(0, null, BuildConfig.FLAVOR);
                int index = 0;
                while (index < 3) {
                    r = CollegaAlServer(Sever, PortaTCP, 1000);
                    Log.e("SendCmd","é stato appena avviato il metodo CollegaAlServer");
                    Log.e("r result", ""+r.result);
                    if (r.result == 0) {
                        int length;
                        nsocket2 = (Socket)r.Dati;
                        SocketAddress sockaddr = new InetSocketAddress(Sever, PortaTCP);
                        Log.e("SendCmd", "lanciata la connessione con il socket");
                        nsocket2.connect(sockaddr, 1000);
                        //r.Dati = nsocket;
                        Log.e("SendCmd","Avviata la connessione dentro all'if");
                        nsocket2.setSoTimeout(5000);
                        InputStream nis = nsocket.getInputStream();
                        OutputStream nos = nsocket.getOutputStream();
                        ArrayList<Byte> dati = new ArrayList();
                        dati.add(Byte.valueOf((byte) 95));
                        byte[] len = TLVParser.intToByteArray(datiDaInviare.length());
                        TLVParser.reverse(len);
                        int i = 0;
                        while (i<len.length)
                        {
                            dati.add(Byte.valueOf(len[i]));
                            i++;
                        }
                        byte[] contenuto = datiDaInviare.getBytes("UTF-8");
                        i = 0;
                        while (i<contenuto.length)
                        {
                            dati.add(Byte.valueOf(len[i]));
                        }
                        byte[] datatosend = new byte[dati.size()];
                        i=0;
                        while (i<dati.size())
                        {

                            datatosend[i] = ((Byte)dati.get(i)).byteValue();
                            i++;
                        }

                        nos.write(datatosend);
                        byte[] ret = TLVParser.readTLV(nis, 95);
                        if (ret == null) {
                            Ret.result = -1;
                            Ret.errMesg = "Errore comunicazione con il server";
                            Log.e("ERRORE", "ERRORE Comunicazione con il server SendCmd");
                        } else {
                            Ret.Dati = new String(ret);
                            if (Ret.Dati.compareTo("OK") != 0) {
                                Ret.result = -1;
                                Ret.errMesg = Ret.Dati;
                            }
                        }
                        if (nsocket2 != null) {
                            nsocket2.close();
                        }
                        if (r.result != 0) {
                            Ret.result = -1;
                            Ret.errMesg = "Errore collegamento 2 con il server";
                            Log.e("ERRORE","Errore collegamento con il server 2 SendCmd");
                        }
                    } else {
                        index++;
                    }
                }
                if (nsocket2 != null) {
                    nsocket2.close();
                }
                if (r.result != 0) {
                    Ret.result = -1;
                    Ret.errMesg = "Errore collegamento 3 con il server";
                    Log.e("ERRORE", "Errore collegamento con il server 3 SendCmd");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Ret.result = -1;
            Ret.errMesg = e.getMessage();
        }
        return Ret;
    }

    public void onDestroy() {
        super.onDestroy();
        this.db.close();
        this.databaseHelper.close();
    }

    private void DatiModificati(boolean b) {
        this._modifiche = b;
    }

    public void set_action_bar()            //Imposta i parametri della action bar
    {
        ActionBar actionBar=getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_settings);
        actionBar.setTitle(R.string.configurazioni_titolo);
        actionBar.setSubtitle(R.string.configurazioni_subTitol);
    }

    private class MyTextWatcher implements TextWatcher/**Classe che controlla lo stato degli input prima dopo e durante la modifica*/ {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!_modifiche) {
                crea_snackbar("Riscordati di salvare prima di chiudere la schermata!!", false);
                DatiModificati(true);
            }
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_text_ip:
                    validateIP();
                    DatiModificati(true);
                    break;
                case R.id.input_text_tcp:
                    validatePorta();
                    break;
            }
        }
    }

    private boolean validateIP()/**Controlla la TextInput dell'IP*/ {
        if (ipAddress.getText().toString().trim().isEmpty()) {
            inputLayoutIP.setError(getString(R.string.configurazioni_err_msg_ip));
            requestFocus(ipAddress);
            return false;
        } else {
            inputLayoutIP.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePorta()/**Controlla la TextInput della Porta*/ {
        if (tcp_port.getText().toString().trim().isEmpty()) {
            inputLayoutPorta.setError(getString(R.string.configurazioni_err_msg_tcp));
            requestFocus(tcp_port);
        } else {
            inputLayoutPorta.setCounterEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void ceck_ip()/**Controlla che L'IP sia nel formato corretto*/{
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
        ipAddress.setFilters(filters);
    }

    public void crea_snackbar(String strr, boolean b)/**crea e manda a video una snackBar */ {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, strr, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.yellow_500));

        snackbar.show();
    }

    public boolean onOptionsItemSelected(MenuItem item)/**Listener per il menu*/ {
        switch (item.getItemId()) {
            case R.id.action_conf_guida:
                crea_snackbar("Le informazioni non sono ancora disponibili in questa versione!", false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)/**Cre Il MENU */ {
        getMenuInflater().inflate(R.menu.menu_configurazioni, menu);
        return true;
    }
}
