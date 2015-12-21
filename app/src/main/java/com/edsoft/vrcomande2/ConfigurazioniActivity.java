package com.edsoft.vrcomande2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ConfigurazioniActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurazioni);

        //impostazioni della ActionBar
        ActionBar actionBar=getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_settings);
        actionBar.setTitle(R.string.configurazioni_titolo);
        actionBar.setSubtitle(R.string.configurazioni_subTitol);

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

}
