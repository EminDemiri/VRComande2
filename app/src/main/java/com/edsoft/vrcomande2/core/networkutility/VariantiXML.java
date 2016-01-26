package com.edsoft.vrcomande2.core.networkutility;

import com.edsoft.vrcomande2.BuildConfig;

/**
 * Created by pagno on 04/01/2016.
 */

public class VariantiXML {
    protected String alfavariante;
    protected String codvariante;
    protected double prezzovariante;
    protected boolean variantepertutti;

    public VariantiXML() {
        this.codvariante = BuildConfig.FLAVOR;
        this.alfavariante = BuildConfig.FLAVOR;
        this.prezzovariante = 0.0d;
        this.variantepertutti = false;
    }

    public String getCodVariante() {
        return this.codvariante;
    }

    public void setCodVariante(String dati) {
        this.codvariante = dati;
    }

    public String getAlfaVariante() {
        return this.alfavariante;
    }

    public void setAlfaVariante(String dati) {
        this.alfavariante = dati;
    }

    public double getPrezzoVariante() {
        return this.prezzovariante;
    }

    public void setPrezzoVariante(double dati) {
        this.prezzovariante = dati;
    }

    public int getPrezzoVarianteInt() {
        return (int) (this.prezzovariante * 100.0d);
    }

    public void setPrezzoVarianteInt(int dati) {
        this.prezzovariante = ((double) dati) / 100.0d;
    }

    public boolean getVariantePerTutti() {
        return this.variantepertutti;
    }

    public void setVariantePerTutti(boolean dati) {
        this.variantepertutti = dati;
    }

    public String toString() {
        return "VariantiXML [codvariante=" + this.codvariante + ", alfavariante=" + this.alfavariante + "]";
    }
}
