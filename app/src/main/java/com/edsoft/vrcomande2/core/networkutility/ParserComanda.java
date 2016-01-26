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

public class ParserComanda {
    ArrayList<VisuArticoloXML> parsedArticoli;
    double totaleScontrino;

    public ParserComanda() {
        this.totaleScontrino = 0.0d;
        this.parsedArticoli = new ArrayList();
    }

    static void vDebug(String debugString) {
        Log.v("DomParsing", debugString + "\n");
    }

    static void eDebug(String debugString) {
        Log.e("DomParsing", debugString + "\n");
    }

    public double getTotaleScontrino() {
        return this.totaleScontrino;
    }

    public ArrayList<VisuArticoloXML> getParsedArticoli() {
        return this.parsedArticoli;
    }

    private VisuArticoloXML leggiArticolo(Element c) {
        VisuArticoloXML newArt = new VisuArticoloXML();
        NodeList articoloDetails = c.getChildNodes();
        for (int j = 0; j < articoloDetails.getLength(); j++) {
            Node c1 = articoloDetails.item(j);
            if (c1.getNodeType() == (short) 1) {
                Element detail = (Element) c1;
                String nodeName = detail.getNodeName();
                String nodeValue = detail.getFirstChild().getNodeValue();
                vDebug("______Dettaglio:" + nodeName);
                vDebug("______Contenuto Dettaglio:" + nodeValue);
                vDebug(BuildConfig.FLAVOR);
                if (nodeName.equals("Alfa")) {
                    newArt.setAlfaArticolo(nodeValue);
                }
                if (nodeName.equals("Qta")) {
                    newArt.setQtaArticolo(Integer.parseInt(nodeValue));
                }
                if (nodeName.equals("variante")) {
                    NodeList variante = c1.getChildNodes();
                    for (int k = 0; k < variante.getLength(); k++) {
                        Node c2 = variante.item(k);
                        if (c2.getNodeType() == (short) 1) {
                            Element detailVar = (Element) c2;
                            String nodeNameVar = detailVar.getNodeName();
                            String nodeValueVar = detailVar.getFirstChild().getNodeValue();
                            vDebug("______Dettaglio:" + nodeNameVar);
                            vDebug("______Contenuto Dettaglio:" + nodeValueVar);
                            vDebug(BuildConfig.FLAVOR);
                            if (nodeNameVar.equals("alfavar")) {
                                newArt.AddLinkVariante(nodeValueVar);
                            }
                        }
                    }
                }
            }
        }
        return newArt;
    }

    public networkresult parseXml(String xmlUrl) {
        networkresult Ret = new networkresult(0, BuildConfig.FLAVOR, BuildConfig.FLAVOR);
        try {
            Element root = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xmlUrl.getBytes("UTF-8"))).getDocumentElement();
            vDebug("Root element :" + root.getNodeName());
            vDebug(BuildConfig.FLAVOR);
            NodeList notes = root.getChildNodes();
            for (int i = 0; i < notes.getLength(); i++) {
                Node c = notes.item(i);
                if (c.getNodeType() == (short) 1) {
                    Element note = (Element) c;
                    if (note.getNodeName().equals("TotaleScontrino")) {
                        this.totaleScontrino = Double.parseDouble(note.getFirstChild().getNodeValue()) / 100.0d;
                    }
                    if (note.getNodeName().equals("Articolo")) {
                        this.parsedArticoli.add(leggiArticolo(note));
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
        return Ret;
    }
}
