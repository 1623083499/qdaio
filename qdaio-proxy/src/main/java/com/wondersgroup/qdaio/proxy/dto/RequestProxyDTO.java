package com.wondersgroup.qdaio.proxy.dto;


public class RequestProxyDTO {
    private String realPath;
    private String params;
    private String logintoken;

    public String getLogintoken() {
        return logintoken;
    }

    public void setLogintoken(String logintoken) {
        this.logintoken = logintoken;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        try {
            if (params.getBytes("UTF-8").length>1999){
                byte[] pb=params.getBytes("UTF-8");
                this.params=new String(subBytes(pb,1999),"UTF-8");
            }else {
                this.params = params;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public byte[] subBytes(byte[] fromBytes,int length){
        byte[]  rebytes= new byte[length];
        int i=0;
        while(++i<length){
            rebytes[i]=fromBytes[i];
        }
        return  rebytes;
    }
}
