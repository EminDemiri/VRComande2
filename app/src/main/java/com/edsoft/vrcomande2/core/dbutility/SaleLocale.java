package com.edsoft.vrcomande2.core.dbutility;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.edsoft.vrcomande2.core.networkutility.SaleXML;

import java.util.ArrayList;

/**
 * Created by Emin Demiri on 22/12/2015.
 */

public class SaleLocale {
    public static final String ALFASALA = "alfasala";
    public static final String CODICE = "codice";
    public static final String[] COLONNE;
    public static final String DELTATAVOLI = "deltatavoli";
    public static final String NUMEROTAVOLI = "numerotavoli";
    public static final String TABELLA = "sale";

    static {
        COLONNE = new String[]{CODICE, ALFASALA, NUMEROTAVOLI, DELTATAVOLI};
    }

    public static void bulkInsertSale(SQLiteDatabase db, ArrayList<SaleXML> sale) {
        db.beginTransaction();
        try {
            SQLiteStatement insert = db.compileStatement("INSERT INTO sale (codice, alfasala, numerotavoli, deltatavoli) VALUES (?, ?, ?, ?)");
            for (int i = 0; i < sale.size(); i++) {
                insert.bindString(1, ((SaleXML) sale.get(i)).getCodSala());
                insert.bindString(2, ((SaleXML) sale.get(i)).getAlfaSala());
                insert.bindString(3, Integer.toString(((SaleXML) sale.get(i)).getNumeroTavoli()));
                insert.bindString(4, Integer.toString(((SaleXML) sale.get(i)).getDeltaTavoli()));
                insert.execute();
                insert.clearBindings();
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("bulkInsert:", e.getMessage() == null ? "bulkInsert failed" : e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public static void insertSala(SQLiteDatabase db, String codice, String descrizione, int numtavoli, int deltatavoli) {
        if (!aggiorna(db, codice, descrizione, numtavoli, deltatavoli)) {
            boolean newrecord = inserisci(db, codice, descrizione, numtavoli, deltatavoli);
        }
    }

    private static boolean inserisci(SQLiteDatabase db, String codice, String descrizione, int numtavoli, int deltatavoli) {
        ContentValues v = new ContentValues();
        v.put(CODICE, codice);
        v.put(ALFASALA, descrizione);
        v.put(NUMEROTAVOLI, Integer.valueOf(numtavoli));
        v.put(DELTATAVOLI, Integer.valueOf(deltatavoli));
        return db.insert(TABELLA, null, v) > 0;
    }

    public static boolean aggiorna(SQLiteDatabase db, String codice, String descrizione, int numtavoli, int deltatavoli) {
        ContentValues v = new ContentValues();
        v.put(CODICE, codice);
        v.put(ALFASALA, descrizione);
        v.put(NUMEROTAVOLI, Integer.valueOf(numtavoli));
        v.put(DELTATAVOLI, Integer.valueOf(deltatavoli));
        return db.update(TABELLA, v, new StringBuilder().append("codice=").append(codice).toString(), null) > 0;
    }

    public static Cursor getAllSale(SQLiteDatabase db) {
        return db.query(TABELLA, COLONNE, null, null, null, null, null);
    }

    public static boolean deleteSala(SQLiteDatabase db, String codice) {
        return db.delete(TABELLA, new StringBuilder().append("codice=").append(codice).toString(), null) > 0;
    }

    public static Cursor getSala(SQLiteDatabase db, String codice) {
        return db.query(true, TABELLA, COLONNE, "codice='" + codice + "'", null, null, null, null, null);
    }

    public static Cursor getSalaByDescr(SQLiteDatabase db, String descr) {
        return db.query(true, TABELLA, COLONNE, "alfasala='" + descr + "'", null, null, null, null, null);
    }
}
