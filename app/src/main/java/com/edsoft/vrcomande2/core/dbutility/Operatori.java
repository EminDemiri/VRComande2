package com.edsoft.vrcomande2.core.dbutility;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Emin Demiri on 22/12/2015.
 */

public class Operatori {
    public static final String ALFAOPERATORE = "alfaoperatore";
    public static final String CODICE = "codice";
    public static final String[] COLONNE;
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String TABELLA = "operatori";

    static {
        COLONNE = new String[]{CODICE, ALFAOPERATORE, LOGIN, PASSWORD};
    }

    public static void insertOperatore(SQLiteDatabase db, String codice, String alfaoperatore, String login, String password) {
        if (!aggiorna(db, codice, alfaoperatore, login, password)) {
            boolean newrecord = inserisci(db, codice, alfaoperatore, login, password);
        }
    }

    private static boolean inserisci(SQLiteDatabase db, String codice, String alfaoperatore, String login, String password) {
        ContentValues v = new ContentValues();
        v.put(CODICE, codice);
        v.put(ALFAOPERATORE, alfaoperatore);
        v.put(LOGIN, login);
        v.put(PASSWORD, password);
        return db.insert(TABELLA, null, v) > 0;
    }

    public static boolean aggiorna(SQLiteDatabase db, String codice, String alfaoperatore, String login, String password) {
        ContentValues v = new ContentValues();
        v.put(CODICE, codice);
        v.put(ALFAOPERATORE, alfaoperatore);
        v.put(LOGIN, login);
        v.put(PASSWORD, password);
        return db.update(TABELLA, v, new StringBuilder().append("codice=").append(codice).toString(), null) > 0;
    }

    public static Cursor getAllOperatori(SQLiteDatabase db) {
        return db.query(TABELLA, COLONNE, null, null, null, null, ALFAOPERATORE);
    }

    public static boolean deleteOperatore(SQLiteDatabase db, String codice) {
        return db.delete(TABELLA, new StringBuilder().append("codice=").append(codice).toString(), null) > 0;
    }

    public static Cursor getOperatore(SQLiteDatabase db, String codice) {
        return db.query(true, TABELLA, COLONNE, "codice='" + codice + "'", null, null, null, null, null);
    }

    public static Cursor getOperatore(SQLiteDatabase db, String login, String pwd) {
        return db.query(true, TABELLA, COLONNE, "login='" + login + "'" + " AND " + PASSWORD + "=" + "'" + pwd + "'", null, null, null, null, null);
    }
}
