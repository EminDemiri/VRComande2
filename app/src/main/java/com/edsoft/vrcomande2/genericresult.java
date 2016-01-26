package com.edsoft.vrcomande2;

import com.edsoft.vrcomande2.core.dbutility.fresul;

/**
 * Created by pagno on 05/01/2016.
 */

public class genericresult extends fresul {
    public Object Dati;

    public genericresult(int ret, Object dati, String errormsg) {
        super(ret, errormsg);
        this.Dati = dati;
    }
}