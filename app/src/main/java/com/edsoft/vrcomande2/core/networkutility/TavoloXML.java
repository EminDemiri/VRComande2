package com.edsoft.vrcomande2.core.networkutility;

import com.edsoft.vrcomande2.BuildConfig;

/**
 * Created by pagno on 04/01/2016.
 */

public class TavoloXML {
    public static final int LIBERO = 0;
    public static final int OCCUPATO = 1;
    public static final int PRENOTATO = 2;
    public static final int RICHIESTO_CONTO = 3;
    protected int coperti;
    protected String nominativopreno;
    protected int numero;
    protected String orariopreno;
    protected String sala;
    protected int situazione;

    public TavoloXML() {
        this.sala = BuildConfig.FLAVOR;
        this.numero = LIBERO;
        this.situazione = LIBERO;
        this.coperti = LIBERO;
        this.nominativopreno = BuildConfig.FLAVOR;
        this.orariopreno = BuildConfig.FLAVOR;
    }

    public String getSala() {
        return this.sala;
    }

    public void setSala(String dati) {
        this.sala = dati;
    }

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int dati) {
        this.numero = dati;
    }

    public int getSituazione() {
        return this.situazione;
    }

    public void setSituazione(int dati) {
        this.situazione = dati;
    }

    public int getCoperti() {
        return this.coperti;
    }

    public void setCoperti(int dati) {
        this.coperti = dati;
    }

    public String getnominativoPreno() {
        return this.nominativopreno;
    }

    public void setnominativoPreno(String dati) {
        this.nominativopreno = dati;
    }

    public String getorarioPreno() {
        return this.orariopreno;
    }

    public void setorarioPreno(String dati) {
        this.orariopreno = dati;
    }

    public String getOrarioPrenoFormattato() {
        String Ret = "00:00";
        if (getorarioPreno().length() < 14) {
            return Ret;
        }
        String ora = getorarioPreno().substring(8, 10);
        return ora + ":" + getorarioPreno().substring(10, 12);
    }

    public int hashCode() {
        return ((getSala().hashCode() + 217) * 31) + Integer.toString(getNumero()).hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        TavoloXML t = (TavoloXML) obj;
        if (getSala().hashCode() == t.getSala().hashCode() && getNumero() == t.getNumero()) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "TavoloXML [sala=" + this.sala + ", numero tavolo=" + Integer.toString(this.numero) + ", situazione=" + Integer.toString(this.situazione) + ", nominativopreno=" + this.nominativopreno + ", orariopreno=" + this.orariopreno + "]";
    }
}