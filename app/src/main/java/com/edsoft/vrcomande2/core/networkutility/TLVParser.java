package com.edsoft.vrcomande2.core.networkutility;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

import com.edsoft.vrcomande2.BuildConfig;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by Emin Demiri on 04/01/2016.
 */

public class TLVParser {
    public static String toUTF8(String isoString) {
        String utf8String = isoString;
        if (isoString == null || isoString.equals(BuildConfig.FLAVOR)) {
            return utf8String;
        }
        try {
            return new String(isoString.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException is: " + e.getMessage());
            return isoString;
        }
    }

    public static final byte[] intToByteArray(int value) {
        return new byte[]{(byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value};
    }

    public static void reverse(byte[] array) {
        if (array != null) {
            int j = array.length - 1;
            for (int i = 0; j > i; i++) {
                byte tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                j--;
            }
        }
    }

    public static byte[] readTLV(InputStream tlv, int tag) {
        if (tlv == null) {
            throw new IllegalArgumentException("Invalid TLV");
        }
        byte[] vals = null;
        try {
            int c = tlv.read();
            if (c != 1 && c == tag) {
                ByteBuffer bb = ByteBuffer.allocate(4);
                c = tlv.read();
                if (c != -1) {
                    bb.put((byte) c);
                }
                c = tlv.read();
                if (c != -1) {
                    bb.put((byte) c);
                }
                c = tlv.read();
                if (c != -1) {
                    bb.put((byte) c);
                }
                c = tlv.read();
                if (c != -1) {
                    bb.put((byte) c);
                }
                int Len = bb.getInt(0);
                vals = new byte[Len];
                int Residuo = Len;
                int MaxRead = AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT;
                if (AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT > Residuo) {
                    MaxRead = Residuo;
                }
                int offSet = 0;
                byte[] bytes = new byte[Len];
                while (Residuo > 0) {
                    while (true) {
                        int len = tlv.read(bytes, 0, MaxRead);
                        if (len > 0) {
                            System.arraycopy(bytes, 0, vals, offSet, len);
                            offSet += len;
                            Residuo -= len;
                            if (MaxRead > Residuo) {
                                MaxRead = Residuo;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            String errmsg = e.getMessage();
            e.printStackTrace();
        }
        return vals;
    }

    public static byte[][] readTLV(String tlvHexString, int tag) throws Throwable {
        return readTLV(hexStringToByteArray(tlvHexString), tag);
    }

    public static byte[][] readTLV(byte[] tlv, int tag) throws Throwable {
        Throwable th;
        if (tlv == null || tlv.length < 1) {
            throw new IllegalArgumentException("Invalid TLV");
        }
        ArrayList al = new ArrayList();
        ByteArrayInputStream is = null;
        try {
            ByteArrayInputStream is2 = new ByteArrayInputStream(tlv);
            while (true) {
                try {
                    int c = is2.read();
                    if (c == -1) {
                        break;
                    } else if (c == tag) {
                        ByteBuffer bb = ByteBuffer.allocate(4);
                        c = is2.read();
                        if (c != -1) {
                            bb.put((byte) c);
                        }
                        c = is2.read();
                        if (c != -1) {
                            bb.put((byte) c);
                        }
                        c = is2.read();
                        if (c != -1) {
                            bb.put((byte) c);
                        }
                        c = is2.read();
                        if (c != -1) {
                            bb.put((byte) c);
                        }
                        int Len = bb.getInt(0);
                        byte[] value = new byte[Len];
                        is2.read(value, 0, Len);
                        al.add(value);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    is = is2;
                }
            }
            if (is2 != null) {
                try {
                    is2.close();
                } catch (IOException e) {
                }
            }
            byte[][] vals = new byte[al.size()][];
            al.toArray(vals);
            return vals;
        } catch (Throwable th3) {
            th = th3;
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e2) {
                }
            }
            throw th;
        }
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[(len / 2)];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static int byteArrayToInt(byte[] b, int offset) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            value += (b[i + offset] & MotionEventCompat.ACTION_MASK) << ((3 - i) * 8);
        }
        return value;
    }
}
