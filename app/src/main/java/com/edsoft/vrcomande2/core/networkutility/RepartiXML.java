package com.edsoft.vrcomande2.core.networkutility;

import com.edsoft.vrcomande2.BuildConfig;

import java.util.ArrayList;

/**
 * Created by Emin Demiri on 04/01/2016.
 */

public class RepartiXML {
    private String alfarep;
    private String codrep;
    private ArrayList<ArticoliXML> elencoart;
    private int posizione;

    public RepartiXML() {
        this.codrep = BuildConfig.FLAVOR;
        this.alfarep = BuildConfig.FLAVOR;
        this.posizione = 0;
        this.elencoart = new ArrayList();
    }

    public String getCodRep() {
        return this.codrep;
    }

    public void setCodRep(String dati) {
        this.codrep = dati;
    }

    public String getAlfaRep() {
        return this.alfarep;
    }

    public void setAlfaRep(String dati) {
        this.alfarep = dati;
    }

    public int getPosizione() {
        return this.posizione;
    }

    public void setPosizione(int dati) {
        this.posizione = dati;
    }

    public ArrayList<ArticoliXML> getElencoArt() {
        return this.elencoart;
    }

    public void setElencoArt(ArrayList<ArticoliXML> dati) {
        this.elencoart = dati;
    }

    public void AddArt(ArticoliXML dati) {
        this.elencoart.add(dati);
    }

    public String toString() {
        return "RepartiXML [codrep=" + this.codrep + ", alfarep=" + this.alfarep + "]";
    }
}
