package com.edsoft.vrcomande2.core.networkutility;

import com.edsoft.vrcomande2.core.dbutility.fresul;

/**
 * Created by pagno on 04/01/2016.
 */

public class networkresult extends fresul {
    public String Dati;

    public networkresult(int ret, String dati, String errormsg) {
        super(ret, errormsg);
        this.Dati = dati;
    }
}
