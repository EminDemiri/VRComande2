package com.edsoft.vrcomande2.core.networkutility;

import android.util.Log;

import com.edsoft.vrcomande2.BuildConfig;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by pagno on 04/01/2016.
 */

public class ParserSituazioneTavoli {
    ArrayList<TavoloXML> _tavoliOppupati;

    public ParserSituazioneTavoli() {
        this._tavoliOppupati = new ArrayList();
    }

    static void vDebug(String debugString) {
        Log.v("DomParsing", debugString + "\n");
    }

    static void eDebug(String debugString) {
        Log.e("DomParsing", debugString + "\n");
    }

    public ArrayList<TavoloXML> getTavoliOccupati() {
        return this._tavoliOppupati;
    }

    private TavoloXML leggiTavoli(Element c) {
        TavoloXML tavolo = new TavoloXML();
        NodeList tavoloDetails = c.getChildNodes();
        for (int j = 0; j < tavoloDetails.getLength(); j++) {
            Node c1 = tavoloDetails.item(j);
            if (c1.getNodeType() == (short) 1) {
                Element detail = (Element) c1;
                if (detail.getChildNodes().getLength() > 0) {
                    String nodeName = detail.getNodeName();
                    String nodeValue = detail.getFirstChild().getNodeValue();
                    if (nodeName.equals("sala")) {
                        tavolo.sala = nodeValue;
                    }
                    if (nodeName.equals("numero")) {
                        tavolo.numero = Integer.parseInt(nodeValue);
                    }
                    if (nodeName.equals("situazione")) {
                        tavolo.situazione = Integer.parseInt(nodeValue);
                    }
                    if (nodeName.equals("coperti")) {
                        tavolo.coperti = Integer.parseInt(nodeValue);
                    }
                    if (nodeName.equals("orarioPreno")) {
                        tavolo.orariopreno = nodeValue;
                    }
                    if (nodeName.equals("nominativoPreno")) {
                        tavolo.nominativopreno = nodeValue;
                    }
                }
            }
        }
        return tavolo;
    }

    public networkresult parseXml(String xmlUrl) {
        networkresult Ret = new networkresult(0, BuildConfig.FLAVOR, BuildConfig.FLAVOR);
        if (xmlUrl != null) {
            try {
                if (xmlUrl.compareTo(BuildConfig.FLAVOR) != 0) {
                    NodeList nl = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xmlUrl.getBytes("UTF-8"))).getDocumentElement().getChildNodes();
                    for (int i = 0; i < nl.getLength(); i++) {
                        Node c = nl.item(i);
                        if (c.getNodeType() == (short) 1) {
                            Element note = (Element) c;
                            if (note.getNodeName().equals("tavolo")) {
                                this._tavoliOppupati.add(leggiTavoli(note));
                            }
                        }
                    }
                }
            } catch (SAXException e) {
                Ret.result = -1;
                Ret.errMesg = e.toString();
                eDebug(Ret.errMesg);
            } catch (IOException e2) {
                Ret.result = -1;
                Ret.errMesg = e2.toString();
                eDebug(Ret.errMesg);
            } catch (ParserConfigurationException e3) {
                Ret.result = -1;
                Ret.errMesg = e3.toString();
                eDebug(Ret.errMesg);
            } catch (FactoryConfigurationError e4) {
                Ret.result = -1;
                Ret.errMesg = e4.toString();
                eDebug(Ret.errMesg);
            }
        }
        return Ret;
    }
}
