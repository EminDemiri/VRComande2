package com.edsoft.vrcomande2.core.dbutility;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Emin Demiri on 22/12/2015.
 */
public class InfoCDP {
    public static final String ALFACDP = "alfacdp";
    public static final String CODICE = "codice";
    public static final String[] COLONNE;
    public static final String TABELLA = "CentriDiProduzione";

    static {
        COLONNE = new String[]{CODICE, ALFACDP};
    }

    public static void insertCDP(SQLiteDatabase db, String codice, String descrizione) {
        if (!aggiorna(db, codice, descrizione)) {
            boolean newrecord = inserisci(db, codice, descrizione);
        }
    }

    private static boolean inserisci(SQLiteDatabase db, String codice, String descrizione) {
        ContentValues v = new ContentValues();
        v.put(CODICE, codice);
        v.put(ALFACDP, descrizione);
        return db.insert(TABELLA, null, v) > 0;
    }

    public static boolean aggiorna(SQLiteDatabase db, String codice, String descrizione) {
        ContentValues v = new ContentValues();
        v.put(CODICE, codice);
        v.put(ALFACDP, descrizione);
        return db.update(TABELLA, v, new StringBuilder().append("codice=").append(codice).toString(), null) > 0;
    }

    public static Cursor getAllCDP(SQLiteDatabase db) {
        return db.query(TABELLA, COLONNE, null, null, null, null, null);
    }

    public static boolean deleteCDP(SQLiteDatabase db, String codice) {
        return db.delete(TABELLA, new StringBuilder().append("codice='").append(codice).append("'").toString(), null) > 0;
    }

    public static Cursor getCDP(SQLiteDatabase db, String codice) throws SQLException {
        return db.query(true, TABELLA, COLONNE, "codice='" + codice + "'", null, null, null, null, null);
    }
}