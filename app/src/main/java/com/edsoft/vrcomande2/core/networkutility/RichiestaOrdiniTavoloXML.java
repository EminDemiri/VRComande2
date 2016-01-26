package com.edsoft.vrcomande2.core.networkutility;

import java.util.ArrayList;

/**
 * Created by pagno on 04/01/2016.
 */

public class RichiestaOrdiniTavoloXML {
    protected ArrayList<VisuArticoloXML> elencoarticoli;
    protected double totalescontrino;

    public RichiestaOrdiniTavoloXML() {
        this.totalescontrino = 0.0d;
        this.elencoarticoli = new ArrayList();
    }
}
