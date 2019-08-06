package com.wondersgroup.qdaio.proxy.bo;

import com.wb.jdbcutils.annos.Column;
import com.wb.jdbcutils.annos.Sequence;
import com.wb.jdbcutils.annos.Table;

import java.util.Date;

@Table(name="net_proxy_log")
public class NetProxyLog {
    private Long id;//ID	NUMBER(16)	Y		ID
   private String logintoken;//LOGINTOKEN	VARCHAR2(20)	Y		用户ID
   private Date ctime;// CTIME	DATE	Y		创建时间
   private String url;// URL	VARCHAR2(200)	Y		路径
   private String params;// PARAMS	VARCHAR2(1000)	Y		参数
   private Long usetime;//  USETIME	NUMBER(12)	Y		耗时
   private String errormsg;// ERRORMSG
    @Column(id = true,name = "id")
    @Sequence(name = "SEQUENCE",sequencename = "SEQ_net_proxy_log")
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "logintoken")
    public String getLogintoken() {
        return logintoken;
    }

    public void setLogintoken(String logintoken) {
        this.logintoken = logintoken;
    }
    @Column(name = "ctime")
    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url.length()>400)
            this.url=url.substring(0,399);
        else
            this.url = url;
    }
    @Column(name = "params")
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
    @Column(name = "usetime")
    public Long getUsetime() {
        return usetime;
    }

    public void setUsetime(Long usetime) {
        this.usetime = usetime;
    }

    @Column(name="errormsg")
    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        if (errormsg.length()>400)
            this.errormsg=errormsg.substring(0,400);
        else
        this.errormsg = errormsg;
    }
}
