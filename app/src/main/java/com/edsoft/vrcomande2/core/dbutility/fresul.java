package com.edsoft.vrcomande2.core.dbutility;

/**
 * Created by Emin Demiri on 22/12/2015.
 */

public class fresul {
    public String errMesg;
    public int result;

    public fresul(int ret, String errormsg) {
        this.result = ret;
        this.errMesg = errormsg;
    }
}