package com.edsoft.vrcomande2.core.dbutility;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Emin Demiri on 22/12/2015.
 */

public class MsgForCDP {
    public static final String CDP = "cdp";
    public static final String CODICE = "codice";
    public static final String[] COLONNE;
    public static final String TABELLA = "MsgForCDP";
    public static final String TESTO = "testo";

    static {
        COLONNE = new String[]{CODICE, TESTO, CDP};
    }

    public static void insertMsgForCDP(SQLiteDatabase db, String codice, String Testo, String Cdp) {
        if (!aggiorna(db, codice, Testo, Cdp)) {
            boolean newrecord = inserisci(db, codice, Testo, Cdp);
        }
    }

    private static boolean inserisci(SQLiteDatabase db, String codice, String Testo, String Cdp) {
        ContentValues v = new ContentValues();
        v.put(CODICE, codice);
        v.put(TESTO, Testo);
        v.put(CDP, Cdp);
        return db.insert(TABELLA, null, v) > 0;
    }

    public static boolean aggiorna(SQLiteDatabase db, String codice, String Testo, String Cdp) {
        ContentValues v = new ContentValues();
        v.put(CODICE, codice);
        v.put(TESTO, Testo);
        v.put(CDP, Cdp);
        return db.update(TABELLA, v, new StringBuilder().append("codice=").append(codice).toString(), null) > 0;
    }

    public static Cursor getAllMsgForCDP(SQLiteDatabase db) {
        return db.query(TABELLA, COLONNE, null, null, null, null, null);
    }

    public static boolean deleteMsgForCDP(SQLiteDatabase db, String codice) {
        return db.delete(TABELLA, new StringBuilder().append("codice='").append(codice).append("'").toString(), null) > 0;
    }

    public static Cursor getMsgForCDP(SQLiteDatabase db, String codice) throws SQLException {
        return db.query(true, TABELLA, COLONNE, "codice='" + codice + "'", null, null, null, null, null);
    }
}
