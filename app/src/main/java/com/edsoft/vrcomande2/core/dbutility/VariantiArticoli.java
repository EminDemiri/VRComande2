package com.edsoft.vrcomande2.core.dbutility;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Emin Demiri on 22/12/2015.
 */

public class VariantiArticoli {
    public static final String ALFAVARIANTE = "alfavariante";
    public static final String CODICE = "codice";
    public static final String[] COLONNE;
    public static final String PREZZOVARIANTE = "prezzovariante";
    public static final String TABELLA = "variantiarticoli";
    public static final String VARIANTEPERTUTTI = "variantepertutti";

    static {
        COLONNE = new String[]{CODICE, ALFAVARIANTE, PREZZOVARIANTE, VARIANTEPERTUTTI};
    }

    public static void insertVariante(SQLiteDatabase db, String codice, String descrizione, double prezzo, boolean PerTutti) {
        if (!aggiorna(db, codice, descrizione, prezzo, PerTutti)) {
            boolean newrecord = inserisci(db, codice, descrizione, prezzo, PerTutti);
        }
    }

    private static boolean inserisci(SQLiteDatabase db, String codice, String descrizione, double prezzo, boolean PerTutti) {
        ContentValues v = new ContentValues();
        v.put(CODICE, codice);
        v.put(ALFAVARIANTE, descrizione);
        v.put(PREZZOVARIANTE, Double.valueOf(prezzo));
        v.put(VARIANTEPERTUTTI, Boolean.valueOf(PerTutti));
        return db.insert(TABELLA, null, v) > 0;
    }

    public static boolean aggiorna(SQLiteDatabase db, String codice, String descrizione, double prezzo, boolean PerTutti) {
        ContentValues v = new ContentValues();
        v.put(CODICE, codice);
        v.put(ALFAVARIANTE, descrizione);
        v.put(PREZZOVARIANTE, Double.valueOf(prezzo));
        v.put(VARIANTEPERTUTTI, Boolean.valueOf(PerTutti));
        return db.update(TABELLA, v, new StringBuilder().append("codice=").append(codice).toString(), null) > 0;
    }

    public static Cursor getAllVarianti(SQLiteDatabase db) {
        return db.query(TABELLA, COLONNE, null, null, null, null, null);
    }

    public static boolean deleteVariante(SQLiteDatabase db, String codice) {
        return db.delete(TABELLA, new StringBuilder().append("codice='").append(codice).append("'").toString(), null) > 0;
    }

    public static Cursor getVariante(SQLiteDatabase db, String codice) throws SQLException {
        return db.query(true, TABELLA, COLONNE, "codice='" + codice + "'", null, null, null, null, null);
    }

    public static Cursor getVariantiPerTutti(SQLiteDatabase db) throws SQLException {
        return db.query(true, TABELLA, COLONNE, "variantepertutti = 1", null, null, null, null, null);
    }
}
