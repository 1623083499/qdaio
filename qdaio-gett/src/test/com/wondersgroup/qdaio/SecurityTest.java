package com.wondersgroup.qdaio;

import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;
import com.wondersgroup.utils.SecureUtils;
import com.wondersgroup.utils.sm4.SM4Utils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

public class SecurityTest {
    @Test
    public void Test01(){
        String secretKey="CVDSgfXSyuwg0SNf";
        SM4Utils sm4=new SM4Utils();
        String cipherText=sm4.getEncStr("薛永辰", secretKey);
        System.out.println("密文："+cipherText);
        String plainText=sm4.getDecStr(cipherText, secretKey);
        System.out.println("明文："+plainText);
    }
    @Test
    public void Test02(){
        String str=SecureUtils.generateRandomKey(16);
        System.out.println(str);
    }
    @Test
    public void Test03(){
        String str=SecureUtils.encryStr("3702422013","vDG5p7z8lYSBgch5");
        System.out.println(str);
        System.out.println(SecureUtils.decryStr(str,"vDG5p7z8lYSBgch5"));
    }
    @Test
    public void sign(){
        String loginname="3702422013";
        String password="96e79218965eb72c92a549dd5a330112";
        String appid="GblWHf10s1";
        String time="20190222110901";
        String str= SecureUtils.signToString(appid+loginname+password,time);
        System.out.println(str);
    }
}
