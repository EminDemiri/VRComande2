package com.edsoft.vrcomande2.core.dbutility;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.edsoft.vrcomande2.BuildConfig;
import com.edsoft.vrcomande2.core.networkutility.ArticoliXML;
import com.edsoft.vrcomande2.core.networkutility.InfoCdpXML;
import com.edsoft.vrcomande2.core.networkutility.MsgForCdpXML;
import com.edsoft.vrcomande2.core.networkutility.OperatoriXML;
import com.edsoft.vrcomande2.core.networkutility.RepartiXML;
import com.edsoft.vrcomande2.core.networkutility.SaleXML;
import com.edsoft.vrcomande2.core.networkutility.VariantiXML;

import java.util.ArrayList;

/**
 * Created by Emin Demiri on 04/01/2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_ARTICOLI = "create table articoli (codice TEXT primary key, descrizione TEXT,prezzo NUMERIC,iva NUMERIC,reparto TEXT NOT NULL, posizione INTEGER NOT NULL CONSTRAINT reparto REFERENCES reparti(codice) ON DELETE CASCADE);";
    private static final String CREATE_TABLE_INFOCDP = "create table CentriDiProduzione (codice TEXT primary key, alfacdp TEXT NOT NULL);";
    private static final String CREATE_TABLE_LINK_VAR_ART = "create table LinkVariantiArticoli (id INTEGER PRIMARY KEY, codice_variante TEXT NOT NULL CONSTRAINT fk_variante_codice REFERENCES variantiarticoli(codice) ON  DELETE CASCADE,codice_articolo TEXT NOT NULL CONSTRAINT fk_articolo_codice REFERENCES articoli(codice) ON  DELETE CASCADE);";
    private static final String CREATE_TABLE_MSGFORCDP = "create table MsgForCDP (codice TEXT primary key, testo TEXT NOT NULL, cdp TEXT NOT NULL);";
    private static final String CREATE_TABLE_OPERATORI = "create table operatori (codice TEXT primary key, alfaoperatore TEXT NOT NULL,login TEXT NOT NULL,password TEXT NOT NULL );";
    private static final String CREATE_TABLE_REPARTI = "create table reparti (codice TEXT primary key, descrizione TEXT NOT NULL, posizione INTEGER NOT NULL );";
    private static final String CREATE_TABLE_SALE = "create table sale (codice TEXT primary key, alfasala TEXT NOT NULL,numerotavoli INTEGER NOT NULL,deltatavoli INTEGER NOT NULL );";
    private static final String CREATE_TABLE_SERVER = "create table connessioneserver (ipserver TEXT primary key, portaserver INTEGER NOT NULL, autoupdatedb INTEGER NOT NULL );";
    private static final String CREATE_TABLE_VARIANTI = "create table variantiarticoli (codice TEXT primary key, alfavariante TEXT NOT NULL, prezzovariante NUMERIC, variantepertutti INTEGER NOT NULL);";
    private static final String f1xc32e135d = "CREATE TRIGGER fkd_LinkVariantiArticoli_codice_articolo_articoli_codice BEFORE DELETE ON articoli FOR EACH ROW BEGIN   SELECT RAISE(ROLLBACK, 'delete on table articoli violates foreign key constraint fkd_LinkVariantiArticoli_codice_articolo_articoli_codice')   WHERE (SELECT codice_articolo FROM LinkVariantiArticoli WHERE codice_articolo = OLD.codice) IS NOT NULL; END;";
    private static final String f2x19809184 = "CREATE TRIGGER fkd_LinkVariantiArticoli_codice_variante_variantiarticoli_codice BEFORE DELETE ON variantiarticoli FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'delete on table variantiarticoli violates foreign key constraint fkd_LinkVariantiArticoli_codice_variante_variantiarticoli_codice')    WHERE (SELECT codice_variante FROM LinkVariantiArticoli WHERE codice_variante = OLD.codice) IS NOT NULL; END;";
    private static final String CREATE_TRIGGER_fkdc = "CREATE TRIGGER fkdc_articoli_reparto_reparti_codice BEFORE DELETE ON reparti FOR EACH ROW BEGIN     DELETE FROM articoli WHERE articoli.reparto = OLD.codice; END;";
    private static final String CREATE_TRIGGER_fki = "CREATE TRIGGER fki_articoli_reparto_reparti_codice BEFORE INSERT ON [articoli] FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'insert on table articoli violates foreign key constraint fki_articoli_reparto_reparti_codice')   WHERE (SELECT codice FROM reparti WHERE codice = NEW.reparto) IS NULL;  END;";
    private static final String f3x45b78c78 = "CREATE TRIGGER fki_LinkVariantiArticoli_codice_articolo_articoli_codice BEFORE INSERT ON [LinkVariantiArticoli] FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'insert on table LinkVariantiArticoli violates foreign key constraint fki_LinkVariantiArticoli_codice_articolo_articoli_codice')    WHERE (SELECT codice FROM articoli WHERE codice = NEW.codice_articolo) IS NULL; END;";
    private static final String f4xfeb8bf9f = "CREATE TRIGGER fki_LinkVariantiArticoli_codice_variante_variantiarticoli_codice BEFORE INSERT ON [LinkVariantiArticoli] FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'insert on table LinkVariantiArticoli violates foreign key constraint fki_LinkVariantiArticoli_codice_variante_variantiarticoli_codice')    WHERE (SELECT codice FROM variantiarticoli WHERE codice = NEW.codice_variante) IS NULL; END;";
    private static final String CREATE_TRIGGER_fku = "CREATE TRIGGER fku_articoli_reparto_reparti_codice BEFORE UPDATE ON [articoli]  FOR EACH ROW BEGIN     SELECT RAISE(ROLLBACK, 'update on table articoli violates foreign key constraint fku_articoli_reparto_reparti_codice')       WHERE (SELECT codice FROM reparti WHERE codice = NEW.reparto) IS NULL; END;";
    private static final String f5x7f017bec = "CREATE TRIGGER fku_LinkVariantiArticoli_codice_articolo_articoli_codice BEFORE UPDATE ON [LinkVariantiArticoli] FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'update on table LinkVariantiArticoli violates foreign key constraint fku_LinkVariantiArticoli_codice_articolo_articoli_codice') WHERE (SELECT codice FROM articoli WHERE codice = NEW.codice_articolo) IS NULL; END;";
    private static final String f6xf1a5fb13 = "CREATE TRIGGER fku_LinkVariantiArticoli_codice_variante_variantiarticoli_codice BEFORE UPDATE ON [LinkVariantiArticoli] FOR EACH ROW BEGIN  SELECT RAISE(ROLLBACK, 'update on table LinkVariantiArticoli violates foreign key constraint fku_LinkVariantiArticoli_codice_variante_variantiarticoli_codice')    WHERE (SELECT codice FROM variantiarticoli WHERE codice = NEW.codice_variante) IS NULL; END;";
    public static final String NOME_DB = "dbVRRisto";
    public static final int VERSIONE_DB = 1;

    public DBHelper(Context context) {
        super(context, NOME_DB, null, VERSIONE_DB);
    }

    public void onCreate(SQLiteDatabase db) {
        System.err.println("Dentro create tables");
        db.execSQL(CREATE_TABLE_OPERATORI);
        db.execSQL(CREATE_TABLE_SERVER);
        db.execSQL(CREATE_TABLE_SALE);
        db.execSQL(CREATE_TABLE_REPARTI);
        db.execSQL(CREATE_TABLE_ARTICOLI);
        db.execSQL(CREATE_TABLE_VARIANTI);
        db.execSQL(CREATE_TABLE_LINK_VAR_ART);
        db.execSQL(CREATE_TABLE_INFOCDP);
        db.execSQL(CREATE_TABLE_MSGFORCDP);
        db.execSQL(CREATE_TRIGGER_fki);
        db.execSQL(CREATE_TRIGGER_fku);
        db.execSQL(CREATE_TRIGGER_fkdc);
        db.execSQL(f4xfeb8bf9f);
        db.execSQL(f6xf1a5fb13);
        db.execSQL(f2x19809184);
        db.execSQL(f3x45b78c78);
        db.execSQL(f5x7f017bec);
        db.execSQL(f1xc32e135d);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.err.println("Dentro update tables");
        db.execSQL("DROP TABLE IF EXISTS operatori");
        db.execSQL("DROP TABLE IF EXISTS connessioneserver");
        db.execSQL("DROP TABLE IF EXISTS sale");
        db.execSQL("DROP TABLE IF EXISTS articoli");
        db.execSQL("DROP TABLE IF EXISTS reparti");
        db.execSQL("DROP TABLE IF EXISTS LinkVariantiArticoli");
        db.execSQL("DROP TABLE IF EXISTS variantiarticoli");
        db.execSQL("DROP TABLE IF EXISTS CentriDiProduzione");
        onCreate(db);
    }

    public fresul BulkImportdbOperatori(SQLiteDatabase db, ArrayList<OperatoriXML> elencoOperatori) throws Throwable {
        fresul Ret = new fresul(0, BuildConfig.FLAVOR);
        Throwable th;
        fresul Ret2 = new fresul(0, BuildConfig.FLAVOR);
        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM operatori");
            SQLiteStatement insert = db.compileStatement("INSERT INTO operatori (codice, alfaoperatore, login, password) VALUES (?, ?, ?, ?)");
            for (int i = 0; i < elencoOperatori.size(); i += VERSIONE_DB) {
                insert.bindString(VERSIONE_DB, ((OperatoriXML) elencoOperatori.get(i)).getCodOp());
                insert.bindString(2, ((OperatoriXML) elencoOperatori.get(i)).getAlfaOp());
                insert.bindString(3, ((OperatoriXML) elencoOperatori.get(i)).getLoginOp());
                insert.bindString(4, ((OperatoriXML) elencoOperatori.get(i)).getPwdOp());
                insert.execute();
                insert.clearBindings();
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            return Ret2;
        } catch (Exception e) {
            String errMsg = e.getMessage() == null ? "bulkInsert failed" : e.getMessage();
            Ret = new fresul(-1, errMsg);
            Log.e("bulkInsert:", errMsg);
            db.endTransaction();
            return Ret;
        } catch (Throwable th2) {
            th = th2;
            Ret2 = Ret;
            db.endTransaction();
            throw th;
        }
    }

    public fresul BulkImportdbSale(SQLiteDatabase db, ArrayList<SaleXML> elencosale) throws Throwable {
        fresul Ret = new fresul(0, BuildConfig.FLAVOR);
        Throwable th;
        fresul Ret2 = new fresul(0, BuildConfig.FLAVOR);
        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM sale");
            SQLiteStatement insert = db.compileStatement("INSERT INTO sale (codice, alfasala, numerotavoli, deltatavoli) VALUES (?, ?, ?, ?)");
            for (int i = 0; i < elencosale.size(); i += VERSIONE_DB) {
                insert.bindString(VERSIONE_DB, ((SaleXML) elencosale.get(i)).getCodSala());
                insert.bindString(2, ((SaleXML) elencosale.get(i)).getAlfaSala());
                insert.bindString(3, Integer.toString(((SaleXML) elencosale.get(i)).getNumeroTavoli()));
                insert.bindString(4, Integer.toString(((SaleXML) elencosale.get(i)).getDeltaTavoli()));
                insert.execute();
                insert.clearBindings();
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            return Ret2;
        } catch (Exception e) {
            String errMsg = e.getMessage() == null ? "bulkInsert failed" : e.getMessage();
            Ret = new fresul(-1, errMsg);
            Log.e("bulkInsert:", errMsg);
            db.endTransaction();
            return Ret;
        } catch (Throwable th2) {
            th = th2;
            Ret2 = Ret;
            db.endTransaction();
            throw th;
        }
    }

    private int boolToInt(boolean b) {
        return b ? VERSIONE_DB : 0;
    }

    public fresul BulkImportdbArticoli(SQLiteDatabase db, ArrayList<RepartiXML> elencorep, ArrayList<VariantiXML> elencovar) throws Throwable {
        fresul Ret = new fresul(0, BuildConfig.FLAVOR);
        Throwable th;
        fresul Ret2 = new fresul(0, BuildConfig.FLAVOR);
        db.beginTransaction();
        try {
            int i;
            String cv;
            db.execSQL("DELETE FROM LinkVariantiArticoli");
            db.execSQL("DELETE FROM variantiarticoli");
            SQLiteStatement insertVariante = db.compileStatement("INSERT INTO variantiarticoli (codice, alfavariante, prezzovariante, variantepertutti) VALUES (?, ?, ?, ?)");
            for (i = 0; i < elencovar.size(); i += VERSIONE_DB) {
                cv = ((VariantiXML) elencovar.get(i)).getCodVariante();
                String av = ((VariantiXML) elencovar.get(i)).getAlfaVariante();
                double pv = ((VariantiXML) elencovar.get(i)).getPrezzoVariante();
                boolean gv = ((VariantiXML) elencovar.get(i)).getVariantePerTutti();
                insertVariante.bindString(VERSIONE_DB, cv);
                insertVariante.bindString(2, av);
                insertVariante.bindDouble(3, pv);
                insertVariante.bindLong(4, (long) boolToInt(gv));
                insertVariante.execute();
                insertVariante.clearBindings();
            }
            db.execSQL("DELETE FROM reparti");
            db.execSQL("DELETE FROM articoli");
            SQLiteStatement insertRep = db.compileStatement("INSERT INTO reparti (codice, descrizione, posizione) VALUES (?, ?, ?)");
            SQLiteStatement insertArt = db.compileStatement("INSERT INTO articoli (codice, descrizione, reparto, prezzo, iva, posizione) VALUES (?, ?, ?, ?, ?, ?)");
            SQLiteStatement insertLinkVarArt = db.compileStatement("INSERT INTO LinkVariantiArticoli (codice_articolo, codice_variante) VALUES (?, ?)");
            for (i = 0; i < elencorep.size(); i += VERSIONE_DB) {
                insertRep.bindString(VERSIONE_DB, ((RepartiXML) elencorep.get(i)).getCodRep());
                insertRep.bindString(2, ((RepartiXML) elencorep.get(i)).getAlfaRep());
                insertRep.bindString(3, Integer.toString(((RepartiXML) elencorep.get(i)).getPosizione()));
                insertRep.execute();
                insertRep.clearBindings();
                for (int y = 0; y < ((RepartiXML) elencorep.get(i)).getElencoArt().size(); y += VERSIONE_DB) {
                    ArticoliXML art = (ArticoliXML) ((RepartiXML) elencorep.get(i)).getElencoArt().get(y);
                    insertArt.bindString(VERSIONE_DB, art.getCodArt());
                    insertArt.bindString(2, art.getAlfaArt());
                    insertArt.bindString(3, ((RepartiXML) elencorep.get(i)).getCodRep());
                    insertArt.bindDouble(4, art.getPrezzoArt());
                    insertArt.bindDouble(5, art.getIvaArt());
                    insertArt.bindString(6, Integer.toString(art.getPosizione()));
                    insertArt.execute();
                    insertArt.clearBindings();
                    for (int K = 0; K < art.getElencoVarianti().size(); K += VERSIONE_DB) {
                        cv = (String) art.getElencoVarianti().get(K);
                        insertLinkVarArt.bindString(VERSIONE_DB, art.getCodArt());
                        insertLinkVarArt.bindString(2, cv);
                        insertLinkVarArt.execute();
                        insertLinkVarArt.clearBindings();
                    }
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            return Ret2;
        } catch (Exception e) {
            String errMsg = e.getMessage() == null ? "bulkInsert failed" : e.getMessage();
            Ret = new fresul(-1, errMsg);
            Log.e("bulkInsert:", errMsg);
            db.endTransaction();
            return Ret;
        } catch (Throwable th2) {
            th = th2;
            Ret2 = Ret;
            db.endTransaction();
            throw th;
        }
    }

    public fresul BulkImportdbCDP(SQLiteDatabase db, ArrayList<InfoCdpXML> elencoCDP) throws Throwable {
        fresul Ret = new fresul(0, BuildConfig.FLAVOR);
        Throwable th;
        fresul Ret2 = new fresul(0, BuildConfig.FLAVOR);
        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM CentriDiProduzione");
            SQLiteStatement insert = db.compileStatement("INSERT INTO CentriDiProduzione (codice, alfacdp) VALUES (?, ?)");
            for (int i = 0; i < elencoCDP.size(); i += VERSIONE_DB) {
                insert.bindString(VERSIONE_DB, ((InfoCdpXML) elencoCDP.get(i)).getCodCDP());
                insert.bindString(2, ((InfoCdpXML) elencoCDP.get(i)).getAlfaCDP());
                insert.execute();
                insert.clearBindings();
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            return Ret2;
        } catch (Exception e) {
            String errMsg = e.getMessage() == null ? "bulkInsert failed" : e.getMessage();
            Ret = new fresul(-1, errMsg);
            Log.e("bulkInsert:", errMsg);
            db.endTransaction();
            return Ret;
        } catch (Throwable th2) {
            th = th2;
            Ret2 = Ret;
            db.endTransaction();
            throw th;
        }
    }

    public fresul BulkImportdbMsgForCDP(SQLiteDatabase db, ArrayList<MsgForCdpXML> elencoMSG) throws Throwable {
        fresul Ret  = new fresul(0, BuildConfig.FLAVOR);
        Throwable th;
        fresul Ret2 = new fresul(0, BuildConfig.FLAVOR);
        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM MsgForCDP");
            SQLiteStatement insert = db.compileStatement("INSERT INTO MsgForCDP (codice, testo, cdp) VALUES (?, ?, ?)");
            for (int i = 0; i < elencoMSG.size(); i += VERSIONE_DB) {
                insert.bindString(VERSIONE_DB, ((MsgForCdpXML) elencoMSG.get(i)).getCodMSG());
                insert.bindString(2, ((MsgForCdpXML) elencoMSG.get(i)).getTestoMSG());
                insert.bindString(3, ((MsgForCdpXML) elencoMSG.get(i)).getCodCDP());
                insert.execute();
                insert.clearBindings();
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            return Ret2;
        } catch (Exception e) {
            String errMsg = e.getMessage() == null ? "bulkInsert failed" : e.getMessage();
            Ret = new fresul(-1, errMsg);
            Log.e("bulkInsert:", errMsg);
            db.endTransaction();
            return Ret;
        } catch (Throwable th2) {
            th = th2;
            Ret2 = Ret;
            db.endTransaction();
            throw th;
        }
    }
}