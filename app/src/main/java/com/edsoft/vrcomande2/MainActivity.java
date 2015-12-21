package com.edsoft.vrcomande2;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle("Home");
        actionBar.setSubtitle("Scegli cosa fare");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main_chiudi:
                //Questo pulsante del menu chiude l'applicazione
                System.exit(0);
                return true;
            case R.id.action_main_info:
                //perform settings
                Toast.makeText(this, "Le informazioni saranno disponibilli a breve" , Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_main_guida:
                //Pulsante per visualizzare una guida sulla schermata visualizzata
                Toast.makeText(this,"La guida non è ancora disponibile", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void click_login (View view){
        Intent intent1= new Intent(this,LoginActivity.class);
        startActivity(intent1);
    }

    public void click_configurazioni (View view) {
        Intent Intent2= new Intent(this,ConfigurazioniActivity.class);
        startActivity(Intent2);
    }

    public void click_main_sincronizza (View view) {
        Toast.makeText(this,"Il codice per la sincronizzazione deve essere ancora scritto", Toast.LENGTH_LONG).show();
    }

}
