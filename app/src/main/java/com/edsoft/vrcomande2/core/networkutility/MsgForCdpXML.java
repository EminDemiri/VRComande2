package com.edsoft.vrcomande2.core.networkutility;

import com.edsoft.vrcomande2.BuildConfig;

/**
 * Created by pagno on 04/01/2016.
 */

public class MsgForCdpXML {
    protected String codCDP;
    protected String codMSG;
    protected String testoMSG;

    public MsgForCdpXML() {
        this.codMSG = BuildConfig.FLAVOR;
        this.testoMSG = BuildConfig.FLAVOR;
        this.codCDP = BuildConfig.FLAVOR;
    }

    public String getCodMSG() {
        return this.codMSG;
    }

    public void setCodMSG(String dati) {
        this.codMSG = dati;
    }

    public String getTestoMSG() {
        return this.testoMSG;
    }

    public void setTestoMSG(String dati) {
        this.testoMSG = dati;
    }

    public String getCodCDP() {
        return this.codCDP;
    }

    public void setCodCDP(String dati) {
        this.codCDP = dati;
    }

    public String toString() {
        return "InfoCdpXML [codMSF=" + this.codMSG + ", testoMSG=" + this.testoMSG + ", codCDP=" + this.codCDP + "]";
    }
}