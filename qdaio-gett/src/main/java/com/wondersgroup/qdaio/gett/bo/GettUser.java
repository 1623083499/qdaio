package com.wondersgroup.qdaio.gett.bo;

import com.wondersgroup.framwork.dao.annotation.Id;
import com.wondersgroup.framwork.dao.annotation.Sequence;
import com.wondersgroup.framwork.dao.annotation.Table;

import java.util.Date;

@Table(name="gett_user")
public class GettUser {
    private Long id ;//	number(20)	y		主键索引
    private String userid;//	varchar2(50)	y		用户编号
    private String appid;//	varchar2(20)	y		用户政企平台唯一编号
    private String name	;//varchar2(50)	y		用户名称
    private String key;//	varchar2(50)	y		用户接口加解密密钥
    private String access_token;//	varchar2(50)	y		用户接口接入口令
    private Date ctime;//	date	y		用户创建时间
    private Integer expdate;//	number(8)	y		过期时间
    private String enabled;//	varchar2(1)	y		有效标志（1有效，0无效）
    private String ext1;//	varchar2(50)	y
    private String ext2	;//varchar2(50)	y
    private String ext3;//	varchar2(50)	y

    @Id
    @Sequence(name = "SEQ_GETT_USER",sequencename = "SEQ_GETT_USER")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Integer getExpdate() {
        return expdate;
    }

    public void setExpdate(Integer expdate) {
        this.expdate = expdate;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }
}
