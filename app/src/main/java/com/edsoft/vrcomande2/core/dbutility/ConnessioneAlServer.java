package com.edsoft.vrcomande2.core.dbutility;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Emin Demiri on 22/12/2015.
 */

public class ConnessioneAlServer {
    public static final String AUTOUPDATEDB = "autoupdatedb";
    public static final String[] COLONNE;
    public static final String IPSERVER = "ipserver";
    public static final String PORTASERVER = "portaserver";
    public static final String TABELLA = "connessioneserver";

    static {
        COLONNE = new String[]{IPSERVER, PORTASERVER, AUTOUPDATEDB};
    }

    public static void insertCfgServer(SQLiteDatabase db, String ipserver, int portaserver, boolean AutoUpdateDB) {
        if (!aggiorna(db, ipserver, portaserver, AutoUpdateDB)) {
            boolean newrecord = inserisci(db, ipserver, portaserver, AutoUpdateDB);
        }
    }

    private static boolean inserisci(SQLiteDatabase db, String ipserver, int portaserver, boolean AutoUpdateDB) {
        ContentValues v = new ContentValues();
        v.put(IPSERVER, ipserver);
        v.put(PORTASERVER, Integer.valueOf(portaserver));
        v.put(AUTOUPDATEDB, Boolean.valueOf(AutoUpdateDB));
        return db.insert(TABELLA, null, v) > 0;
    }

    public static boolean aggiorna(SQLiteDatabase db, String ipserver, int portaserver, boolean AutoUpdateDB) {
        ContentValues v = new ContentValues();
        v.put(IPSERVER, ipserver);
        v.put(PORTASERVER, Integer.valueOf(portaserver));
        v.put(AUTOUPDATEDB, Boolean.valueOf(AutoUpdateDB));
        return db.update(TABELLA, v, null, null) > 0;
    }

    public static Cursor getAllServer(SQLiteDatabase db) {
        return db.query(TABELLA, COLONNE, null, null, null, null, null);
    }

    public static boolean deleteAllServer(SQLiteDatabase db) {
        return db.delete(TABELLA, null, null) > 0;
    }

    public static Cursor getServer(SQLiteDatabase db, String ipserver) throws SQLException {
        Cursor c = db.query(true, TABELLA, COLONNE, "ipserver=" + ipserver, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
}
