package com.edsoft.vrcomande2.core.networkutility;

import com.edsoft.vrcomande2.BuildConfig;

/**
 * Created by Emin Demiri on 22/12/2015.
 */

public class SaleXML {
    private String alfasala;
    private String codsala;
    private int deltatavoli;
    private int numerotavoli;

    public SaleXML() {
        this.codsala = BuildConfig.FLAVOR;
        this.alfasala = BuildConfig.FLAVOR;
        this.numerotavoli = 0;
        this.deltatavoli = 0;
    }

    public String getCodSala() {
        return this.codsala;
    }

    public void setCodSala(String dati) {
        this.codsala = dati;
    }

    public String getAlfaSala() {
        return this.alfasala;
    }

    public void setAlfaSala(String dati) {
        this.alfasala = dati;
    }

    public int getNumeroTavoli() {
        return this.numerotavoli;
    }

    public void setNumeroTavoli(int dati) {
        this.numerotavoli = dati;
    }

    public int getDeltaTavoli() {
        return this.deltatavoli;
    }

    public void setDeltaTavoli(int dati) {
        this.deltatavoli = dati;
    }

    public String toString() {
        return "RepartiXML [codsala=" + this.codsala + ", alfasala=" + this.alfasala + ", numerotavoli=" + this.numerotavoli + ", deltatavoli=" + this.deltatavoli + "]";
    }
}