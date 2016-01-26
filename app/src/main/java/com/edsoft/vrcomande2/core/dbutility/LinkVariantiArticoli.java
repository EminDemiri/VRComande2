package com.edsoft.vrcomande2.core.dbutility;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Emin Demiri on 22/12/2015.
 */

public class LinkVariantiArticoli {
    public static final String CODICE_ARTICOLO = "codice_articolo";
    public static final String CODICE_VARIANTE = "codice_variante";
    public static final String[] COLONNE;
    public static final String ID = "id";
    public static final String TABELLA = "LinkVariantiArticoli";

    static {
        COLONNE = new String[]{ID, CODICE_VARIANTE, CODICE_ARTICOLO};
    }

    public static void insertVariante(SQLiteDatabase db, String codice_variante, String codice_articolo) {
        inserisci(db, codice_variante, codice_articolo);
    }

    private static boolean inserisci(SQLiteDatabase db, String codice_variante, String codice_articolo) {
        ContentValues v = new ContentValues();
        v.put(CODICE_VARIANTE, codice_variante);
        v.put(CODICE_ARTICOLO, codice_articolo);
        return db.insert(TABELLA, null, v) > 0;
    }

    public static Cursor getAllLink(SQLiteDatabase db) {
        return db.query(TABELLA, COLONNE, null, null, null, null, null);
    }

    public static Cursor getAllArtLink(SQLiteDatabase db, String codice_articolo) {
        return db.query(TABELLA, COLONNE, "codice_articolo='" + codice_articolo + "'", null, null, null, null, null);
    }

    public static boolean deleteVariante(SQLiteDatabase db, String codice_variante) {
        return db.delete(TABELLA, new StringBuilder().append("codice_variante=").append(codice_variante).toString(), null) > 0;
    }
}
