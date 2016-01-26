package com.edsoft.vrcomande2.core.utility;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.edsoft.vrcomande2.R;

/**
 * Created by EminDemiri on 28/12/2015.
 */
public class utility_varie extends AppCompatActivity{

    public utility_varie() {}

    //questo metodo crea la scnackBar da visualizzare
    public void crea_snackbar(CoordinatorLayout paramcoordinatorLayout, String str, View view){
        Snackbar snackbar = Snackbar.make(paramcoordinatorLayout, str, Snackbar.LENGTH_LONG);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.yellow_500));
        snackbar.show();
    }

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
