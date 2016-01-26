package com.edsoft.vrcomande2.core.dbutility;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Emin Demiri on 22/12/2015.
 */

public class Articoli {
    public static final String ALFAARTICOLO = "descrizione";
    public static final String ALIQUOTAIVA = "iva";
    public static final String CODICE = "codice";
    public static final String CODICEREPARTO = "reparto";
    public static final String[] COLONNE;
    public static final String POSIZIONE = "posizione";
    public static final String PREZZODIVENDITA = "prezzo";
    public static final String TABELLA = "articoli";

    static {
        COLONNE = new String[]{CODICE, ALFAARTICOLO, PREZZODIVENDITA, ALIQUOTAIVA, CODICEREPARTO, POSIZIONE};
    }

    public static void insertArticolo(SQLiteDatabase db, String codice, String descrizione, String reparto, double prezzo, double aliquota, int posizione) {
        if (!aggiorna(db, codice, descrizione, reparto, prezzo, aliquota, posizione)) {
            boolean newrecord = inserisci(db, codice, descrizione, reparto, prezzo, aliquota, posizione);
        }
    }

    private static boolean inserisci(SQLiteDatabase db, String codice, String descrizione, String reparto, double prezzo, double aliquota, int posizione) {
        ContentValues v = new ContentValues();
        v.put(CODICE, codice);
        v.put(ALFAARTICOLO, descrizione);
        v.put(PREZZODIVENDITA, Double.valueOf(prezzo));
        v.put(ALIQUOTAIVA, Double.valueOf(aliquota));
        v.put(CODICEREPARTO, reparto);
        v.put(POSIZIONE, Integer.valueOf(posizione));
        return db.insert(TABELLA, null, v) > 0;
    }

    public static boolean aggiorna(SQLiteDatabase db, String codice, String descrizione, String reparto, double prezzo, double aliquota, int posizione) {
        ContentValues v = new ContentValues();
        v.put(CODICE, codice);
        v.put(ALFAARTICOLO, descrizione);
        v.put(PREZZODIVENDITA, Double.valueOf(prezzo));
        v.put(ALIQUOTAIVA, Double.valueOf(aliquota));
        v.put(CODICEREPARTO, reparto);
        v.put(POSIZIONE, Integer.valueOf(posizione));
        return db.update(TABELLA, v, new StringBuilder().append("codice=").append(codice).toString(), null) > 0;
    }

    public static Cursor getAllArticoli(SQLiteDatabase db) {
        return db.query(TABELLA, COLONNE, null, null, null, null, POSIZIONE);
    }

    public static Cursor getAllArticoli(SQLiteDatabase db, String reparto) {
        return db.query(TABELLA, COLONNE, "reparto='" + reparto + "'", null, null, null, POSIZIONE);
    }

    public static boolean deleteArticolo(SQLiteDatabase db, String codice) {
        return db.delete(TABELLA, new StringBuilder().append("codice=").append(codice).toString(), null) > 0;
    }

    public static Cursor getArticolo(SQLiteDatabase db, String codice) {
        return db.query(true, TABELLA, COLONNE, "codice='" + codice + "'", null, null, null, null, null);
    }
}
