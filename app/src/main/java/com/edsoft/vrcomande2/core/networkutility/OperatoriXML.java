package com.edsoft.vrcomande2.core.networkutility;

import com.edsoft.vrcomande2.BuildConfig;

/**
 * Created by pagno on 04/01/2016.
 */

public class OperatoriXML {
    protected String _alfaoperatore;
    protected String _codice;
    protected String _login;
    protected String _password;

    public OperatoriXML() {
        this._codice = BuildConfig.FLAVOR;
        this._alfaoperatore = BuildConfig.FLAVOR;
        this._login = BuildConfig.FLAVOR;
        this._password = BuildConfig.FLAVOR;
    }

    public String getCodOp() {
        return this._codice;
    }

    public void setCodOp(String dati) {
        this._codice = dati;
    }

    public String getAlfaOp() {
        return this._alfaoperatore;
    }

    public void setAlfaOp(String dati) {
        this._alfaoperatore = dati;
    }

    public String getLoginOp() {
        return this._login;
    }

    public void setLoginOp(String dati) {
        this._login = dati;
    }

    public String getPwdOp() {
        return this._password;
    }

    public void setPwdOp(String dati) {
        this._password = dati;
    }
}
