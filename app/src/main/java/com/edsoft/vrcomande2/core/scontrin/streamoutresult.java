package com.edsoft.vrcomande2.core.scontrin;

import com.edsoft.vrcomande2.core.dbutility.fresul;

import java.io.OutputStream;

/**
 * Created by pagno on 04/01/2016.
 */

public class streamoutresult extends fresul {
    public OutputStream Dati;

    public streamoutresult(int ret, OutputStream dati, String errormsg) {
        super(ret, errormsg);
        this.Dati = dati;
    }
}