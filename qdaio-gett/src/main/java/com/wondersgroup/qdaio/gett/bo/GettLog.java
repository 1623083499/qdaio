package com.wondersgroup.qdaio.gett.bo;

import com.wondersgroup.framwork.dao.annotation.Id;
import com.wondersgroup.framwork.dao.annotation.Sequence;
import com.wondersgroup.framwork.dao.annotation.Table;

import java.util.Date;

@Table(name="GETT_LOG")
public class GettLog {
    //	主键id
    private Long id;
    //	用户id
    private String userid;
    //	用户政企平台唯一编号
    private String appid;
    // 业务ID
    private String busiid;
    // 密钥
    private String key;
    //	请入路径
    private String url;
    //	参数
    private String params;
    // 参数（解密后）
    private String clearparams;
    //	http头
    private String headers;
    //	请求时间
    private Date cdate;
    //	执行时间
    private Long usetime;
    //	请求IP
    private String ip;
    // 返回信息（加密前）
    private String premessage;
    // 异常信息（加密后）
    private String message;

    @Id
    @Sequence(name="SEQ_GETT_LOG",sequencename = "SEQ_GETT_LOG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getBusiid() {
        return busiid;
    }

    public void setBusiid(String busiid) {
        this.busiid = busiid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getClearparams() {
        return clearparams;
    }

    public void setClearparams(String clearparams) {
        this.clearparams = clearparams;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public Long getUsetime() {
        return usetime;
    }

    public void setUsetime(Long usetime) {
        this.usetime = usetime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPremessage() {
        return premessage;
    }

    public void setPremessage(String premessage) {
        this.premessage = premessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
