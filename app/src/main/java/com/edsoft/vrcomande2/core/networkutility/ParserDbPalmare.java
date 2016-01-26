package com.edsoft.vrcomande2.core.networkutility;

import android.util.Log;

import com.edsoft.vrcomande2.BuildConfig;
import com.edsoft.vrcomande2.core.dbutility.SaleLocale;

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

public class ParserDbPalmare {
    ArrayList<RepartiXML> parsedData;
    ArrayList<InfoCdpXML> parsedInfoCdp;
    ArrayList<MsgForCdpXML> parsedMsgForCdp;
    ArrayList<OperatoriXML> parsedOperatori;
    ArrayList<SaleXML> parsedSale;
    ArrayList<VariantiXML> parsedVarianti;

    public ParserDbPalmare() {
        this.parsedData = new ArrayList();
        this.parsedSale = new ArrayList();
        this.parsedVarianti = new ArrayList();
        this.parsedOperatori = new ArrayList();
        this.parsedInfoCdp = new ArrayList();
        this.parsedMsgForCdp = new ArrayList();
    }

    static void vDebug(String debugString) {
        Log.v("DomParsing", debugString + "\n");
    }

    static void eDebug(String debugString) {
        Log.e("DomParsing", debugString + "\n");
    }

    public ArrayList<OperatoriXML> getParsedOperatori() {
        return this.parsedOperatori;
    }

    public ArrayList<RepartiXML> getParsedReparti() {
        return this.parsedData;
    }

    public ArrayList<SaleXML> getParsedSale() {
        return this.parsedSale;
    }

    public ArrayList<VariantiXML> getParsedVarianti() {
        return this.parsedVarianti;
    }

    public ArrayList<InfoCdpXML> getparsedInfoCdp() {
        return this.parsedInfoCdp;
    }

    public ArrayList<MsgForCdpXML> getparsedMsgForCdp() {
        return this.parsedMsgForCdp;
    }

    private OperatoriXML leggiOperatore(Element c) {
        OperatoriXML newOp = new OperatoriXML();
        NodeList operatoreDetails = c.getChildNodes();
        for (int j = 0; j < operatoreDetails.getLength(); j++) {
            Node c1 = operatoreDetails.item(j);
            if (c1.getNodeType() == (short) 1) {
                Element detail = (Element) c1;
                String nodeName = detail.getNodeName();
                String nodeValue = detail.getFirstChild().getNodeValue();
                if (nodeName.equals("codop")) {
                    newOp.setCodOp(nodeValue);
                }
                if (nodeName.equals("alfaop")) {
                    newOp.setAlfaOp(nodeValue);
                }
                if (nodeName.equals("loginop")) {
                    newOp.setLoginOp(nodeValue);
                }
                if (nodeName.equals("pwdop")) {
                    newOp.setPwdOp(nodeValue);
                }
            }
        }
        return newOp;
    }

    private VariantiXML leggiVariante(Element c) {
        VariantiXML newVariante = new VariantiXML();
        NodeList varianteDetails = c.getChildNodes();
        for (int j = 0; j < varianteDetails.getLength(); j++) {
            Node c1 = varianteDetails.item(j);
            if (c1.getNodeType() == (short) 1) {
                Element detail = (Element) c1;
                String nodeName = detail.getNodeName();
                String nodeValue = detail.getFirstChild().getNodeValue();
                vDebug("______Dettaglio:" + nodeName);
                vDebug("______Contenuto Dettaglio:" + nodeValue);
                vDebug(BuildConfig.FLAVOR);
                if (nodeName.equals("codvar")) {
                    newVariante.setCodVariante(nodeValue);
                }
                if (nodeName.equals("alfavar")) {
                    newVariante.setAlfaVariante(nodeValue);
                }
                if (nodeName.equals("prezzovar")) {
                    newVariante.setPrezzoVarianteInt(Integer.parseInt(nodeValue));
                }
                if (nodeName.equals("generalvar")) {
                    newVariante.setVariantePerTutti(Boolean.parseBoolean(nodeValue));
                }
            }
        }
        return newVariante;
    }

    private SaleXML leggiSala(Element c) {
        SaleXML newSala = new SaleXML();
        NodeList salaDetails = c.getChildNodes();
        for (int j = 0; j < salaDetails.getLength(); j++) {
            Node c1 = salaDetails.item(j);
            if (c1.getNodeType() == (short) 1) {
                Element detail = (Element) c1;
                String nodeName = detail.getNodeName();
                String nodeValue = detail.getFirstChild().getNodeValue();
                if (nodeName.equals("codsala")) {
                    newSala.setCodSala(nodeValue);
                }
                if (nodeName.equals(SaleLocale.ALFASALA)) {
                    newSala.setAlfaSala(nodeValue);
                }
                if (nodeName.equals("numtavoli")) {
                    newSala.setNumeroTavoli(Integer.parseInt(nodeValue));
                }
                if (nodeName.equals(SaleLocale.DELTATAVOLI)) {
                    newSala.setDeltaTavoli(Integer.parseInt(nodeValue));
                }
            }
        }
        return newSala;
    }

    private RepartiXML leggiReparto(Element c) {
        RepartiXML newRep = new RepartiXML();
        NodeList noteDetails = c.getChildNodes();
        for (int j = 0; j < noteDetails.getLength(); j++) {
            Node c1 = noteDetails.item(j);
            if (c1.getNodeType() == (short) 1) {
                Element detail = (Element) c1;
                String nodeName = detail.getNodeName();
                String nodeValue = detail.getFirstChild().getNodeValue();
                vDebug("______Dettaglio:" + nodeName);
                vDebug("______Contenuto Dettaglio:" + nodeValue);
                vDebug(BuildConfig.FLAVOR);
                if (nodeName.equals("codrep")) {
                    newRep.setCodRep(nodeValue);
                }
                if (nodeName.equals("alfarep")) {
                    newRep.setAlfaRep(nodeValue);
                }
                if (nodeName.equals("Articolo")) {
                    NodeList articolo = c1.getChildNodes();
                    ArticoliXML newArt = new ArticoliXML();
                    for (int k = 0; k < articolo.getLength(); k++) {
                        Node c2 = articolo.item(k);
                        if (c2.getNodeType() == (short) 1) {
                            Element detailArt = (Element) c2;
                            String nodeNameArt = detailArt.getNodeName();
                            String nodeValueArt = detailArt.getFirstChild().getNodeValue();
                            vDebug("______Dettaglio:" + nodeNameArt);
                            vDebug("______Contenuto Dettaglio:" + nodeValueArt);
                            vDebug(BuildConfig.FLAVOR);
                            if (nodeNameArt.equals("codart")) {
                                newArt.setCodArt(nodeValueArt);
                            }
                            if (nodeNameArt.equals("alfaart")) {
                                newArt.setAlfaArt(nodeValueArt);
                            }
                            if (nodeNameArt.equals("LinkVariante")) {
                                newArt.AddLinkVariante(nodeValueArt);
                            }
                            if (nodeNameArt.equals("prezzoart")) {
                                newArt.setPrezzoArt(Double.parseDouble(nodeValueArt) / 100.0d);
                            }
                            if (nodeNameArt.equals("ivaart")) {
                                newArt.setIvaArtInt(Integer.parseInt(nodeValueArt));
                            }
                        }
                    }
                    newArt.setPosizione(newRep.getElencoArt().size() + 1);
                    newRep.AddArt(newArt);
                }
            }
        }
        vDebug(BuildConfig.FLAVOR);
        newRep.setPosizione(this.parsedData.size());
        return newRep;
    }

    private InfoCdpXML leggiCDP(Element c) {
        InfoCdpXML newCdp = new InfoCdpXML();
        NodeList cdpDetails = c.getChildNodes();
        for (int j = 0; j < cdpDetails.getLength(); j++) {
            Node c1 = cdpDetails.item(j);
            if (c1.getNodeType() == (short) 1) {
                Element detail = (Element) c1;
                String nodeName = detail.getNodeName();
                String nodeValue = detail.getFirstChild().getNodeValue();
                if (nodeName.equals("Codice")) {
                    newCdp.setCodCDP(nodeValue);
                }
                if (nodeName.equals("AlfaCdp")) {
                    newCdp.setAlfaCDP(nodeValue);
                }
            }
        }
        return newCdp;
    }

    private MsgForCdpXML leggiMagCDP(Element c) {
        MsgForCdpXML newMsgCdp = new MsgForCdpXML();
        NodeList cdpDetails = c.getChildNodes();
        for (int j = 0; j < cdpDetails.getLength(); j++) {
            Node c1 = cdpDetails.item(j);
            if (c1.getNodeType() == (short) 1) {
                Element detail = (Element) c1;
                String nodeName = detail.getNodeName();
                String nodeValue = detail.getFirstChild().getNodeValue();
                if (nodeName.equals("DatiMSG")) {
                    String[] arr = nodeValue.split("\\|");
                    if (arr.length >= 3) {
                        newMsgCdp.setCodMSG(arr[0]);
                        newMsgCdp.setTestoMSG(arr[1]);
                        newMsgCdp.setCodCDP(arr[2]);
                    }
                }
            }
        }
        return newMsgCdp;
    }

    public networkresult parseXml(String xmlUrl) {
        networkresult Ret = new networkresult(0, BuildConfig.FLAVOR, BuildConfig.FLAVOR);
        try {
            ArrayList<String> VariantiPerTutti = new ArrayList();
            Element root = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xmlUrl.getBytes("UTF-8"))).getDocumentElement();
            vDebug("Root element :" + root.getNodeName());
            vDebug(BuildConfig.FLAVOR);
            NodeList notes = root.getChildNodes();
            for (int i = 0; i < notes.getLength(); i++) {
                Node c = notes.item(i);
                if (c.getNodeType() == (short) 1) {
                    Element note = (Element) c;
                    if (note.getNodeName().equals("Operatore")) {
                        this.parsedOperatori.add(leggiOperatore(note));
                    }
                    if (note.getNodeName().equals("VarianteArticolo")) {
                        VariantiXML appov = leggiVariante(note);
                        this.parsedVarianti.add(appov);
                        if (appov.variantepertutti) {
                            VariantiPerTutti.add(appov.codvariante);
                        }
                    }
                    if (note.getNodeName().equals("Sala")) {
                        this.parsedSale.add(leggiSala(note));
                    }
                    if (note.getNodeName().equals("Reparto")) {
                        this.parsedData.add(leggiReparto(note));
                    }
                    if (note.getNodeName().equals("InfoCDP")) {
                        this.parsedInfoCdp.add(leggiCDP(note));
                    }
                    if (note.getNodeName().equals("MsgCucina")) {
                        this.parsedMsgForCdp.add(leggiMagCDP(note));
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
