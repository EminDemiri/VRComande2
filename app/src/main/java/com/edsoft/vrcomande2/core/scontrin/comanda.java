package com.edsoft.vrcomande2.core.scontrin;

import android.util.Log;
import android.util.Xml;

import com.edsoft.vrcomande2.BuildConfig;
import com.edsoft.vrcomande2.core.dbutility.Articoli;
import com.edsoft.vrcomande2.core.dbutility.VariantiArticoli;
import com.edsoft.vrcomande2.core.dbutility.fresul;

import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by pagno on 04/01/2016.
 */

public class comanda {
    String _alfaOperatore;
    String _codiceOperatore;
    ArrayList<ArticoliComanda> _elencoart;
    String _idComanda;
    String _idTerminale;
    String _nominativo_preno;
    int _numeroCoperti;
    int _numeroSala;
    int _numeroTavolo;
    String _orario_oreno;

    public comanda() {
        this._numeroCoperti = 0;
        this._numeroTavolo = 0;
        this._numeroSala = 0;
        this._codiceOperatore = BuildConfig.FLAVOR;
        this._alfaOperatore = BuildConfig.FLAVOR;
        this._elencoart = new ArrayList();
        this._idTerminale = BuildConfig.FLAVOR;
        this._idComanda = BuildConfig.FLAVOR;
        this._nominativo_preno = BuildConfig.FLAVOR;
        this._orario_oreno = BuildConfig.FLAVOR;
    }

    public comanda(int numeroTavolo, int numeroSala, String codiceOperatore, String alfaOperatore, int numeroCoperti, String idTerminale, ArticoliComanda[] elencoart, String idComanda, String nominativo_preno, String orario_oreno) {
        this();
        this._numeroTavolo = numeroTavolo;
        this._numeroSala = numeroSala;
        this._codiceOperatore = codiceOperatore;
        this._alfaOperatore = alfaOperatore;
        this._elencoart.clear();
        this._numeroCoperti = numeroCoperti;
        this._idTerminale = idTerminale;
        this._idComanda = idComanda;
        this._nominativo_preno = nominativo_preno;
        this._orario_oreno = orario_oreno;
        for (ArticoliComanda AggiungiArticolo : elencoart) {
            AggiungiArticolo(AggiungiArticolo);
        }
    }

    public String getIdTerminale() {
        return this._idTerminale;
    }

    public void setIdTerminale(String dati) {
        this._idTerminale = dati;
    }

    public String getIdComanda() {
        return this._idComanda;
    }

    public void setIdComanda(String dati) {
        this._idComanda = dati;
    }

    public int getNumeroCoperti() {
        return this._numeroCoperti;
    }

    public void setNumeroCoperti(int dati) {
        this._numeroCoperti = dati;
    }

    public int getNumeroTavolo() {
        return this._numeroTavolo;
    }

    public void setNumeroTavolo(int dati) {
        this._numeroTavolo = dati;
    }

    public int getNumeroSala() {
        return this._numeroSala;
    }

    public void setNumeroSala(int dati) {
        this._numeroSala = dati;
    }

    public String getCodiceOperatore() {
        return this._codiceOperatore;
    }

    public void setCodiceOperatore(String dati) {
        this._codiceOperatore = dati;
    }

    public String getAlfaOperatore() {
        return this._alfaOperatore;
    }

    public void setAlfaOperatore(String dati) {
        this._alfaOperatore = dati;
    }

    public String getNominativoPreno() {
        return this._nominativo_preno;
    }

    public void setNominativoPreno(String dati) {
        this._nominativo_preno = dati;
    }

    public String getOrarioPreno() {
        return this._orario_oreno;
    }

    public void setOrarioPreno(String dati) {
        this._orario_oreno = dati;
    }

    public fresul AggiungiArticolo(ArticoliComanda dati) {
        fresul ret = new fresul(0, BuildConfig.FLAVOR);
        try {
            ArticoliComanda addart = new ArticoliComanda();
            addart.setCodArt(dati.getCodArt());
            addart.setAlfaArt(dati.getAlfaArt());
            addart.setPrezzoArt(dati.getPrezzoArt());
            addart.setIvaArt(dati.getIvaArt());
            addart.setQta(dati.getQta());
            for (int index = 0; index < dati.getElencoVarianti().size(); index++) {
                addart.AddLinkVariante((String) dati.getElencoVarianti().get(index));
            }
            this._elencoart.add(addart);
        } catch (Exception ex) {
            ret.result = -1;
            ret.errMesg = ex.getMessage();
            Log.e("AggiungiArticolo comanda:", ex.getMessage());
        }
        return ret;
    }

    public void RimuoviArticolo(ArticoliComanda dati) {
        try {
            if (this._elencoart.contains(dati)) {
                this._elencoart.remove(dati);
            }
        } catch (Exception ex) {
            Log.e("RimuoviArt comanda:", ex.getMessage());
        }
    }

    public streamoutresult ToXml() {
        ByteArrayOutputStream fileos = new ByteArrayOutputStream();
        XmlSerializer serializer = Xml.newSerializer();
        try {
            serializer.setOutput(fileos, "ASCII");
            serializer.startDocument(null, Boolean.valueOf(true));
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            serializer.startTag(null, "comanda");
            serializer.startTag(null, "info");
            serializer.startTag(null, "idTerminale");
            serializer.text(this._idTerminale);
            serializer.endTag(null, "idTerminale");
            serializer.startTag(null, "sala");
            serializer.text(Integer.toString(this._numeroSala));
            serializer.endTag(null, "sala");
            serializer.startTag(null, "tavolo");
            serializer.text(Integer.toString(this._numeroTavolo));
            serializer.endTag(null, "tavolo");
            serializer.startTag(null, "coperti");
            serializer.text(Integer.toString(this._numeroCoperti));
            serializer.endTag(null, "coperti");
            serializer.startTag(null, "IDComanda");
            serializer.text(this._idComanda);
            serializer.endTag(null, "IDComanda");
            serializer.startTag(null, "NominativoPreno");
            serializer.text(this._nominativo_preno);
            serializer.endTag(null, "NominativoPreno");
            serializer.startTag(null, "OrarioPreno");
            serializer.text(this._orario_oreno);
            serializer.endTag(null, "OrarioPreno");
            serializer.startTag(null, "operatore");
            serializer.attribute(null, VariantiArticoli.CODICE, this._codiceOperatore);
            serializer.text(this._alfaOperatore);
            serializer.endTag(null, "operatore");
            serializer.endTag(null, "info");
            if (this._elencoart.size() > 0) {
                serializer.startTag(null, "ElencoArticoli");
                for (int index = 0; index < this._elencoart.size(); index++) {
                    serializer.startTag(null, "articolo");
                    serializer.attribute(null, VariantiArticoli.CODICE, ((ArticoliComanda) this._elencoart.get(index)).getCodArt());
                    serializer.attribute(null, "qta", Integer.toString(((ArticoliComanda) this._elencoart.get(index)).getQta()));
                    serializer.attribute(null, "alfa", ((ArticoliComanda) this._elencoart.get(index)).getAlfaArt());
                    serializer.attribute(null, Articoli.PREZZODIVENDITA, Integer.toString(((ArticoliComanda) this._elencoart.get(index)).getPrezzoArtInt()));
                    serializer.attribute(null, Articoli.ALIQUOTAIVA, Integer.toString(((ArticoliComanda) this._elencoart.get(index)).getIvaArtInt()));
                    if (((ArticoliComanda) this._elencoart.get(index)).getElencoVarianti().size() > 0) {
                        serializer.startTag(null, "varianti");
                        for (int index2 = 0; index2 < ((ArticoliComanda) this._elencoart.get(index)).getElencoVarianti().size(); index2++) {
                            serializer.startTag(null, "variante");
                            String[] campi = ((String) ((ArticoliComanda) this._elencoart.get(index)).getElencoVarianti().get(index2)).split("\\|");
                            serializer.attribute(null, VariantiArticoli.CODICE, campi[0]);
                            serializer.attribute(null, Articoli.PREZZODIVENDITA, campi[2]);
                            serializer.text(campi[1]);
                            serializer.endTag(null, "variante");
                        }
                        serializer.endTag(null, "varianti");
                    }
                    serializer.endTag(null, "articolo");
                }
                serializer.endTag(null, "ElencoArticoli");
            }
            serializer.endTag(null, "comanda");
            serializer.endDocument();
            serializer.flush();
            fileos.close();
            return new streamoutresult(0, fileos, BuildConfig.FLAVOR);
        } catch (Exception e) {
            streamoutresult ret = new streamoutresult(-1, fileos, e.getMessage());
            Log.e("Exception", "Exception occured in wroting");
            return ret;
        }
    }
}
