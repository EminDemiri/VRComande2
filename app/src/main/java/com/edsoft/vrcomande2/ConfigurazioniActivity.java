package com.edsoft.vrcomande2;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ConfigurazioniActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private int i=0;
    private String stringa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurazioni);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayoutConfigurazioni);

        set_action_bar();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_conf_guida:
                //perform send
                Toast.makeText(this, "Hai premuto Il pulsante della guida, se vedi questo messaggio vuol dire che questa funzione è in fase di sviluppo!!", Toast.LENGTH_LONG).show();
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

    public void set_action_bar(){
        //Imposta i parametri della action bar
        ActionBar actionBar=getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_settings);
        actionBar.setTitle(R.string.configurazioni_titolo);
        actionBar.setSubtitle(R.string.configurazioni_subTitol);
    }

    public void click_conf_registra(View view) {
        i=1;
        controllo_oulsante(i);
    }

    public void click_conf_salva(View view){
        i=2;
        controllo_oulsante(i);
    }

    public void click_conf_chiudi(View view){
        i=3;
        controllo_oulsante(i);
    }

    //Questo controlla quale pulsante è stato premuto e setta la stringa
    public void controllo_oulsante(int a) {
        stringa = "";
        switch (a) {
            case 0:
                stringa = "non va";
            case 1:
                stringa = "Questo pulsante regitrerà il palmare sul server";
            case 2:
                stringa = "questo pulsante salverà le informazioni";
            case 3:
                stringa = "Questo pulsante chiuderà questa activity";
        }
        crea_snackbar();
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

}
