package com.edsoft.vrcomande2.core.scontrin;

import android.os.Parcel;
import android.os.Parcelable;

import com.edsoft.vrcomande2.core.networkutility.ArticoliXML;

/**
 * Created by pagno on 04/01/2016.
 */

public class ArticoliComanda extends ArticoliXML implements Parcelable {
    public static final Parcelable.Creator<ArticoliComanda> CREATOR;
    private boolean _getchecked;
    int _qtaInComanda;

    /* renamed from: novarum.VRAndroid.scontrin.ArticoliComanda.1 */
    static class C00471 implements Creator<ArticoliComanda> {
        C00471() {
        }

        public ArticoliComanda createFromParcel(Parcel in) {
            return new ArticoliComanda(in);
        }

        public ArticoliComanda[] newArray(int size) {
            return new ArticoliComanda[size];
        }
    }

    public ArticoliComanda() {
        this._qtaInComanda = 0;
        this._getchecked = false;
    }

    public ArticoliComanda(Parcel p) {
        this();
        readFromParcel(p);
    }

    public boolean getChecked() {
        return this._getchecked;
    }

    public void setChecked(boolean dati) {
        this._getchecked = dati;
    }

    public int getQta() {
        return this._qtaInComanda;
    }

    public void setQta(int dati) {
        this._qtaInComanda = dati;
    }

    public int hashCode() {
        int hash = ((super.getCodArt().hashCode() + 217) * 31) + (super.getAlfaArt() == null ? 0 : super.getAlfaArt().hashCode());
        for (int i = 0; i < super.getElencoVarianti().size(); i++) {
            hash = (hash * 31) + ((String) super.getElencoVarianti().get(i)).hashCode();
        }
        return hash;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        ArticoliComanda art = (ArticoliComanda) obj;
        String codsuper = super.getCodArt();
        String alfaSuper = super.getAlfaArt();
        String codnew = art.getCodArt();
        String alfanew = art.getAlfaArt();
        if (codsuper.compareTo(codnew) != 0 || alfaSuper.compareTo(alfanew) != 0) {
            return false;
        }
        if (super.getElencoVarianti().size() != art.getElencoVarianti().size()) {
            return false;
        }
        for (int i = 0; i < super.getElencoVarianti().size(); i++) {
            if (((String) super.getElencoVarianti().get(i)).compareTo((String) art.getElencoVarianti().get(i)) != 0) {
                return false;
            }
        }
        return true;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.codart);
        dest.writeString(this.alfaart);
        dest.writeDouble(this.prezzoart);
        dest.writeDouble(this.ivaart);
        dest.writeInt(this._qtaInComanda);
        dest.writeStringList(this.linkvarianti);
    }

    private void readFromParcel(Parcel in) {
        this.codart = in.readString();
        this.alfaart = in.readString();
        this.prezzoart = in.readDouble();
        this.ivaart = in.readDouble();
        this._qtaInComanda = in.readInt();
        in.readStringList(this.linkvarianti);
    }

    static {
        CREATOR = new C00471();
    }
}
