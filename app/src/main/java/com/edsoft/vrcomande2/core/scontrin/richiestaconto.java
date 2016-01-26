package com.edsoft.vrcomande2.core.scontrin;

import android.util.Log;
import android.util.Xml;

import com.edsoft.vrcomande2.BuildConfig;

import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayOutputStream;

/**
 * Created by pagno on 04/01/2016.
 */

public class richiestaconto {
    String _sala;
    String _tavolo;

    public richiestaconto() {
        this._sala = BuildConfig.FLAVOR;
        this._tavolo = BuildConfig.FLAVOR;
    }

    public String getSala() {
        return this._sala;
    }

    public void setSala(String dati) {
        this._sala = dati;
    }

    public String getTavolo() {
        return this._tavolo;
    }

    public void setTavolo(String dati) {
        this._tavolo = dati;
    }

    public streamoutresult ToXml() {
        ByteArrayOutputStream fileos = new ByteArrayOutputStream();
        XmlSerializer serializer = Xml.newSerializer();
        try {
            serializer.setOutput(fileos, "ASCII");
            serializer.startDocument(null, Boolean.valueOf(true));
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            serializer.startTag(null, "richiestaconto");
            serializer.startTag(null, "sala");
            serializer.text(this._sala);
            serializer.endTag(null, "sala");
            serializer.startTag(null, "tavolo");
            serializer.text(this._tavolo);
            serializer.endTag(null, "tavolo");
            serializer.endTag(null, "richiestaconto");
            serializer.endDocument();
            serializer.flush();
            fileos.close();
            return new streamoutresult(0, fileos, BuildConfig.FLAVOR);
        } catch (Exception e) {
            streamoutresult ret = new streamoutresult(-1, fileos, e.getMessage());
            Log.e("Exception", "Exception occured in wroting");
            return ret;
        }
    }
}
