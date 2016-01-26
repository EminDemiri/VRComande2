package com.edsoft.vrcomande2.core.dbutility;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Emin Demiri on 22/12/2015.
 */

public class Reparti {
    public static final String ALFAREPARTO = "descrizione";
    public static final String CODICE = "codice";
    public static final String[] COLONNE;
    public static final String POSIZIONE = "posizione";
    public static final String TABELLA = "reparti";

    static {
        COLONNE = new String[]{CODICE, ALFAREPARTO, POSIZIONE};
    }

    public static void insertReparto(SQLiteDatabase db, String codice, String descrizione, int posizione) {
        if (!aggiorna(db, codice, descrizione, posizione)) {
            boolean newrecord = inserisci(db, codice, descrizione, posizione);
        }
    }

    private static boolean inserisci(SQLiteDatabase db, String codice, String descrizione, int posizione) {
        ContentValues v = new ContentValues();
        v.put(CODICE, codice);
        v.put(ALFAREPARTO, descrizione);
        v.put(POSIZIONE, Integer.valueOf(posizione));
        return db.insert(TABELLA, null, v) > 0;
    }

    public static boolean aggiorna(SQLiteDatabase db, String codice, String descrizione, int posizione) {
        ContentValues v = new ContentValues();
        v.put(CODICE, codice);
        v.put(ALFAREPARTO, descrizione);
        v.put(POSIZIONE, Integer.valueOf(posizione));
        return db.update(TABELLA, v, new StringBuilder().append("codice=").append(codice).toString(), null) > 0;
    }

    public static Cursor getAllReparti(SQLiteDatabase db) {
        return db.query(TABELLA, COLONNE, null, null, null, null, POSIZIONE);
    }

    public static boolean deleteReparto(SQLiteDatabase db, String codice) {
        return db.delete(TABELLA, new StringBuilder().append("codice=").append(codice).toString(), null) > 0;
    }

    public static Cursor getReparto(SQLiteDatabase db, String codice) throws SQLException {
        Cursor c = db.query(true, TABELLA, COLONNE, "codice=" + codice, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
}


