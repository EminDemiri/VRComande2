package com.edsoft.vrcomande2.core.networkutility;

import com.edsoft.vrcomande2.BuildConfig;

import java.util.ArrayList;

/**
 * Created by pagno on 04/01/2016.
 */

public class VisuArticoloXML {
    protected ArrayList<String> Varianti;
    protected String alfa;
    protected int qta;

    public VisuArticoloXML() {
        this.alfa = BuildConfig.FLAVOR;
        this.qta = 0;
        this.Varianti = new ArrayList();
    }

    public String getAlfaArticolo() {
        return this.alfa;
    }

    public void setAlfaArticolo(String dati) {
        this.alfa = dati;
    }

    public int getQtaArticolo() {
        return this.qta;
    }

    public void setQtaArticolo(int dati) {
        this.qta = dati;
    }

    public ArrayList<String> getElencoVarianti() {
        return this.Varianti;
    }

    public void setElencoVarianti(ArrayList<String> dati) {
        this.Varianti = dati;
    }

    public void AddLinkVariante(String dati) {
        this.Varianti.add(dati);
    }
}
