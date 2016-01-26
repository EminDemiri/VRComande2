package com.edsoft.vrcomande2.core.networkutility;

import com.edsoft.vrcomande2.BuildConfig;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by pagno on 04/01/2016.
 */

public class ArticoliXML {
    protected String alfaart;
    protected String codart;
    protected double ivaart;
    protected ArrayList<String> linkvarianti;
    protected int posizione;
    protected double prezzoart;

    public ArticoliXML() {
        this.codart = BuildConfig.FLAVOR;
        this.alfaart = BuildConfig.FLAVOR;
        this.prezzoart = 0.0d;
        this.ivaart = 0.0d;
        this.linkvarianti = new ArrayList();
        this.posizione = 0;
    }

    public String getCodArt() {
        return this.codart;
    }

    public void setCodArt(String dati) {
        this.codart = dati;
    }

    public String getAlfaArt() {
        return this.alfaart;
    }

    public void setAlfaArt(String dati) {
        this.alfaart = dati;
    }

    public double getPrezzoArt() {
        return this.prezzoart;
    }

    public void setPrezzoArt(double dati) {
        this.prezzoart = dati;
    }

    public int getPrezzoArtInt() {
        return (int) (this.prezzoart * 100.0d);
    }

    public void setPrezzoArtInt(int dati) {
        this.prezzoart = ((double) dati) / 100.0d;
    }

    public double getIvaArt() {
        return this.ivaart;
    }

    public void setIvaArt(double dati) {
        this.ivaart = dati;
    }

    public int getIvaArtInt() {
        return (int) (this.ivaart * 100.0d);
    }

    public void setIvaArtInt(int dati) {
        this.ivaart = ((double) dati) / 100.0d;
    }

    public ArrayList<String> getElencoVarianti() {
        return this.linkvarianti;
    }

    public void setElencoVarianti(ArrayList<String> dati) {
        this.linkvarianti = dati;
    }

    public void AddLinkVariante(String dati) {
        this.linkvarianti.add(dati);
    }

    public void AddLinkVariante(String[] dati) {
        Collections.addAll(this.linkvarianti, dati);
    }

    public int getPosizione() {
        return this.posizione;
    }

    public void setPosizione(int dati) {
        this.posizione = dati;
    }

    public String toString() {
        return "ArticoliXML [codart=" + this.codart + ", alfaart=" + this.alfaart + ", prezzoart=" + this.prezzoart + ", ivaart=" + this.ivaart + "]";
    }
}
