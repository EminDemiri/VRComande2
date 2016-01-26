package com.edsoft.vrcomande2.core.scontrin;

import android.os.Parcel;
import android.os.Parcelable;

import com.edsoft.vrcomande2.core.networkutility.VariantiXML;

/**
 * Created by pagno on 04/01/2016.
 */

public class VariantiComanda extends VariantiXML implements Parcelable {
    public static final Creator<VariantiComanda> CREATOR;
    private boolean _getchecked;

    /* renamed from: novarum.VRAndroid.scontrin.VariantiComanda.1 */
    static class C00481 implements Parcelable.Creator<VariantiComanda> {
        C00481() {
        }

        public VariantiComanda createFromParcel(Parcel in) {
            return new VariantiComanda(in);
        }

        public VariantiComanda[] newArray(int size) {
            return new VariantiComanda[size];
        }
    }

    public VariantiComanda() {
        this._getchecked = false;
    }

    public VariantiComanda(Parcel p) {
        this();
        readFromParcel(p);
    }

    public boolean getChecked() {
        return this._getchecked;
    }

    public void setChecked(boolean dati) {
        this._getchecked = dati;
    }

    public int hashCode() {
        return ((super.getCodVariante().hashCode() + 217) * 31) + (super.getAlfaVariante() == null ? 0 : super.getAlfaVariante().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        VariantiComanda art = (VariantiComanda) obj;
        if (super.getCodVariante() == art.getCodVariante() && super.getAlfaVariante() == art.getAlfaVariante()) {
            return true;
        }
        return false;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.codvariante);
        dest.writeString(this.alfavariante);
    }

    private void readFromParcel(Parcel in) {
        this.codvariante = in.readString();
        this.alfavariante = in.readString();
    }

    static {
        CREATOR = new C00481();
    }
}
