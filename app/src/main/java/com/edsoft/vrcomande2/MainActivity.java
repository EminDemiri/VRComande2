package com.edsoft.vrcomande2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.edsoft.vrcomande2.core.dbutility.ConnessioneAlServer;
import com.edsoft.vrcomande2.core.dbutility.DBHelper;
import com.edsoft.vrcomande2.core.dbutility.fresul;
import com.edsoft.vrcomande2.core.networkutility.ParserDbPalmare;
import com.edsoft.vrcomande2.core.networkutility.networkresult;

public class MainActivity extends AppCompatActivity {

    private Button SincronizzaDB;
    String _androidId;
    String _sn;
    Context context;
    DBHelper databaseHelper;
    ProgressDialog pb;




    CoordinatorLayout coordinatorLayout;
    String stringa = "";
    ImageView image;
    Animation animationFadeIn, animationFadeInLong;
    CardView card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //overridePendingTransition(R.transition.open_scale, R.transition.close_translate);

        prepare_main();
        image.startAnimation(animationFadeInLong);
        card.startAnimation(animationFadeIn);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.transition.open_scale, R.transition.close_translate);
    }


    private void prepare_main() {
        set_actionBar();
        get_id();
    }

    private void get_id()           //imposto tutti gli ID degli elementi grafici
    {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayoutmain);
        image = (ImageView) findViewById(R.id.main_image);
        animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animationFadeInLong = AnimationUtils.loadAnimation(this, R.anim.fade_in_long);
        card = (CardView) findViewById(R.id.card_view_main);
    }

    private void set_actionBar()    // preparo la action bar con stringhe ed icone
    {
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle("Home");
        actionBar.setSubtitle("Scegli cosa fare");
    }

    public void crea_snackbar()     //crea e manda a video una snackBar
    {

        Snackbar snackbar = Snackbar.make(coordinatorLayout, stringa, Snackbar.LENGTH_LONG);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.yellow_500));
        snackbar.show();
    }

    public boolean onOptionsItemSelected(MenuItem item)     //Selezione del menu
    {
        switch (item.getItemId()) {
            case R.id.action_main_chiudi:                //Questo pulsante del menu chiude l'applicazione
                stringa = "Ricordati di chiudere l applicazione dal tasc manager";
                crea_snackbar();
                System.exit(0);
                return true;
            case R.id.action_main_info:                //perform settings
                stringa = "Le informazioni saranno disponibilli a breve";
                crea_snackbar();
                image.startAnimation(animationFadeIn);
                return true;
            case R.id.action_main_guida:                //Pulsante per visualizzare una guida sulla schermata visualizzata
                stringa = "La guida non è ancora disponibile";
                crea_snackbar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void click_login(View view)                  //Click sul pulsante Login
    {
        Intent intent1= new Intent(this,LoginActivity.class);
        startActivity(intent1);
        overridePendingTransition(R.transition.close_translate, R.transition.close_translate);
    }

    public void click_configurazioni (View view)        //Click sul pulsante delle configurazioni
    {
        Intent Intent2= new Intent(this,ConfigurazioniActivity.class);
        startActivity(Intent2);
    }

    public void click_main_sincronizza (View view)      //Click sul pulsante per la sincronizzazione
    {
        stringa="Il codice per la sincronizzazione deve essere ancora scritto";
        crea_snackbar();
    }


/**
    private void StartDbSincTask() {
        try
        {
            this.pb = ProgressDialog.show(this,"sincronizza db","Start...", true,false);
            new SincTask().execute(new String[]["Process Started!"]);
        } catch (Exception ex) {
            Log.e("StartDbSincTask: ",ex.getMessage());
        }
    }

    private class SincTask extends AsyncTask<String, String, String> {
        private SincTask(){}

        protected String doInBackground(String... params) {
            String Ret = BuildConfig.FLAVOR;
            String errMsg = BuildConfig.FLAVOR;
            try {
                publishProgress(new String[]{"\n" + params[0]});
                SQLiteDatabase db = MainActivity.this.databaseHelper.getWritableDatabase();
                String ipServer = BuildConfig.FLAVOR;
                int PortaServer = 0;
                Cursor c = ConnessioneAlServer.getAllServer(db);
                while (c.moveToNext()) {
                    ipServer = c.getString(0);
                    PortaServer = c.getInt(1);
                }
                if (ipServer == BuildConfig.FLAVOR || PortaServer == 0) {
                    ipServer = "192.168.1.2";
                    PortaServer = 4444;
                }
                if (ipServer == BuildConfig.FLAVOR || PortaServer == 0) {
                    return "Errore nella configurazione del server";
                }
                publishProgress(new String[]{"Download dati, attendere..."});
                networkresult r = MainActivity.this.ScaricaDB(ipServer, PortaServer);
                if (r.result != 0) {
                    return r.errMesg;
                }
                publishProgress(new String[]{"elaborazione dei dati, attendere..."});
                ParserDbPalmare parser = new ParserDbPalmare();
                r = parser.parseXml(r.Dati);
                if (r.result != 0) {
                    return r.errMesg;
                }
                publishProgress(new String[]{"inserimento dati nel db, attendere..."});
                fresul sbret = new fresul(0, BuildConfig.FLAVOR);
                if (parser.getParsedOperatori().size() > 0) {
                    dbret = MainActivity.this.databaseHelper.BulkImportdbOperatori(db, parser.getParsedOperatori());
                }

            }
        }
    }
*/


    private void showIndeterminateProgressDialog(boolean horizontal, String paramTitolo, String paramContent)   //Crea un progress dialog con titolo e content
    {
        new MaterialDialog.Builder(this)
                .title(paramTitolo)
                .content(paramContent)
                .progress(true, 0)
                .progressIndeterminateStyle(horizontal)
                .show();
    }
}
