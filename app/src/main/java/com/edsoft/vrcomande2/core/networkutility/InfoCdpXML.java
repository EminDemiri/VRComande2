package com.edsoft.vrcomande2.core.networkutility;

import com.edsoft.vrcomande2.BuildConfig;

/**
 * Created by pagno on 04/01/2016.
 */

public class InfoCdpXML {
    protected String alfaCDP;
    protected String codCDP;

    public InfoCdpXML() {
        this.codCDP = BuildConfig.FLAVOR;
        this.alfaCDP = BuildConfig.FLAVOR;
    }

    public String getCodCDP() {
        return this.codCDP;
    }

    public void setCodCDP(String dati) {
        this.codCDP = dati;
    }

    public String getAlfaCDP() {
        return this.alfaCDP;
    }

    public void setAlfaCDP(String dati) {
        this.alfaCDP = dati;
    }

    public String toString() {
        return "InfoCdpXML [codCDP=" + this.codCDP + ", alfaCDP=" + this.alfaCDP + "]";
    }
}
