package com.wondersgroup.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Base64Util {
    /**
     * base64转码
     * @param str
     * @param chartSetName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encode(String str, String chartSetName) {
        if(chartSetName!=null) {
            try {
                return (new BASE64Encoder()).encodeBuffer(str.getBytes(chartSetName));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
        else return (new BASE64Encoder()).encodeBuffer(str.getBytes());
    }

    /**
     * base64转码
     * @param str
     * @return
     */
    public static String encode(String str)  {
            return (new BASE64Encoder()).encodeBuffer(str.getBytes());
    }

    /**
     * base64解密
     * @param str
     * @return
     */
    public static byte[] decode2byte(String str)  {
        try {
            return (new BASE64Decoder()).decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
    /**
     * base64解密
     * @param str
     * @return
     */
    public static String decode2str(String str){
       byte[] bytes=decode2byte(str);
       return  new String(bytes);
    }
    /**
     * base64解密
     * @param str
     * @return
     */
    public static String decode2str(String str, String chartSetName)  {
       byte[] bytes=decode2byte(str);
        try {
            return  new String(bytes,chartSetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String str="中华人民共和国";
        String encodeStr=encode(str,"gbk");
        System.out.println("encodeStr:"+encodeStr);
        System.out.println("decodeStr:"+decode2str(encodeStr,"gbk"));

        encodeStr=encode(str);
        System.out.println("encodeStr:"+encodeStr);
        System.out.println("decodeStr:"+decode2str(encodeStr));
    }
}
