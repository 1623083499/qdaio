package com.wondersgroup.qdaio.gett.dto;

import com.alibaba.fastjson.JSON;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上下文Dto
 */
public class ContextDto {
    private String appid;
    private String loginname;
    private String password;
    private String key;
    private String time;
    private String sign;
    private String access_token;
    private String params;
    private String busiid;
    // 入参明文
    private String paramInCipherText;
    // 入参密文
    private String paramInClearText;
    // 出参明文
    private String paramOutClearText;
    // 出参密文
    private String paramOutCipherText;
    // 日志
    private GettLogDto gettLogDto;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParamInCipherText() {
        return paramInCipherText;
    }

    public void setParamInCipherText(String paramInCipherText) {
        this.paramInCipherText = paramInCipherText;
    }

    public String getParamInClearText() {
        return paramInClearText;
    }

    public void setParamInClearText(String paramInClearText) {
        this.paramInClearText = paramInClearText;
    }

    public String getParamOutClearText() {
        return paramOutClearText;
    }

    public void setParamOutClearText(String paramOutClearText) {
        this.paramOutClearText = paramOutClearText;
    }

    public String getParamOutCipherText() {
        return paramOutCipherText;
    }

    public void setParamOutCipherText(String paramOutCipherText) {
        this.paramOutCipherText = paramOutCipherText;
    }

    public GettLogDto getGettLogDto() {
        return gettLogDto;
    }

    public void setGettLogDto(GettLogDto gettLogDto) {
        this.gettLogDto = gettLogDto;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getBusiid() {
        return busiid;
    }

    public void setBusiid(String busiid) {
        this.busiid = busiid;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
